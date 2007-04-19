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
package wicket.contrib.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;
import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.file.WebApplicationPath;
import org.apache.wicket.util.io.Streams;
import org.apache.wicket.util.lang.Packages;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * An implementation of {@link wicket.IInitializer} for the Velocity Runtime
 * Singleton. If Application is an instance of WebApplication, Initializer will
 * retrieve "velocityPropertiesFolder" as an initparam to point to the directory
 * the properties file lives in, and "velocity.properties" for the name of the
 * properties file. If the params don't exist, then velocity.properties next to
 * this class will be loaded.
 * 
 */
public class Initializer implements IInitializer
{
	private static final Log log = LogFactory.getLog(Initializer.class);

	private String velocityPropertiesFolder;

	private String velocityPropertiesFile = "velocity.properties";

	private Properties getVelocityProperties(Application application)
	{
		if (application instanceof WebApplication)
		{
			return getVelocityProperties((WebApplication) application);
		}
		else
		{
			return getVelocityProperties();
		}
	}

	private Properties getVelocityProperties(WebApplication webapp)
	{
		ServletContext servletContext = webapp.getServletContext();
		ServletContext sc = servletContext;
		velocityPropertiesFolder = sc.getInitParameter("velocityPropertiesFolder");
		String propsFile = servletContext.getInitParameter("velocity.properties");

		if (null != propsFile)
		{
			velocityPropertiesFile = propsFile;
		}

		if (null != velocityPropertiesFolder)
		{
			WebApplicationPath webPath = new WebApplicationPath(sc);
			webPath.add(velocityPropertiesFolder);
			URL url = webPath.find(velocityPropertiesFile);
			try
			{
				InputStream is = url.openStream();
				Properties props = new Properties();
				props.load(is);
				return props;
			}
			catch (IOException e)
			{
				throw new WicketRuntimeException(e);
			}
		}
		else
		{
			return getVelocityProperties();
		}
	}

	private Properties getVelocityProperties()
	{
		ClassLoaderResourceStreamLocator clrsr = new ClassLoaderResourceStreamLocator();
		String absolutePath = Packages.absolutePath(Initializer.class,
				velocityPropertiesFile);
		IResourceStream irs = clrsr.locate(Initializer.class, absolutePath);

		try
		{
			InputStream is = irs.getInputStream();
			Properties props = new Properties();
			props.load(is);
			return props;
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	/**
	 * @see wicket.IInitializer#init(wicket.Application)
	 */
	public void init(Application application)
	{
		Properties props = getVelocityProperties(application);

		try
		{
			if (null != props)
			{
				Velocity.init(props);
			}
			else
			{
				Velocity.init();
			}
			log.info("Initialized Velocity successfully");
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException(e);
		}
	}

}
