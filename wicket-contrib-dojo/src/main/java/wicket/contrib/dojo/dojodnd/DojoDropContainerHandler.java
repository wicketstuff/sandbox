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
package wicket.contrib.dojo.dojodnd;

import java.util.HashMap;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

/**
 * Package class for Dojo DropContainer
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public class DojoDropContainerHandler extends AbstractRequireDojoBehavior
{
	/** container handler is attached to. */
	private DojoDropContainer container;


	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		this.container = (DojoDropContainer)getComponent();
	}
	

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(this.getClass(), "DojoDropContainerHandlerTemplate.js");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MarkupId", container.getMarkupId());
		map.put("DropId", container.getDropPattern());
		map.put("CallbackUrl", getCallbackUrl());
		map.put("Id", container.getId());
		response.renderJavascript(template.asString(map), template.getWidgetUniqueKey(this.getComponent()));

	}
	
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		container.onAjaxModelUpdated(target);
	}

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.dnd.*");
		libs.add("dojo.event.*");
		libs.add("dojo.io.*");
	}
	


}
