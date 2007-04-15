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
package org.wicketstuff.dojo.markup.html.form.suggestionlist;

import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.wicketstuff.dojo.AbstractDefaultDojoBehavior;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.target.basic.StringRequestTarget;

/**
 * Handler for {@link DojoRequestSuggestionList}
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 * <p>
 * <i>SuggestionList does not use {@link AbstractRequireDojoBehavior} because XMLHTTPRequest 
 * does not respond a DOM fragment but a json</i>
 * </p>
 *
 */
@SuppressWarnings("serial")
public class DojoRequestSuggestionListHandler extends AbstractAjaxBehavior{

	/* (non-Javadoc)
	 * @see wicket.behavior.AbstractAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response){
		super.renderHead(response);
		response.renderJavascriptReference(AbstractDefaultDojoBehavior.DOJO);
		
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "	dojo.require('dojo.widget.ComboBox')\n";
		require += "\n";
		require += "</script>\n";
		
		response.renderString(require);
	}
	
	protected void onComponentTag(ComponentTag tag){
		super.onComponentTag(tag);
		tag.put("dataUrl", getCallbackUrl() + "&search=%{searchString}");
		tag.put("mode","remote");
	}
	
	/**
	 * @see wicket.behavior.IBehaviorListener#onRequest()
	 */
	public final void onRequest()
	{
		boolean isPageVersioned = true;
		Page page = getComponent().getPage();
		try
		{
			isPageVersioned = page.isVersioned();
			page.setVersioned(false);

			String response = respond();
			
			StringRequestTarget target = new StringRequestTarget(response);
			RequestCycle.get().setRequestTarget(target);
		}
		finally
		{
			page.setVersioned(isPageVersioned);
		}
	}

	protected String respond(){
		String pattern = getComponent().getRequest().getParameter("search");
		SuggestionList list = ((DojoRequestSuggestionList)getComponent()).getMatchingValues(pattern);
		return list.getJson();
	}

	

}
