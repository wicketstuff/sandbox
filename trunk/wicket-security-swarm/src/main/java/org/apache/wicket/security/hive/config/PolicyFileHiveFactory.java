/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.security.hive.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.security.hive.BasicHive;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.SimpleCachingHive;
import org.apache.wicket.security.hive.authorization.EverybodyPrincipal;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.authorization.Principal;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.hive.authorization.permissions.DataPermission;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory to produce Hive's based on policy files. This factory is designed
 * to make a best effort when problems occur. Meaning any malconfiguration in
 * the policy file is logged and then skipped. This factory accepts the
 * following policy format<br>
 * 
 * <pre>
 * grant[ principal &lt;principal class&gt; &quot;name&quot;]
 * {
 * permission &lt;permission class&gt; &quot;name&quot;,[ &quot;actions&quot;];
 * };
 * </pre>
 * 
 * where [] denotes an optional block, &lt;&gt; denotes a classname.<br>
 * For brevity aliases are allowed in / for classnames and permission-,
 * principal names. An alias takes the form of ${foo} the alias (the part
 * between {}) must be at least 1 character long and must not contain one of the
 * following 4 characters "${} For example: permission ${ComponentPermission}
 * "myname.${foo}", "render";<br>
 * Note that:
 * <ul>
 * <li>names and action must be quoted</li>
 * <li>a permission statement must be on a single line and terminated by a ;</li>
 * <li>the grant block must be terminated by a ;</li>
 * <li>if you don't specify a principal after the grant statement, everybody
 * will be given those permissions automagically</li>
 * <li>using double quotes '"' is not allowed, instead use a single quote '''</li>
 * <li>aliases may be chained but not nested, so ${foo}${bar} is valid but not
 * ${foo${bar}}</li>
 * <li>aliases are not allowed in actions or reserved words (grant, permission,
 * principal)</li>
 * <li>aliases are case sensitive</li>
 * By default the following aliases are available ComponentPermission,
 * DataPermission and AllPermissions for
 * org.apache.wicket.security.hive.authorization.permissions.ComponentPermission,
 * org.apache.wicket.security.hive.authorization.permissions.DataPermission and
 * org.apache.wicket.security.hive.authorization.permissions.AllPermissions
 * respectively.
 * 
 * @author marrink
 */
public class PolicyFileHiveFactory implements HiveFactory
{
	private static final Logger log = LoggerFactory.getLogger(PolicyFileHiveFactory.class);

	// TODO use JAAS to check for enough rights
	private Set policyFiles;
	private Set inputStreams;
	private Set inputReaders;

	private static final Pattern principalPattern = Pattern
			.compile("\\s*(?:grant(?:\\s+principal\\s+([^\"]+)\\s+\"([^\"]+)\")?){1}\\s*");

	private static final Pattern permissionPattern = Pattern
			.compile("\\s*(?:permission\\s+([^\",]+?)\\s+(?:(?:\"([^\"]+)\"){1}?(?:\\s*,\\s*\"([^\"]*)\")?)?\\s*;){1}\\s*");

	private static final Pattern aliasPattern = Pattern.compile("(\\$\\{[^\"\\{\\}\\$]+?\\})+?");

	private static final Class[] stringArgs1 = new Class[] { String.class };

	private static final Class[] stringArgs2 = new Class[] { String.class, String.class };

	private Map aliases = new HashMap();

	private boolean useHiveCache = true;

	private boolean closeInputStreams = true;

	private int currentLineNr;

	/**
	 * 
	 * Constructs a new factory that builds a Hive out of one (1) or more policy
	 * files. It registers an alias for {@link ComponentPermission} and
	 * {@link DataPermission}.
	 */
	public PolicyFileHiveFactory()
	{
		policyFiles = new HashSet();
		inputStreams = new HashSet();
		inputReaders = new HashSet();
		setAlias("ComponentPermission",
				"org.apache.wicket.security.hive.authorization.permissions.ComponentPermission");
		setAlias("DataPermission",
				"org.apache.wicket.security.hive.authorization.permissions.DataPermission");
		setAlias("AllPermissions",
				"org.apache.wicket.security.hive.authorization.permissions.AllPermissions");

	}

	/**
	 * Adds a new Hive policy file to this factory. The file is not used until
	 * {@link #createHive()} is executed. Url's are always retained for possible
	 * re-use.
	 * 
	 * @param file
	 * @return true, if the file was added, false otherwise
	 */
	public final boolean addPolicyFile(URL file)
	{
		return policyFiles.add(file);
	}

	/**
	 * A readonly view of the policy files added to this factory.
	 * 
	 * @return a set containing {@link URL}'s
	 */
	protected final Set getPolicyFiles()
	{
		return Collections.unmodifiableSet(policyFiles);
	}

	/**
	 * Adds a new Hive policy to this factory. The stream is not read until
	 * {@link #createHive()} is executed. Depending on the state of the flag
	 * {@link #isCloseInputStreams()} the stream is closed or left untouched
	 * after it is read. In all cases the stream is removed from the factory
	 * after being read. The format of the inputstream must be the same as that
	 * of a regular policy file.
	 * 
	 * @param stream
	 * @return true, if the stream was added, false otherwise
	 */
	public final boolean addStream(InputStream stream)
	{
		return inputStreams.add(stream);
	}

	/**
	 * A readonly view of the streams added to this factory.
	 * 
	 * @return a set containing {@link InputStream}s
	 */
	protected final Set getStreams()
	{
		return Collections.unmodifiableSet(inputStreams);
	}

	/**
	 * Adds a new Hive policy to this factory. The reader is not read until
	 * {@link #createHive()} is executed. Depending on the state of the flag
	 * {@link #isCloseInputStreams()} the reader is closed or left untouched
	 * after it is read. In all cases the reader is removed from the factory
	 * after being read. The format of the inputstream must be the same as that
	 * of a regular policy file.
	 * 
	 * @param input
	 * @return true, if the reader was added, false otherwise
	 */
	public final boolean addReader(Reader input)
	{
		return inputReaders.add(input);
	}

	/**
	 * A readonly view of the readers added to this factory.
	 * 
	 * @return a set containing {@link Reader}s
	 */
	protected final Set getReaders()
	{
		return Collections.unmodifiableSet(inputReaders);
	}

	/**
	 * Returns the value of the alias.
	 * 
	 * @param key
	 *            the part between the ${}
	 * @return the value or null if that alias does not exist
	 */
	public final String getAlias(String key)
	{
		return (String)aliases.get(key);
	}

	/**
	 * Sets the value for an alias, overwrites any existing alias with the same
	 * name
	 * 
	 * @param key
	 *            the part between the ${}
	 * @param value
	 *            the value the alias is replaced with at hive creation time.
	 * @return the previous value or null
	 */
	public final String setAlias(String key, String value)
	{
		return (String)aliases.put(key, value);
	}

	/**
	 * The current line being read.
	 * 
	 * @return the line number
	 */
	protected final int getCurrentLineNr()
	{
		return currentLineNr;
	}

	/**
	 * Checks raw input for aliases and then replaces those with the registered
	 * values. Note that if the encountered alias is not allowed it is left
	 * unresolved and will probably later in the creation of the factory be
	 * skipped or cause a failure.
	 * 
	 * @param raw
	 *            the raw input
	 * @return the input with as much aliases resolved
	 */
	private String resolveAliases(String raw)
	{
		Matcher m = aliasPattern.matcher(raw);
		AppendingStringBuffer buff = new AppendingStringBuffer(raw.length() + 30); // guess
		int index = 0;
		while (m.find())
		{
			if (m.start() > index)
				buff.append(raw.substring(index, m.start()));
			else if (m.start() == index)
			{
				String key = raw.substring(m.start() + 2, m.end() - 1);
				String alias = getAlias(key);
				if (alias == null) // will probably be skipped later on
				{
					alias = key;
					if (log.isDebugEnabled())
						log.debug("failed to resolve alias: " + key);
				}
				else if (log.isDebugEnabled())
					log.debug("resolved alias: " + key + " to " + alias);
				buff.ensureCapacity(buff.length() + alias.length());
				buff.append(alias);
			}
			else
				// should not happen
				throw new IllegalStateException("These aliases are not supported: " + raw);
			index = m.end();
		}
		if (index < raw.length())
			buff.append(raw.substring(index, raw.length()));
		String temp = buff.toString();
		if (temp.indexOf("${") >= 0)
			throw new IllegalStateException("Nesting aliases is not supported: " + raw);
		return temp;
	}

	/**
	 * This method is not thread safe.
	 * 
	 * @see org.apache.wicket.security.hive.config.HiveFactory#createHive()
	 */
	public Hive createHive()
	{
		BasicHive hive;
		if (isUsingHiveCache())
			hive = new SimpleCachingHive();
		else
			hive = new BasicHive();
		boolean readAnything = false;
		Iterator it = policyFiles.iterator();
		while (it.hasNext())
		{
			readAnything = true;
			URL file = (URL)it.next();
			try
			{
				readPolicyFile(file, hive);
			}
			catch (IOException e)
			{
				log.error("Could not read from " + file, e);
			}
		}
		it = inputStreams.iterator();
		while (it.hasNext())
		{
			readAnything = true;
			InputStream stream = (InputStream)it.next();
			try
			{
				readInputStream(stream, hive);
			}
			catch (IOException e)
			{
				log.error("Could not read from stream", e);
			}
		}
		inputStreams.clear();
		it = inputReaders.iterator();
		while (it.hasNext())
		{
			readAnything = true;
			Reader stream = (Reader)it.next();
			try
			{
				readInputReader(stream, hive);
			}
			catch (IOException e)
			{
				log.error("Could not read from reader", e);
			}
		}
		inputReaders.clear();
		if (!readAnything)
			log.warn("No policyFiles or inputstreams were added to the factory!");
		return hive;
	}

	/**
	 * Reads principals and permissions from a file, found items are added to
	 * the hive.
	 * 
	 * @param file
	 *            the file to read
	 * @param hive
	 *            the hive where found items are appended to.
	 * @throws IOException
	 *             if a problem occurs while reading the file
	 * @see #readStream(InputStream, BasicHive)
	 */
	protected final void readPolicyFile(URL file, BasicHive hive) throws IOException
	{
		notifyFileStart(file);
		InputStream stream = null;
		try
		{
			stream = file.openStream();
			readStream(stream, hive);
		}
		finally
		{
			notifyFileClose(file, currentLineNr);
			if (stream != null)
				stream.close();
		}
	}

	/**
	 * Reads principals and permissions from a stream, found items are added to
	 * the hive. The stream is closed depending on the
	 * {@link #isCloseInputStreams()} flag.
	 * 
	 * @param stream
	 *            the stream to read
	 * @param hive
	 *            the hive where found items are appended to.
	 * @throws IOException
	 *             if a problem occurs while reading the file
	 * @see #isCloseInputStreams()
	 * @see #setCloseInputStreams(boolean)
	 * @see #readStream(InputStream, BasicHive)
	 */
	protected final void readInputStream(InputStream stream, BasicHive hive) throws IOException
	{
		notifyStreamStart(stream);
		try
		{
			readStream(stream, hive);
		}
		finally
		{
			notifyStreamEnd(stream, currentLineNr);
			if (closeInputStreams)
				stream.close();

		}
	}

	/**
	 * Reads principals and permissions from a {@link Reader}, found items are
	 * added to the hive. The reader is closed depending on the
	 * {@link #isCloseInputStreams()} flag.
	 * 
	 * @param input
	 *            the reader to read
	 * @param hive
	 *            the hive where found items are appended to.
	 * @throws IOException
	 *             if a problem occurs while reading the file
	 * @see #isCloseInputStreams()
	 * @see #setCloseInputStreams(boolean)
	 * @see #readStream(InputStream, BasicHive)
	 */
	protected final void readInputReader(Reader input, BasicHive hive) throws IOException
	{
		notifyReaderStart(input);
		try
		{
			readReader(input, hive);
		}
		finally
		{
			notifyReaderEnd(input, currentLineNr);
			if (closeInputStreams)
				input.close();

		}
	}

	/**
	 * Reads principals and permissions from a {@link Reader}, found items are
	 * added to the hive. Subclasses should override this method or
	 * {@link #readStream(InputStream, BasicHive)} if they want do do something
	 * different from the default. No need to call the notifyMethods as that is
	 * handled by {@link #readInputReader(Reader, BasicHive)} and
	 * {@link #readInputStream(InputStream, BasicHive)} respectively. Default
	 * implementation is to call {@link #read(Reader, BasicHive)}. This method
	 * never closes the reader.
	 * 
	 * @param input
	 * @param hive
	 * @throws IOException
	 */
	protected void readReader(Reader input, BasicHive hive) throws IOException
	{
		read(input, hive);
	}

	/**
	 * Notifies that the stream will be read no further. Typically this is
	 * because the end of the stream is reached but it is also called when an
	 * exception occurs while reading the stream.
	 * 
	 * @param stream
	 * @param lineNr
	 *            number of lines processed
	 */
	protected void notifyStreamEnd(InputStream stream, int lineNr)
	{
	}

	/**
	 * Notifies that a reader is about to be read.
	 * 
	 * @param input
	 *            the reader
	 */
	protected void notifyReaderStart(Reader input)
	{
	}

	/**
	 * Notifies that the {@link Reader} will be read no further. Typically this
	 * is because the end of the stream is reached but it is also called when an
	 * exception occurs while reading the reader.
	 * 
	 * @param input
	 * @param lineNr
	 *            number of lines processed
	 */
	protected void notifyReaderEnd(Reader input, int lineNr)
	{
	}

	/**
	 * Notifies that a stream is about to be read.
	 * 
	 * @param stream
	 *            the stream
	 */
	protected void notifyStreamStart(InputStream stream)
	{
	}

	/**
	 * Reads principals and permissions from a {@link InputStream} , found items
	 * are added to the hive. This method never closes the input stream.
	 * 
	 * @param input
	 * @param hive
	 * @throws IOException
	 */
	protected void readStream(InputStream input, BasicHive hive) throws IOException
	{
		read(new InputStreamReader(input), hive);
	}

	/**
	 * Reads principals and permissions from a {@link Reader} , found items are
	 * added to the hive. This method never closes the reader.
	 * 
	 * @param input
	 * @param hive
	 * @throws IOException
	 */
	protected final void read(Reader input, BasicHive hive) throws IOException
	{
		BufferedReader reader;
		if (input instanceof BufferedReader)
			reader = (BufferedReader)input;
		else
			reader = new BufferedReader(input);

		boolean inPrincipalBlock = false;
		Principal principal = null;
		Set permissions = null;
		currentLineNr = 0;
		String line = reader.readLine();
		while (line != null)
		{
			currentLineNr++;
			if (inPrincipalBlock)
			{
				String trim = line.trim();
				boolean startsWith = trim.startsWith("{");
				if (startsWith)
				{
					if (permissions != null || principal == null)
						skipIllegalPrincipal(currentLineNr, principal, permissions);
					permissions = new HashSet();
				}
				boolean endsWith = trim.endsWith("};");
				if (endsWith)
				{
					inPrincipalBlock = false;
					if (permissions != null && permissions.size() > 0)
						hive.addPrincipal(principal, permissions);
					else
						skipEmptyPrincipal(currentLineNr, principal);

					permissions = null;
					principal = null;
				}
				if (!(startsWith || endsWith))
				{
					Matcher m = permissionPattern.matcher(line);
					if (m.matches())
					{
						String classname = m.group(1);
						if (classname == null)
						{
							skipPermission(currentLineNr, null);
							line = reader.readLine();
							continue;
						}
						Class permissionClass = null;
						try
						{
							permissionClass = Class.forName(resolveAliases(classname));
							if (!Permission.class.isAssignableFrom(permissionClass))
							{
								skipPermission(currentLineNr, permissionClass);
								line = reader.readLine();
								continue;
							}
							String name = resolveAliases(m.group(2));
							String actions = m.group(3);
							Constructor constructor = null;
							Class[] args = stringArgs2;
							if (actions == null)
								args = stringArgs1;
							try
							{
								constructor = permissionClass.getConstructor(args);
							}
							catch (SecurityException e)
							{
								log.error("No valid constructor found for "
										+ permissionClass.getName(), e);
							}
							catch (NoSuchMethodException e)
							{
								log.error("No valid constructor found for "
										+ permissionClass.getName(), e);
							}
							if (constructor == null)
							{
								skipPermission(currentLineNr, permissionClass, args);
								line = reader.readLine();
								continue;
							}
							Object[] argValues = new Object[] { name, actions };
							if (actions == null)
								argValues = new Object[] { name };
							Permission temp;
							try
							{
								temp = (Permission)constructor.newInstance(argValues);
							}
							catch (Exception e)
							{
								skipPermission(currentLineNr, permissionClass, argValues, e);
								line = reader.readLine();
								continue;
							}
							if (permissions == null)
							{
								skipIllegalPermission(currentLineNr, principal, temp);
								line = reader.readLine();
								continue;
							}
							if (!permissions.add(temp))
								skipPermission(currentLineNr, principal, temp);
							else
								notifyPermission(currentLineNr, principal, temp);

						}
						catch (ClassNotFoundException e)
						{
							skipPermission(currentLineNr, classname, e);
							line = reader.readLine();
							continue;
						}
					}
					else
					{
						// skip this line
						skipLine(currentLineNr, line);
					}
				}
			}
			else
			{
				Matcher m = principalPattern.matcher(line);
				if (m.matches())
				{
					String classname = m.group(1);
					if (classname == null)
						principal = new EverybodyPrincipal();
					else
					{
						Class principalClass = null;
						try
						{
							principalClass = Class.forName(resolveAliases(classname));
							if (!Principal.class.isAssignableFrom(principalClass))
							{
								skipPrincipalClass(currentLineNr, principalClass);
								line = reader.readLine();
								continue;
							}
							Constructor constructor = null;
							try
							{
								constructor = principalClass.getConstructor(stringArgs1);
							}
							catch (SecurityException e)
							{
								log.error("No valid constructor found for "
										+ principalClass.getName(), e);
							}
							catch (NoSuchMethodException e)
							{
								log.error("No valid constructor found for "
										+ principalClass.getName(), e);
							}
							if (constructor == null)
							{
								skipPrincipal(currentLineNr, principalClass);
								line = reader.readLine();
								continue;
							}
							try
							{
								principal = (Principal)constructor
										.newInstance(new Object[] { resolveAliases(m.group(2)) });
							}
							catch (Exception e)
							{
								skipPrincipal(currentLineNr, principalClass, e);
								line = reader.readLine();
								continue;
							}

						}
						catch (ClassNotFoundException e)
						{
							skipPrincipalClass(currentLineNr, classname, e);
							line = reader.readLine();
							continue;
						}
					}
					notifyOfPrincipal(currentLineNr, principal);
					inPrincipalBlock = true;
				}
				else
				{
					skipLine(currentLineNr, line);
				}
			}
			line = reader.readLine();
		}
		// catch forgotten closeStatement
		if (inPrincipalBlock)
		{
			warnUnclosedPrincipalBlock(principal, currentLineNr);
			inPrincipalBlock = false;
			if (permissions != null && permissions.size() > 0)
				hive.addPrincipal(principal, permissions);
			else
				skipEmptyPrincipal(currentLineNr, principal);

			permissions = null;
			principal = null;
		}
	}

	/**
	 * Warning when the last principal of a file is not properly closed.
	 * Although the principal is automatically closed the user should complete
	 * the block statement for the principal.
	 * 
	 * @param principal
	 * @param lineNr
	 */
	protected void warnUnclosedPrincipalBlock(Principal principal, int lineNr)
	{
		log.warn("The principal " + principal + " running to line " + lineNr
				+ " is not properly closed with '};'.");
	}

	/**
	 * Notifies when a file is closed, either because the end of the file was
	 * reached or because an uncaught exception was thrown. Default is noop.
	 * 
	 * @param file
	 *            the file
	 * @param lineNr
	 *            the last line read
	 */
	protected void notifyFileClose(URL file, int lineNr)
	{
	}

	/**
	 * Notifies when a new file is about to be read. Default is noop.
	 * 
	 * @param file
	 *            the file
	 */
	protected void notifyFileStart(URL file)
	{
	}

	/**
	 * Notifies when a permission class could not be found. Default is to log
	 * the exception
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param classname
	 *            the class of the permission
	 * @param e
	 *            the exception thrown when trying to locate the class
	 */
	protected void skipPermission(int lineNr, String classname, ClassNotFoundException e)
	{
		log.error("Permission class not found: " + classname + ", line " + lineNr, e);
	}

	/**
	 * Notifies when a permission is added to a principal. Default is noop.
	 * 
	 * @param lineNr
	 *            the currently process line
	 * @param principal
	 *            the current principal
	 * @param permission
	 *            the permission added to the principal
	 */
	protected void notifyPermission(int lineNr, Principal principal, Permission permission)
	{
	}

	/**
	 * Notifies of duplicate permissions for a principal. Default is to log an
	 * exception. Note that the duplicates might appear in different files.
	 * 
	 * @param lineNr
	 *            the duplicate line
	 * @param principal
	 *            the principal
	 * @param permission
	 *            the duplicate permission
	 */
	protected void skipPermission(int lineNr, Principal principal, Permission permission)
	{
		log.debug(permission + " skipped because it was already added to the permission set for "
				+ principal + ", line nr " + lineNr);
	}

	/**
	 * Notifies of permissions located outside the { and }; block statements but
	 * after a valid principal was found.
	 * 
	 * @param lineNr
	 *            the line declaring the illegal permission
	 * @param principal
	 *            the declared principal
	 * @param permission
	 *            the declared permission
	 */
	protected void skipIllegalPermission(int lineNr, Principal principal, Permission permission)
	{
		log.debug(permission + " skipped because the pricipal " + principal
				+ " has not yet declared its opening block statement '{', line nr " + lineNr);
	}

	/**
	 * Notifies when a Principal class could not be found. Default is to log the
	 * exception.
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param classname
	 *            the class of the Principal
	 * @param e
	 *            the exception thrown when the class could not be found
	 */
	protected void skipPrincipalClass(int lineNr, String classname, ClassNotFoundException e)
	{
		log.error("Unable to find principal of class " + classname + " at line nr " + lineNr, e);
	}

	/**
	 * Notifies when the principal does not have an accessible constructor for a
	 * single {@link String} argument. Default is to log the exception.
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param principalClass
	 *            the class of the Principal
	 */
	protected void skipPrincipal(int lineNr, Class principalClass)
	{
		log.error("No valid constructor found for " + principalClass.getName() + " at line "
				+ lineNr);
	}

	/**
	 * Notifies of a new Principal read in the policy file. Default is noop.
	 * 
	 * @param lineNr
	 *            the line currently processed
	 * @param principal
	 *            the principl
	 */
	protected void notifyOfPrincipal(int lineNr, Principal principal)
	{
	}

	/**
	 * Notifies when a new instance of the principl could not be created.
	 * Default is to log the exception.
	 * 
	 * @param lineNr
	 *            the line currently read
	 * @param principalClass
	 *            the class of the principal
	 * @param e
	 *            the exception thrown when trying to create a new instance
	 */
	protected void skipPrincipal(int lineNr, Class principalClass, Exception e)
	{
		log.error("Unable to create new instance of " + principalClass.getName() + " at line nr "
				+ lineNr, e);
	}

	/**
	 * Notifies when a classname is not a {@link Principal}. Default is to log
	 * the exception.
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param principalClass
	 *            the class which is not a subclass of Principal
	 */
	protected void skipPrincipalClass(int lineNr, Class principalClass)
	{
		log.error(principalClass.getName() + "is not a subclass of " + Principal.class.getName()
				+ ", line nr " + lineNr);
	}

	/**
	 * Notifies when a line is skipped because it was not understood for any
	 * other reason. Default is to print this debug info.
	 * 
	 * @param lineNr
	 *            the number of the line in the file
	 * @param line
	 *            the line that was skipped
	 */
	protected void skipLine(int lineNr, String line)
	{
		log.debug("skipping line " + lineNr + ": " + line);
	}

	/**
	 * Notified when a new instance of the permission could not be created.
	 * Default is to log the exception
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param permissionClass
	 *            the class trying to instantiate
	 * @param argValues
	 *            the constructor argument(s)
	 * @param e
	 *            the exception thrown when trying to create a new instance
	 */
	protected void skipPermission(int lineNr, Class permissionClass, Object[] argValues, Exception e)
	{
		log.error("Unable to create new instance of class " + permissionClass.getName()
				+ " using the following argument(s) " + arrayToString(argValues) + ", line nr "
				+ lineNr, e);
	}

	/**
	 * Generates a comma (,) separated string of all the items in the array
	 * 
	 * @param array
	 *            the input
	 * @return a comma separated string, an empty string or null if the input
	 *         array has 1 or more items, zero items or is null respectively.
	 */
	protected final String arrayToString(Object[] array)
	{
		if (array == null)
			return null;
		if (array.length < 1)
			return "";
		StringBuffer buffer = new StringBuffer(array.length * 12);// guess
		for (int i = 0; i < array.length; i++)
		{
			buffer.append(array[i]);
			if (i < array.length - 1)
				buffer.append(", ");
		}
		return buffer.toString();
	}

	/**
	 * Notifies when a Permission could not be created because no suitable
	 * constructor was found. Default is to log an exception.
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param permissionClass
	 *            the class of the permission
	 * @param args
	 *            the number and type of constructor arguments
	 * 
	 */
	protected void skipPermission(int lineNr, Class permissionClass, Class[] args)
	{
		log.error("No constructor found matching argument(s) " + arrayToString(args)
				+ " for class " + permissionClass.getName() + ", line nr " + lineNr);
	}

	/**
	 * Notifies when a Class is skipped because it is not a Permission. Default
	 * is to log an exception.
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param permissionClass
	 *            the class (if available)
	 * 
	 */
	protected void skipPermission(int lineNr, Class permissionClass)
	{
		if (permissionClass == null)
			log.error("Missing permission class at line " + lineNr);
		else
			log.error(permissionClass.getName() + " is not a subclass of "
					+ Permission.class.getName());
	}

	/**
	 * Notifies when a principal is kipped because there are no permissions
	 * attached. Default is to log an exception.
	 * 
	 * @param lineNr
	 *            the line closing the principal.
	 * @param principal
	 *            the skipped principal
	 */
	protected void skipEmptyPrincipal(int lineNr, Principal principal)
	{
		if (log.isDebugEnabled())
			log.debug("skipping principal " + principal + ", no permissions found before line nr "
					+ lineNr);
	}

	/**
	 * Notifies when a {@link Principal} begins at an illegal place in the file.
	 * Default is to log an exception.
	 * 
	 * @param lineNr
	 *            the faulty line
	 * @param principal
	 *            the principal we are currently working on
	 * @param permissions
	 *            the permission collected for the current principal so far.
	 */
	protected void skipIllegalPrincipal(int lineNr, Principal principal, Set permissions)
	{
		log.error("Illegal principal block detected at line " + lineNr);
	}

	/**
	 * Flag indicating if caching for the {@link Hive} is enabled or disabled.
	 * Default is enabled.
	 * 
	 * @return useHiveCache
	 */
	public final boolean isUsingHiveCache()
	{
		return useHiveCache;
	}

	/**
	 * Sets useHiveCache.
	 * 
	 * @param useCache
	 *            enable or disable caching
	 */
	public final void useHiveCache(boolean useCache)
	{
		this.useHiveCache = useCache;
	}

	/**
	 * Gets closeInputStreams.
	 * 
	 * @return closeInputStreams
	 */
	public final boolean isCloseInputStreams()
	{
		return closeInputStreams;
	}

	/**
	 * Sets closeInputStreams.
	 * 
	 * @param closeInputStreams
	 *            closeInputStreams
	 */
	public final void setCloseInputStreams(boolean closeInputStreams)
	{
		this.closeInputStreams = closeInputStreams;
	}

}
