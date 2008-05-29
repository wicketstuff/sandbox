package wicket.contrib.examples.gmap.listen.overlay.advanced;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GEvent;
import wicket.contrib.gmap.api.GEventHandler;
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

	public HomePage()
	{
		final GMap2<Object> map = new GMap2<Object>("map", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		map.addControl(GControl.GLargeMapControl);
		add(map);
		final WebMarkupContainer<Void> repeaterParent = new WebMarkupContainer<Void>(
				"repeaterParent");
		repeaterParent.setOutputMarkupId(true);
		add(repeaterParent);
		final RepeatingView<Void> rv = new RepeatingView<Void>("label");
		rv.setOutputMarkupId(true);
		repeaterParent.add(rv);
		map.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, GLatLng latLng, GOverlay overlay)
			{
				if (latLng != null)
				{
					if (map.getOverlays().size() >= 3)
					{
						map.removeOverlay(map.getOverlays().get(0));
					}
					final GMarker marker = new GMarker(latLng, new GMarkerOptions().draggable(true));
					map.addOverlay(marker);
					marker.addListener(GEvent.dragend, new GEventHandler()
					{
						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(AjaxRequestTarget target)
						{
							target.addComponent(repeaterParent);
						}
					});
					rv.removeAll();
					for (GOverlay myMarker : map.getOverlays())
					{
						final Label<GLatLng> label = new Label<GLatLng>(myMarker.getId(),
								new PropertyModel<GLatLng>(myMarker, "latLng"));
						label.setOutputMarkupId(true);
						rv.add(label);
					}

					target.addComponent(repeaterParent);
				}
			}
		});
	}
}
