package wicket.contrib.data.model.bind;

import java.io.Serializable;

import wicket.markup.html.form.CheckBox;
import wicket.model.IModel;
import wicket.model.Model;

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
				return GridView.isEdit(this);
			}
		});

		add(new CheckMark("image", (Boolean) model.getObject(this))
		{
			public boolean isVisible()
			{
				return super.isVisible() && !GridView.isEdit(this);
			}
		});
	}
}
