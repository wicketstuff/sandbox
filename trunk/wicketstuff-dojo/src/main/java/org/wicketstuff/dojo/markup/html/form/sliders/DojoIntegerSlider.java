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
package org.wicketstuff.dojo.markup.html.form.sliders;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.dojo.IDojoWidget;

/**
 * A Slider a get an Integer
 * <pre>
 * 		DojoIntegerSlider slider = new DojoIntegerSlider("slider1", model);
 *		add(slider);
 *		slider.setStart(0);
 *		slider.setEnd(100);
 * </pre>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoIntegerSlider extends Panel 
{
	
	private TextField value;
	private DojoSlider slider;
	
	/**
	 * Construct an Integer Slider 
	 * @param id slider id
	 * @param model model
	 */
	public DojoIntegerSlider(String id, IModel model)
	{
		super( id, model);
		createSlider(model);
	}

	/**
	 * Private :  initilize the slider
	 * @param model 
	 */
	private void createSlider(IModel model){
		value = new TextField("value", model);
		value.setOutputMarkupId(true);
		value.add(new AjaxEventBehavior("onchange"){
			protected void onEvent(AjaxRequestTarget target)
			{
				target.appendJavascript("dojo.widget.byId('" + slider.getMarkupId() + "').setValue(document.getElementById('" + value.getMarkupId() + "').value)");			
			}
		});
		this.add(value);
		
		slider = new DojoSlider("slider", value);
		slider.add(new DojoIntegerSliderHandler());
		if (getModelObject() != null){
			slider.setInitialValue(getModelObject().toString());
		}
		this.add(slider);
	}
	
	/**
	 * Set the minimum value for the slider
	 * @param start minimum value for the slider
	 */
	public void setStart(int start){
		slider.setStart(Integer.toString(start));
		setNumberSelection();
	}
	
	/**
	 * Set the maximum value for the slider
	 * @param end maximum value for the slider
	 */
	public void setEnd(int end){
		slider.setEnd(Integer.toString(end));
		setNumberSelection();
	}
	
	/**
	 * Private : calculate the number of possible values
	 */
	private void setNumberSelection(){
		if (slider.getEnd() != null && slider.getStart() != null){
			slider.setSelectableValueNumber(new Integer(Integer.parseInt(slider.getEnd()) + 1 - Integer.parseInt(slider.getStart())));
		}
	}
	
	/**
	 * Set the html slider lenght
	 * Warning : choose a lenght at least as big as the range of the slider
	 * @param length
	 */
	public void setLength(int length)
	{
		slider.setLength(length);
	}
	
	/**
	 * Set true to see the value of false otherwise
	 * @param visible true to see the value of false otherwise
	 */
	public void setValueVisible(boolean visible){
		slider.setValueVisible(visible);
	}
	
	
	
	

}
