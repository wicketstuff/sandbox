package wicket.contrib.dojo.markup.html.tabs;

import wicket.MarkupContainer;
import wicket.markup.html.WebMarkupContainer;

public class DojoTab extends WebMarkupContainer
{

	public DojoTab(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoTabHandler());
	}

}
