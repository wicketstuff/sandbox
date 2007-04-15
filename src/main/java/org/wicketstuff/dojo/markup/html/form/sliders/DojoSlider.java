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

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;

/**
 * Create a slider
 * Be carreful : do not use directly this component : there isn't any DataType : 
 * see {@link DojoIntegerSlider}
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoSlider extends WebComponent
{
	//input field
	private Component field;
	
	//start and End values
	private String start;
	private String end;
	
	//number of selectable values
	private Integer selectableValueNumber;
	
	//initialvalue
	private String initialValue;
	
	//lenght
	private int length;

	/**
	 * Create a Dojo Slider
	 * @param id slider id
	 * @param field associated field
	 */
	public DojoSlider(String id, final Component field)
	{
		super(id);
		this.field = field;
		this.start = "0";
		this.end = "0";
		this.length = 200;
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("backgroundSrc",urlFor(new ResourceReference(DojoIntegerSlider.class, "blank.gif")));
		tag.put("progressBackgroundSrc",urlFor(new ResourceReference(DojoIntegerSlider.class, "slider-bg.gif")));
		tag.put("handleSrc",urlFor(new ResourceReference(DojoIntegerSlider.class, "slider2.gif")));
		tag.put("onValueChanged", "document.getElementById('" + field.getMarkupId() + "').value = arguments[0];") ;
		tag.put("minimum", this.start);
		tag.put("maximum", this.end);
		tag.put("snapValues", this.selectableValueNumber.toString());
		tag.put("initialValue", this.initialValue);
		tag.put("backgroundSize", "width:" + this.length + "px;height:5px;");
	}

	/**
	 * Set the minimum value for the slider
	 * @param start minimum value for the slider
	 */
	public void setStart(String start)
	{
		this.start = start;		
	}

	/**
	 * Set the maximum value for the slider
	 * @param end maximum value for the slider
	 */
	public void setEnd(String end)
	{
		this.end = end;
	}
	
	/**
	 * Set the number of value available
	 * @param number number of value available
	 */
	public void setSelectableValueNumber(Integer number){
		this.selectableValueNumber = number;
	}

	/**
	 * return the minimum value of the slider
	 * @return the minimum value of the slider
	 */
	public String getEnd()
	{
		return end;
	}

	/**
	 * return the number of value available
	 * @return the number of value available
	 */
	public Integer getSelectableValueNumber()
	{
		return selectableValueNumber;
	}

	/**
	 * return the minimum value for the slider
	 * @return the minimum value for the slider
	 */
	public String getStart()
	{
		return start;
	}

	/**
	 * 
	 * @return the initial value
	 */
	public String getInitialValue()
	{
		return initialValue;
	}

	/**
	 * Set the initial value
	 * @param initialValue initial value
	 */
	public void setInitialValue(String initialValue)
	{
		this.initialValue = initialValue;
	}

	/**
	 * return  the html slider length 
	 * @return the html slider length 
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * set the html slider length 
	 * @param length the html slider length 
	 */
	public void setLength(int length)
	{
		this.length = length;
	}

	/**
	 * Set true to see the value of false otherwise
	 * @param visible true to see the value of false otherwise
	 */
	public void setValueVisible(boolean visible)
	{
		field.setVisible(visible);
	}
	
	

}
