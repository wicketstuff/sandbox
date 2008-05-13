package wicket.contrib.examples.gmap.bottom;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.ClickListener;
import wicket.contrib.gmap.event.InfoWindowCloseListener;
import wicket.contrib.gmap.event.InfoWindowOpenListener;
import wicket.contrib.gmap.event.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage<Void>
{

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;

	private final Label<GLatLng> center;

	private MoveEndListener moveEndBehavior;

	public HomePage()
	{
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		final GMap2<Object> bottomMap = new GMap2<Object>("bottomPanel", new GMapHeaderContributor(
				GMapExampleApplication.get().getGoogleMapsAPIkey()));
		bottomMap.setOutputMarkupId(true);
		bottomMap.setMapType(GMapType.G_SATELLITE_MAP);
		bottomMap.setScrollWheelZoomEnabled(true);
		moveEndBehavior = new MoveEndListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onMoveEnd(AjaxRequestTarget target)
			{
				target.addComponent(center);
			}
		};
		bottomMap.add(moveEndBehavior);
		bottomMap.add(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, GLatLng gLatLng, GOverlay overlay)
			{
				if (gLatLng != null)
				{
					bottomMap.getInfoWindow().open(gLatLng, new HelloPanel());
				}
			}

		});
		bottomMap.add(new InfoWindowCloseListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onInfoWindowClose(AjaxRequestTarget target)
			{
				info("InfoWindow was closed");
				target.addComponent(feedback);
			}
		});
		bottomMap.add(new InfoWindowOpenListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onInfoWindowOpen(AjaxRequestTarget target)
			{
				info("InfoWindow was opened");
				target.addComponent(feedback);
			}
		});
		bottomMap.addControl(GControl.GSmallMapControl);
		bottomMap.getInfoWindow().open(new GLatLng(37.5, -122.1),
				new GInfoWindowTab("One", new HelloPanel()),
				new GInfoWindowTab("Two", new HelloPanel()));
		add(bottomMap);

		center = new Label<GLatLng>("center", new PropertyModel<GLatLng>(bottomMap, "center"));
		center.add(bottomMap.new SetCenterBehavior("onclick", new GLatLng(37.5, -122.1, false)));
		center.setOutputMarkupId(true);
		add(center);

		final Label<String> infoWindow = new Label<String>("infoWindow", "openInfoWindow");
		infoWindow.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				bottomMap.getInfoWindow().open(new GLatLng(37.5, -122.1), new HelloPanel());
			}
		});
		add(infoWindow);
		add(new Link<Object>("reload")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick()
			{
			}
		});
		final Label<Boolean> enabledLabel = new Label<Boolean>("enabled", new Model<Boolean>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean getObject()
			{
				return bottomMap.getBehaviors().contains(moveEndBehavior);
			}
		});
		enabledLabel.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				if (bottomMap.getBehaviors().contains(moveEndBehavior))
				{
					bottomMap.remove(moveEndBehavior);
				}
				else
				{
					// TODO AbstractAjaxBehaviors are not reusable, so we have
					// to recreate:
					// https://issues.apache.org/jira/browse/WICKET-713
					moveEndBehavior = new MoveEndListener()
					{
						private static final long serialVersionUID = 1L;

						@Override
						protected void onMoveEnd(AjaxRequestTarget target)
						{
							target.addComponent(center);
						}
					};
					bottomMap.add(moveEndBehavior);
				}
				target.addComponent(bottomMap);
				target.addComponent(enabledLabel);
			}
		});
		add(enabledLabel);
	}
}
