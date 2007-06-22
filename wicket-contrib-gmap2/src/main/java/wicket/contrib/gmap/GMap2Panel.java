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
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
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
	private static final ResourceReference WICKET_GMAP_JS = new JavascriptResourceReference(GMap2Panel.class, "wicket-gmap.js");
	
	// We also depend on wicket-ajax.js within wicket-gmap.js 
	private static final ResourceReference WICKET_AJAX_JS = new JavascriptResourceReference(AbstractDefaultAjaxBehavior.class,
			"wicket-ajax.js");
	

	private static final int DEFAULT_WIDTH = 500, DEFAULT_HEIGHT = 300;
	
	private final int width, height;

	private final DivMapComponent div;

	/** Invisible container that holds the information about the InfoWindow. */
	private WebMarkupContainer divDisplayNone;

	/** Panel containing the node to be displayed in the GMap. */
	private Panel divInfoWindowPanel;

	private final List<ClickListener> clickListeners = new ArrayList<ClickListener>();
	private final List<MoveEndListener> moveEndListeners = new ArrayList<MoveEndListener>();

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey Google gmap API KEY
	 * @param model
	 */
	public GMap2Panel(final String id, final String gMapKey, final GMap2 model)
	{
		this(id, gMapKey, DEFAULT_WIDTH, DEFAULT_HEIGHT, model);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param gMapKey Google gmap API KEY
	 * @param width Width in pixels
	 * @param height Height in pixels
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
		add(new HeaderContributor(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;
			
			public void renderHead(IHeaderResponse response) {
				response.renderJavascriptReference(GMAP_API_URL + gMapKey);
				// We don't want to have to fake in a 
				response.renderJavascriptReference(WicketEventReference.INSTANCE);
				response.renderJavascriptReference(WICKET_AJAX_JS);
				response.renderJavascriptReference(WICKET_GMAP_JS);
				
				// TODO: Add the GUnload() method to wicket-gmap.js to do whatever it
				// is the original author intended in his previous todo. ;-) --Al
				// response.renderOnBeforeUnloadJavascript("Wicket.Event.add(window, \"unload\", function() { GUnload();});");
			}
		}));

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
	 * Binds an 'openInfoWindow' call on this map, to the given event of the given component. <a
	 * href="http://www.google.com/apis/maps/documentation/reference.html#GMap2">GMap2</a>
	 * 
	 * @param component
	 * @param factory
	 * @param event
	 */
	public void addOpenInfoWindowControl(final Component component, final GLatLng point,
			final InfoWindowPanel panel, String event)
	{
		component.add(new AjaxEventBehavior(event)
		{
			private static final long serialVersionUID = 1L;
	
			@Override
			protected CharSequence getFailureScript()
			{
				return "alert('Failure on: " + component.getId() + "')";
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
	 * @param component
	 * @param factory
	 * @param event
	 */
	public void addAddOverlayControll(final Component component, final GLatLngFactory factory,
			final String event)
	{
		component.add(new AjaxEventBehavior(event)
		{
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
	 * @param component
	 */
	public void addZoomInControll(final Component component, final String event)
	{
		component.add(new AjaxEventBehavior(event)
		{
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
	public void addPanDirectionControll(final Component component, final int dx, final int dy,
			final String event)
	{
		component.add(new AjaxEventBehavior(event)
		{
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
	public void addZoomOutControll(final Component component, final String event)
	{
		component.add(new AjaxEventBehavior(event)
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