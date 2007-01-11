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
package wicket.contrib.dojo;

import java.util.HashSet;
import java.util.Iterator;

import wicket.RequestCycle;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;

/**
 * This behavior has to be extend to write a new Behavior (Handler) using Dojo : 
 * This behavior allows user to add require dojo Libs (see dojo.require(...)) - to add a require dojo libs
 * implement <code>setRequire()</code> and add libs to the libs variable.
 * <br/>
 * <br/>
 * This behavior also auto-reload a Dojo component when it is re-render via AjaxRequest.
 *  
 * @author vdemay
 *
 */
public abstract class AbstractRequireDojoBehavior extends AbstractDefaultDojoBehavior
{
	private RequireDojoLibs libs = new RequireDojoLibs();
	
	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractDefaultDojoBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		setRequire(libs);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		
		Iterator ite = libs.iterator();
		while(ite.hasNext()){
			require += getRequire((String)ite.next());
		}
		
		require += "\n";
		require += "</script>\n";
		
		response.renderString(require);
	}
	
	/**
	 * allow subclass to register new Dojo require libs
	 * @param libs
	 */
	public abstract void setRequire(RequireDojoLibs libs);
	
	
	private String getRequire(String lib){
		return "	dojo.require(\"" + lib + "\")\n";
	}
	
	/**
	 * this method is used to interpret dojoWidgets rendered via XMLHTTPRequest
	 */
	protected void onComponentRendered() {
		//if a Dojo Widget is rerender needs to run some javascript to refresh it
		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).appendJavascript("djConfig.searchIds = ['" + getComponent().getMarkupId() + "'];dojo.hostenv.makeWidgets()");
			onComponentReRendered(((AjaxRequestTarget)RequestCycle.get().getRequestTarget()));
		}
	}
	
	/**
	 * Add Javascript scripts when a dojo component is Rerender in a {@link AjaxRequestTarget}
	 * @param ajaxTarget
	 */
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget) {
		
	}
	
	protected void onComponentTag(ComponentTag tag){
		super.onComponentTag(tag);
		tag.put("widgetId", getComponent().getMarkupId());
	}
	
	/**
	 * @author vdemay
	 *
	 */
	public class RequireDojoLibs extends HashSet{
		
	}

}
