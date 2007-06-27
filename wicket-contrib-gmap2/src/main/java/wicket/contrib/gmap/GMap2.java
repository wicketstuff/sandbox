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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.converters.IntegerConverter;

import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a>
 * into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate
 * one for each deployment context you have. See the <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API sign up
 * page</a> for more information.
 */
public class GMap2 extends Panel
{
	private static final long serialVersionUID = 1L;

	/** URL for Google Maps' API endpoint. */
	private static final String GMAP_API_URL = "http://maps.google.com/maps?file=api&amp;v=2&amp;key=";

	// We have some custom Javascript.
	private static final ResourceReference WICKET_GMAP_JS = new JavascriptResourceReference(
			GMap2.class, "wicket-gmap.js");

	// We also depend on wicket-ajax.js within wicket-gmap.js
	private static final ResourceReference WICKET_AJAX_JS = new JavascriptResourceReference(
			AbstractDefaultAjaxBehavior.class, "wicket-ajax.js");

	private GLatLng center = new GLatLng(37.4419, -122.1419);

	private int zoom = 13;

	private Set<GControl> controls = new HashSet<GControl>();

	private List<GOverlay> overlays = new ArrayList<GOverlay>();

	private final WebMarkupContainer mapContainer;

	private Component infoWindow;

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 */
	public GMap2(final String id, final String gMapKey)
	{
		this(id, gMapKey, new ArrayList<GOverlay>());
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 * @param overlays
	 */
	public GMap2(final String id, final String gMapKey, List<GOverlay> overlays)
	{
		super(id);

		this.overlays = overlays;

		add(getHeaderContributor(gMapKey));

		WebMarkupContainer infoWindowContainer = new WebMarkupContainer("info");
		infoWindowContainer.setOutputMarkupId(true);
		add(infoWindowContainer);

		infoWindow = new EmptyPanel("window");
		infoWindowContainer.add(infoWindow);

		mapContainer = new WebMarkupContainer("map");
		mapContainer.setOutputMarkupId(true);
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
				// see:
				// http://www.google.com/apis/maps/documentation/#Memory_Leaks
				response.renderOnBeforeUnloadJavascript("GUnload();");
				response.renderOnDomReadyJavascript(getJSinit());
			}
		});
	}

	/**
	 * Add a control.
	 * 
	 * @param control
	 *            control to add
	 * @return This
	 */
	public GMap2 addControl(GControl control)
	{
		controls.add(control);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSaddControl(control));
		}

		return this;
	}

	/**
	 * Remove a control.
	 * 
	 * @param control
	 *            control to remove
	 * @return This
	 */
	public GMap2 removeControl(GControl control)
	{
		controls.remove(control);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSremoveControl(control));
		}

		return this;
	}

	/**
	 * Add an overlay.
	 * 
	 * @param overlay
	 *            overlay to add
	 * @return This
	 */
	public GMap2 addOverlay(GOverlay overlay)
	{
		overlays.add(overlay);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSaddOverlay(overlay));
		}

		return this;
	}

	/**
	 * Remove an overlay.
	 * 
	 * @param overlay
	 *            overlay to remove
	 * @return This
	 */
	public GMap2 removeOverlay(GOverlay overlay)
	{
		for (int o = 0; o < overlays.size(); o++)
		{
			overlays.remove(overlay);
		}

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSremoveOverlay(overlay));
		}

		return this;
	}

	public List<GOverlay> getOverlays()
	{
		return Collections.unmodifiableList(overlays);
	}

	public Set<GControl> getControls()
	{
		return Collections.unmodifiableSet(controls);
	}

	public GLatLng getCenter()
	{
		return center;
	}

	public int getZoom()
	{
		return zoom;
	}

	public void setZoom(int level)
	{
		if (this.zoom != level)
		{
			this.zoom = level;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSsetZoom(zoom));
			}
		}
	}

	/**
	 * Set the center.
	 * 
	 * @param center
	 *            center to set
	 */
	public void setCenter(GLatLng center)
	{
		if (!this.center.equals(center))
		{
			this.center = center;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSsetCenter(center));
			}
		}
	}

	/**
	 * Open an info window.
	 * 
	 * @param infoWindow
	 *            info window to open
	 * @return This
	 */
	public GMap2 openInfoWindow(GInfoWindow infoWindow)
	{
		// replace the panel held, by the invisible div element.
		this.infoWindow.replaceWith(infoWindow);
		this.infoWindow = infoWindow;

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSopenInfoWindow(infoWindow));
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).addComponent(infoWindow
					.getParent());
		}

		return this;
	}

	/**
	 * Get the current info window.
	 * 
	 * @return the current info window or <code>null</code> if no current
	 */
	public GInfoWindow getInfoWindow()
	{
		if (infoWindow instanceof GInfoWindow)
		{
			return (GInfoWindow)infoWindow;
		}
		else
		{
			return null;
		}
	}

	private String getJSMapId()
	{
		return mapContainer.getMarkupId();
	}

	private String getJSinit()
	{
		String js = "addGMap(\"" + getJSMapId() + "\", " + getCenter().getLat() + ", "
				+ getCenter().getLng() + ", " + getZoom() + ");\n";

		// Add the controls.
		for (GControl control : controls)
		{
			js += getJSaddControl(control);
		}

		// Add the overlays.
		for (GOverlay overlay : overlays)
		{
			js += getJSaddOverlay(overlay);
		}

		if (infoWindow instanceof GInfoWindow)
		{
			js += getJSopenInfoWindow(((GInfoWindow)infoWindow));
		}

		for (Object behavior : getBehaviors(ListenerBehavior.class)) {
			js += getJSaddListener((ListenerBehavior)behavior);
		}

		return js;
	}

	private String getJSaddListener(ListenerBehavior behavior)
	{
		return "addListener(\"" + getJSMapId() + "\", " + "\"" + behavior.getJSEvent() + "\", " + "\""
					+ behavior.getCallbackUrl() + "\");\n";
	}

	private String getJSsetZoom(int zoom)
	{
		return "setZoom('" + getJSMapId() + "', " + zoom + ");\n";
	}

	private String getJSsetCenter(GLatLng center)
	{
		return "setCenter('" + getJSMapId() + "', '" + center.getJSConstructor() + "');\n";
	}

	private String getJSaddControl(GControl control)
	{
		return "addControl('" + getJSMapId() + "', '" + control.getJSIdentifier() + "', '"
				+ control.getJSConstructor() + "');\n";
	}

	private String getJSremoveControl(GControl control)
	{
		return "removeControl('" + getJSMapId() + "', '" + control.getJSIdentifier() + "');\n";
	}

	private String getJSaddOverlay(GOverlay overlay)
	{
		return "addOverlay('" + getJSMapId() + "', '" + overlay.getJSIdentifier() + "', '"
				+ overlay.getJSConstructor() + "');\n";
	}

	private String getJSremoveOverlay(GOverlay overlay)
	{
		return "removeOverlay('" + getJSMapId() + "', '" + overlay.getJSIdentifier() + "');\n";
	}

	private String getJSopenInfoWindow(GInfoWindow panel)
	{
		return "openInfoWindow('" + getJSMapId() + "'," + "'"
				+ panel.getGLatLng().getJSConstructor() + "','" + panel.getMarkupId() + "');\n";
	}

	private String getJSpanDirection(int dx, int dy) {
		return "Wicket.gmaps['" + getJSMapId() + "']" + ".panDirection(" + dx
		+ "," + dy + ");\n";
	}
	
	public class ZoomOut extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public ZoomOut(String event)
		{
			super(event, true, new Model("javascript:Wicket.gmaps['" + getJSMapId() + "']" + ".zoomOut();"));
		}
	}

	public class ZoomIn extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public ZoomIn(String event)
		{
			super(event, true, new Model("javascript:Wicket.gmaps['" + getJSMapId() + "']" + ".zoomIn();"));
		}
	}

	public class PanDirection extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public PanDirection(String event, final int dx, final int dy)
		{
			super(event, true, new Model("javascript:" + getJSpanDirection(dx, dy)));
		}
	}

	public class SetZoom extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public SetZoom(String event, final int zoom)
		{
			super(event, true, new Model("javascript:" + getJSsetZoom(zoom)));
		}
	}

	public class SetCenter extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public SetCenter(String event, GLatLng gLatLng)
		{
			super(event, true, new Model("javascript:" + getJSsetCenter(gLatLng)));
		}
	}

	private abstract class ListenerBehavior extends AbstractDefaultAjaxBehavior
	{
		public abstract String getJSEvent();
	}

	public abstract class MoveEndBehavior extends ListenerBehavior
	{

		private static final long serialVersionUID = 1L;

		@Override
		public String getJSEvent() {
			return "moveend";
		}

		/*
		 * TODO update bounds
		 */
		@Override
		protected final void respond(AjaxRequestTarget target)
		{
			Request request = RequestCycle.get().getRequest();

			// Attention: don't use setters as this will result in an endless
			// AJAX request loop
			center = GLatLng.fromString(request.getParameter("center"));
			zoom = (Integer)IntegerConverter.INSTANCE.convertToObject(request
					.getParameter("zoom"), Locale.getDefault());

			MoveEndBehavior.this.onMoveEnd(target);
		}

		/**
		 * Override this method to provide handling of a move.<br>
		 * You can get the new center coordinates of the map by calling
		 * {@link #getCenter()}.
		 * 
		 * @param target
		 *            the target that initiated the move
		 */
		protected abstract void onMoveEnd(AjaxRequestTarget target);
	}

	public abstract class ClickBehavior extends ListenerBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		public String getJSEvent() {
			return "click";
		}

		@Override
		protected final void respond(AjaxRequestTarget target)
		{
			Request request = RequestCycle.get().getRequest();

			String markerString = request.getParameter("marker");
			if ("".equals(markerString))
			{
				GLatLng gLatLng = GLatLng.fromString(request.getParameter("gLatLng"));
				ClickBehavior.this.onClick(gLatLng, target);
			}
			else
			{
				for (GOverlay overlay : overlays)
				{
					if (overlay.getJSIdentifier().equals(markerString))
					{
						ClickBehavior.this.onClick((GMarker)overlay, target);
						break;
					}
				}
			}
		}

		/**
		 * Override this method to provide handling of a click on a GLatLng.
		 * 
		 * @param gLatLng
		 *            the clicked GLatLng
		 * @param target
		 *            the target that initiated the click
		 */
		protected abstract void onClick(GLatLng gLatLng, AjaxRequestTarget target);

		/**
		 * Override this method to provide handling of a click on a marker.<br>
		 * This default implementation forwards the click to the marker.
		 * 
		 * @param marker
		 *            the clicked marker
		 * @param target
		 *            the target that initiated the click
		 */
		protected abstract void onClick(GMarker marker, AjaxRequestTarget target);

	}
}