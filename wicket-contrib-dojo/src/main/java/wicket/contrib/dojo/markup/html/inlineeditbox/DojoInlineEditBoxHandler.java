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
package wicket.contrib.dojo.markup.html.inlineeditbox;

import java.util.HashMap;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

/**
 * Handler for {@link DojoInlineEditBox}
 * @author Gregory Maes
 */
class DojoInlineEditBoxHandler extends AbstractRequireDojoBehavior {
	
	/** connect the onSave function of dojo with the wicket callbackUrl */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		DojoPackagedTextTemplate packagedTextTemplate = new DojoPackagedTextTemplate(this.getClass(), "InlineEditBox.js");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("callbackUrl", getCallbackUrl());
		map.put("markupId", getComponent().getMarkupId());	
		response.renderJavascript(packagedTextTemplate.asString(map), packagedTextTemplate.getWidgetUniqueKey(this.getComponent()));
	}

	/** set the required libraries for the inlineEditBox dojo widget
	 * @param libs the dojo libraries to be included
	 */
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.InlineEditBox");
		libs.add("dojo.event.*");
	}

	/** recover the text value and set it in the model */
	@SuppressWarnings("unchecked")
	@Override
	protected void respond(AjaxRequestTarget target) {
		String textValue = getComponent().getRequest().getParameter("newTextValue");
		getComponent().setModelObject(textValue);
		((DojoInlineEditBox)getComponent()).onSave(target);
	}

}
