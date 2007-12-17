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
package org.apache.wicket.security.actions;

import org.apache.wicket.authorization.Action;

/**
 * Immutable {@link Action} class, already extends Action. These actions are
 * instantiated by an ActionFactory.
 * 
 * @author marrink
 * @see Action
 */
public abstract class AbstractWaspAction extends Action implements WaspAction
{
	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor for actions.
	 * 
	 * @param name
	 *            the name of this action
	 */
	protected AbstractWaspAction(String name)
	{
		super(name);
	}

	/**
	 * @see org.apache.wicket.authorization.Action#getName()
	 */
	public final String getName()
	{
		return super.getName();
	}

}
