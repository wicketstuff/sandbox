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
package org.wicketstuff.dojo.markup.html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.wicketstuff.dojo.MockPage;
import org.wicketstuff.dojo.WicketTestCase;
import org.wicketstuff.dojo.markup.html.form.DojoDatePicker;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.Model;

public class DatePickerTest extends WicketTestCase {

	private Date date;
	private Long timeBefore;
	private Long timeAfter;
	
	/**
	 * @param name
	 */
	public DatePickerTest(String name)
	{
		super(name);
	}
	
	public void testBeforeEqualsAfter() {
		GregorianCalendar calendar = new GregorianCalendar(1982, 4, 25);
		date = calendar.getTime();
		timeBefore = date.getTime();
		
		MockPage page = new MockPage();
		
		Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				super.onSubmit();
				timeAfter = ((Date)this.get("picker1").getModelObject()).getTime();
			}
		};
		DojoDatePicker picker = new DojoDatePicker("picker1", new Model(date), "dd/MM/yyyy");
		form.add(picker);
		page.add(form);
		
		//simulate the dojo not visible field by a date formatter
		SimpleDateFormat formatter = new SimpleDateFormat(picker.getInternalDatePattern());
		tester.getServletRequest().setParameter(picker.getInputName(), formatter.format(date));
		form.onFormSubmitted();
		
		//test displayed format
		assertEquals(picker.getInput(), formatter.format(date));
		
		//test dates are correctly converted
		assertEquals(timeBefore, timeAfter);
	}
}
