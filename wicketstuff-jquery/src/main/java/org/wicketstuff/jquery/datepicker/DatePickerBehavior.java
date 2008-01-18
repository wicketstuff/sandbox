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
package org.wicketstuff.jquery.datepicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.DateConverter;
import org.wicketstuff.jquery.JQueryBehavior;
import org.wicketstuff.misc.behaviors.CompositeBehavior;
import org.wicketstuff.misc.behaviors.SimpleAttributeAppender;

/**
 * Add support of the <a href="http://kelvinluck.com/assets/jquery/datePicker/v2/demo/">datePicker</a>.
 *
 * @author David Bernard (dwayne)
 * @created 2007-09-08
 */
@SuppressWarnings(value = "serial")
public class DatePickerBehavior extends JQueryBehavior {

    public static final ResourceReference DATE_JS = new CompressedResourceReference(DatePickerBehavior.class, "date.js");
    public static final ResourceReference JQUERY_DATEPICKER_JS = new CompressedResourceReference(DatePickerBehavior.class, "jquery.datePicker.js");
    public static final ResourceReference DATEPICKER_CSS = new CompressedResourceReference(DatePickerBehavior.class, "datePicker.css");
    private DatePickerOptions options_;
    private String format_;

    public DatePickerBehavior() {
        this(null);
    }

    public DatePickerBehavior(DatePickerOptions options) {
        if (options == null) {
            options = new DatePickerOptions();
        }
        options_ = options;
    }

    private void convertDateInOptions(IConverter cnv, String key) {
        Date date = (Date)options_.get(key);
        if (date != null) {
            options_.set(key, cnv.convertToString(date, null));
        }        
    }
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderCSSReference(DATEPICKER_CSS);
        response.renderJavascriptReference(DATE_JS);
        response.renderJavascriptReference(JQUERY_DIMENSIONS_JS);
        try {
            WebClientInfo info = (WebClientInfo) RequestCycle.get().getClientInfo();
            if (info.getUserAgent().contains("MSIE")) {
                response.renderJavascriptReference(JQUERY_BGIFRAME_JS);
            }
        } catch (ClassCastException exc) {
            logger().info("can't find info about client", exc);
        }

        // if (ie) {
        // response.renderJavascriptReference(JQUERY_BGIFRAME_JS);
        // }
        response.renderJavascriptReference(JQUERY_DATEPICKER_JS);
    }

    @Override
    protected CharSequence getOnReadyScript() {
        String selector = ".date-pick";
        Component component = getComponent();
        if (!(component instanceof Page)) {
            selector = "#" + component.getMarkupId();
        }
        return String.format("Date.format = '%s';$('%s').datePicker(%s)", format_, selector, options_.toString(false));
    }

    @Override
    protected void onBind() {
        super.onBind();
        Component component = getComponent();
        if (component instanceof TextField) {
            component.setOutputMarkupId(true);
            
            if (component instanceof ITextFormatProvider) {
            	format_ = ((ITextFormatProvider) component).getTextFormat();
            } else {
	            TextField tf = (TextField) component;
	            IConverter cnv = tf.getConverter(tf.getType());
	            if ((cnv != null) && (DateConverter.class.isAssignableFrom(cnv.getClass()))) {
	            	SimpleDateFormat sdf = (SimpleDateFormat) ((DateConverter) cnv).getDateFormat(component.getLocale()); 
	            	format_ = sdf.toLocalizedPattern().toLowerCase();
	            }
	            
	            convertDateInOptions(cnv, "startDate");
	            convertDateInOptions(cnv, "endDate");
            }
            
            component.add(getDatePickerStyle());
        }
    }

    public IBehavior getDatePickerStyle() {
        return new CompositeBehavior(
            new SimpleAttributeAppender("class", "date-pick", " "),
            new SimpleAttributeModifier("size", String.valueOf(format_.length())),
            new SimpleAttributeModifier("maxlength", String.valueOf(format_.length())),
            new SimpleAttributeModifier("title", format_)
        );
    }
}