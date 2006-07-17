package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;

/**
 * A text field that's read only (a label) when its model is not currently being
 * edited.
 * 
 * @author Phil Kulak
 */
public class InlineTextField extends InlineValidatingComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            the id of the the InlineTextField
	 * @param model
	 *            the model for both the label and the text field
	 */
	public InlineTextField(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id);

		setFormComponent(new TextField(this, "textField", model)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return GridView.isEdit(this);
			}
		});

		new Label(this, "label", model)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return !GridView.isEdit(this);
			}
		};
	}
}
