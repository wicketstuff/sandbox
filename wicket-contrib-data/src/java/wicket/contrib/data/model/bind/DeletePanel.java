package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.panel.Panel;

public class DeletePanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeletePanel(MarkupContainer parent, String id)
	{
		super(parent, id);
		new InlineDeleteLink(this, "delete");
	}
}
