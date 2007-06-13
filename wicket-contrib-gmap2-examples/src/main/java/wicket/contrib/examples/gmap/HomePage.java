package wicket.contrib.examples.gmap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2Panel;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GLatLngFactory;
import wicket.contrib.gmap.api.GMap2;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.events.ClickEvent;
import wicket.contrib.gmap.api.events.ClickListener;
import wicket.contrib.gmap.api.events.MoveEndEvent;
import wicket.contrib.gmap.api.events.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	public HomePage()
	{

		final GMap2 topMap = new GMap2();
		topMap.addControl(GControl.GLargeMapControl);
		topMap.addControl(GControl.GMapTypeControl);
		topMap.addOverlay(new GMarker(new GLatLng(49f, 49f)));

		final GMap2Panel topPanel = new GMap2Panel("topPanel",
				LOCALHOST_8080_WICKET_CONTRIB_GMAP2_EXAMPLES_KEY, topMap);

		final Label zoomIn = new Label("zoomInLabel", "ZoomIn");
		topPanel.addZoomInControll(zoomIn, "onclick");
		
		final Label zoomLabel = new Label("glabel", new PropertyModel(topMap, "zoomLevel"));
		zoomLabel.setOutputMarkupId(true);
		topPanel.addMoveEndListener(new MoveEndListener()
		{
			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void moveEndPerformed(MoveEndEvent event, AjaxRequestTarget target)
			{
				target.addComponent(zoomLabel);
			}
		});
		
		final Label zoomOut = new Label("zoomOutLabel", "ZoomOut");
		topPanel.addZoomOutControll(zoomOut, "onclick");

		final Label clickLabel = new Label("clickLabel", new Model(new GLatLng(50f, 50f)));
		clickLabel.add(new AttributeModifier("lat", true, clickLabel.getModel()));
		topPanel.addClickListener(new ClickListener()
		{
			/**
			 * Defauls serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void clickPerformed(ClickEvent clickEvent, AjaxRequestTarget target)
			{
				GMarker marker = new GMarker(clickEvent.getPoint());
				topMap.addOverlay(marker);
				clickLabel.getModel().setObject(clickEvent.getPoint());
				target.addComponent(clickLabel);
				target.addListener(marker.getJSAdd());
			}

		});
		topPanel.addAddOverlayControll(clickLabel, new GLatLngFactory()
		{

			public GLatLng getGLatLng()
			{
				GLatLng point = (GLatLng)clickLabel.getModel().getObject();
				return new GLatLng(point.getLat() * (0.9995 + Math.random() / 1000), point.getLng()
						* (0.9995 + Math.random() / 1000));
			}

		}, "onclick");

		add(topPanel);
		add(zoomIn);
		add(clickLabel);
		add(zoomLabel);
		add(zoomOut);

		final GMap2 bottomMap = new GMap2();
		bottomMap.addControl(GControl.GSmallMapControl);
		final GMap2Panel bottomPanel = new GMap2Panel("bottomPanel",
				LOCALHOST_8080_WICKET_CONTRIB_GMAP2_EXAMPLES_KEY, bottomMap);

		final Label n = new Label("n", "N");
		bottomPanel.addPanDirectionControll(n, 0, 1, "onclick");

		final Label ne = new Label("ne", "NE");
		bottomPanel.addPanDirectionControll(ne, -1, 1, "onclick");

		final Label e = new Label("e", "E");
		bottomPanel.addPanDirectionControll(e, -1, 0, "onclick");

		final Label se = new Label("se", "SE");
		bottomPanel.addPanDirectionControll(se, -1, -1, "onclick");

		final Label s = new Label("s", "S");
		bottomPanel.addPanDirectionControll(s, 0, -1, "onclick");

		final Label sw = new Label("sw", "SW");
		bottomPanel.addPanDirectionControll(sw, 1, -1, "onclick");

		final Label w = new Label("w", "W");
		bottomPanel.addPanDirectionControll(w, 1, 0, "onclick");

		final Label nw = new Label("nw", "NW");
		bottomPanel.addPanDirectionControll(nw, 1, 1, "onclick");

		final Label center = new Label("center", new PropertyModel(bottomMap, "center"));
		center.setOutputMarkupId(true);
		bottomPanel.addMoveEndListener(new MoveEndListener()
		{

			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void moveEndPerformed(MoveEndEvent event, AjaxRequestTarget target)
			{
				target.addComponent(center);
			}

		});

		final Label infoWindow = new Label("infoWindow", "openInfoWindow");
		bottomPanel.addOpenInfoWindowControl(infoWindow, new GLatLng(44.0f, 44.0f),
				new HelloPanel(), "onclick");

		bottomPanel.addClickListener(new ClickListener()
		{
			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void clickPerformed(ClickEvent event, AjaxRequestTarget target)
			{
				target.addListener(bottomPanel.getJSOpenInfoWindow(event.getPoint(),
						new HelloPanel()));
			}

		});

		add(n);
		add(ne);
		add(e);
		add(se);
		add(s);
		add(sw);
		add(w);
		add(nw);
		add(center);
		add(infoWindow);
		add(bottomPanel);
	}

	// pay attention at webapp deploy context, we need a different key for each
	// deploy context
	// check <a href="http://www.google.com/apis/maps/signup.html">Google Maps
	// API - Sign Up</a> for more info

	// key for http://localhost:8080/wicket-contrib-gmap-examples/
	@SuppressWarnings("unused")
	private static final String LOCALHOST_8080_WICKET_CONTRIB_GMAP_EXAMPLES_KEY = "ABQIAAAAf5yszl-6vzOSQ0g_Sk9hsxQwbIpmX_ZriduCDVKZPANEQcosVRSYYl2q0zAQNI9wY7N10hRcPVFbLw";

	// http://localhost:8080/wicket-contrib-gmap2-examples/gmap/
	private static final String LOCALHOST_8080_WICKET_CONTRIB_GMAP2_EXAMPLES_KEY = "ABQIAAAAf5yszl-6vzOSQ0g_Sk9hsxSRJOeFm910afBJASoNgKJoF-fSURRPODotP7LZxsDKHpLi_jvawkMyrQ";

	// key for http://localhost:8080/wicket-contrib-gmap, deploy context is
	// wicket-contrib-gmap
	@SuppressWarnings("unused")
	private static final String LOCALHOST_8080_WICKET_CONTRIB_GMAP_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTDxbH1TVfo7w-iwzG2OxhXSIjJdhQTwgha-mCK8wiVEq4rgi9qvz8HYw";

	// key for http://localhost:8080/gmap, deploy context is gmap
	@SuppressWarnings("unused")
	private static final String GMAP_8080_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTh_sjBSLCHIDZfjzu1cFb3Pz7MrRQLOeA7BMLtPnXOjHn46gG11m_VFg";

	// key for http://localhost/gmap
	@SuppressWarnings("unused")
	private static final String GMAP_DEFAULT_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxTIqKwA3nrz2BTziwZcGRDeDRNmMxS-FtSv7KGpE1A21EJiYSIibc-oEA";

	// key for http://www.wicket-library.com/wicket-examples/
	@SuppressWarnings("unused")
	private static final String WICKET_LIBRARY_KEY = "ABQIAAAALjfJpigGWq5XvKwy7McLIxQTV35WN9IbLCS5__wznwqtm2prcBQxH8xw59T_NZJ3NCsDSwdTwHTrhg";
}
