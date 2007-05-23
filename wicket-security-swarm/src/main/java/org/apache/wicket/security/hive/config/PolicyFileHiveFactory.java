/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.security.hive.BasicHive;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.authorization.EverybodyPrincipal;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.authorization.Principal;


/**
 * A factory to produce Hive's based on policy files. This factory is designed to make a
 * best effort when problems aoocur. Meaning any malconfiguration in the policy file is
 * logged and then skipped. This factory accepts the following policy format<br>
 * 
 * <pre>
 * grant [principal &lt;pricipal class&gt; &quot;name&quot;]
 * {
 * permission &lt;permission class&gt; &quot;name&quot;,[ &quot;actions&quot;];
 * };
 * </pre>
 * 
 * where [] denotes an optional block, &lt;&gt; denotes a classname.<br>
 * Note that:
 * <ul>
 * <li> names and action must be quoted</li>
 * <li>a permission statement must be on a single line and terminated by a ;</li>
 * <li>the grant block must be terminated by a ;</li>
 * <li>if you don't specify a principal after the grant statement, everybody will be
 * given those permissions automagically</li>
 * <li>at the moment you need to include the ',' even if you don't specify actions, this will change but i am having trouble getting the right regex</li>
 * @author marrink
 */
public class PolicyFileHiveFactory implements HiveFactory
{
	private static final Log log = LogFactory.getLog(PolicyFileHiveFactory.class);
	//TODO fix regex so the , is no longer required when not using actions
	// TODO use JAAS to check for enough rights
	private Set policyFiles;

	private static final Pattern principalPattern = Pattern
			.compile("\\s*(grant(\\s+principal\\s+(.+)\\s+\"(.+)\")?){1}\\s*");

	private static final Pattern permissionPattern = Pattern
			.compile("\\s*(permission\\s+(.+)\\s+\"(.+)\"\\s*,(?:\\s+\"(.+)\")?\\s*;){1}\\s*");

	private static final Class[] stringArgs1 = new Class[] {String.class};

	private static final Class[] stringArgs2 = new Class[] {String.class, String.class};

	public PolicyFileHiveFactory()
	{
		policyFiles = new HashSet();
	}

	public boolean addPolicyFile(URL file)
	{
		return policyFiles.add(file);
	}

	/**
	 * This method is not thread safe.
	 * @see org.apache.wicket.security.hive.config.HiveFactory#createHive()
	 */
	public Hive createHive()
	{
		BasicHive hive = new BasicHive();
		if (policyFiles.isEmpty())
			log.warn("No policy files have been defined yet.");
		Iterator it = policyFiles.iterator();
		while (it.hasNext())
		{
			URL file = (URL) it.next();
			try
			{
				readPolicyFile(file, hive);
			}
			catch (IOException e)
			{
				log.error("Could not read from " + file, e);
			}
		}
		return hive;
	}

	private void readPolicyFile(URL file, BasicHive hive) throws IOException
	{
		boolean inPrincipalBlock = false;
		Principal principal = null;
		BufferedReader reader = null;
		Set permissions = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(file.openStream()));
			String line = reader.readLine();
			int lineNr = 0;
			while (line != null)
			{
				lineNr++;
				if (inPrincipalBlock)
				{
					String trim = line.trim();
					boolean startsWith = trim.startsWith("{");
					if (startsWith)
					{
						if (permissions != null || principal == null)
							log.error("Illegal principal block detected at line " + lineNr);
						permissions = new HashSet();
					}
					boolean endsWith = trim.endsWith("};");
					if (endsWith)
					{
						inPrincipalBlock = false;
						if(permissions!=null && permissions.size()>0)
							hive.addPrincipal(principal, permissions);
						else if(log.isDebugEnabled())
							log.debug("skipping principal "+principal+", no permissions found");
							
						permissions = null;
						principal = null;
					}
					if(!(startsWith||endsWith))
					{
						Matcher m = permissionPattern.matcher(line);
						if (m.matches())
						{
							String classname = m.group(2);
							if (classname == null)
							{
								log.error("Missing permission class at line " + lineNr);
								line=reader.readLine();
								continue;
							}
							Class permissionClass = null;
							try
							{
								permissionClass = Class.forName(classname);
								if (!Permission.class.isAssignableFrom(permissionClass))
								{
									log
											.error(permissionClass.getName()
													+ " is not a subclass of "
													+ Permission.class.getName());
									line=reader.readLine();
									continue;
								}
								String name = m.group(3);
								String actions = m.group(4);
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
									log.error("No constructor found matching "
											+ args + " for class " + permissionClass.getName());
									line=reader.readLine();
									continue;
								}
								Object[] argValues = new Object[] {name, actions};
								if (actions == null)
									argValues = new Object[] {name};
								Permission temp;
								try
								{
									temp = (Permission) constructor.newInstance(argValues);
								}
								catch (Exception e)
								{
									log.error("Unable to create new instance of class "
											+ permissionClass.getName()
											+ " using the following arguments " + argValues,e);
									line=reader.readLine();
									continue;
								}
								if (!permissions.add(temp))
									log
											.debug(temp
													+ " skipped because it was already added to the permission set for "
													+ principal);

							}
							catch (ClassNotFoundException e)
							{
								log.error("Permission class not found: " + classname+", line "+lineNr, e);
								line=reader.readLine();
								continue;
							}
						}
						else
						{
							// skip this line
							log.debug("skipping line " + lineNr + ": " + line);
						}
					}
				}
				else
				{
					Matcher m = principalPattern.matcher(line);
					if (m.matches())
					{
						String classname = m.group(3);
						if (classname == null)
							principal = new EverybodyPrincipal();
						else
						{
							Class principalClass = null;
							try
							{
								principalClass = Class.forName(classname);
								if (!Principal.class.isAssignableFrom(principalClass))
								{
									log.error(principalClass.getName()
											+ "is not a subclass of " + Principal.class.getName());
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
									log.error("No valid constructor found for "
											+ principalClass.getName());
									line = reader.readLine();
									continue;
								}
								try
								{
									principal = (Principal) constructor.newInstance(new Object[] {m
											.group(4)});
								}
								catch (Exception e)
								{
									log.error("Unable to create new instance of "
											+ principalClass.getName(), e);
									line = reader.readLine();
									continue;
								}

							}
							catch (ClassNotFoundException e)
							{
								log.error("Unable to find principal of class " + classname, e);
								line = reader.readLine();
								continue;
							}
						}
						inPrincipalBlock = true;
					}
					else
					{
						// skip this line
						log.debug("skipping line " + lineNr + ": " + line);
					}
				}
				line = reader.readLine();
			}
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
	}

}
