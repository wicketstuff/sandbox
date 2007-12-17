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
package org.apache.wicket.seam;

import java.lang.reflect.Field;

import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.Strings;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;

/**
 * Locates {@link Component Seam components}. Used for handling {@link In}
 * annotations.
 * 
 * @author eelcohillenius
 */
class SeamProxyTargetLocator implements IProxyTargetLocator {

	/** the {@link In} annotation. */
	private final In in;

	/**
	 * target type of the annotation (use name to enable reloading by other
	 * framework parts.
	 */
	private final String targetType;

	SeamProxyTargetLocator(Field field, In in) {
		this.in = in;
		this.targetType = field.getType().getName();
	}

	/**
	 * @see org.apache.wicket.proxy.IProxyTargetLocator#locateProxyTarget()
	 */
	public Object locateProxyTarget() {

		String name = in.value();
		ScopeType scope = in.scope();
		boolean create = in.create();
		boolean required = in.required();

		Object component = null;
		if (!Strings.isEmpty(name)) {
			if (scope == null || scope == ScopeType.UNSPECIFIED) {
				component = Component.getInstance(name, create);
			} else {
				component = Component.getInstance(name, scope, create);
			}
		} else {
			Class<?> target = Classes.resolveClass(targetType);
			if (scope == null || scope == ScopeType.UNSPECIFIED) {
				component = Component.getInstance(target, create);
			} else {
				component = Component.getInstance(target, scope, create);
			}
		}

		if (required && component == null) {
			throw new IllegalStateException("required In dependency " + in
					+ " not found");
		}

		return component;
	}
}
