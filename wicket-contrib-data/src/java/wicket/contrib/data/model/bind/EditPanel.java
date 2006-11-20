package wicket.contrib.data.model.bind;

import wicket.markup.html.panel.Panel;

public class EditPanel extends Panel
{
	public EditPanel(String id)
	{
		super(id);
		add(new InlineEditLink("edit"));
		add(new InlineSubmitButton("save"));
	}
}
