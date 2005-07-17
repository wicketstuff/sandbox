package wicket.contrib.data.model.hibernate.binding;

import wicket.contrib.data.model.sandbox.OrderByLink;
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
	public OrderByPanel(String id, String field, String displayName, ListView list)
	{
		super(id);
		add(new OrderByLink("orderBy", field, list)
            .add(new Label("name", displayName)));
	}
}
