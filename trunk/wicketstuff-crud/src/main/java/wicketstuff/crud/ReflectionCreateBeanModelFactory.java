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
package wicketstuff.crud;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Quick create bean model factory that uses reflection to create an instance
 * using default constructor
 * 
 * @author igor.vaynberg
 * 
 */
public class ReflectionCreateBeanModelFactory implements ICreateBeanModelFactory
{
	/** name of class whose instance will be created */
	private final String className;

	/**
	 * @param clazz
	 *            class that represents the type of instance that will be
	 *            created
	 */
	public ReflectionCreateBeanModelFactory(Class<? extends Serializable> clazz)
	{
		className = clazz.getName();
	}


	/** {@inheritDoc} */
	public IModel newModel()
	{
		try
		{
			return new Model((Serializable)Class.forName(className).newInstance());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unable to instantiate instance of class " + className, e);
		}

	}

}
