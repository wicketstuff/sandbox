package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.panel.Panel;

public class EditPanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditPanel(MarkupContainer parent, String id)
	{
		super(parent, id);
		new InlineEditLink(this, "edit");
		new InlineSubmitButton(this, "save");
	}
}
