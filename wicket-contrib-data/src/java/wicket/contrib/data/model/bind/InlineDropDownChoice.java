package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.markup.html.basic.Label;
import wicket.markup.html.form.DropDownChoice;
import wicket.model.IModel;

/**
 * A drop down list that's read only when it's not being edited.
 * 
 * @author Phil Kulak
 */
public class InlineDropDownChoice extends InlineValidatingComponent
{
	public InlineDropDownChoice(String id, IModel model, List choices)
	{
		super(id);
		setFormComponent(new DropDownChoice("dropDownChoice", model, choices)
		{
			public boolean isVisible()
			{
				return GridView.isEdit(this);
			}
		});
		
		add(new Label("label", model)
		{
			public boolean isVisible()
			{
				return !GridView.isEdit(this);
			}
		});
	}
}
