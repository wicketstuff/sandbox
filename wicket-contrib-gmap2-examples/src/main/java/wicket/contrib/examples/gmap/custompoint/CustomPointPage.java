package wicket.contrib.examples.gmap.custompoint;

import org.apache.wicket.ResourceReference;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.GPoint;
import wicket.contrib.gmap.api.GSize;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class CustomPointPage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	public CustomPointPage() {
		GMap2<Object> map = new GMap2<Object>("map", LOCALHOST);
		map.setCenter(new GLatLng(52.37649, 4.888573));
		add(map);

		GIcon icon = new GIcon(urlFor(
				new ResourceReference(CustomPointPage.class, "image.gif"))
				.toString(), urlFor(
				new ResourceReference(CustomPointPage.class, "shadow.png"))
				.toString())
			.iconSize(new GSize(64, 64))
			.shadowSize(new GSize(64, 64))
			.iconAnchor(new GPoint(19, 40))
			.infoWindowAnchor(new GPoint(9, 2))
			.infoShadowAnchor(new GPoint(18, 25));

		GOverlay marker = new GMarker(new GLatLng(52.37649, 4.888573),
				new GMarkerOptions("My Title", icon));

		map.addOverlay(marker);
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
