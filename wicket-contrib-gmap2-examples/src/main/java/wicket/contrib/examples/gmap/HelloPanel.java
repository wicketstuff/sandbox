package wicket.contrib.examples.gmap;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import wicket.contrib.gmap.GInfoWindow;
import wicket.contrib.gmap.api.GLatLng;

/**
 * Panel used as an InfoWindow in the GMap.
 */
public class HelloPanel extends GInfoWindow
{

	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static int i;

	public HelloPanel(GLatLng gLatLng)
	{
		super(gLatLng);
		add(new Label("label", new Model()
		{

			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject()
			{
				return Integer.toString(i++);
			}
		}));
	}
}
