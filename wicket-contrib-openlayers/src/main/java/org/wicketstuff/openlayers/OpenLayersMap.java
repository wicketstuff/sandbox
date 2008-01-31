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
package org.wicketstuff.openlayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.openlayers.api.Bounds;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.GInfoWindow;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.Overlay;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.event.OverlayListenerBehavior;
import org.wicketstuff.openlayers.event.PopupListener;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a>
 * into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate
 * one for each deployment context you have. See the <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API sign up
 * page</a> for more information.
 */
public class OpenLayersMap extends Panel {

	private static final long serialVersionUID = 1L;

	private LonLat center = new LonLat(37.4419, -122.1419);

	private int zoom = 13;

	private List<Control> controls = new ArrayList<Control>();

	private List<Overlay> overlays = new ArrayList<Overlay>();

	private List<Layer> layers = new ArrayList<Layer>();

	private final WebMarkupContainer map;

	private GInfoWindow infoWindow;

	private Bounds bounds;
	
	private boolean externalControls=false;

	private PopupListener callbackListener = null;

	private HashMap<String, String> options = new HashMap<String, String>();

	/**
	 * 
	 * Constructs a map with a default layer : "OpenLayers WMS",
	 * "http://labs.metacarta.com/wms/vmap0"
	 * 
	 * @param id
	 */
	public OpenLayersMap(final String id) {
		this(id, new OpenLayersMapHeaderContributor(), new ArrayList<Overlay>());

		HashMap<String, String> options = new HashMap<String, String>();
		options.put("layers", "'basic'");
		layers.add(new WMS("OpenLayers WMS",
				"http://labs.metacarta.com/wms/vmap0", options));
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public OpenLayersMap(final String id, List<Layer> defaultLayers,
			HashMap<String, String> options) {
		this(id, new OpenLayersMapHeaderContributor(), new ArrayList<Overlay>());
		this.layers = defaultLayers;
		this.options = options;
	}

	public OpenLayersMap(final String id, List<Layer> defaultLayers,
			HashMap<String, String> options, List<Overlay> overlays) {
		this(id, new OpenLayersMapHeaderContributor(), overlays);
		this.layers = defaultLayers;
		this.options = options;
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param overlays
	 */
	public OpenLayersMap(final String id, List<Layer> layers,
			List<Overlay> overlays) {
		this(id, new OpenLayersMapHeaderContributor(), overlays);
		this.layers = layers;
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param googleHeaderContrib
	 * @param overlays
	 */
	private OpenLayersMap(final String id,
			final OpenLayersMapHeaderContributor headerContrib,
			List<Overlay> overlays) {
		super(id);

		this.overlays = overlays;

		// always add callbacklistener dont know if its gonna be used later on!
		callbackListener = new PopupListener() {
			@Override
			protected void onClick(AjaxRequestTarget target,
					Overlay overlay) {
				// Currently only support clicking on markers!
				Marker markerPassed = (Marker) overlay;
				OpenLayersMap.this.infoWindow.getContent()
						.replaceWith(markerPassed.getPopup());
				OpenLayersMap.this.infoWindow
						.setContent(markerPassed.getPopup());
				target.addComponent(markerPassed.getPopup());
				// String js = (OpenLayersMap.this
				// .getJSinvoke("setPopupId('"
				// + marker.getPopup().getMarkupId()
				// + "')"));
				// target.prependJavascript(js);

			}
		};
		add(callbackListener);

		add(headerContrib);
		add(new HeaderContributor(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response) {
				response.renderOnDomReadyJavascript(getJSinit());
			}
		}));

		infoWindow = new GInfoWindow();
		add(infoWindow);

		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);
	}

