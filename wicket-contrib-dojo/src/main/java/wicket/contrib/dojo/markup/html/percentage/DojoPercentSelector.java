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
package wicket.contrib.dojo.markup.html.percentage;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_PERCENTSELECTOR;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.contrib.dojo.markup.html.percentage.model.PercentageRanges;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;

/**
 * <p>
 * Dojo Percent Selector is a widget to select some percentage 
 * dependents each others. The sum of all percentage should be
 * 100.
 * </p>
 * <p> 
 * <p><b>Usage</b>
 * <pre>
 * DojoPercentSelector percent = new DojoPercentSelector(this, "percent", new Model&lt;PercentageRanges>(ranges));	
 * </pre>
 * <u>Be carrefull </u>: model associated with this widget should be {@link PercentageRanges}. Others models are not allowed. 
 * </p>
 *  
 * @author Vincent Demay
 *
 */
public class DojoPercentSelector extends WebMarkupContainer {

	/**
	 * The widget Constructor
	 * @param parent parent where the widget will be added
	 * @param id widget id
	 * @param model model associated with the widget. <b>Only PercentageRange</b>
	 */
	@SuppressWarnings("unchecked")
	public DojoPercentSelector(MarkupContainer parent, String id, IModel<PercentageRanges> model)
	{
		super(parent, id, model);
		this.add(new DojoPercentSelectorHandler());
	}
	

	/**
	 * Set the model associated with this widget
	 * @param model model associated with the widget. <b>Only PercentageRange</b>
	 * @return the component
	 */
	@SuppressWarnings("unchecked")
	public Component setModel(IModel model)
	{
		if (!(model.getObject() instanceof PercentageRanges)){
			throw new WicketRuntimeException("Model for a DojoPercentSelector should be a PercentageRanges instance");
		}
		return super.setModel(model);
	}

	/**
	 * The widget constructor
	 * @param parent parent where the widget will be added
	 * @param id widget id
	 */
	public DojoPercentSelector(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.add(new DojoPercentSelectorHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_PERCENTSELECTOR);
		tag.put("widgetId", getMarkupId());

		tag.put("templatePath", urlFor(new ResourceReference(DojoPercentSelector.class, "PercentSelector.htm")));
		tag.put("templateCssPath", urlFor(new ResourceReference(DojoPercentSelector.class, "PercentSelector.css")));
	}

}
