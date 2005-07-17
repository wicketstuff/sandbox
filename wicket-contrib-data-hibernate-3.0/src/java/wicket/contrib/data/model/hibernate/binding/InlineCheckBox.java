package wicket.contrib.data.model.hibernate.binding;

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
	 * @param id
	 *            the id of this component
	 * @param model
	 *            the model for this component
	 */
	public InlineCheckBox(String id, IModel model)
	{
		super(id);

		setFormComponent(new CheckBox(("checkBox"), model)
		{
			public boolean isVisible()
			{
				return isEdit();
			}
		});

		add(new CheckMark("image", model)
		{
			public boolean isVisible()
			{
				return super.isVisible() && !isEdit();
			}
		});
	}
}
