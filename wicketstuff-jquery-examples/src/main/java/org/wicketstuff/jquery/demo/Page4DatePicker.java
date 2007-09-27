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
package org.wicketstuff.jquery.demo;

import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.jquery.datepicker.DatePickerBehavior;
import org.wicketstuff.jquery.datepicker.DatePickerOptions;

@SuppressWarnings("serial")
public class Page4DatePicker extends PageSupport {
    protected Date date01;
    protected Date date02;

    public Page4DatePicker() throws Exception {
        // note: a Converter for Date.class is registered (see DemoApplication) 
        DatePickerBehavior dpicker = new DatePickerBehavior();
        add(dpicker);
        add(new Form("myForm", new CompoundPropertyModel(this)) {
            @Override
            protected void onSubmit() {
                ((FeedbackPanel)getPage().get("feedback")).info("date01 set to :"  + date01);
                ((FeedbackPanel)getPage().get("feedback")).info("date02 set to :"  + date02);
            }
        }
            .add(new TextField("date01").add(dpicker.getDatePickerStyle()))
            .add(new TextField("date02")
                    .add(new DatePickerBehavior(new DatePickerOptions().clickInput(true).allowDateInPast(true)))
            )
        );
    }
}
