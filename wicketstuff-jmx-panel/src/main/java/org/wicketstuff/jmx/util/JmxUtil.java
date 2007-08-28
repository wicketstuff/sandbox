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

import org.apache.wicket.WicketRuntimeException;

public class JmxUtil
{
	@SuppressWarnings("unchecked")
	public static Class getType(String type)
	{
		try
		{
			if ("boolean".equals(type))
			{
				return boolean.class;
			}
			else if ("int".equals(type))
			{
				return int.class;
			}
			else if ("long".equals(type))
			{
				return long.class;
			}
			else if ("char".equals(type))
			{
				return char.class;
			}
			else if ("byte".equals(type))
			{
				return byte.class;
			}
			else
			{
				return Class.forName(type);
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new WicketRuntimeException(e);
		}
	}
}
