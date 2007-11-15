package wicket.contrib.examples.gmap.controls;

import org.apache.wicket.markup.html.basic.Label;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GMarkerOptions;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	public HomePage() {
		final GMap2 topMap = new GMap2("topPanel", LOCALHOST);
		topMap.setDoubleClickZoomEnabled(true);
		topMap.setZoom(10);
		GMarkerOptions options = new GMarkerOptions();
		options.setTitle("Home");
		options.setDraggable(true);
		options.setAutoPan(true);
		topMap.addControl(GControl.GLargeMapControl);
		topMap.addControl(GControl.GMapTypeControl);
		add(topMap);

		final Label zoomIn = new Label("zoomInLabel", "ZoomIn");
		zoomIn.add(topMap.new ZoomInBehavior("onclick"));
		add(zoomIn);

		final Label zoomOut = new Label("zoomOutLabel", "ZoomOut");
		zoomOut.add(topMap.new ZoomOutBehavior("onclick"));
		add(zoomOut);
	}

	/**
	 * pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info. Also the GClientGeocoder is pickier on this than
	 * the GMap2. Running on 'localhost' GMap2 will ignore the key and the maps
	 * will show up, but GClientGeocoder wount. So if the key doesnt match the
	 * url down to the directory GClientGeocoder will not work.
	 * 
	 * This key is good for all URLs in this directory:
	 * http://localhost:8080/wicket-contrib-gmap2-examples/gmap2/
	 */
	private static final String LOCALHOST = "ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xQRS2YPSd8S9D1NKPBvdB1fr18_CxR-svEYj6URCf5QDFq3i03mqrDlbA";
}
