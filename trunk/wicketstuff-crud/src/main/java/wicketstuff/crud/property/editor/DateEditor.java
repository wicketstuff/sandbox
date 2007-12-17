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
package wicketstuff.crud.property.editor;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Date editor
 * 
 * @author igor.vaynberg
 * 
 */
public class DateEditor extends FormComponentEditor implements Editor
{
	private final DateTextField field;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param pattern
	 */
	public DateEditor(String id, IModel model, String pattern)
	{
		super(id);
		add(field = DateTextField.forDatePattern("text", model, pattern));
		field.add(new DatePicker());
	}

	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}


}
