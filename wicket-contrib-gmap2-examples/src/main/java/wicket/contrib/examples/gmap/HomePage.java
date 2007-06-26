package wicket.contrib.examples.gmap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.InfoWindow;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final Label markerLabel;
	private final Label zoomLabel;
	private final Label center;

	public HomePage()
	{

		final GMap2 topPanel = new GMap2("topPanel",
				LOCALHOST_8080_WICKET_CONTRIB_GMAP2_EXAMPLES_KEY)
		{
			@Override
			public void onMoveEnd(AjaxRequestTarget target)
			{
				target.addComponent(zoomLabel);
			}

			@Override
			public void onClick(GLatLng gLatLng, AjaxRequestTarget target)
			{
				GMarker marker = new GMarker(gLatLng);
				addOverlay(marker);
				markerLabel.getModel().setObject(marker);
				target.addComponent(markerLabel);
			}

			@Override
			public void onClick(GMarker marker, AjaxRequestTarget target)
			{
				removeOverlay(marker);
			}
		};
		topPanel.addOverlay(new GMarker(new GLatLng(49f, 49f), "Home"));
		topPanel.addControl(GControl.GLargeMapControl);
		topPanel.addControl(GControl.GMapTypeControl);
		add(topPanel);

		zoomLabel = new Label("zoomLabel", new PropertyModel(topPanel, "zoomLevel"));
		zoomLabel.add(topPanel.new SetZoom("onclick", 5));
		zoomLabel.setOutputMarkupId(true);
		add(zoomLabel);

		markerLabel = new Label("markerLabel", new Model(null));
		markerLabel.add(topPanel.new AddOverlay("onclick")
		{
			protected GOverlay getOverlay()
			{
				GLatLng point = ((GMarker)markerLabel.getModelObject()).getLagLng();

				GMarker marker = new GMarker(new GLatLng(point.getLat()
						* (0.9995 + Math.random() / 1000), point.getLng()
						* (0.9995 + Math.random() / 1000)));

				return marker;
			}
		});
		add(markerLabel);

		final Label zoomIn = new Label("zoomInLabel", "ZoomIn");
		zoomIn.add(topPanel.new ZoomIn("onclick"){
			
		});
		add(zoomIn);

		final Label zoomOut = new Label("zoomOutLabel", "ZoomOut");
		zoomOut.add(topPanel.new ZoomOut("onclick"));
		add(zoomOut);

		final GMap2 bottomPanel = new GMap2("bottomPanel",
				LOCALHOST_8080_WICKET_CONTRIB_GMAP2_EXAMPLES_KEY)
		{

			@Override
			public void onMoveEnd(AjaxRequestTarget target)
			{
				target.addComponent(center);
			}

			@Override
			public void onClick(GLatLng point, AjaxRequestTarget target)
			{
				openInfoWindow(new HelloPanel(point));
			}
		};
		bottomPanel.addControl(GControl.GSmallMapControl);
		bottomPanel.openInfoWindow(new HelloPanel(new GLatLng(45.0, 45.0)));
		add(bottomPanel);


		center = new Label("center", new PropertyModel(bottomPanel, "center"));
		center.add(bottomPanel.new SetCenter("onclick")
		{
			protected GLatLng getCenter()
			{
				return new GLatLng(45.30580139160156, 44.483642578125, false);
			}
		});
		center.setOutputMarkupId(true);
		add(center);

		final Label n = new Label("n", "N");
		n.add(bottomPanel.new PanDirection("onclick", 0, 1));
		add(n);

		final Label ne = new Label("ne", "NE");
		ne.add(bottomPanel.new PanDirection("onclick", -1, 1));
		add(ne);

		final Label e = new Label("e", "E");
		e.add(bottomPanel.new PanDirection("onclick", -1, 0));
		add(e);

		final Label se = new Label("se", "SE");
		se.add(bottomPanel.new PanDirection("onclick", -1, -1));
		add(se);

		final Label s = new Label("s", "S");
		s.add(bottomPanel.new PanDirection("onclick", 0, -1));
		add(s);

		final Label sw = new Label("sw", "SW");
		sw.add(bottomPanel.new PanDirection("onclick", 1, -1));
		add(sw);

		final Label w = new Label("w", "W");
		w.add(bottomPanel.new PanDirection("onclick", 1, 0));
		add(w);

		final Label nw = new Label("nw", "NW");
		nw.add(bottomPanel.new PanDirection("onclick", 1, 1));
		add(nw);

		final Label infoWindow = new Label("infoWindow", "openInfoWindow");
		infoWindow.add(bottomPanel.new OpenInfoWindow("onclick")
		{
			protected InfoWindow getInfoWindow()
			{
				return new HelloPanel(new GLatLng(44.0, 44.0));
			}
		});
		add(infoWindow);
		add(new Link("reload")
		{
			@Override
			public void onClick()
			{
			}
		});
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
