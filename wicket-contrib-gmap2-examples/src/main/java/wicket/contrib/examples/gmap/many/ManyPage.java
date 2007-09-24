package wicket.contrib.examples.gmap.many;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class ManyPage extends WicketExamplePage {

	private static final long serialVersionUID = 1L;

	private final WebMarkupContainer container;

	private final RepeatingView repeating;

	public ManyPage() {
		AjaxFallbackLink more = new AjaxFallbackLink("more") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				ManyPage.this.addPanel();
				target.addComponent(container);
			}
		};
		add(more);

		AjaxFallbackLink less = new AjaxFallbackLink("less") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				ManyPage.this.removePanel();
				target.addComponent(container);
			}
		};
		add(less);

		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		add(container);

		repeating = new RepeatingView("repeating");
		container.add(repeating);

		addPanel();
	}

	@SuppressWarnings("unchecked")
	protected void removePanel() {

		Component component = null;
		Iterator<Component> iterator = repeating.iterator();
		while (iterator.hasNext()) {
			component = iterator.next();
		}

		// remove last component (if any)
		if (component != null) {
			repeating.remove(component);
		}
	}

	protected void addPanel() {
		repeating.add(new ManyPanel(repeating.newChildId(), LOCALHOST));
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
