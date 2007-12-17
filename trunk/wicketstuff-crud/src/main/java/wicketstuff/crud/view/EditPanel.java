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
package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;

/**
 * Edit screen.
 * 
 * I18N Keys:
 * 
 * <pre>
 * wicket-crud.edit.save=save button text
 * wicket-crud.edit.cancel=cancel button text
 * </pre>
 * 
 * @see #newFormBorder(String)
 * @see #newEditorBorder(String, Component)
 * @author igor.vaynberg
 * 
 */
public class EditPanel extends Panel
{
	private final List<Property> properties;
	private final ICrudListener crudListener;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param properties
	 * @param crudListener
	 */
	public EditPanel(String id, IModel model, List<Property> properties,
			final ICrudListener crudListener)
	{
		super(id, model);
		this.properties = properties;
		this.crudListener = crudListener;
	}

	@Override
	protected void onBeforeRender()
	{
		if (!hasBeenRendered())
		{
			// we perform a two-phase initialization because we use factories
			// which if overridden would be called from the constructor of this
			// class

			Form form = new Form("form", getModel());
			addOrReplace(form);

			Border formBody = newFormBorder("form-body-border");
			form.add(formBody);


			RepeatingView props = new RepeatingView("props");
			formBody.add(props);

			for (Property property : properties)
			{
				WebMarkupContainer prop = new WebMarkupContainer(props.newChildId());
				props.add(prop);

				final Component editor = property.getEditor("editor", getModel());
				editor.setOutputMarkupId(true);
				Border border = newEditorBorder("border", editor);
				border.add(editor);

				prop.add(border);
			}

			formBody.add(new Button("save")
			{
				@Override
				public void onSubmit()
				{
					crudListener.onSave(EditPanel.this.getModel());
				}
			});

			formBody.add(new Link("cancel")
			{

				@Override
				public void onClick()
				{
					crudListener.onCancel();
				}

			});

		}
		super.onBeforeRender();
	}


	/**
	 * Factory method used to create a border around form-body
	 */
	protected Border newFormBorder(String id)
	{
		return new FormBorder(id);
	}

	/**
	 * Factory method used to create a border that goes around each editor
	 * 
	 * @param id
	 * @param editor
	 * @return
	 */
	protected Border newEditorBorder(String id, Component editor)
	{
		return new EditorBorder(id);
	}

}
