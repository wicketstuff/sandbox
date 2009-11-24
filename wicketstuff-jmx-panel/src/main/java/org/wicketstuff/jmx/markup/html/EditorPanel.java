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
package org.wicketstuff.jmx.markup.html;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Gerolf Seitz
 * 
 */
@SuppressWarnings("unchecked")
public class EditorPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	private Component editor;

	public EditorPanel(String id, final IModel model, IModel labelModel, final Class clazz,
			boolean isInplaceEditor)
	{
		super(id);
		setRenderBodyOnly(true);
		if (String.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz)
				|| (clazz.isPrimitive() && !boolean.class.equals(clazz)))
		{
			// Check for @Required on the field.
			editor = isInplaceEditor ? new AjaxEditableLabel("editor", model).setType(clazz)
					.setLabel(labelModel) : new StringEditor("editor", model, labelModel, clazz);
		}
		else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz))
		{
			editor = isInplaceEditor
					? getAjaxEditableChoiceLabel(model, labelModel, Model.valueOf(Arrays
							.asList(new Boolean[] { Boolean.TRUE, Boolean.FALSE })), clazz)
					: new BooleanEditor("editor", model, labelModel);
		}
		else if (Enum.class.isAssignableFrom(clazz))
		{
			// Dig out other enum choices from the type of enum that it is.
			IModel enumChoices = new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject()
				{
					return Arrays.asList(clazz.getEnumConstants());
				}

			};
			editor = isInplaceEditor ? getAjaxEditableChoiceLabel(model, labelModel, enumChoices,
					clazz) : new EnumEditor("editor", model, labelModel, enumChoices);
		}
		else if (clazz.isArray())
		{
			IModel arrayChoices = new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject()
				{
					return Arrays.asList(model.getObject());
				}

			};
			editor = isInplaceEditor ? getAjaxEditableChoiceLabel(new Model(null), labelModel,
					arrayChoices, clazz) : new EnumEditor("editor", new Model(null), labelModel,
					arrayChoices);
		}
		else
		{
			editor = new Label("editor", model);
			// throw new RuntimeException("Type " + clazz + " not supported.");
		}
		add(editor);
	}

	public Component getEditor()
	{
		return editor;
	}

	private Component getAjaxEditableChoiceLabel(IModel model, IModel labelModel,
			IModel choiceModel, Class clazz)
	{
		return new AjaxEditableChoiceLabel("editor", model, choiceModel)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected WebComponent newLabel(MarkupContainer parent, String componentId, IModel model)
			{
				Label label = new Label(componentId, model);
				label.setOutputMarkupId(true);
				label.add(new LabelAjaxBehavior("onclick"));
				return label;
			}
		}.setLabel(labelModel);
	}

	private static class StringEditor extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public StringEditor(String id, IModel model, IModel labelModel, Class type)
		{
			super(id, "stringEditor");
			add(new RequiredTextField("edit", model, type).setLabel(labelModel));
		}
	}

	private static class BooleanEditor extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public BooleanEditor(String id, IModel model, IModel labelModel)
		{
			super(id, "booleanEditor");
			add(new CheckBox("edit", model).setLabel(labelModel));
		}
	}

	private static class EnumEditor extends Fragment
	{
		private static final long serialVersionUID = 1L;

		public EnumEditor(String id, IModel model, IModel labelModel, IModel choices)
		{
			super(id, "enumEditor");
			add(new DropDownChoice("edit", model, choices).setLabel(labelModel));
		}
	}

}
