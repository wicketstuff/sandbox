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
package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.ChoiceEditor;

/**
 * Property that represents a single-choice from a list of choices
 * 
 * @author igor.vaynberg
 * 
 */
public class ChoiceProperty extends Property
{
	private IModel choices;
	private IChoiceRenderer renderer;

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public ChoiceProperty(String path, IModel label)
	{
		super(path, label);
	}

	/**
	 * Sets model that will be used to provide a list of choices to select from
	 * 
	 * @param choicesModel
	 */
	public void setChoices(IModel choicesModel)
	{
		this.choices = choicesModel;
	}


	/**
	 * Sets renderer used to render choices
	 * 
	 * @param renderer
	 */
	public void setRenderer(IChoiceRenderer renderer)
	{
		this.renderer = renderer;
	}


	/** {@inheritDoc} */
	@Override
	public Component getEditor(String id, IModel object)
	{
		ChoiceEditor editor = new ChoiceEditor(id, new PropertyModel(object, getPath()), choices,
				renderer);
		configure(editor);
		return editor;
	}

	/** {@inheritDoc} */
	@Override
	public Component getViewer(String id, IModel object)
	{
		final PropertyModel prop = new PropertyModel(object, getPath());
		return new Label(id, new AbstractReadOnlyModel()
		{

			@Override
			public Object getObject()
			{
				final Object object = prop.getObject();
				return renderer.getDisplayValue(object);
			}

			@Override
			public void detach()
			{
				prop.detach();
			}

		});
	}
}
