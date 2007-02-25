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
import java.util.Locale;
import java.util.TimeZone;

import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.DojoLocaleManager;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.convert.ConversionException;

/**
 * <p>
 * A TimePicker to select a Time in a popup (not navigator but Js)
 * You can add effect to this popup adding a {@link DojoToggle} with the setToggle method.<br/>
 * This component should be associated to an <b>input</b> in the markup
 * </p>
 * <p>
 * <b><u>Sample</u></b>
 * <pre>
 * [...]
 * DojoTimePicker timePicker = new DojoTimePicker("time", new Model(date));
 * timePicker.setToggle(new DojoWipeToggle(200));
 * form.add(timePicker);
 * </pre>
 * </p>
 * 
 */
public class DojoTimePicker extends TextField {

	private SimpleDateFormat formatter;
	private String displayFormat;
	private Locale locale;
	
	/**
	 * @param id
	 * @param model
	 * @param locale 
	 * @param displayFormat 
	 * @param timeZone 
	 */
	public DojoTimePicker(String id, IModel model, Locale locale, String displayFormat, TimeZone timeZone) {
		super(id, model);
		add(new DojoTimePickerHandler());
		this.setOutputMarkupId(true);
		setLocale(locale);
		this.displayFormat = displayFormat;
		formatter = new SimpleDateFormat(getInternalDatePattern());
		if (timeZone != null){
			formatter.setTimeZone(timeZone);
		}
	}
	
	/**
	 * @param id
	 * @param model
	 * @param locale 
	 * @param displayFormat 
	 */
	public DojoTimePicker(String id, IModel model, Locale locale, String displayFormat) {
		this(id, model, locale, displayFormat, null);
	}

	/**
	 * @param id
	 * @param locale 
	 * @param displayFormat 
	 */
	public DojoTimePicker(String id, Locale locale, String displayFormat) {
		this(id, null, locale, displayFormat);
	}
	
	private String getLocaleAsString() {
		if (locale == null) return null;
		return locale.toString().replace("_", "-").toLowerCase();
	}

	protected void onComponentTag(ComponentTag tag)	{
		super.onComponentTag(tag);
		tag.put("dojoType", "DropdownTimePicker");
		if(getLocaleAsString() != null) {
			tag.put("lang", getLocaleAsString());
		}
		tag.put("displayFormat", getDisplayFormat());
	}
	
	public static String getInternalDatePattern() {
		return "HH:mm";
	}

	/**
	 * Set the date picker effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("containerToggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("containerToggleDuration", new Model(toggle.getDuration() + ""),""));
	}

	/**
	 * @see FormComponent#getModelValue()
	 */
	public final String getModelValue()	{
		if (getModelObject() != null) {
			return formatter.format((Date)getModelObject());
		}
		return null;
	}

	protected Object convertValue(String[] value) throws ConversionException {
		//	FIXME hack waiting for a new dojo version (must get the hidden value (value[0]) instead of the display value (value[1]))
		if (value == null || "".equals(value[1])) {
			return null;
		}
		
		try {
			return formatter.parse(value[1]);
		} catch (ParseException e) {
			throw new ConversionException(e);
		}
		
	}

	public String getDisplayFormat() {
		return displayFormat;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
		if(locale != null) {
			DojoLocaleManager.getInstance().addLocale(locale);
		}
	}
	
	public Locale getLocale() {
		return locale;
	}

}
