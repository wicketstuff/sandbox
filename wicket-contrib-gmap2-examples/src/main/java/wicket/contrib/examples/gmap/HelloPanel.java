package wicket.contrib.examples.gmap;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap.GMap2;

/**
 * Panel used as an InfoWindow in the GMap.
 */
public class HelloPanel extends Panel
{

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static int i;

	public HelloPanel()
	{
		super(GMap2.CONTENT_ID);
		
		add(new Label("label", "" + (i++)));
	}
}
