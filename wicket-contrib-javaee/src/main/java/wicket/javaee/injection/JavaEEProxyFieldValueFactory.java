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
package wicket.javaee.injection;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;

import wicket.extensions.injection.IFieldValueFactory;
import wicket.extensions.proxy.LazyInitProxyFactory;
import wicket.javaee.JavaEEBeanLocator;
import wicket.javaee.naming.IJndiNamingStrategy;
import wicket.javaee.naming.StandardJndiNamingStrategy;

/**
 * {@link IFieldValueFactory} that creates
 * proxies of EJBs based on the {@link javax.persistence.EJB} annotation
 * applied to a field. 
 * 
 * @author Filippo Diotalevi
 * 
 */
public class JavaEEProxyFieldValueFactory implements IFieldValueFactory
{
	private final ConcurrentHashMap<JavaEEBeanLocator, Object> cache = new ConcurrentHashMap<JavaEEBeanLocator, Object>();
	private IJndiNamingStrategy namingStrategy;

	/**
	 * Constructor
	 */
	public JavaEEProxyFieldValueFactory()
	{
		this(new StandardJndiNamingStrategy());
	}
	
	/**
	 * Constructor
	 */
	public JavaEEProxyFieldValueFactory(IJndiNamingStrategy namingStrategy)
	{
		this.namingStrategy = namingStrategy;
	}

	/**
	 * @see wicket.extensions.injection.IFieldValueFactory#getFieldValue(java.lang.reflect.Field,
	 *      java.lang.Object)
	 */
	public Object getFieldValue(Field field, Object fieldOwner)
	{

		if (field.isAnnotationPresent(EJB.class))
		{
			EJB annot = field.getAnnotation(EJB.class);

			String name = annot.name();
			JavaEEBeanLocator locator = new JavaEEBeanLocator(name, field.getType(), namingStrategy);

			if (cache.containsKey(locator))
			{
				return cache.get(locator);
			}

			Object proxy = LazyInitProxyFactory.createProxy(field.getType(), locator);
			cache.put(locator, proxy);
			return proxy;
		}
		else
		{
			return null;
		}
	}


	/**
	 * @see wicket.extensions.injection.IFieldValueFactory#supportsField(java.lang.reflect.Field)
	 */
	public boolean supportsField(Field field)
	{
		return field.isAnnotationPresent(EJB.class);
	}

}
