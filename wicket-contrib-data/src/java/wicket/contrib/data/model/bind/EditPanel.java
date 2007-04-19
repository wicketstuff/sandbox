package wicket.contrib.data.model.bind;

import org.apache.wicket.markup.html.panel.Panel;

public class EditPanel extends Panel
{
	public EditPanel(String id)
	{
		super(id);
		add(new InlineEditLink("edit"));
		add(new InlineSubmitButton("save"));
	}
}
