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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.wicketstuff.dojo.IDojoWidget;
import org.wicketstuff.dojo.widgets.StylingWebMarkupContainer;

/**
 * Dojo Abstract floating pane
 * @author Vincent Demay
 *
 */
public abstract class DojoAbstractFloatingPane extends StylingWebMarkupContainer implements IDojoWidget 
{
	
	private String title;
	private boolean rezisable;
	private boolean displayMinimizeAction;
	private boolean displayMaximizeAction;
	private boolean displayCloseAction;
	private boolean hasShadow;

	public DojoAbstractFloatingPane(String id)
	{
		super(id);
		title = "";
		rezisable = true;
		displayCloseAction = true;
		displayMaximizeAction = true;
		hasShadow = false;
	}
	

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("title", title);
		tag.put("templatePath", urlFor(new ResourceReference(DojoAbstractFloatingPane.class, "FloatingPane.htm")));
		tag.put("widgetId", getMarkupId());
		
		if (rezisable){
			tag.put("rezisable", "true");
		}
		else{
			tag.put("rezisable", "false");
		}
		
		if (displayMinimizeAction){
			tag.put("displayMinimizeAction", "true");
		}
		else{
			tag.put("displayMinimizeAction", "false");
		}
		
		if (displayMaximizeAction){
			tag.put("displayMaximizeAction", "true");
		}
		else{
			tag.put("displayMaximizeAction", "false");
		}
		
		if (displayCloseAction){
			tag.put("displayCloseAction", "true");
		}
		else{
			tag.put("displayCloseAction", "false");
		}
		
		if (hasShadow){
			tag.put("hasShadow", "true");
		}
		else{
			tag.put("hasShadow", "false");
		}
	}
	
	
	/**
	 * Show the modal pane
	 * @param target
	 */
	public void show(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').show()");
	}
	
	/**
	 * Refresh content and show the modal pane
	 * Should be called if this widget has been added to the target
	 * @param target
	 * @deprecated : use show instead
	 */
	public void refreshAndShow(AjaxRequestTarget target){
		target.appendJavascript("djConfig.searchIds = ['" + getMarkupId() + "'];dojo.hostenv.makeWidgets();");
		show(target);
	}
	
	/**
	 * Hide the modal pane
	 * @param target
	 */
	public void close(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').hide()");
	}


	public boolean isDisplayCloseAction()
	{
		return displayCloseAction;
	}


	public void setDisplayCloseAction(boolean displayCloseAction)
	{
		this.displayCloseAction = displayCloseAction;
	}


	public boolean isDisplayMaximizeAction()
	{
		return displayMaximizeAction;
	}


	public void setDisplayMaximizeAction(boolean displayMaximizeAction)
	{
		this.displayMaximizeAction = displayMaximizeAction;
	}


	public boolean isDisplayMinimizeAction()
	{
		return displayMinimizeAction;
	}


	public void setDisplayMinimizeAction(boolean displayMinimizeAction)
	{
		this.displayMinimizeAction = displayMinimizeAction;
	}


	public boolean isHasShadow()
	{
		return hasShadow;
	}


	public void setHasShadow(boolean hasShadow)
	{
		this.hasShadow = hasShadow;
	}


	public boolean isRezisable()
	{
		return rezisable;
	}


	public void setRezisable(boolean rezisable)
	{
		this.rezisable = rezisable;
	}


	public String getTitle()
	{
		return title;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}

}
