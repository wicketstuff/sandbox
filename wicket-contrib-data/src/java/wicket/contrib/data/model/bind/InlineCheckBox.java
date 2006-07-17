package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.form.CheckBox;
import wicket.model.IModel;

/**
 * A checkbox that is read only when it's model is not currently being editied.
 * 
 * @author Phil Kulak
 */
public class InlineCheckBox extends InlineValidatingComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            the id of this component
	 * @param model
	 *            the model for this component
	 */
	public InlineCheckBox(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id);

		setFormComponent(new CheckBox(this, "checkBox", model)
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

		new CheckMark(this, "image", model)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return super.isVisible() && !GridView.isEdit(this);
			}
		};
	}
}
