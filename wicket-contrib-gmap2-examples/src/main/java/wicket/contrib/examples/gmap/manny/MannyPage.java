package wicket.contrib.examples.gmap.manny;

import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class MannyPage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	public MannyPage() {
		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		repeating.add(new MannyPanel("1", LOCALHOST));
		repeating.add(new MannyPanel("2", LOCALHOST));
		repeating.add(new MannyPanel("3", LOCALHOST));
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
