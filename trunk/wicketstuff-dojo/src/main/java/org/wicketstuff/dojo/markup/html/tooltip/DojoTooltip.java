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
package org.wicketstuff.dojo.markup.html.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.IDojoWidget;
import org.wicketstuff.dojo.toggle.DojoToggle;
import org.wicketstuff.dojo.widgets.HideWebMarkupContainer;

/**
 * Tooltip
 * <p>
 * </p>
 * @author vdemay
 *
 */
@SuppressWarnings("serial")
public class DojoTooltip extends HideWebMarkupContainer implements IDojoWidget
{
	
	private Component component;
	
	/**
	 * @param id
	 * @param component
	 */
	public DojoTooltip(String id, Component component)
	{
		super(id);
		this.component = component;
		add(new DojoTooltipHandler());
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_TOOLTIP;
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("connectId", component.getMarkupId());
	}
	
	/**
	 * @return tooltiped component
	 */
	public Component getTooltipedComponent(){
		return component;
	}
	
	/**
	 * Set the dialog effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model(toggle.getDuration() + ""),""));
	}

}
