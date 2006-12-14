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
package wicket.contrib.markup.html.form.sliders;

import wicket.MarkupContainer;
import wicket.ajax.AjaxEventBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.ClientEvent;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * A Slider a get an Integer
 * <pre>
 * 		DojoIntegerSlider slider = new DojoIntegerSlider(parent, "slider1", model);
		slider.setStart(0);
		slider.setEnd(100);
 * </pre>
 * @author Vincent Demay
 *
 */
public class DojoIntegerSlider extends Panel<Integer>
{
	
	private TextField<Integer> value;
	private DojoSlider slider;
	private Label label;
	
	/**
	 * Construct an Integer Slider 
	 * @param parent parent where the slider will be addes
	 * @param id slider id
	 * @param model model
	 */
	public DojoIntegerSlider(MarkupContainer parent, String id, IModel<Integer> model)
	{
		super(parent, id, model);
		createSlider(parent, model);
	}

	/**
	 * Private :  initilize the slider
	 * @param parent 
	 * @param model 
	 */
	private void createSlider(MarkupContainer parent, IModel<Integer> model){
		value = new TextField<Integer>(this, "value", model);
		value.setOutputMarkupId(true);
		value.add(new AjaxEventBehavior(ClientEvent.CHANGE){
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				target.appendJavascript("dojo.widget.byId('" + slider.getMarkupId() + "').setValue(document.getElementById('" + value.getMarkupId() + "').value)");			
			}
		});
		
		slider = new DojoSlider(this, "slider", value);
		slider.add(new DojoIntegerSliderHandler());
		if (getModelObject() != null){
			slider.setInitialValue(getModelObject().toString());
		}
	}
	
	/**
	 * Set the minimum value for the slider
	 * @param start minimum value for the slider
	 */
	public void setStart(Integer start){
		slider.setStart(start.toString());
		setNumberSelection();
	}
	
	/**
	 * Set the maximum value for the slider
	 * @param end maximum value for the slider
	 */
	public void setEnd(Integer end){
		slider.setEnd(end.toString());
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
