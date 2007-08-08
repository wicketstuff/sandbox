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
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.wicketstuff.dojo.DojoLocaleManager;
import org.wicketstuff.dojo.toggle.DojoToggle;

/**
 * <p>
 * A DatePicker to select a Date in a popup (not navigator but Js)
 * You can add effect to this popup adding a {@link DojoToggle} with the setToggle method.<br/>
 * This component should be associated to an <b>input</b> in the markup
 * </p>
 * <p>
 * This picker can accept a minimum/maximun Date : 
 * use {@link #setMinimumDate(Date)} / {@link #setMaximumDate(Date)} 
 * If a input date in not in the range a {@link ConversionException} will be thrown
 * and handle by a {@link FeedbackPanel}
 * </p>
 * <p>
 * <b><u>Sample</u></b>
 * <pre>
 * [...]
 * DojoDatePicker datePicker = new DojoDatePicker("date", new Model(date));
 * datePicker.setToggle(new DojoWipeToggle(200));
 * form.add(datePicker);
 * </pre>
 * </p>
 * 
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 */
@SuppressWarnings("serial")
public class DojoDatePicker extends TextField {
    
    private SimpleDateFormat formatter;
    private String displayFormat;
    private Locale locale;
    private boolean allowInput = true;
    
    private Date minimumDate;
    private Date maximumDate;

    /**
     * @param parent
     * @param id
     * @param model
     * @param pattern
     */
    public DojoDatePicker(String id, IModel model, String displayFormat) {
        this(id, model, displayFormat, null);
    }
    
    /**
     * @param parent
     * @param id
     * @param model
     * @param pattern
     * @param timeZone 
     */
    public DojoDatePicker(String id, IModel model, String displayFormat, TimeZone timeZone) {
        super(id, model, Date.class);     
        this.displayFormat = displayFormat;
        add(new DojoDatePickerHandler());
        this.setOutputMarkupId(true);
        formatter = new SimpleDateFormat(getInternalDatePattern());
        if (timeZone != null) {
            formatter.setTimeZone(timeZone);
        }
    }
    
    public static String getInternalDatePattern() {
        return "yyyy-MM-dd";
    }

    public DojoDatePicker(String id, String displayFormat) {
        this(id, null, displayFormat);
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        tag.put("dojoType", "SimpleDropdownDatePicker");
        tag.put("displayFormat", getDisplayFormat());
        String localeString = getLocaleAsString();
        if (localeString != null) {
            tag.put("lang", localeString);
        }
        if (!this.allowInput) {
            tag.put("inputNotAllowed", "true");
        }
        if (getOutputMarkupId()) {
            tag.put("inputId", getMarkupId() + "_input");
        }
    }

    /**
     * Set the date picker effect
     * @param toggle
     */
    public void setToggle(DojoToggle toggle) {
        this.add(new AttributeAppender("containerToggle", new Model(toggle.getToggle()),""));
        this.add(new AttributeAppender("containerToggleDuration", new Model(toggle.getDuration() + ""),""));
    }
    
    @Override
    public IConverter getConverter(Class type) {
    	return new IConverter() {

			public Object convertToObject(String value, Locale locale) {
				if (value == null || "".equals(value)) {
					return null;
				}
  
				try {
					Date parsed = formatter.parse(value);
					Long parsedTime = parsed.getTime();
					//check if the date is in the range
					if (minimumDate != null && minimumDate.getTime() > parsedTime) {
						throw new ConversionException(getInputName() + " should not be less than " + formatter.format(minimumDate));
					}
					if (maximumDate != null && maximumDate.getTime() < parsedTime) {
						throw new ConversionException(getInputName() + " should not be greater than " + formatter.format(maximumDate));
					}
					
					return parsed;
				} catch (ParseException e) {
					throw new ConversionException(getInputName() + " is not a valid date");
				}
			}

			public String convertToString(Object value, Locale locale) {
				if (value != null) {
					if(!(value instanceof Date)) {
						throw new IllegalArgumentException("A Date is expected for the model");
					}
					return formatter.format((Date)value);
				}
				return null;
			}
    	};
    }
    
    public String getDisplayFormat() {
        return displayFormat;
    }


    public void setDisplayFormat(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    public Locale getLocale() {
        return locale;
    }
    
    public String getLocaleAsString() {
        if (locale == null) return null;
        return locale.toString().replace('_', '-').toLowerCase();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        DojoLocaleManager.getInstance().addLocale(locale);
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
    
    /**
     * Set the minimun date that could be inpu in the field
     * @param minimumDate minimun date that could be inpu in the field
     */
    public void setMinimumDate(Date minimumDate){
    	this.minimumDate = minimumDate;
    }

	/**
     * Set the maximum date that could be inpu in the field
     * @param maximumDate maximum date that could be inpu in the field
     */
	public void setMaximumDate(Date maximumDate)
	{
		this.maximumDate = maximumDate;
	}
    
}
