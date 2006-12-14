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
package wicket.contrib.dojo.markup.html.percentage;

import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.markup.html.percentage.model.PercentageRanges;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;

/**
 * Packaged Class. Can only be used by {@link DojoPercentSelector}
 * @author Vincent Demay
 *
 */
class DojoPercentSelectorHandler extends AbstractRequireDojoBehavior
{

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		//DO NOTHING For the moment
		
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		String result = getComponent().getRequest().getParameter("json");
		((PercentageRanges)getComponent().getModelObject()).createFromJson(result);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(DojoPercentSelectorHandler.class, "PercentSelector.js"));
		if (((PercentageRanges)getComponent().getModelObject()) != null){
			String toRender = "";
			toRender += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
			toRender += "function init" + getComponent().getMarkupId() + "(){\n";
			toRender += "	var json='" + ((PercentageRanges)getComponent().getModelObject()).generateJson() + "'\n";
			toRender += "	dojo.widget.byId('" + getComponent().getMarkupId() + "').setJson(json);\n";
			toRender += "}\n";
			toRender += "dojo.addOnLoad(init"+ getComponent().getMarkupId() +");\n";
			toRender += "</script>\n";
			response.renderString(toRender);
		}

	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("onValueChange", getCallbackChange());
	}
	
	/**
	 * CallBack Used when a percentage change
	 * @return the callBack javascript
	 */
	protected String getCallbackChange(){
		return "var wcall=wicketAjaxGet('" + getCallbackUrl() + "&json=' + this.getJson(), function() { }, function() { })";
	}
}
