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

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_TABCONTAINER;
import wicket.MarkupContainer;
import wicket.contrib.dojo.markup.html.container.AbstractDojoContainer;
import wicket.markup.ComponentTag;

/**
 * AbstractDojoContainer should be added 
 * @author Vincent Demay
 *
 */
public class DojoTabContainer extends AbstractDojoContainer
{
	private AbstractDojoContainer selected;
	
	/**
	 * Construct a DojoTabContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 * @param title container title
	 */
	public DojoTabContainer(MarkupContainer parent, String id, String title)
	{
		super(parent, id, title);
		add(new DojoTabHandler());
	}

	/**
	 * Construct a DojoTabContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoTabContainer(MarkupContainer parent, String id)
	{
		this(parent, id, null);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_TABCONTAINER);
		tag.put("label", getTitle());
		tag.put("selectedChild", getSelectedTabId());
	}

	/**
	 * Tab select by default
	 * @param tab Tab select by default
	 */
	public void setSelectedTab(AbstractDojoContainer tab){
		selected = tab;
	}
	
	/**
	 * return the current selected tab id
	 * @return the current selected tab id
	 */
	public String getSelectedTabId(){
		if (selected != null){
			return selected.getMarkupId();
		}
		else return "";
	}
	
	/**
	 * return the current container selected in the tab container
	 * @return the selected container in the tab container
	 */
	public AbstractDojoContainer getSelectedTab(){
		return selected;
	}

	/**
	 * Ovewrite this methos to handle clicks on tab
	 * @param tab new tab selected
	 */
	public void onSelectTab(AbstractDojoContainer tab)
	{
				
	}


}
