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
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.AbstractReadOnlyModel;
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
	private List<GOverlay> overlays = new ArrayList<GOverlay>();

	private final WebMarkupContainer mapContainer;

	private Component infoWindow;

	private final MoveEndBehaviour moveEndBehaviour;
	private final ClickBehaviour clickBehaviour;

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 */
	public GMap2Panel(final String id, final String gMapKey)
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
	public GMap2Panel(final String id, final String gMapKey, List<GOverlay> overlays)
	{
		super(id);

		this.overlays = overlays;

		moveEndBehaviour = new MoveEndBehaviour();
		clickBehaviour = new ClickBehaviour();

		add(getHeaderContributor(gMapKey));
		add(moveEndBehaviour);
		add(clickBehaviour);

		WebMarkupContainer infoWindowContainer = new WebMarkupContainer("infoWindow");
		infoWindowContainer.setOutputMarkupId(true);
		add(infoWindowContainer);

		infoWindow = new EmptyPanel("infoWindow");
		infoWindowContainer.add(infoWindow);

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
				// see:
				// http://www.google.com/apis/maps/documentation/#Memory_Leaks
				response.renderOnBeforeUnloadJavascript("GUnload();");
				response.renderOnDomReadyJavascript(getJSInit());
			}
		});
	}

	private String getJSInit()
	{
		String js = "addGMap(\"" + getMapId() + "\", " + getCenter().getLat() + ", "
				+ getCenter().getLng() + ", "
				+ getZoomLevel()
				+ ", \""
				// provides the GMap with information which script to
				// call
				// on a moveend or a click event.
				+ moveEndBehaviour.getCallbackUrl() + "\", " + "\""
				+ clickBehaviour.getCallbackUrl() + "\");\n";

		// Add the controls.
		for (GControl control : controls)
		{
			js += getJSControlAdded(control);
		}

		// Add the overlays.
		for (GOverlay overlay : overlays)
		{
			js += getJSOverlayAdded(overlay);
		}

		if (infoWindow instanceof InfoWindowPanel)
		{
			js += getJSInfoWindowOpened(((InfoWindowPanel)infoWindow));
		}

		return js;
	}

	private String getMapId()
	{
		return mapContainer.getMarkupId();
	}

	public void addControl(GControl control)
	{
		controls.add(control);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSControlAdded(control));
		}
	}

	public void removeControl(GControl control)
	{
		controls.remove(control);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSControlRemoved(control));
		}
	}

	public void addOverlay(GOverlay overlay)
	{
		overlays.add(overlay);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSOverlayAdded(overlay));
		}
	}

	public void removeOverlay(GOverlay overlay)
	{
		overlays.remove(overlay);

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSOverlayRemoved(overlay));
		}
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

	public int getZoomLevel()
	{
		return zoomLevel;
	}

	public void setZoomLevel(int parameter)
	{
		if (this.zoomLevel != parameter)
		{
			this.zoomLevel = parameter;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSZoomSet(parameter));
			}
		}
	}

	public void setCenter(GLatLng center)
	{
		if (!this.center.equals(center))
		{
			this.center = center;

			if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
			{
				((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
						.appendJavascript(getJSCenterSet(center));
			}
		}
	}

	public void openInfoWindow(InfoWindowPanel panel)
	{
		// replace the panel held, by the invisible div element.
		infoWindow.replaceWith(panel);
		this.infoWindow = panel;

		if (RequestCycle.get().getRequestTarget() instanceof AjaxRequestTarget)
		{
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget())
					.appendJavascript(getJSInfoWindowOpened(panel));
			((AjaxRequestTarget)RequestCycle.get().getRequestTarget()).addComponent(infoWindow
					.getParent());
		}
	}

	public InfoWindowPanel getInfoWindow()
	{
		if (infoWindow instanceof InfoWindowPanel)
		{
			return (InfoWindowPanel)infoWindow;
		}
		else
		{
			return null;
		}
	}

	private String getJSZoomSet(int zoom)
	{
		return "setZoom('" + getMapId() + "', " + zoom + ");\n";
	}

	private String getJSCenterSet(GLatLng center)
	{
		return "setCenter('" + getMapId() + "', '" + center.getJSConstructor() + "');\n";
	}

	private String getJSControlAdded(GControl control)
	{
		return "addControl('" + getMapId() + "', '" + control.getIdentifier() + "', '"
				+ control.getJSConstructor() + "');\n";
	}

	private String getJSControlRemoved(GControl control)
	{
		return "removeControl('" + getMapId() + "', '" + control.getIdentifier() + "');\n";
	}

	private String getJSOverlayAdded(GOverlay overlay)
	{
		return "addOverlay('" + getMapId() + "', '" + overlay.getIdentifier() + "', '"
				+ overlay.getJSConstructor() + "');\n";
	}

	private String getJSOverlayRemoved(GOverlay overlay)
	{
		return "removeOverlay('" + getMapId() + "', '" + overlay.getIdentifier() + "');\n";
	}

	private String getJSInfoWindowOpened(InfoWindowPanel panel)
	{
		return "openInfoWindow('" + getMapId() + "'," + "'" + panel.getGLatLng().getJSConstructor()
				+ "','" + panel.getMarkupId() + "')";
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
	 * Notify of a click.
	 * 
	 * @param target
	 * @param target
	 */
	public void onClick(GMarker marker, AjaxRequestTarget target)
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


	public class ZoomOut extends AjaxEventBehavior
	{
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public ZoomOut(String event)
		{
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			target.appendJavascript("Wicket.gmaps['" + getMapId() + "']" + ".zoomOut();");
		}
	}

	public class ZoomIn extends AjaxEventBehavior
	{
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public ZoomIn(String event)
		{
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			target.appendJavascript("Wicket.gmaps['" + getMapId() + "']" + ".zoomIn();");
		}
	}

	public abstract class OpenInfoWindow extends AjaxEventBehavior
	{
		private static final long serialVersionUID = 1L;

		public OpenInfoWindow(String event)
		{
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			openInfoWindow(getInfoWindow());
		}

		protected abstract InfoWindowPanel getInfoWindow();
	}

	public class PanDirection extends AjaxEventBehavior
	{
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		private int dx;

		private int dy;

		public PanDirection(String event, final int dx, final int dy)
		{
			super(event);

			this.dx = dx;
			this.dy = dy;
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			target.appendJavascript("Wicket.gmaps['" + getMapId() + "']" + ".panDirection(" + dx
					+ "," + dy + ");");
		}
	}

	public class SetZoom extends AjaxEventBehavior
	{
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		private int zoom;

		public SetZoom(String event, final int zoom)
		{
			super(event);

			this.zoom = zoom;
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			setZoomLevel(zoom);
		}
	}

	public abstract class AddOverlay extends AjaxEventBehavior
	{
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public AddOverlay(String event)
		{
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			addOverlay(getOverlay());
		}

		protected abstract GOverlay getOverlay();
	}

	public abstract class SetCenter extends AjaxEventBehavior
	{
		/**
		 * Default serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		public SetCenter(String event)
		{
			super(event);
		}

		/**
		 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void onEvent(AjaxRequestTarget target)
		{
			setCenter(getCenter());
		}

		protected abstract GLatLng getCenter();
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

	public class MoveEndBehaviour extends AbstractDefaultAjaxBehavior
	{

		private static final long serialVersionUID = 1L;

		/*
		 * TODO update bounds
		 */
		@Override
		protected void respond(AjaxRequestTarget target)
		{
			Request request = RequestCycle.get().getRequest();

			// Attention: don't use setters as this will result in an endless
			// loop
			center = GLatLng.fromString(request.getParameter("center"));
			zoomLevel = (Integer)IntegerConverter.INSTANCE.convertToObject(request
					.getParameter("zoom"), Locale.getDefault());

			onMoveEnd(target);
		}
	}

	public class ClickBehaviour extends AbstractDefaultAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target)
		{
			Request request = RequestCycle.get().getRequest();

			String markerString = request.getParameter("marker");
			if ("".equals(markerString))
			{
				GLatLng gLatLng = GLatLng.fromString(request.getParameter("point"));
				onClick(gLatLng, target);
			}
			else
			{
				for (GOverlay overlay : overlays)
				{
					if (overlay.getIdentifier().equals(markerString))
					{
						((GMarker)overlay).onClick(target);
						onClick((GMarker)overlay, target);
						break;
					}
				}
			}
		}
	}
}