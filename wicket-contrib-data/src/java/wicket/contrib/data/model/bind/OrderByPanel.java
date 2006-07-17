package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;

/**
 * A panel that displays an order by column header.
 * 
 * @author Phil Kulak
 */
public class OrderByPanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderByPanel(MarkupContainer parent, String id, String field,
			String displayName, ListView list)
	{
		super(parent, id);
		OrderByLink l = new OrderByLink(this, "orderBy", field, list);
		new Label(l, "name", displayName);
	}
}
