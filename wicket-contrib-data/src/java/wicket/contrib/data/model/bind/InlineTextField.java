package wicket.contrib.data.model.bind;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * A text field that's read only (a label) when its model is not currently being
 * edited.
 * 
 * @author Phil Kulak
 */
public class InlineTextField extends InlineValidatingComponent
{
	/**
	 * @param id
	 *            the id of the the InlineTextField
	 * @param model
	 *            the model for both the label and the text field
	 */
	public InlineTextField(String id, IModel model)
	{
		super(id);

		setFormComponent(new TextField("textField", model)
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
