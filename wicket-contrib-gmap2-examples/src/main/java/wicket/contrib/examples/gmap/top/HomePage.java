package wicket.contrib.examples.gmap.top;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.ClickListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage<Void>
{

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	private final Label<GMarker> markerLabel;

	public HomePage()
	{
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		final GMap2<Object> topMap = new GMap2<Object>("topPanel", LOCALHOST);
		topMap.setDoubleClickZoomEnabled(true);
		topMap.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, GLatLng latLng, GOverlay overlay)
			{
				GMarker marker = (overlay instanceof GMarker) ? (GMarker)overlay : null;
				if (marker != null)
				{
					topMap.getInfoWindow().open(marker, new HelloPanel());
				}
				else if (latLng != null)
				{
					marker = new GMarker(latLng);
					topMap.addOverlay(marker);
				}
				markerSelected(target, marker);
			}
		});
		topMap.setZoom(10);
		GMarkerOptions options = new GMarkerOptions("Home").draggable(true).autoPan(true);
		topMap.addOverlay(new GMarker(new GLatLng(37.4, -122.1), options));
		topMap.addControl(GControl.GLargeMapControl);
		topMap.addControl(GControl.GMapTypeControl);
		add(topMap);

		markerLabel = new Label<GMarker>("markerLabel", new Model<GMarker>(null));
		markerLabel.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				GMarker marker = (GMarker)markerLabel.getModelObject();
				if (marker != null)
				{
					GLatLng point = marker.getLagLng();

					GMarker random = new GMarker(new GLatLng(point.getLat()
							* (0.9995 + Math.random() / 1000), point.getLng()
							* (0.9995 + Math.random() / 1000)));

					topMap.addOverlay(random);
				}
			}
		});
		add(markerLabel);

		add(new Link<Object>("reload")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
			}
		});
	}

	private void markerSelected(AjaxRequestTarget target, GMarker marker)
	{
		markerLabel.getModel().setObject(marker);
		target.addComponent(markerLabel);
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
