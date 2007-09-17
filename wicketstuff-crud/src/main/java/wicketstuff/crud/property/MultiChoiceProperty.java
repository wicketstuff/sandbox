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

import java.util.Collection;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.ChoiceEditor;

/**
 * Multiple-choice property
 * 
 * @author igor.vaynberg
 * 
 */
public class MultiChoiceProperty extends Property
{
	private IModel choices;
	private IChoiceRenderer renderer;

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public MultiChoiceProperty(String path, IModel label)
	{
		super(path, label);
	}


	/**
	 * Sets model used to provide a list of choices
	 * 
	 * @param choicesModel
	 * @return
	 */
	public MultiChoiceProperty setChoices(IModel choicesModel)
	{
		this.choices = choicesModel;
		return this;
	}


	/** Sets renderer used to render choices */
	public MultiChoiceProperty setRenderer(IChoiceRenderer renderer)
	{
		this.renderer = renderer;
		return this;
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

	@Override
	public Component getViewer(String id, IModel object)
	{
		final PropertyModel prop = new PropertyModel(object, getPath());
		return new Label(id, new AbstractReadOnlyModel()
		{

			@Override
			public Object getObject()
			{
				final Collection<?> choices = (Collection<?>)prop.getObject();
				StringBuilder buff = new StringBuilder();
				if (choices != null && !choices.isEmpty())
				{
					Iterator<?> it = choices.iterator();
					while (it.hasNext())
					{
						buff.append(renderer.getDisplayValue(it.next()));
						if (it.hasNext())
						{
							buff.append(", ");
						}
					}
				}
				return buff.toString();
			}

			@Override
			public void detach()
			{
				prop.detach();
			}

		});
	}
}
