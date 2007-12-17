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
package org.wicketstuff.dojo.markup.html.container;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.markup.html.container.tab.DojoTabContainer;

/**
 * A panel that can be used as a {@link DojoSimpleContainer}. <br/>
 * It can be used as child of a {@link IDojoContainer}
 * @author Vincent Demay
 * @version SVN: $Id$
 *
 */
@SuppressWarnings("serial")
public abstract class DojoPanelContainer extends Panel implements IDojoContainer
{
	/**
	 * Title used in titled container such as {@link DojoTabContainer}
	 */
	private String title;
	
	/**
	 * Construct 
	 * @param id panel id
	 * @param model model associated with the panel
	 * @param title Title used in titled container such as {@link DojoTabContainer}
	 */
	public DojoPanelContainer(String id, IModel model, String title) {
		super(id, model);
		this.title = title;
		add(new DojoSimpleContainerHandler());
	}
	
	/**
	 * Construct 
	 * @param id panel id
	 * @param title Title used in titled container such as {@link DojoTabContainer}
	 */
	public DojoPanelContainer(String id, String title) {
		this(id, null, title);
	}
	
	/**
	 * add attributes on component tag
	 * @param tag 
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_CONTENTPANE);
		tag.put("label", getTitle());
	}
	
	
	/**
	 * Return the Container title
	 * @return container title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Set the container title
	 * @param title container title
	 */
	public void setTitle(String title){
		this.title = title;
	}

}
