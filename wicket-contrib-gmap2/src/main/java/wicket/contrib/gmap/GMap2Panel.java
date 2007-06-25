/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.behaviour.ClickBehaviour;
import wicket.contrib.gmap.behaviour.MoveEndBehaviour;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a> into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate one for each deployment context you have. See the
 * <a href="http://www.google.com/apis/maps/signup.html">Google Maps API sign up page</a> for more information.
 */
public class GMap2Panel extends Panel
{
	private static final long serialVersionUID = 1L;

	/** URL for Google Maps' API endpoint. */
	private static final String GMAP_API_URL = "http://maps.google.com/maps?file=api&amp;v=2&amp;key=";

	// We have some custom Javascript.
	private static final ResourceReference WICKET_GMAP_JS = new JavascriptResourceReference(
			GMap2Panel.class, "wicket-gmap.js");

	// We also depend on wicket-ajax.js within wicket-gmap.js 
	private static final ResourceReference WICKET_AJAX_JS = new JavascriptResourceReference(
			AbstractDefaultAjaxBehavior.class, "wicket-ajax.js");

	private GLatLng center = new GLatLng(37.4419, -122.1419);
	private int zoomLevel = 13;
	private Set<GControl> controls = new HashSet<GControl>();
	private Set<GControl> previousUpdateControls;
	private List<GOverlay> previousUpdateOverlays;

	private final WebMarkupContainer mapContainer;

	/** Invisible container that holds the information about the InfoWindow. */
	private WebMarkupContainer infoWindowContainer;

