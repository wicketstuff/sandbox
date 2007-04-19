package wicket.contrib.data.model.bind;

import org.apache.wicket.markup.html.panel.Panel;

public class DeletePanel extends Panel
{
	public DeletePanel(String id)
	{
		super(id);
		add(new InlineDeleteLink("delete"));
	}
}
