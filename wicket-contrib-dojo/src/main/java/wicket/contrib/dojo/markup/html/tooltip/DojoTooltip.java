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
package wicket.contrib.dojo.markup.html.tooltip;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.contrib.dojo.widgets.HideWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.model.Model;

/**
 * Tooltip
 * <p>
 * 	<b>Sample</b>
 * <pre>
 * 	public TooltipTestPage(PageParameters parameters)
 * 	{
 * 		target = new Label(this, "target", "point here for a simple tooltip");
 * 		new DojoTooltip(this, "stooltip", target);
 * 		target2 = new Label(this, "target2", "point here for a Dynamic tooltip");
 * 		DojoTooltip tooltip2 = new DojoTooltip(this, "tooltip2", target2);
 * 		new TooltipSimplePanel(tooltip2, "tooltip2Panel");
 * 		
 * 		//add student example
 *       stlabel = new Label(this, "stlabel", "Marco van de Haar");
 *         stlabel2 = new Label(this, "stlabel2", "Ruud Booltink"); 
 * 
 *         DojoTooltip studenttooltip = new DojoTooltip(this, "studenttooltip", stlabel);
 *         DojoTooltip studenttooltip2 = new DojoTooltip(this, "studenttooltip2", stlabel2);
 * 		
 *         new TooltipStudentPanel(studenttooltip, "student1", new CompoundPropertyModel(new TooltipStudentModel(1234, "van de Haar", "Marco", 'm')));
 *         new TooltipStudentPanel(studenttooltip2, "student2", new CompoundPropertyModel(new TooltipStudentModel(1235, "Booltink", "Ruud", 'm')));
 *                 
 * 	}
 * </pre>
 * </p>
 * @author vdemay
 *
 */
public class DojoTooltip extends HideWebMarkupContainer
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
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_TOOLTIP);
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
