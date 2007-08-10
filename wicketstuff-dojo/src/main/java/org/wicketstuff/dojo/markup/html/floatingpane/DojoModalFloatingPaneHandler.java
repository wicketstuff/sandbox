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
package org.wicketstuff.dojo.markup.html.floatingpane;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.protocol.http.WebRequest;
import org.wicketstuff.dojo.AbstractDojoWidgetBehavior;

/**
 * Handler for {@link DojoModalFloatingPane}
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoModalFloatingPaneHandler extends AbstractDojoWidgetBehavior
{
	
	private static final String SHOW = "show";
	private static final String HIDE = "hide"; 
	
	protected void respond(AjaxRequestTarget target)
	{
		DojoModalFloatingPane pane = (DojoModalFloatingPane)getComponent();
		if (SHOW.equals(((WebRequest)RequestCycle.get().getRequest()).getParameter("state"))){
			pane.onShow(target);
		}
		if (HIDE.equals(((WebRequest)RequestCycle.get().getRequest()).getParameter("state"))){
			pane.onHide(target);
		}
	}
	
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.FloatingPane");
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		DojoModalFloatingPane pane = (DojoModalFloatingPane)getComponent();
		if (pane.isNotifyHide()){
			tag.put("onHide", getCallbackScript(HIDE));
		}
		if (pane.isNotifyShow()){
			tag.put("onShow", getCallbackScript(SHOW));
		}
	}
	
	/**
	 * To send datas to the server : the state of the pane
	 * @param state "show" or hide 
	 * @return
	 */
	protected CharSequence getCallbackScript(String state)
	{
		return generateCallbackScript("wicketAjaxGet('"
				+ getCallbackUrl() + "&state=" + state + "'");
	}

}
