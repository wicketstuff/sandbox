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

import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GInfoWindow;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GLatLngBounds;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.GEventListener;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a>
 * into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate
 * one for each deployment context you have. See the <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API sign up
 * page</a> for more information.
 */
public class GMap2 extends Panel {

	private static final long serialVersionUID = 1L;

	private GLatLng center = new GLatLng(37.4419, -122.1419);

	private boolean draggingEnabled = true;

	private boolean doubleClickZoomEnabled = false;

	private boolean scrollWheelZoomEnabled = false;

	private GMapType mapType = GMapType.G_NORMAL_MAP;

	private int zoom = 13;

	private Set<GControl> controls = new HashSet<GControl>();

	List<GOverlay> overlays = new ArrayList<GOverlay>();

	private final WebMarkupContainer map;

	private GInfoWindow infoWindow;

	private GLatLngBounds bounds;

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 */
	public GMap2(final String id, final GMapHeaderContributor headerContrib) {
		this(id, headerContrib, new ArrayList<GOverlay>());
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 */
	public GMap2(final String id, final String gMapKey) {
		this(id, new GMapHeaderContributor(gMapKey), new ArrayList<GOverlay>());
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 * @param overlays
	 */
	public GMap2(final String id, final String gMapKey, List<GOverlay> overlays) {
		this(id, new GMapHeaderContributor(gMapKey), overlays);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 * @param overlays
	 */
	public GMap2(final String id, final GMapHeaderContributor headerContrib,
			List<GOverlay> overlays) {
		super(id);

		this.overlays = overlays;

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
	public GMap2 addControl(GControl control) {
		controls.add(control);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
					.appendJavascript(control.getJSadd(GMap2.this));
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
	public GMap2 removeControl(GControl control) {
		controls.remove(control);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
					.appendJavascript(control.getJSremove(GMap2.this));
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
	public GMap2 addOverlay(GOverlay overlay) {
		overlays.add(overlay);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
					.appendJavascript(overlay.getJSadd(GMap2.this));
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
	public GMap2 removeOverlay(GOverlay overlay) {
		while (overlays.contains(overlay)) {
			overlays.remove(overlay);
		}

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
			((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
					.appendJavascript(overlay.getJSremove(GMap2.this));
		}

		return this;
	}

	public List<GOverlay> getOverlays() {
		return Collections.unmodifiableList(overlays);
	}

	public Set<GControl> getControls() {
		return Collections.unmodifiableSet(controls);
	}

	public GLatLng getCenter() {
		return center;
	}

	public GLatLngBounds getBounds() {
		return bounds;
	}

	public void setDraggingEnabled(boolean enabled) {
		if (this.draggingEnabled != enabled) {
			draggingEnabled = enabled;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSsetDraggingEnabled(enabled));
			}
		}
	}

	public boolean isDraggingEnabled() {
		return draggingEnabled;
	}

	public void setDoubleClickZoomEnabled(boolean enabled) {
		if (this.doubleClickZoomEnabled != enabled) {
			doubleClickZoomEnabled = enabled;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSsetDoubleClickZoomEnabled(enabled));
			}
		}
	}

	public boolean isDoubleClickZoomEnabled() {
		return doubleClickZoomEnabled;
	}

	public void setScrollWheelZoomEnabled(boolean enabled) {
		if (this.scrollWheelZoomEnabled != enabled) {
			scrollWheelZoomEnabled = enabled;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSsetScrollWheelZoomEnabled(enabled));
			}
		}
	}

	public boolean isScrollWheelZoomEnabled() {
		return scrollWheelZoomEnabled;
	}

	public GMapType getMapType() {
		return mapType;
	}

	public void setMapType(GMapType mapType) {
		if (this.mapType != mapType) {
			this.mapType = mapType;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
						.appendJavascript(mapType.getJSset(GMap2.this));
			}
		}
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int level) {
		if (this.zoom != level) {
			this.zoom = level;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
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
	public void setCenter(GLatLng center) {
		if (!this.center.equals(center)) {
			this.center = center;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget) {
				((AjaxRequestTarget) RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSsetCenter(center));
			}
		}
	}

	public GInfoWindow getInfoWindow() {
		return infoWindow;
	}

	private String getJSinit() {
		StringBuffer js = new StringBuffer("new WicketGMap2('"
				+ map.getMarkupId() + "');\n");

		js.append(getJSsetCenter(center) + "\n");
		js.append(getJSsetZoom(zoom) + "\n");
		js.append(getJSsetDraggingEnabled(draggingEnabled) + "\n");
		js
				.append(getJSsetDoubleClickZoomEnabled(doubleClickZoomEnabled)
						+ "\n");
		js
				.append(getJSsetScrollWheelZoomEnabled(scrollWheelZoomEnabled)
						+ "\n");

		js.append(mapType.getJSset(this) + "\n");

		// Add the controls.
		for (GControl control : controls) {
			js.append(control.getJSadd(this) + "\n");
		}

		// Add the overlays.
		for (GOverlay overlay : overlays) {
			js.append(overlay.getJSadd(this) + "\n");
		}

		js.append(infoWindow.getJSinit() + "\n");

		for (Object behavior : getBehaviors(GEventListener.class)) {
			js.append(((GEventListener) behavior).getJSadd() + "\n");
		}

		return js.toString();
	}

	public String getJSinvoke(String invocation) {
		return "Wicket.maps['" + map.getMarkupId() + "']." + invocation + ";";
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

	private String getJSsetCenter(GLatLng center) {
		return getJSinvoke("setCenter(" + center.getJSconstructor() + ")");
	}

	private String getJSpanDirection(int dx, int dy) {
		return getJSinvoke("panDirection(" + dx + "," + dy + ")");
	}

	private String getJSzoomOut() {
		return getJSinvoke("zoomOut('" + map.getMarkupId() + "')");
	}

	private String getJSzoomIn() {
		return getJSinvoke("zoomIn('" + map.getMarkupId() + "')");
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = GLatLngBounds.parse(request.getParameter("bounds"));
		center = GLatLng.parse(request.getParameter("center"));
		zoom = Integer.parseInt(request.getParameter("zoom"));

		infoWindow.update(target);
	}

	private abstract class JSMethod extends AbstractBehavior {

		private static final long serialVersionUID = 1L;

		private String attribute;

		public JSMethod(final String attribute) {
			this.attribute = attribute;
		}

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

	public class ZoomOut extends JSMethod {
		public ZoomOut(String event) {
			super(event);
		}

		@Override
		protected String getJSinvoke() {
			return getJSzoomOut();
		}
	}

	public class ZoomIn extends JSMethod {
		public ZoomIn(String event) {
			super(event);
		}

		@Override
		protected String getJSinvoke() {
			return getJSzoomIn();
		}
	}

	public class PanDirection extends JSMethod {
		private int dx;

		private int dy;

		public PanDirection(String event, final int dx, final int dy) {
			super(event);
			this.dx = dx;
			this.dy = dy;
		}

		@Override
		protected String getJSinvoke() {
			return getJSpanDirection(dx, dy);
		}
	}

	public class SetZoom extends JSMethod {
		private int zoom;

		public SetZoom(String event, final int zoom) {
			super(event);
			this.zoom = zoom;
		}

		@Override
		protected String getJSinvoke() {
			return getJSsetZoom(zoom);
		}
	}

	public class SetCenter extends JSMethod {
		private GLatLng gLatLng;

		public SetCenter(String event, GLatLng gLatLng) {
			super(event);
			this.gLatLng = gLatLng;
		}

		@Override
		protected String getJSinvoke() {
			return getJSsetCenter(gLatLng);
		}
	}
}