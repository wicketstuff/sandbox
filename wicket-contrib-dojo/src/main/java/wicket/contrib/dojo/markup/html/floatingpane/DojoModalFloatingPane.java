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
package wicket.contrib.dojo.markup.html.floatingpane;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_MODALFLOATINGPANE;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.model.Model;

/**
 * <p>
 * 	This widget has the same skin as {@link DojoFloatingPane} but it is modal 
 * </p>
 * <p>
 * 	<b>Sample</b>
 * <pre>
 * 	public class ModalPaneSample extends WebPage {
 *	
 *	public ModalPaneSample(PageParameters parameters){
 *		final DojoModalFloatingPane dialog = new DojoModalFloatingPane(this,"dialogFloating");
 *		dialog.setTitle("Sample...");
 *		dialog.setWidth("300px");
 *		dialog.setHeight("300px");
 *		dialog.setMinHeight("200px");
 *		dialog.setMinWidth("200px");
 *		new AjaxLink(this, "open"){
 *
 *			@Override
 *			public void onClick(AjaxRequestTarget target) {
 *				dialog.show(target);			
 *			}
 *			
 *		};
 *	}
 *}
 *
 *
 * </pre>
 * </p>
 * @author doume
 *
 */
public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{
	
	private String bgColor="white";
	private String bgOpacity="0.5";

	/**
	 * Modal floasting pane constructor
	 * @param parent parent where the widget will be added
	 * @param id widget Id
	 */
	public DojoModalFloatingPane(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_MODALFLOATINGPANE);
		tag.put("bgColor", bgColor);
		tag.put("bgOpacity", bgOpacity);
	}

	/**
	 * Background color for floating pane
	 * @return Background color for floating pane
	 */
	public String getBgColor()
	{
		return bgColor;
	}

	/**
	 * Background color for floating pane
	 * @param bgColor Background color for floating pane
	 */
	public void setBgColor(String bgColor)
	{
		this.bgColor = bgColor;
	}

	/**
	 * Background opacity for floating pane
	 * @return Background opacity for floating pane
	 */
	public String getBgOpacity()
	{
		return bgOpacity;
	}

	/**
	 * Background opacity for floating pane
	 * @param bgOpacity Background opacity for floating pane
	 */
	public void setBgOpacity(String bgOpacity)
	{
		this.bgOpacity = bgOpacity;
	}
	
	/**
	 * Set the dialog effect : see {@link DojoToggle}
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}
	
}
