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

public class EditorBorder extends Border
{
	private static final String ERROR_CSS_CLASS = "wicket-crud-error ";
	private Component editorCache = null;
	private final FeedbackPanel feedback;

	public EditorBorder(String id)
	{
		super(id);

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

		container.add(new Label("label", new LabelModel())
		{
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("for", getEditor().getMarkupId());
			}
		});
		container.add(new WebMarkupContainer("required-indicator")
		{
			@Override
			public boolean isVisible()
			{
				return ((Editor)getEditor()).isRequired();
			}
		});
		container.add(feedback = new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(
				this)));
		container.add(getBodyContainer());
	}

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

	private void initializeEditor(Component editor)
	{
		editor.setOutputMarkupId(true);
	}


	private class LabelModel extends AbstractReadOnlyModel
	{
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
