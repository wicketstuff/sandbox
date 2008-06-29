package wicket.contrib.examples.gmap.controls;

import org.apache.wicket.markup.html.WebMarkupContainer;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final GMap2 topMap = new GMap2("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		topMap.addControl(GControl.GMapTypeControl);
		add(topMap);

		final WebMarkupContainer zoomIn = new WebMarkupContainer("zoomIn");
		zoomIn.add(topMap.new ZoomInBehavior("onclick"));
		add(zoomIn);

		final WebMarkupContainer zoomOut = new WebMarkupContainer("zoomOut");
		zoomOut.add(topMap.new ZoomOutBehavior("onclick"));
		add(zoomOut);
	}
}
