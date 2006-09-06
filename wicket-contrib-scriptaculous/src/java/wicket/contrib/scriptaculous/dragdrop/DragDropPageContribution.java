package wicket.contrib.scriptaculous.dragdrop;

import wicket.PageParameters;
import wicket.markup.html.WebPage;

public class DragDropPageContribution extends WebPage
{

	private static final long serialVersionUID = 1L;
	private final String input;

	public DragDropPageContribution(PageParameters parameters)
	{
		String input = parameters.getString("id");
		this.input = input;
	}

	protected String getInputValue()
	{
		return input;
	}
}
