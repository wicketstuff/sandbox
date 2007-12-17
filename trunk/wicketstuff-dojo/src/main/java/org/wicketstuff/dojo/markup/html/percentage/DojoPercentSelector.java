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
package org.wicketstuff.dojo.markup.html.percentage;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.IDojoWidget;
import org.wicketstuff.dojo.markup.html.percentage.model.PercentageRanges;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * <p>
 * Dojo Percent Selector is a widget to select some percentage 
 * dependents each others. The sum of all percentage should be
 * 100.
 * </p>
 * <p> 
 * <p><b>Usage</b>
 * <pre>
 * DojoPercentSelector percent = new DojoPercentSelector("percent", new Model&lt;PercentageRanges>(ranges));	
 * add(percent);
 * </pre>
 * <u>Be carrefull </u>: model associated with this widget should be {@link PercentageRanges}. Others models are not allowed. 
 * </p>
 *  
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoPercentSelector extends WebMarkupContainer implements IDojoWidget {

	/**
	 * The widget Constructor
	 * @param id widget id
	 * @param model model associated with the widget. <b>Only PercentageRange</b>
	 */
	public DojoPercentSelector(String id, IModel model)
	{
		super(id, model);
		if (!(model.getObject() instanceof PercentageRanges)){
			throw new WicketRuntimeException("Model for a DojoPercentSelector should be a PercentageRanges instance");
		}
		this.add(new DojoPercentSelectorHandler());
	}
	

	/**
	 * Set the model associated with this widget
	 * @param model model associated with the widget. <b>Only PercentageRange</b>
	 * @return the component
	 */
	public Component setModel(IModel model)
	{
		if (!(model.getObject() instanceof PercentageRanges)){
			throw new WicketRuntimeException("Model for a DojoPercentSelector should be a PercentageRanges instance");
		}
		return super.setModel(model);
	}

	/**
	 * The widget constructor
	 * @param id widget id
	 */
	public DojoPercentSelector(String id)
	{
		super(id);
		this.add(new DojoPercentSelectorHandler());
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_PERCENTSELECTOR;
	}

	//FIXME : Usefull????
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("widgetId", getMarkupId());
	}

}
