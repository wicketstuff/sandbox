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

import org.apache.wicket.Component;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Border that is created around every editor in edit screen
 * 
 * @author igor.vaynberg
 * 
 */
public class EditorBorder extends Border
{
	private static final String ERROR_CSS_CLASS = "wicket-crud-error ";
	private Component editorCache = null;
	private final FeedbackPanel feedback;

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public EditorBorder(String id)
	{
		super(id);

		// add container that will add the error class when editor did not
		// validate
		WebMarkupContainer container = new WebMarkupContainer("container")
		{
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				if (feedback.anyErrorMessage())
				{
					tag.put("class", ERROR_CSS_CLASS + tag.getAttributes().getString("class"));
				}
			}
		};
		add(container);

		// create label for editor
		container.add(new Label("label", new LabelModel())
		{
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("for", getEditor().getMarkupId());
			}
		});

		// create required indicator for the label
		container.add(new WebMarkupContainer("required-indicator")
		{
			@Override
			public boolean isVisible()
			{
				return ((Editor)getEditor()).isRequired();
			}
		});

		// create feedback that will be used to show errors just for the editor
		container.add(feedback = new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(
				this)));

		// realign border body with hierarchy
		container.add(getBodyContainer());
	}

	/**
	 * finds {@link Editor} component inside this border
	 * 
	 * @return
	 */
	private Component getEditor()
	{
		if (editorCache == null)
		{
			visitChildren(new IVisitor()
			{

				public Object component(Component component)
				{
					if (component instanceof Editor)
					{
						editorCache = component;
						return STOP_TRAVERSAL;
					}
					else
					{
						return CONTINUE_TRAVERSAL;
					}
				}

			});
			if (editorCache == null)
			{
				throw new IllegalStateException("Could not find Editor in this component");
			}
			else
			{
				initializeEditor(editorCache);
			}
		}
		return editorCache;

	}

	/**
	 * Performs one-time border-releated initialization of the component
	 * 
	 * @param editor
	 */
	private void initializeEditor(Component editor)
	{
		editor.setOutputMarkupId(true);
	}


	/**
	 * Model used to construct editor label text
	 * 
	 * @author igor.vaynberg
	 * 
	 */
	private class LabelModel extends AbstractReadOnlyModel
	{
		/** {@inheritDoc} */
		@Override
		public Object getObject()
		{
			IModel label = ((Editor)getEditor()).getLabel();
			if (label == null)
			{
				return "";
			}
			else
			{
				return label.getObject();
			}

		}

		/** {@inheritDoc} */
		@Override
		public void detach()
		{
			IModel label = ((Editor)getEditor()).getLabel();
			if (label != null)
			{
				label.detach();
			}
		}
	}

}
