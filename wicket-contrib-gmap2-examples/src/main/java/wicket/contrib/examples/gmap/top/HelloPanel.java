package wicket.contrib.examples.gmap.top;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel used as an InfoWindow in the GMap.
 */
public class HelloPanel extends Panel<Object>
{

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static int i;

	public HelloPanel()
	{
		super("content" + i++);
		
		add(new Label<String>("label", HelloPanel.this.getId()));
	}
}
