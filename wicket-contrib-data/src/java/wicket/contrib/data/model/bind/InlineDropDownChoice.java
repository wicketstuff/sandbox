package wicket.contrib.data.model.bind;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

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
