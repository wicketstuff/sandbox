package wicket.contrib.examples.gmap.listen.overlay;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.event.GMarkerDragendListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage<Void>
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{
		final GMap2<Object> topMap = new GMap2<Object>("topPanel", LOCALHOST);
		topMap.addControl(GControl.GLargeMapControl);
		add(topMap);

		GMarkerOptions options = new GMarkerOptions().draggable(true);
		final GMarker marker = new GMarker(topMap.getCenter(), options);

		final Label<GLatLng> label = new Label<GLatLng>("label", new PropertyModel<GLatLng>(marker,
				"latLng"));
		label.setOutputMarkupId(true);
		add(label);

		marker.addBehavior(new GMarkerDragendListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onDragend(AjaxRequestTarget target)
			{
				target.addComponent(label);
			}

		});
		topMap.addOverlay(marker);
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
