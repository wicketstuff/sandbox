package wicket.contrib.examples.gmap.refreshpoint;

import java.util.Collections;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;

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
public class RefreshPointPage extends WicketExamplePage<Void> {

	private static final long serialVersionUID = 1L;

	private GMap2<Object> map;

	public RefreshPointPage() {
		map = new GMap2<Object>("map", LOCALHOST);
		add(map);

		GOverlay overlay = createOverlay("Amsterdam", new GLatLng(52.37649,
				4.888573), "image.gif", "shadow.png");

		map.addOverlay(overlay);

		map.add(new GMapAutoUpdatingBehavior(Duration.seconds(5)) {
			private static final long serialVersionUID = 1L;

			private int i = 1;

			@Override
			protected void onTimer(AjaxRequestTarget target, GMap2<?> map) {
				GOverlay overlay;
				if (i % 3 == 0) {
					overlay = createOverlay("Amsterdam", new GLatLng(52.37649,
							4.888573), "image.gif", "shadow.png");
					i = 0;
				} else if (i % 3 == 1) {
					overlay = createOverlay("Amsterdam", new GLatLng(52.37649,
							4.888573), "image2.gif", "shadow2.png");
				} else {
					overlay = createOverlay("Toulouse", new GLatLng(43.604363,
							1.442951), "image2.gif", "shadow2.png");
				}
				i++;
				map.setOverlays(Collections.singletonList(overlay));
			}
		});
	}

	private GOverlay createOverlay(String title, GLatLng latLng, String image,
			String shadow) {
		GIcon icon = new GIcon(urlFor(
				new ResourceReference(RefreshPointPage.class, image))
				.toString(), urlFor(
				new ResourceReference(RefreshPointPage.class, shadow))
				.toString())
			.iconSize(new GSize(64, 64))
			.shadowSize(new GSize(64, 64))
			.iconAnchor(new GPoint(19, 40))
			.infoWindowAnchor(new GPoint(9, 2))
			.infoShadowAnchor(new GPoint(18, 25));
		map.setCenter(latLng);
		return new GMarker(latLng, new GMarkerOptions(title, icon));
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
