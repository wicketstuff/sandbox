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

import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Wraps the connection to an {@link MBeanServer}.
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxMBeanServerWrapper extends LoadableDetachableModel
{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected Object load()
	{
		List servers = MBeanServerFactory.findMBeanServer(null);
		if (servers != null && !servers.isEmpty())
		{
			return servers.get(0);
		}
		return null;
	}

	/**
	 * @return the {@link MBeanServer}
	 */
	public MBeanServerConnection getServer()
	{
		return (MBeanServerConnection)getObject();
	}

}