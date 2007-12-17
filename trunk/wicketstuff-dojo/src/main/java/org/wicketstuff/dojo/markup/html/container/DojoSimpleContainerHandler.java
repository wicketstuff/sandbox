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
package org.wicketstuff.dojo.markup.html.container;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractDojoWidgetBehavior;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.widgetloadingpolicy.IDojoWidgetLoadingPolicy;

/**
 * A very simple handler for {@link DojoSimpleContainer} and {@link DojoPanelContainer}
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoSimpleContainerHandler extends AbstractDojoWidgetBehavior
{
	
	public DojoSimpleContainerHandler()
	{
		super();
	}

	public DojoSimpleContainerHandler(IDojoWidgetLoadingPolicy loadingPolicy)
	{
		super(loadingPolicy);
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#setRequire(wicket.contrib.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	@SuppressWarnings("unchecked")
	public void setRequire(final RequireDojoLibs libs)
	{
		libs.add("dojo.widget.ContentPane");
	}

}
