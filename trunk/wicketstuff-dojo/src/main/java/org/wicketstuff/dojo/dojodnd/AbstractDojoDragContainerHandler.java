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

package org.wicketstuff.dojo.dojodnd;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;

/**
 * <p>
 * A Handler associated to {@link DojoDragContainer}
 * </p>
 * 
 * @author Vincent Demay
 *
 */
public abstract class AbstractDojoDragContainerHandler extends AbstractRequireDojoBehavior
{
	/** container handler is attached to. */
	private DojoDragContainer container;
	
	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		this.container = (DojoDragContainer)getComponent();
	}
	
	
	protected void respond(AjaxRequestTarget target)
	{
		((DojoDragContainer)getComponent()).onDrag(target);
	}

	/**
	 * Returns the drag container.
	 * @return
	 */
	protected DojoDragContainer getDojoDragContainer() {
		return this.container;
	}

	
	@SuppressWarnings("unchecked")
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.dnd.*");
		libs.add("dojo.event.*");
		libs.add("dojo.io.*");
	}

}
