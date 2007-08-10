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
package org.wicketstuff.dojo.markup.html.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.wicket.behavior.AttributeAppender;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.DojoLocaleManager;
import org.wicketstuff.dojo.IDojoWidget;
import org.wicketstuff.dojo.toggle.DojoToggle;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.AbstractConverter;

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
@SuppressWarnings("serial")
public class DojoTimePicker extends TextField implements IDojoWidget {

	private SimpleDateFormat formatter;
	private String displayFormat;
	private Locale locale;
	private boolean allowInput = false;
	
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
    
    @Override
    public IConverter getConverter(Class type) {
        return new TimePickerConvertor();
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
		return locale.toString().replace('_', '-').toLowerCase();
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_TIMEPICKER;
	}

	protected void onComponentTag(ComponentTag tag)	{
		super.onComponentTag(tag);
		if(getLocaleAsString() != null) {
			tag.put("lang", getLocaleAsString());
		}
		tag.put("displayFormat", getDisplayFormat());
		if (!this.allowInput){
			tag.put("inputNotAllowed", "true");
		}
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

	public boolean isAllowInput() {
		return allowInput;
	}

	/**
	 * Allow or not to input with keyboard in the field. If true, field can only be field by the date picker
	 * @param allowInput If true, field can only be field by the date picker
	 */
	public void setAllowInput(boolean allowInput) {
		this.allowInput = allowInput;
	}
    
	@Override
	//  FIXME hack waiting for a new dojo version (must get the hidden value (value[0]) instead of the display value (value[1]))
    //remove me when value will be store in input[1]
    public String getInput()
    {
        String[] input = getInputAsArray();
        if (input == null || input.length == 0)
        {
            return null;
        }
        else
        {
            return input[1];
        }
    }
	
    /**
     * A convertor to use with {@link DojoTimePicker}
     * @author Vincent Demay
     *
     */
    public class TimePickerConvertor implements IConverter{
        
        public Object convertToObject(String value, Locale locale) {
            if (value == null || "".equals(value)) {
                return null;
            }
            
            try {
                return formatter.parse(value);
            } catch (ParseException e) {
                throw new ConversionException(e);
            }
        }

        public String convertToString(Object value, Locale locale) {
            if (getModelObject() != null) {
                return formatter.format((Date)getModelObject());
            }
            return null;
        }

        
    }

}
