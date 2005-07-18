package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.StaticResource;
import wicket.markup.html.form.ImageButton;

/**
 * An image button that submits the current row and is only visble when the row
 * is being edited.
 * 
 * @author Phil Kulak
 */
public class InlineSubmitButton extends ImageButton
{
	/** an image representing a save button */
	public static final StaticResource SAVE = StaticResource.get(InlineSubmitButton.class
			.getPackage(), "save.gif");

	/**
	 * @param id
	 *            the id of the button
	 */
	public InlineSubmitButton(String id)
	{
		super(id, SAVE);
	}

	/**
	 * @return true when the list item is being edited
	 */
	public boolean isVisible()
	{
		return GridView.isEdit(this);
	}
}
