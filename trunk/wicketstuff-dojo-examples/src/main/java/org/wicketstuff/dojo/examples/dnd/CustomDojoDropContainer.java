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
package org.wicketstuff.dojo.examples.dnd;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.dojodnd.AbstractDojoDropContainerHandler;
import org.wicketstuff.dojo.dojodnd.DojoDragContainer;
import org.wicketstuff.dojo.dojodnd.DojoDropContainer;

/**
 *
 */
public class CustomDojoDropContainer extends DojoDropContainer
{

	/**
	 * Create a DropContainer
	 * @param id Drop container id
	 */
	public CustomDojoDropContainer(String id)
	{
		super(id);
	}
	
	protected AbstractDojoDropContainerHandler createDropBehavior() {
		return new CustomDojoDropContainerHandler();
	}
	
	/**
	 * This method is triggered when a {@link DojoDragContainer} is dropped
	 * on this container.
	 * @param container {@link DojoDragContainer} dropped
	 * @param position position where it is dropped
	 * @param target {@link AjaxRequestTarget}
	 */
	public void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position) {
		System.out.println("CUSTOM CONTAINER: position = " + position);
		System.out.println("CUSTOM CONTAINER: DojoDragContainer" + container.getId());
	}
}
