package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.PackageResource;
import wicket.markup.html.form.ImageButton;

/**
 * An image button that submits the current row and is only visble when the row
 * is being edited.
 * 
 * @author Phil Kulak
 */
public class InlineSubmitButton extends ImageButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** an image representing a save button */
	public static final PackageResource SAVE = PackageResource.get(InlineSubmitButton.class, "save.gif");

	/**
	 * @param id the id of the button
	 */
	public InlineSubmitButton(MarkupContainer parent, String id)
	{
		super(parent, id, SAVE);
	}
	
	@Override
	public void onSubmit()
	{
		GridView.mergeEdit(this);
	}

	/**
	 * @return true when the list item is being edited
	 */
	@Override
	public boolean isVisible()
	{
		return GridView.isEdit(this);
	}
}
