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
package org.apache.wicket.contrib.dojo.markup.html.container;

import org.apache.wicket.ajax.AjaxRequestTarget;

public abstract class AbstractDojoChangeContainer extends AbstractDojoContainer{

	public AbstractDojoChangeContainer(String id) {
		super(id);
	} 
	
	public AbstractDojoChangeContainer(String id, String title) {
		super(id, title);
	}

	/**
	 * Current selected 
	 */
	private IDojoContainer selected;
	
	/**
	 * child select by default
	 * @param toSelect child select by default
	 */
	public void setSelected(IDojoContainer toSelect) {
		selected = toSelect;
	}
	
	/**
	 * return the current selected tab id
	 * @return the current selected tab id
	 */
	public String getSelectedChildId(){
		if (selected != null){
			return selected.getMarkupId();
		}
		else return "";
	}
	
	/**
	 * return the current container selected in the tab container
	 * @return the selected container in the tab container
	 */
	public IDojoContainer getSelectedTab(){
		return selected;
	}

	/**
	 * Ovewrite this methos to handle clicks on tab
	 * @param tab new tab selected
	 */
	public void onSelectionChange(IDojoContainer selected, AjaxRequestTarget target)
	{
				
	}


}
