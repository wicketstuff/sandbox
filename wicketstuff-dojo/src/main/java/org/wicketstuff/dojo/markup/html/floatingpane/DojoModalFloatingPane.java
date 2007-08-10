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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.toggle.DojoToggle;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.Model;

/**
 * <p>
 * 	This widget has the same skin as {@link DojoFloatingPane} but modal 
 * </p>
 * <p>
 * 	Can also notify to the server hide or show event : 
 *  <code>
 *  	modal.setNotify[Hide|Show](true);
 *  </code>
 * </p>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{
	private String bgColor="white";
	private String bgOpacity="0.5";
	
	/**
	 * Send notification to server on hide/show event
	 */
	private boolean notifyHide = false;
	private boolean notifyShow = false;

	/**
	 * Modal floating pane constructor
	 * @param id widget Id
	 */
	public DojoModalFloatingPane(String id)
	{
		super(id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_MODALFLOATINGPANE;
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
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
		this.add(new AttributeAppender("toggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model(toggle.getDuration() + ""),""));
	}

	/**
	 * If true, send a notification to serveur when Hide.<br/>
	 * Calls onHide method
	 * @param notifyHide
	 */
	public void setNotifyHide(boolean notifyHide) {
		this.notifyHide = notifyHide;
	}
	
	/**
	 * Called when modal is hidden if setNotifyHide is set to true
	 * <br/><b>WARNING</b> : if setNotifyShow is set to true before the page loading, This triggered 
	 * will be called during the page loading because of DOJO. Dojo hidde the modalFloatingPane
	 * calling hide during loading  
	 * @param target {@link AjaxRequestTarget}
	 */
	public void onHide(AjaxRequestTarget target){
		//Do nothing
	}

	/**
	 * If true, send a notification to serveur when Show
	 * Calls onShow method
	 * @param notifyHide
	 */
	public void setNotifyShow(boolean notifyShow) {
		this.notifyShow = notifyShow;
	}
	
	/**
	 * Called when modal is shown if setNotifyShow is set to true
	 * @param target {@link AjaxRequestTarget}
	 */
	public void onShow(AjaxRequestTarget target){
		//Do nothing
	}

	/**
	 * Do we need to notify Hide event
	 * @return true if yes false otherwise
	 */
	public boolean isNotifyHide() {
		return notifyHide;
	}

	/**
	 * Do we need to notify Show event
	 * @return true if yes false otherwise
	 */
	public boolean isNotifyShow() {
		return notifyShow;
	}
	
	
}
