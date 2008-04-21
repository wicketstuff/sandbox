package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMapHeaderContributor;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class ManyPage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	private final WebMarkupContainer<Object> container;

	private final RepeatingView<Object> repeating;

	public ManyPage() {
		AjaxFallbackLink<Object> create = new AjaxFallbackLink<Object>("create") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				ManyPage.this.addPanel();

				if (target != null) {
					target.addComponent(container);
				}
			}
		};
		add(create);

		container = new WebMarkupContainer<Object>("container");
		container.setOutputMarkupId(true);
		// optional: do this if no GMap2 is added initially
		container.add(new GMapHeaderContributor(LOCALHOST));
		add(container);

		repeating = new RepeatingView<Object>("repeating");
		container.add(repeating);	

		//addPanel();
	}

	protected void addPanel() {
		ManyPanel newPanel = new ManyPanel(repeating.newChildId(), LOCALHOST) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void closing(AjaxRequestTarget target) {
				repeating.remove(this);

				if (target != null) {
					target.addComponent(container);
				}
			}
		};
		repeating.add(newPanel);
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
