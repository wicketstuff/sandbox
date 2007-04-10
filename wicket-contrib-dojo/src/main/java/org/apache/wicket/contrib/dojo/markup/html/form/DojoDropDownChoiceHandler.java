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
package org.apache.wicket.contrib.dojo.markup.html.form;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.contrib.dojo.AbstractRequireDojoBehavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * Handler for DojoInline suggestionList
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoDropDownChoiceHandler extends AbstractRequireDojoBehavior
{

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#setRequire(wicket.contrib.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	public void setRequire(RequireDojoLibs libs){
		libs.add("dojo.widget.ComboBox");		
	}

	protected void respond(AjaxRequestTarget target){
		String value = RequestCycle.get().getRequest().getParameter("value");
		System.out.println(value);
		//TODO : needs to update model and call selectionChange
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		
		DojoDropDownChoice c = (DojoDropDownChoice) getComponent();

		if (c.isHandleSelectionChange()){
			tag.put("onchange", getCallbackScript());
		}
	}
	
	protected CharSequence getCallbackScript(boolean recordPageVersion, boolean onlyTargetActivePage)
	{
		return getCallbackScript("wicketAjaxGet('"
				+ getCallbackUrl(recordPageVersion, onlyTargetActivePage) + "&value=' + dojo.widget.getId('" + getComponent().getMarkupId() + "')", 
				null, null);
	}
	
	


}
