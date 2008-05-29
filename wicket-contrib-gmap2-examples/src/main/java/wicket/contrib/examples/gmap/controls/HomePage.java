package wicket.contrib.examples.gmap.controls;

import org.apache.wicket.markup.html.WebMarkupContainer;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage<Void>
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final GMap2<Void> topMap = new GMap2<Void>("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		topMap.addControl(GControl.GMapTypeControl);
		add(topMap);

		final WebMarkupContainer<Void> zoomIn = new WebMarkupContainer<Void>("zoomIn");
		zoomIn.add(topMap.new ZoomInBehavior("onclick"));
		add(zoomIn);

		final WebMarkupContainer<Void> zoomOut = new WebMarkupContainer<Void>("zoomOut");
		zoomOut.add(topMap.new ZoomOutBehavior("onclick"));
		add(zoomOut);
	}
}
