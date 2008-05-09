package wicket.contrib.examples.gmap.polygones;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GPolygon;
import wicket.contrib.gmap.api.GPolyline;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class PolygonesPage extends WicketExamplePage<Void> {

	private static final long serialVersionUID = 1L;

	public PolygonesPage() {
		GMap2<Object> map = new GMap2<Object>("topPanel", LOCALHOST); 
		map.addOverlay(new GPolygon("#000000", 4, 0.7f, "#E9601A", 0.7f,
				new GLatLng(37.3, -122.4), new GLatLng(37.2, -122.2),
				new GLatLng(37.3, -122.0), new GLatLng(37.4, -122.2),
				new GLatLng(37.3, -122.4)));
		map.addOverlay(new GPolyline("#FFFFFF", 8, 1.0f, new GLatLng(37.35,
				-122.3), new GLatLng(37.25, -122.25),
				new GLatLng(37.3, -122.2), new GLatLng(37.25, -122.15),
				new GLatLng(37.35, -122.1)));
		map.setZoom(10);
		add(map);
	}

	/**
	 * pay attention at webapp deploy context, we need a different key for each
	 * deploy context check <a
	 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign
	 * Up</a> for more info. Also the GClientGeocoder is pickier on this than
	 * the GMap2. Running on 'localhost' GMap2 will ignore the key and the maps
	 * will show up, but GClientGeocoder wount. So if the key doesn't match the
	 * url down to the directory GClientGeocoder will not work.
	 * 
	 * This key is good for all URLs in this directory:
	 * http://localhost:8080/wicket-contrib-gmap2-examples/gmap/
	 */
	private static final String LOCALHOST = "ABQIAAAAzaZpf6nHOd9w1PfLaM9u2xSRJOeFm910afBJASoNgKJoF-fSURQRJ7dNBq-d-8hD7iUYeN2jQHZi8Q";
}
