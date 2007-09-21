package wicket.contrib.examples.gmap.manny;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.examples.WicketExamplePage;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class MannyPage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	final RepeatingView repeating;

	int panels = 1;

	public MannyPage() {
		final Panel repeaterPanel = new RepeaterPanel("repeater");
		add(repeaterPanel);
		repeating = new RepeatingView("repeating");
		repeaterPanel.add(repeating);

		AjaxFallbackLink more = new AjaxFallbackLink("more") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				MannyPage.this.addPanel();
				target.addComponent(repeaterPanel);
			}
		};
		add(more);

		AjaxFallbackLink less = new AjaxFallbackLink("less") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				MannyPage.this.removePanel();
				target.addComponent(repeaterPanel);
			}
		};
		add(less);

		for (int i = 1; i <= panels; i++) {
			repeating.add(new MannyPanel(Integer.toString(i), LOCALHOST));
		}
	}

	protected void removePanel() {
		if (panels > 0) {
			panels--;
			repeating.remove(Integer.toString(panels));
		}
	}

	protected void addPanel() {
		panels++;
		repeating.add(new MannyPanel(Integer.toString(panels), LOCALHOST));
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
