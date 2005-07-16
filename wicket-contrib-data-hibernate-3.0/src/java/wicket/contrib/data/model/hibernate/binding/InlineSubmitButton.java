package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.StaticResource;
import wicket.markup.html.form.ImageButton;

/**
 * An image button that submits the current row and is only visble when the
 * row is being worked on.
 * 
 * @author Phil Kulak
 */
public class InlineSubmitButton extends ImageButton {
	public static final StaticResource SAVE =
		StaticResource.get(InlineSubmitButton.class.getPackage(), "save.gif");
	
	public InlineSubmitButton(String id) {
		super(id, SAVE);
	}
	
	public boolean isVisible() {
		return InlineComponent.isEdit(this);
	}
}
