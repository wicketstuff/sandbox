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
package org.wicketstuff.dojo.markup.html.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;

/**
 * Handler for DojoInline suggestionList
 * 
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 * 
 */
@SuppressWarnings("serial")
public class DojoDropDownChoiceHandler extends AbstractRequireDojoBehavior {
	/**
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#setRequire(wicket.contrib.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.ComboBox");
	}

	protected void respond(AjaxRequestTarget target) {
		DojoDropDownChoice c = (DojoDropDownChoice) getComponent();
		// cannot call DropDownChoice#onSelectionChanged() here because
		// newly created values are not part of the choices model
		c.onSetValue(target);
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);

		DojoDropDownChoice c = (DojoDropDownChoice) getComponent();
		// Should a roundtrip be made (have onSelectionChanged called) when the
		// selection changed?
		if (c.isHandleSelectionChange()) {
			tag.put("setValue", getCallbackScript());
		}
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		DojoDropDownChoice c = (DojoDropDownChoice) getComponent();

		if (c.getValue().equals("-1"))
			// Reset the ComboBox value
			response.renderOnLoadJavascript("dojo.widget.byId('" + c.getMarkupId() + "').textInputNode.value = '';");
	}

	protected CharSequence getCallbackScript(boolean recordPageVersion, boolean onlyTargetActivePage) {
		DojoDropDownChoice c = (DojoDropDownChoice) getComponent();
		return getCallbackScript("wicketAjaxGet('" + getCallbackUrl(recordPageVersion, onlyTargetActivePage) + "&amp;"
				+ c.getInputName() + "=' + arguments[0]", null, null);
	}
}
