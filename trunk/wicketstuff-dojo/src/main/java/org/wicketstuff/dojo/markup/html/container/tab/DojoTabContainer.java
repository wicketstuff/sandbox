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
package org.wicketstuff.dojo.markup.html.container.tab;

import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.markup.html.container.AbstractDojoChangeContainer;
import org.wicketstuff.dojo.widgetloadingpolicy.IDojoWidgetLoadingPolicy;
import org.apache.wicket.markup.ComponentTag;

/**
 * <p>
 * TabContainer widget where
 * AbstractDojoContainer should be added 
 * </p>
 * <p>
 * 	<b>Sample</b>
 *  <pre>
 * package org.wicketstuff.dojo.examples;
 * 
 * import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
 * import org.wicketstuff.dojo.markup.html.container.page.DojoPageContainer;
 * import org.wicketstuff.dojo.markup.html.container.tab.DojoTabContainer;
 * import org.apache.wicket.markup.html.WebPage;
 * 
 * public class TabContainerSample extends WebPage {
 * 
 * 	public TabContainerSample() {
 * 		super();
 * 		DojoTabContainer container = new DojoTabContainer("tabContainer");
 * 		add(container);
 * 		container.setHeight("500px");
 * 		container.add(new DojoSimpleContainer("tab1", "title1"));
 * 		container.add(new DojoSimpleContainer("tab2", "title2"));
 * 		
 * 		DojoPageContainer page = new DojoPageContainer("tab3", DatePickerShower.class);
 * 		page.setTitle("title3");
 * 		container.add(page);
 * 		
 * 	}
 * 
 * }
 * 
 *  </pre>
 * </p>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoTabContainer extends AbstractDojoChangeContainer
{
	
	private String tabPosition;
	
	/**
	 * Tab position on top see {@link #setTabPosition(String)}
	 */
	public final static String TAB_POS_TOP 		= "top";
	/**
	 * Tab position on bottom see {@link #setTabPosition(String)}
	 */
	public final static String TAB_POS_BOTTOM 	= "bottom";
	/**
	 * Tab position on left see {@link #setTabPosition(String)}
	 */
	public final static String TAB_POS_LEFT 	= "left-h";
	/**
	 * Tab position on right see {@link #setTabPosition(String)}
	 */
	public final static String TAB_POS_RIGHT 	= "right-h";
	
	
	/**
	 * Construct a DojoTabContainer
	 * @param id container id
	 * @param title container title
	 * @param loadingPolicy
	 */
	public DojoTabContainer(String id, String title, IDojoWidgetLoadingPolicy loadingPolicy)
	{
		super(id, title);
		add(new DojoTabContainerHandler(loadingPolicy));
	}
	
	/**
	 * Construct a DojoTabContainer
	 * @param id container id
	 * @param title container title
	 */
	public DojoTabContainer(String id, String title)
	{
		super(id, title);
		add(new DojoTabContainerHandler());
	}

	/**
	 * Construct a DojoTabContainer
	 * @param id container id
	 */
	public DojoTabContainer(String id)
	{
		this(id, null);
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_TABCONTAINER;
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("label", getTitle());
		tag.put("selectedChild", getSelectedChildId());
		tag.put("labelPosition", tabPosition);
	}
	
	/**
	 * Set the tab Position used constants : TAB_POS_*
	 * @param tabPosition
	 */
	public void setTabPosition(String tabPosition)
	{
		this.tabPosition = tabPosition;
	}

}
