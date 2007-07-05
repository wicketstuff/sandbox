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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GLatLngBounds;
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

	List<GOverlay> overlays = new ArrayList<GOverlay>();

	private final WebMarkupContainer map;

	private InfoWindow infoWindow;

	private GLatLngBounds bounds;

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

		infoWindow = new InfoWindow();
		add(infoWindow);

		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);
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

	public GLatLngBounds getBounds() {
		return bounds;
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
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GMap2 openInfoWindow(GLatLng latLng, Component content)
	{
		infoWindow.open(latLng, new GInfoWindowTab(content));

		return this;
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GMap2 openInfoWindow(GMarker marker, Component content)
	{
		infoWindow.open(marker, new GInfoWindowTab(content));

		return this;
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GMap2 openInfoWindow(GLatLng latLng, GInfoWindowTab... tabs)
	{
		infoWindow.open(latLng, tabs);

		return this;
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public GMap2 openInfoWindow(GMarker marker, GInfoWindowTab... tabs)
	{
		infoWindow.open(marker, tabs);

		return this;
	}

	/**
	 * Close the info window.
	 * 
	 * @return This
	 */
	public GMap2 closeInfoWindow()
	{
		if (infoWindow.isOpen()) {
			infoWindow.close();
		}

		return this;
	}

	private String getJSid()
	{
		return map.getMarkupId();
	}

	private String getJSinit()
	{
		String js = "Wicket.GMap2.addMap(\"" + getJSid() + "\", " + getCenter().getJSConstructor() + ", " + getZoom() + ");\n";

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

		js += infoWindow.getJSinit();
		
		for (Object behavior : getBehaviors(ListenerBehavior.class)) {
			js += ((ListenerBehavior)behavior).getJSinit();
		}

		return js;
	}

	private String getJSsetZoom(int zoom)
	{
		return "Wicket.GMap2.setZoom('" + getJSid() + "', " + zoom + ");\n";
	}

	private String getJSsetCenter(GLatLng center)
	{
		return "Wicket.GMap2.setCenter('" + getJSid() + "', '" + center.getJSConstructor() + "');\n";
	}

	private String getJSaddControl(GControl control)
	{
		return "Wicket.GMap2.addControl('" + getJSid() + "', '" + control.getJSIdentifier() + "', '"
				+ control.getJSConstructor() + "');\n";
	}

	private String getJSremoveControl(GControl control)
	{
		return "Wicket.GMap2.removeControl('" + getJSid() + "', '" + control.getJSIdentifier() + "');\n";
	}

	private String getJSaddOverlay(GOverlay overlay)
	{
		return "Wicket.GMap2.addOverlay('" + getJSid() + "', '" + overlay.getJSIdentifier() + "', '"
				+ overlay.getJSConstructor() + "');\n";
	}

	private String getJSremoveOverlay(GOverlay overlay)
	{
		return "Wicket.GMap2.removeOverlay('" + getJSid() + "', '" + overlay.getJSIdentifier() + "');\n";
	}

	private String getJSpanDirection(int dx, int dy) {
		return "Wicket.GMap2.panDirection('" + getJSid() + "'," + dx
		+ "," + dy + ");\n";
	}

	private String getJSzoomOut() {
		return "Wicket.GMap2.zoomOut('" + getJSid() + "');\n";
	}
		
	private String getJSzoomIn() {
		return "Wicket.GMap2.zoomIn('" + getJSid() + "');\n";
	}
	
	/**
	 * Update state from a request to an AJAX target.
	 */
	private void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = GLatLngBounds.parse(request.getParameter("bounds"));
		center = GLatLng.parse(request.getParameter("center"));
		zoom = Integer.parseInt(request.getParameter("zoom"));
		if (Boolean.parseBoolean(request.getParameter("hidden"))) {
			closeInfoWindow();
		}
	}

	/**
	 * Represents an Google Maps API's
	 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GInfoWindow">GInfoWindow</a>.
	 */
	private class InfoWindow extends WebMarkupContainer
	{

		private GInfoWindowTab[] tabs;
		
		private GLatLng latLng;
		
		private GMarker marker;
		
		private RepeatingView repeater = new RepeatingView("content");

		public InfoWindow()
		{
			super("infoWindow");

			setOutputMarkupId(true);
			
			add(repeater);
		}

		public String getJSinit() {
			if (latLng != null)
			{
				return getJSopen(latLng, tabs);
			}

			if (marker != null)
			{
				return getJSopen(marker, tabs);
			}
			
			return "";
		}

		private void setTabs(GInfoWindowTab[] tabs)
		{
			repeater.removeAll();
				
			this.tabs = tabs;
			for (GInfoWindowTab tab : tabs) {
				repeater.add(tab.getContent());
			}
		}
		
		public void open(GLatLng latLng, GInfoWindowTab... tabs)
		{
			setTabs(tabs);
			
			this.latLng = latLng;
			this.marker = null;
			
			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSopen(latLng, tabs));
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).addComponent(this);
			}
		}
		
		public boolean isOpen() {
			return (latLng != null || marker != null);
		}
		
		public void open(GMarker marker, GInfoWindowTab... tabs)
		{
			setTabs(tabs);
			
			this.latLng = null;
			this.marker = marker;
			
			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSopen(marker, tabs));
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).addComponent(this);
			}
		}
		
		public void close() {
			tabs = null;
			repeater.removeAll();

			this.marker = null;
			this.latLng = null;
			
			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSclose());
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).addComponent(this);
			}
		}
		
		private String getJSopen(GLatLng latLng, GInfoWindowTab[] tabs)
		{
			StringBuffer buffer = new StringBuffer();

			buffer.append("Wicket.GMap2.openInfoWindowTabs('");
			buffer.append(getJSid());
			buffer.append("',");
			buffer.append(latLng.getJSConstructor());
			buffer.append(",[");
			
			boolean first = true;
			for (GInfoWindowTab tab : tabs) {
				if (!first) {
					buffer.append(",");
				}
				buffer.append(tab.getJSConstructor());
				first = false;
			}
			
			buffer.append("])\n");
			
			return buffer.toString();			
		}

		private String getJSopen(GMarker marker, GInfoWindowTab[] tabs)
		{
			StringBuffer buffer = new StringBuffer();

			buffer.append("Wicket.GMap2.openMarkerInfoWindowTabs('");
			buffer.append(getJSid());
			buffer.append("',");
			buffer.append(marker.getJSIdentifier());
			buffer.append(",[");
			
			boolean first = true;
			for (GInfoWindowTab tab : tabs) {
				if (!first) {
					buffer.append(",");
				}
				buffer.append(tab.getJSConstructor());
				first = false;
			}
			
			buffer.append("])\n");
			
			return buffer.toString();			
		}

		private String getJSclose()
		{
			return "Wicket.GMap2.closeInfoWindow('" + getJSid() + "');\n";
		}

	}
	
	public class ZoomOut extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public ZoomOut(String event)
		{
			super(event, true, new Model("javascript:" + getJSzoomOut()));
		}
	}

	public class ZoomIn extends AttributeModifier
	{

		private static final long serialVersionUID = 1L;

		public ZoomIn(String event)
		{
			super(event, true, new Model("javascript:" + getJSzoomIn()));
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

	public static abstract class ListenerBehavior extends AbstractDefaultAjaxBehavior
	{	
		private static final long serialVersionUID = 1L;

		private String getJSinit()
		{
			StringBuffer buffer = new StringBuffer();

			buffer.append("Wicket.GMap2.");
			buffer.append(getJSadd());
			buffer.append("(\"");
			buffer.append(getGMap2().getJSid());
			buffer.append("\", \"");
			buffer.append(getCallbackUrl());
			buffer.append("\");\n");
			
			return buffer.toString();
		}
		
		protected abstract String getJSadd();
		
		protected final GMap2 getGMap2() {
			return (GMap2)getComponent();
		}
		
		@Override
		protected final void respond(AjaxRequestTarget target)
		{
			getGMap2().update(target);
			
			onEvent(target);
		}

		protected abstract void onEvent(AjaxRequestTarget target);
	}
}