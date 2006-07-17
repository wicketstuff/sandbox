package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.DropDownChoice;
import wicket.model.IModel;

/**
 * A drop down list that's read only when it's not being edited.
 * 
 * @author Phil Kulak
 */
public class InlineDropDownChoice<T> extends InlineValidatingComponent<T>
{
	private static final long serialVersionUID = 1L;

	public InlineDropDownChoice(MarkupContainer parent, String id, IModel<T> model, List<T> choices)
	{
		super(parent, id);
		setFormComponent(new DropDownChoice<T>(this, "dropDownChoice", model, choices)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return GridView.isEdit(this);
			}
		});
		
		new Label(this, "label", model)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return !GridView.isEdit(this);
			}
		};
	}
}
