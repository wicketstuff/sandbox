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
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.IJavascriptResponse;
import org.apache.wicket.ajax.AjaxRequestTarget.IListener;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.CompoundPropertyModel;

import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GLatLngFactory;
import wicket.contrib.gmap.api.GMap2;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.events.ClickEvent;
import wicket.contrib.gmap.api.events.ClickListener;
import wicket.contrib.gmap.api.events.MoveEndEvent;
import wicket.contrib.gmap.api.events.MoveEndListener;

/**
 * A reusable wicket component for <a href="http://maps.google.com">Google Maps</a>.
 * Becasue Maps API requires a different key for each deployment context you
 * have to either generate a new key (check <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API - Sign Up</a>
 * for more info).
 */
public class GMap2Panel extends Panel
{
	private static final long serialVersionUID = 1L;

	public static final String GMAP_URL = "http://maps.google.com/maps?file=api&amp;v=2&amp;key=";

	private final int height;

	private final int width;

	private final DivMapComponent div;

	/**
	 * Invisible container that holds the information about the InfoWindow.
	 */
	private WebMarkupContainer divDisplayNone;

	/**
	 * Panel containing the node to be displayed in the GMap.
	 */
	private Panel divInfoWindowPanel;

	private final List<ClickListener> clickListeners = new ArrayList<ClickListener>();

	private final List<MoveEndListener> moveEndListeners = new ArrayList<MoveEndListener>();

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 * @param model
	 */
	public GMap2Panel(final String id, final String gMapKey, final GMap2 model)
	{
		this(id, gMapKey, 500, 300, model);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey
	 * @param width
	 * @param height
	 * @param model
	 */
	public GMap2Panel(final String id, final String gMapKey, final int width, final int height,
			final GMap2 model)
	{
		super(id, new CompoundPropertyModel(model));
		setOutputMarkupId(true);
		this.width = width;
		this.height = height;
		model.setGMap2Panel(this);

		// Set up the JavaScript context for this Panel.
		add(new AbstractDefaultAjaxBehavior()
		{
			/**
			 * Default serailVersionUID
			 */
			private static final long serialVersionUID = 1L;

			/** reference to the default support javascript file. */
			private final ResourceReference GMAPSCRIPT = new JavascriptResourceReference(
					GMap2Panel.class, "wicket-gmap.js");

			@Override
			public void renderHead(IHeaderResponse response)
			{
				response.renderJavascriptReference(GMAP_URL + gMapKey);
				super.renderHead(response);
				response.renderJavascriptReference(GMAPSCRIPT);

				response.renderOnLoadJavascript(getCallbackScript().toString());
			}

			@Override
			protected void respond(AjaxRequestTarget target)
			{
				// Adds a call to GUnLoad() to the unload event of the body
				// element.
				// TODO Bug, doesen't work.
				target
						.appendJavascript("Wicket.Event.add(window, \"unload\", function() { GUnLoad();});");
			}
		});

		divInfoWindowPanel = new EmptyPanel("divInfoWindowPanel");
		divInfoWindowPanel.setOutputMarkupId(true);
		divDisplayNone = new WebMarkupContainer("divDisplayNone");
		divDisplayNone.setOutputMarkupId(true);
		divDisplayNone.add(divInfoWindowPanel);
		add(divDisplayNone);

		div = new DivMapComponent(this, "divMapComponent", model);
		add(div);
	}

	/**
	 * Binds a 'openInfoWindow' call on this map, to the given event of the
	 * given component. <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param comp
	 * @param factory
	 * @param event
	 */
	public void addOpenInfoWindowControl(final Component comp, final GLatLng point,
			final InfoWindowPanel panel, String event)
	{
		comp.add(new AjaxEventBehavior(event)
		{
			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;
	
			@Override
			protected CharSequence getFailureScript()
			{
				return "alert('Failure on: " + comp.getId() + "')";
			}
	
			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				// replace the panel held, by the invisible div element.
				panel.setOutputMarkupId(true);
				divDisplayNone.replace(panel);
				target.addComponent(divDisplayNone);
	
				target.appendJavascript("openInfoWindow('" + getMarkupId() + "'," + "'"
						+ point.getJSConstructor() + "','" + divInfoWindowPanel.getMarkupId()
						+ "')");
			}
		});
	}

