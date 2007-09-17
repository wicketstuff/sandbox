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

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Single-choice editor
 * 
 * @author igor.vaynberg
 * 
 */
public class ChoiceEditor extends FormComponentEditor implements Editor
{
	private final DropDownChoice field;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param choices
	 * @param renderer
	 */
	public ChoiceEditor(String id, IModel model, IModel choices, IChoiceRenderer renderer)
	{
		super(id);
		add(field = new DropDownChoice("choice", model, choices, renderer));
	}

	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}
}
