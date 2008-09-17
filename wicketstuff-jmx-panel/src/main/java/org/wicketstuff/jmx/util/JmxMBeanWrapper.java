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
package org.wicketstuff.jmx.util;

import java.io.IOException;
import java.io.Serializable;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps an {@link ObjectName} instance.
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxMBeanWrapper implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(JmxMBeanWrapper.class);

	private JmxMBeanServerWrapper server;
	private ObjectName objectName;

	/**
	 * @param name
	 *            the {@link ObjectName} instance to wrap
	 * @param server
	 *            the wrapper for an {@link MBeanServer} instance, used to
	 *            retrieve {@link MBeanInfo}.
	 */
	public JmxMBeanWrapper(ObjectName name, JmxMBeanServerWrapper server)
	{
		this.server = server;
		this.objectName = name;
	}

	/**
	 * @return the {@link ObjectName} instance
	 */
	public ObjectName getObjectName()
	{
		return objectName;
	}

	/**
	 * @return the objectName's {@link MBeanInfo}
	 */
	public MBeanInfo getMBeanInfo()
	{
		MBeanInfo info = null;
		try
		{
			info = server.getServer().getMBeanInfo(objectName);
		}
		catch (InstanceNotFoundException e)
		{
			// logger.error("could not retrieve MBeanInfo of " +
			// objectName.getCanonicalName(), e);
		}
		catch (IntrospectionException e)
		{
			// logger.error("could not retrieve MBeanInfo of " +
			// objectName.getCanonicalName(), e);
		}
		catch (ReflectionException e)
		{
			// logger.error("could not retrieve MBeanInfo of " +
			// objectName.getCanonicalName(), e);
		}
		catch (IOException e)
		{
			// logger.error("could not retrieve MBeanInfo of " +
			// objectName.getCanonicalName(), e);
		}
		return info;
	}

	/**
	 * @return the objectName's {@link MBeanAttributeInfo}s
	 */
	public MBeanAttributeInfo[] getAttributes()
	{
		MBeanInfo info = getMBeanInfo();
		return info != null ? info.getAttributes() : new MBeanAttributeInfo[0];
	}

	/**
	 * @return the objectName's {@link MBeanOperationInfo}s
	 */
	public MBeanOperationInfo[] getOperations()
	{
		MBeanInfo info = getMBeanInfo();
		return info != null ? info.getOperations() : new MBeanOperationInfo[0];
	}


	/**
	 * @return the objectName's description
	 */
	public String getDescription()
	{
		MBeanInfo info = getMBeanInfo();
		return info != null ? info.getDescription() : "";
	}

	/**
	 * @param attribute
	 *            the attribute to retrieve
	 * @return the value of the attribute
	 */
	public Object getAttribute(Attribute attribute)
	{
		try
		{
			return server.getServer().getAttribute(getObjectName(), attribute.getName());
		}
		catch (AttributeNotFoundException e)
		{
			logger.error("could not retrieve the value of the attribute \"" + attribute.getName()
					+ "\"  of " + objectName.getCanonicalName(), e);
		}
		catch (InstanceNotFoundException e)
		{
			logger.error("could not retrieve the value of the attribute \"" + attribute.getName()
					+ "\"  of " + objectName.getCanonicalName(), e);
		}
		catch (MBeanException e)
		{
			logger.error("could not retrieve the value of the attribute \"" + attribute.getName()
					+ "\"  of " + objectName.getCanonicalName(), e);
		}
		catch (ReflectionException e)
		{
			logger.error("could not retrieve the value of the attribute \"" + attribute.getName()
					+ "\"  of " + objectName.getCanonicalName(), e);
		}
		catch (IOException e)
		{
			logger.error("could not retrieve the value of the attribute \"" + attribute.getName()
					+ "\"  of " + objectName.getCanonicalName(), e);
		}
		return null;
	}

	public void setAttribute(Attribute value)
	{
		try
		{
			server.getServer().setAttribute(getObjectName(), value);
		}
		catch (AttributeNotFoundException e)
		{
			logger.error("could not set the value \"" + value.getValue().toString()
					+ "\" for the attribute \"" + value.getName() + "\"  of "
					+ objectName.getCanonicalName(), e);
		}
		catch (InstanceNotFoundException e)
		{
			logger.error("could not set the value \"" + value.getValue().toString()
					+ "\" for the attribute \"" + value.getName() + "\"  of "
					+ objectName.getCanonicalName(), e);
		}
		catch (MBeanException e)
		{
			logger.error("could not set the value \"" + value.getValue().toString()
					+ "\" for the attribute \"" + value.getName() + "\"  of "
					+ objectName.getCanonicalName(), e);
		}
		catch (ReflectionException e)
		{
			logger.error("could not set the value \"" + value.getValue().toString()
					+ "\" for the attribute \"" + value.getName() + "\"  of "
					+ objectName.getCanonicalName(), e);
		}
		catch (InvalidAttributeValueException e)
		{
			logger.error("could not set the value \"" + value.getValue().toString()
					+ "\" for the attribute \"" + value.getName() + "\"  of "
					+ objectName.getCanonicalName(), e);
		}
		catch (IOException e)
		{
			logger.error("could not set the value \"" + value.getValue().toString()
					+ "\" for the attribute \"" + value.getName() + "\"  of "
					+ objectName.getCanonicalName(), e);
		}
	}

	public Object invoke(MBeanOperationInfo operation, Object[] values, String[] signature)
	{
		try
		{
			return server.getServer().invoke(getObjectName(), operation.getName(), values,
					signature);

		}
		catch (InstanceNotFoundException e)
		{
			logger.error("could not invoke operation \"" + operation.getName() + "\" of "
					+ objectName.getCanonicalName(), e);
		}
		catch (MBeanException e)
		{
			logger.error("could not invoke operation \"" + operation.getName() + "\" of "
					+ objectName.getCanonicalName(), e);
		}
		catch (ReflectionException e)
		{
			logger.error("could not invoke operation \"" + operation.getName() + "\" of "
					+ objectName.getCanonicalName(), e);
		}
		catch (IOException e)
		{
			logger.error("could not invoke operation \"" + operation.getName() + "\" of "
					+ objectName.getCanonicalName(), e);
		}
		return null;
	}
}
