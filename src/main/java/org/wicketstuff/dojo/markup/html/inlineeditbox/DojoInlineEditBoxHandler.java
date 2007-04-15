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
package org.wicketstuff.dojo.markup.html.inlineeditbox;

import java.util.HashMap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.apache.wicket.extensions.util.resource.PackagedTextTemplate;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Handler for {@link DojoInlineEditBox}
 * @author Gregory Maes
 */
@SuppressWarnings("serial")
public class DojoInlineEditBoxHandler extends AbstractRequireDojoBehavior {
	
	/** connect the onSave function of dojo with the wicket callbackUrl */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		PackagedTextTemplate packagedTextTemplate = new PackagedTextTemplate(this.getClass(), "InlineEditBox.js");
		HashMap<String, CharSequence> map = new HashMap<String, CharSequence>();
		map.put("callbackUrl", getCallbackUrl());
		map.put("markupId", getComponent().getMarkupId());	
		response.renderJavascript(packagedTextTemplate.asString(map), null);
	}

	/** set the required libraries for the inlineEditBox dojo widget
	 * @param libs the dojo libraries to be included
	 */
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.InlineEditBox");
		libs.add("dojo.event.*");
	}

	/** recover the text value and set it in the model */
	protected void respond(AjaxRequestTarget target) {
		String textValue = getComponent().getRequest().getParameter("newTextValue");
		getComponent().setModelObject(textValue);
		((DojoInlineEditBox)getComponent()).onSave(target);
	}

	/**
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#onComponentReRendered(wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget) {
		super.onComponentReRendered(ajaxTarget);
		
		String markupId = getComponent().getMarkupId();
		ajaxTarget.appendJavascript("initInlineEditBox('" + markupId + "')\n");
	}
}