	/**
	 * Add a control.
	 * 
	 * @param control
	 *            control to add
	 * @return This
	 */
	public OpenLayersMap addControl(Control control) {
		controls.add(control);

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					control.getJSadd(OpenLayersMap.this));
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
	public OpenLayersMap removeControl(Control control) {
		controls.remove(control);

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					control.getJSremove(OpenLayersMap.this));
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
	public OpenLayersMap addOverlay(Overlay overlay) {
		overlays.add(overlay);
		for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
			add(behavior);
		}

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					overlay.getJSadd(OpenLayersMap.this));
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
	public OpenLayersMap removeOverlay(Overlay overlay) {
		while (overlays.contains(overlay)) {
			overlays.remove(overlay);
		}
		for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
			remove(behavior);
		}

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					overlay.getJSremove(OpenLayersMap.this));
		}

		return this;
	}

	/**
	 * Clear all overlays.
	 * 
	 * @return This
	 */
	public OpenLayersMap clearOverlays() {
		for (Overlay overlay : overlays) {
			for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
				remove(behavior);
			}
		}
		overlays.clear();
		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					getJSinvoke("clearOverlays()"));
		}
		return this;
	}

	public List<Overlay> getOverlays() {
		return Collections.unmodifiableList(overlays);
	}

	public List<Control> getControls() {
		return controls;
	}

	public LonLat getCenter() {
		return center;
	}

	public Bounds getBounds() {
		return bounds;
	}



	public int getZoom() {
		return zoom;
	}

	public void setZoom(int level) {
		if (this.zoom != level) {
			this.zoom = level;

			if (AjaxRequestTarget.get() != null && findPage() != null) {
				AjaxRequestTarget.get().appendJavascript(getJSsetZoom(zoom));
			}
		}
	}

	/**
	 * Set the center.
	 * 
	 * @param center
	 *            center to set
	 */
	public void setCenter(LonLat center) {
		if (!this.center.equals(center)) {
			this.center = center;

			if (AjaxRequestTarget.get() != null && findPage() != null) {
				AjaxRequestTarget.get()
						.appendJavascript(getJSsetCenter(center));
			}
		}
	}

	// public GInfoWindow getInfoWindow()
	// {
	// return infoWindow;
	// }

	/**
	 * Generates the JavaScript used to instantiate this GMap2 as an JavaScript
	 * class on the client side.
	 * 
	 * @return The generated JavaScript
	 */
	private String getJSinit() {

		StringBuffer js = new StringBuffer();

		if (options.size() > 0) {
			js.append("\nvar options = {");
			boolean first = true;
			for (String key : options.keySet()) {
				if (first) {
					first = false;
				} else {
					js.append(",\n");
				}
				js.append(key + ":" + options.get(key));
			}
			js.append("};\n");
			js
					.append("new WicketOMap('" + map.getMarkupId()
							+ "', options);\n");
		} else {
			js.append("new WicketOMap('" + map.getMarkupId() + "', null);\n");
		}

		int layersid = 0;
		for (Layer layer : layers) {
			if (layer instanceof WMS) {
				WMS wms = (WMS) layer;
				js.append("var wms" + layersid + " =" + wms.getJSconstructor()
						+ ";\n");
				js.append(getJSinvoke("addLayer(wms" + layersid + ")"));
			}
			layersid++;
		}
		js.append(getJSinvoke("zoomToMaxExtent()"));
		for (Control control : controls) {
			js.append(control.getJSadd(this));
		}
		// js.append(getJSsetCenter(getCenter()) + "\n");
		// js.append(getJSsetZoom(getZoom()) + "\n");
		// js.append(getJSsetDraggingEnabled(draggingEnabled) + "\n");
		// js
		// .append(getJSsetDoubleClickZoomEnabled(doubleClickZoomEnabled)
		// + "\n");
		// js
		// .append(getJSsetScrollWheelZoomEnabled(scrollWheelZoomEnabled)
		// + "\n");
		//
		// js.append(mapType.getJSsetMapType(this) + "\n");
		//
		//
		// Add the overlays.
		for (Overlay overlay : overlays) {
			js.append(overlay.getJSadd(this) + "\n");
			if (overlay instanceof Marker) {
				Marker marker = (Marker) overlay;
				if (marker.getPopup() != null) {
					js.append(getJSinvoke("addMarkerListener('mousedown','"
							+ callbackListener.getCallBackForMarker(marker) + "'," + marker.getOverlayJSVar()
							+ ")"));
				}
			}
		}
		js.append(getJSinvoke("setPopupId('"
				+ infoWindow.getContent().getMarkupId() + "')"));

		// js.append(infoWindow.getJSinit() + "\n");
		//
		// for (Object behavior : getBehaviors(EventListenerBehavior.class)) {
		// js.append(((EventListenerBehavior) behavior).getJSaddListener()
		// + "\n");
		// }

		return js.toString();
	}

	/**
	 * Convenience method for generating a JavaScript call on this GMap2 with
	 * the given invocation.
	 * 
	 * @param invocation
	 *            The JavaScript call to invoke on this GMap2.
	 * @return The generated JavaScript.
	 */
	// TODO Could this become default or protected?
	public String getJSinvoke(String invocation) {
		return "Wicket.omaps['" + map.getMarkupId() + "']." + invocation
				+ ";\n";
	}
	public String getJSInstance() {
		return "Wicket.omaps['" + map.getMarkupId() + "']";
	}

	public String getJSinvokeNoLineEnd(String invocation) {
		return "Wicket.omaps['" + map.getMarkupId() + "']." + invocation;
	}

	private String getJSsetDraggingEnabled(boolean enabled) {
		return getJSinvoke("setDraggingEnabled(" + enabled + ")");
	}

	private String getJSsetDoubleClickZoomEnabled(boolean enabled) {
		return getJSinvoke("setDoubleClickZoomEnabled(" + enabled + ")");
	}

	private String getJSsetScrollWheelZoomEnabled(boolean enabled) {
		return getJSinvoke("setScrollWheelZoomEnabled(" + enabled + ")");
	}

	private String getJSsetZoom(int zoom) {
		return getJSinvoke("setZoom(" + zoom + ")");
	}

	private String getJSsetCenter(LonLat center) {
		if (center != null)
			return getJSinvoke("setCenter(" + center.getJSconstructor() + ")");
		else
			return "";
	}

	private String getJSpanDirection(int dx, int dy) {
		return getJSinvoke("panDirection(" + dx + "," + dy + ")");
	}

	private String getJSzoomOut() {
		return getJSinvoke("zoomOut()");
	}

	private String getJSzoomIn() {
		return getJSinvoke("zoomIn()");
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = Bounds.parse(request.getParameter("bounds"));
		center = LonLat.parse(request.getParameter("center"));
		zoom = Integer.parseInt(request.getParameter("zoom"));

		infoWindow.update(target);
	}

	public void setOverlays(List<Overlay> overlays) {
		clearOverlays();
		for (Overlay overlay : overlays) {
			addOverlay(overlay);
		}
	}

	private abstract class JSMethodBehavior extends AbstractBehavior {

		private static final long serialVersionUID = 1L;

		private String attribute;

		public JSMethodBehavior(final String attribute) {
			this.attribute = attribute;
		}

		/**
		 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
		 *      org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		public void onComponentTag(Component component, ComponentTag tag) {
			String invoke = getJSinvoke();

			if (attribute.equalsIgnoreCase("href")) {
				invoke = "javascript:" + invoke;
			}

			tag.put(attribute, invoke);
		}

		protected abstract String getJSinvoke();
	}

	public class ZoomOutBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		public ZoomOutBehavior(String event) {
			super(event);
		}

		@Override
		protected String getJSinvoke() {
			return getJSzoomOut();
		}
	}

	public class ZoomInBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		public ZoomInBehavior(String event) {
			super(event);
		}

		@Override
		protected String getJSinvoke() {
			return getJSzoomIn();
		}
	}

	public class PanDirectionBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		private int dx;

		private int dy;

		public PanDirectionBehavior(String event, final int dx, final int dy) {
			super(event);
			this.dx = dx;
			this.dy = dy;
		}

		@Override
		protected String getJSinvoke() {
			return getJSpanDirection(dx, dy);
		}
	}

	public class SetZoomBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		private int zoom;

		public SetZoomBehavior(final String event, final int zoom) {
			super(event);
			this.zoom = zoom;
		}

		@Override
		protected String getJSinvoke() {
			return getJSsetZoom(zoom);
		}
	}

	public class SetCenterBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		private LonLat gLatLng;

		public SetCenterBehavior(String event, LonLat gLatLng) {
			super(event);
			this.gLatLng = gLatLng;
		}

		@Override
		protected String getJSinvoke() {
			return getJSsetCenter(gLatLng);
		}
	}

	public PopupListener getCallbackListener() {
		return callbackListener;
	}

	public boolean isExternalControls() {
		return externalControls;
	}

	public void setExternalControls(boolean externalControls) {
		this.externalControls = externalControls;
	}
}