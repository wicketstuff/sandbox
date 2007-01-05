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
package wicket.contrib.dojo.markup.html.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.convert.ConversionException;

/**
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public class DatePicker extends TextField<Date>{
	
	private SimpleDateFormat formatter;
	private String pattern;

	/**
	 * @param parent
	 * @param id
	 * @param model
	 * @param pattern
	 */
	public DatePicker(MarkupContainer parent, String id, IModel<Date> model, String pattern)
	{
		super(parent, id, model);
		add(new DatePickerHandler());
		this.setOutputMarkupId(true);
		setDatePattern(pattern);
	}
	
	/**
	 * Set the date pattern
	 * @param pattern date pattern example %d/%m/%y
	 */
	public void setDatePattern(String pattern){
		this.pattern = pattern;
		formatter = new SimpleDateFormat(getSimpleDatePattern());
	}
	
	private String getSimpleDatePattern(){
		return pattern.replace("%d", "dd").replace("%Y", "yyyy").replace("%m", "MM");
	}


	@Override
	protected String getInputType()
	{
		return "text";
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		if (getValue() != null){
			tag.put("date",  getValue());
		}else {
			tag.put("date", "");
		}
		tag.put("dojoType", "dropdowndatepicker");
		tag.put("dateFormat", this.pattern);
		tag.put("inputName", this.getId());
	}

	/**
	 * Set the date picker effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("containerToggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("containerToggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}

	/**
	 * @see FormComponent#getModelValue()
	 */
	@Override
	public final String getModelValue()
	{
		if (getModelObject() != null){
			return formatter.format((Date)getModelObject());
		}
		return null;
	}


	@Override
	protected Date convertValue(String[] value) throws ConversionException
	{
		if (value != null){
			try
			{
				return formatter.parse(value[0]);
			}
			catch (ParseException e)
			{
				new ConversionException(e);
			}
		}
		return null;
	}
}