	/**
	 * Binds a 'addOverlay' call on this map, to the given event of the given
	 * component. <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param comp
	 * @param factory
	 * @param event
	 */
	public void addAddOverlayControll(final Component comp, final GLatLngFactory factory,
			final String event)
	{
		comp.add(new AjaxEventBehavior(event)
		{
			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				GMarker marker = new GMarker(factory.getGLatLng());
				target.appendJavascript("addOverlay(\"" + getMarkupId() + "\", '"
						+ marker.hashCode() + "', '" + marker.getJSConstructor() + "');");
			}
		});
	}

	/**
	 * Binds a 'zoomIn()' call on this map, to the 'onclick' event of the given
	 * Component. <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param comp
	 */
	public void addZoomInControll(final Component comp, final String event)
	{
		comp.add(new AjaxEventBehavior(event)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
	
			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				target.appendJavascript("Wicket.gmaps['" + getMarkupId() + "']" + ".zoomIn();");
			}
		});
	}

	/**
	 * Binds a 'panDirection(dx, dy)' call on this map, to the event of the
	 * given component. <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param comp
	 * @param dx
	 * @param dy
	 * @param event
	 */
	public void addPanDirectionControll(final Component comp, final int dx, final int dy,
			final String event)
	{
		comp.add(new AjaxEventBehavior(event)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
	
			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				target.appendJavascript("Wicket.gmaps['" + getMarkupId() + "']" + ".panDirection("
						+ dx + "," + dy + ");");
			}
		});
	}

	/**
	 * Binds a 'zoomOut()' call on this map, to the given event of the given
	 * Component.
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param comp
	 */
	public void addZoomOutControll(final Component comp, final String event)
	{
		comp.add(new AjaxEventBehavior(event)
		{
			/**
			 * Default serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;
	
			/**
			 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
			 */
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				target.appendJavascript("Wicket.gmaps['" + getMarkupId() + "']" + ".zoomOut();");
			}
		});
	}

	/**
	 * @param listener
	 */
	public void addClickListener(ClickListener listener)
	{
		clickListeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeClickListener(Component listener)
	{
		clickListeners.remove(listener);
	}

	/**
	 * All Listeners will be notified of the event.
	 * 
	 * @param clickEvent
	 * @param target
	 */
	public void processClickEvent(ClickEvent clickEvent, AjaxRequestTarget target)
	{
		for (ClickListener listener : clickListeners)
		{
			listener.clickPerformed(clickEvent, target);
		}
	}

	/**
	 * @param listener
	 */
	public void addMoveEndListener(MoveEndListener listener)
	{
		moveEndListeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeMoveEndListener(Component listener)
	{
		moveEndListeners.remove(listener);
	}

	/**
	 * All Listeners will be notified of the event.
	 * 
	 * @param event
	 * @param target
	 */
	public void processMoveEndEvent(MoveEndEvent event, AjaxRequestTarget target)
	{
		for (MoveEndListener listener : moveEndListeners)
		{
			listener.moveEndPerformed(event, target);
		}
	}

	/**
	 * Provides an AjaxRequestTarget.IListener providing JavaScript code to call
	 * the openInfoWindow method on the GMap. This is typically used by
	 * Listeners on the client programmers side to be able to add InfoWindow
	 * within a AjaxCallCycle.
	 * 
	 * @param latLng
	 * @param panel
	 * @return
	 */
	public IListener getJSOpenInfoWindow(final GLatLng latLng, final InfoWindowPanel panel)
	{
		return new AjaxRequestTarget.IListener()
		{

			public void onAfterRespond(Map map, IJavascriptResponse response)
			{
				response.addJavascript("openInfoWindow('" + getMarkupId() + "'," + "'"
						+ latLng.getJSConstructor() + "','" + divInfoWindowPanel.getMarkupId()
						+ "')");
			}

			public void onBeforeRespond(Map map, AjaxRequestTarget target)
			{
				panel.setOutputMarkupId(true);
				divDisplayNone.replace(panel);
				target.addComponent(divDisplayNone);
			}

		};
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}
}