package wicket.contrib.examples.gmap.listen.overlay;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.GMapExampleApplication;
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
		final GMap2<Object> topMap = new GMap2<Object>("topPanel", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
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
}
