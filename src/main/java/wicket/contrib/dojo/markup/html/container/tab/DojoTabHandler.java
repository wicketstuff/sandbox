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
package wicket.contrib.dojo.markup.html.container.tab;

import wicket.Component;
import wicket.Component.IVisitor;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.markup.html.container.AbstractDojoContainer;
import wicket.markup.html.IHeaderResponse;

/**
 * AHandler for {@link DojoTabContainer}
 * @author vdemay
 *
 */
public class DojoTabHandler extends AbstractRequireDojoBehavior
{
	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.TabContainer");
	}

	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		AbstractDojoContainer container = (AbstractDojoContainer) getComponent();
		String tabId = container.getRequest().getParameter("tabId");
		String widgetPath = tabId;
		if (tabId.contains("_")){
			widgetPath = tabId.substring(tabId.lastIndexOf('_')+1, tabId.length());
		}
		AbstractDojoContainer tab = (AbstractDojoContainer)container.get(widgetPath);
		((DojoTabContainer)getComponent()).setSelectedTab(tab);
		((DojoTabContainer)getComponent()).onSelectTab(tab);
	}
	
	/**
	 * @return javascript that will generate an ajax GET request to this
	 *         behavior *
	 * @param recordPageVersion
	 *            if true the url will be encoded to execute on the current page
	 *            version, otherwise url will be encoded to execute on the
	 *            latest page version
	 */
	protected CharSequence getCallbackScript(boolean recordPageVersion)
	{
		return getCallbackScript("wicketAjaxGet('" + getCallbackUrl(recordPageVersion) + "&tabId=' + dojo.widget.byId('" + getComponent().getMarkupId() + "').selectedTabWidget.widgetId", null,null);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		//add onShow event on each child in the tabContainer
		DojoTabContainer container = (DojoTabContainer)getComponent();
		RenderHeadCreator head = new RenderHeadCreator(container);
		container.visitChildren(head);
		
		response.renderString(head.getHead());
	}
	
	/**
	 * Create the head contribution
	 * @author Vincent demay
	 */
	private class RenderHeadCreator implements IVisitor{

		private String toReturn;
		private DojoTabContainer container;
		
		public RenderHeadCreator(DojoTabContainer container)
		{
			toReturn = "";
			toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
			toReturn += "function initTab" + container.getMarkupId() + "(){\n";
			
			this.container = container;
			
		}

		public Object component(Component< ? > component)
		{
			String id = component.getMarkupId();
			toReturn += "	var widget = dojo.widget.byId('" + id + "')\n";
			toReturn += "	dojo.event.connect(widget,'onShow', function(){" + getCallbackScript() + "})\n";
			
			return CONTINUE_TRAVERSAL;
		}
		
		public String getHead(){
			toReturn += "}\n";
			toReturn += "dojo.event.connect(dojo, \"loaded\", \"initTab" + container.getMarkupId() + "\");\n";
			toReturn += "</script>\n";
			return toReturn;
		}
		
	}

}