	private final MoveEndBehaviour moveEndBehaviour;
	private final ClickBehaviour clickBehaviour;
	
	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey Google gmap API KEY
	 * @param model
	 */
	public GMap2Panel(final String id, final String gMapKey)
	{
		this(id, gMapKey, new Model(new ArrayList<GOverlay>()));
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey Google gmap API KEY
	 * @param overlays
	 */
	public GMap2Panel(final String id, final String gMapKey, List< ? extends GOverlay> overlays)
	{
		this(id, gMapKey, new Model((Serializable)overlays));
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey Google gmap API KEY
	 * @param model
	 */
	public GMap2Panel(final String id, final String gMapKey, final IModel overlays)
	{
		super(id, overlays);
		moveEndBehaviour = new MoveEndBehaviour(this);
		clickBehaviour = new ClickBehaviour(this);

		add(getHeaderContributor(gMapKey));
		add(moveEndBehaviour);
		add(clickBehaviour);

		infoWindowContainer = new WebMarkupContainer("infoWindow");
		infoWindowContainer.setOutputMarkupId(true);
		add(infoWindowContainer);

		infoWindowContainer.add(new EmptyPanel("infoWindow"));

		mapContainer = new WebMarkupContainer("map");
		mapContainer.setOutputMarkupId(true);
		mapContainer.add(new AttributeModifier("style", true, new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject()
			{
				return "width: " + getWidth() + getWidthUnit() + "; height: " + getHeight()
						+ getHeightUnit() + ";";
			}
		}));
		add(mapContainer);
	}

	private HeaderContributor getHeaderContributor(final String gMapKey)
	{
		// Set up the JavaScript context for this Panel.
		return new HeaderContributor(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response)
			{
				response.renderJavascriptReference(GMAP_API_URL + gMapKey);
				// We don't want to have to fake in a 
				response.renderJavascriptReference(WicketEventReference.INSTANCE);
				response.renderJavascriptReference(WICKET_AJAX_JS);
				response.renderJavascriptReference(WICKET_GMAP_JS);
				// see: http://www.google.com/apis/maps/documentation/#Memory_Leaks
				response.renderOnBeforeUnloadJavascript("GUnload();");
				response.renderOnDomReadyJavascript(getJSOnLoad() + getJSControlAndOverlayUpdate());
			}
		});
	}
	
	private String getJSOnLoad() {
		String js = "addGMap(\"" + getMapId() + "\", " + getCenter().getLat()
		+ ", " + getCenter().getLng() + ", "
		+ getZoomLevel()
		+ ", \""
		// provides the GMap with information which script to
		// call
		// on a moveend or a click event.
		+ moveEndBehaviour.getCallbackUrl() + "\", " + "\""
		+ clickBehaviour.getCallbackUrl() + "\");\n";
		return js;
	}
	
	private String getJSControlAndOverlayUpdate() {
		String js = "";
		
		HashSet<GControl> newControls = new HashSet<GControl>(getControls());
		if (previousUpdateControls != null && !previousUpdateControls.equals(newControls))
		{
			// Remove previous controls that are no longer present.
			for (GControl control : previousUpdateControls)
			{
				if (!newControls.contains(control))
				{
					js += getJSControlRemoved(control);
				}
			}
		}
		if (previousUpdateControls != null)
		{
			// Remove any previous controls from our new set of overlays, which will
			// leave only new items to add.
			newControls.removeAll(previousUpdateControls);
		}
		
		// Add the new controls.
		for (GControl control : newControls)
		{
			js += getJSControlAdded(control);
		}

		HashSet<GOverlay> newOverlays = new HashSet<GOverlay>((List<GOverlay>)getModelObject());
		if (previousUpdateOverlays != null && !previousUpdateOverlays.equals(newOverlays))
		{
			// Remove previous overlays that are no longer present.
			for (GOverlay overlay : previousUpdateOverlays)
			{
				if (!newOverlays.contains(overlay))
				{
					js += getJSOverlayRemoved(overlay);
				}
			}
		}
		if (previousUpdateOverlays != null)
		{
			// Remove any previous overlays from our new set of overlays, which will
			// leave only new items to add.
			newOverlays.removeAll(previousUpdateOverlays);
		}

		// Add the new overlays.
		for (GOverlay overlay : newOverlays)
		{
			js += getJSOverlayAdded(overlay);
		}
		
		previousUpdateControls = getControls();
		previousUpdateOverlays = new ArrayList((List<GOverlay>)getModelObject());
		
		return js;
	}

	private String getMapId()
	{
		return mapContainer.getMarkupId();
	}

	public void addControl(GControl control)
	{
		controls.add(control);
	}

	public Set<GControl> getControls()
	{
		return Collections.unmodifiableSet(controls);
	}

	public GLatLng getCenter()
	{
		return center;
	}

	public int getZoomLevel()
	{
		return zoomLevel;
	}

	public void setZoomLevel(int parameter)
	{
		this.zoomLevel = parameter;
	}

	public void setCenter(GLatLng center)
	{
		this.center = center;
	}

	public String getJSControlAdded(GControl control)
	{
		return "addControl('" + getMapId() + "', '" + control.hashCode() + "', '"
				+ control.getJSConstructor() + "');\n";
	}

	public String getJSControlRemoved(GControl control)
	{
		return "removeControl('" + getMapId() + "', '" + control.hashCode() + "');\n";
	}

	public String getJSOverlayAdded(GOverlay overlay)
	{
		return "addOverlay('" + getMapId() + "', '" + overlay.hashCode() + "', '"
				+ overlay.getJSConstructor() + "');\n";
	}

	public String getJSOverlayRemoved(GOverlay overlay)
	{
		return "removeOverlay('" + getMapId() + "', '" + overlay.hashCode() + "');\n";
	}
	
	public void updateControlsAndOverlays(AjaxRequestTarget target)
	{
		target.appendJavascript(getJSControlAndOverlayUpdate());
	}
	
	public void openInfoWindow(InfoWindowPanel panel, GLatLng point, AjaxRequestTarget target)
	{
		// replace the panel held, by the invisible div element.
		panel.setOutputMarkupId(true);
		infoWindowContainer.replace(panel);

		target.addComponent(infoWindowContainer);
		target.appendJavascript("openInfoWindow('" + getMapId() + "'," + "'"
				+ point.getJSConstructor() + "','" + panel.getMarkupId() + "')");
	}

	/**
	 * Notify of a click.
	 * 
	 * @param gLatLng
	 * @param target
	 */
	public void onClick(GLatLng gLatLng, AjaxRequestTarget target)
	{
		// Override me.
	}

	/**
	 * Event handler invoked when map finishes moving. You can get the new
	 * center coordinates of the map by calling {@link #getCenter()}.
	 * 
	 * @param target
	 */
	public void onMoveEnd(AjaxRequestTarget target)
	{
		// Override me.
	}


	public class ZoomOut extends AjaxEventBehavior {
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public ZoomOut(String event) {
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target) {
			target.appendJavascript("Wicket.gmaps['" + getMapId() + "']"
					+ ".zoomOut();");
		}
	}

	public class ZoomIn extends AjaxEventBehavior {
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public ZoomIn(String event) {
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target) {
			target.appendJavascript("Wicket.gmaps['" + getMapId() + "']"
					+ ".zoomIn();");
		}
	}
	 
	/**
	 * Binds an 'openInfoWindow' call on this map, to the given event of the given component. <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param component
	 * @param factory
	 * @param event
	 */
	public IBehavior createOpenInfoWindowBehavior(final GLatLng point, final InfoWindowPanel panel,
			String event)
	{
		return new AjaxEventBehavior(event)
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				openInfoWindow(panel, point, target);
			}
		};
	}

	public class PanDirection extends AjaxEventBehavior {
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		private int dx;

		private int dy;

		public PanDirection(String event, final int dx, final int dy) {
			super(event);

			this.dx = dx;
			this.dy = dy;
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target) {
			target.appendJavascript("Wicket.gmaps['" + getMapId() + "']"
					+ ".panDirection(" + dx + "," + dy + ");");
		}
	}
		 

	public int getWidth()
	{
		return 400;
	}

	public int getHeight()
	{
		return 300;
	}

	public String getWidthUnit()
	{
		return "px";
	}

	public String getHeightUnit()
	{
		return "px";
	}
}