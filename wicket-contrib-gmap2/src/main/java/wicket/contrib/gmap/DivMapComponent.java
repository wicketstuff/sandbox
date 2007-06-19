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

import java.util.Iterator;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.converters.IntegerConverter;

import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMap2;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.converter.GLatLngConverter;
import wicket.contrib.gmap.api.converter.GMarkerConverter;
import wicket.contrib.gmap.api.events.ClickEvent;
import wicket.contrib.gmap.api.events.MoveEndEvent;

/**
 * Div Element of the GMap2Panel. This element contains the map.
 * See: <a
 * href="http://www.google.com/apis/maps/documentation/#The_Hello_World_of_Google_Maps">The_Hello_World_of_Google_Maps</a>
 */
class DivMapComponent extends WebComponent
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param panel
	 * @param id
	 * @param model
	 */
	public DivMapComponent(final GMap2Panel panel, final String id, final GMap2 model)
	{
		super(id);
		setOutputMarkupId(true);
		add(new AttributeModifier("style", new Model("width: " + panel.getWidth() + "px; height: "
				+ panel.getHeight() + "px")));

		// moveend event
		//Nothing is provided to the generated page, this behaviour waits for calls generated
		//by other behaviours.
		final AbstractDefaultAjaxBehavior moveendBehaviour = new AbstractDefaultAjaxBehavior()
		{
			/**
			 * Default serailVersionUID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target)
			{
				model.setCenter(
						(GLatLng)GLatLngConverter.INSTANCE.convertToObject(getRequest()
								.getParameter("center"), Locale.getDefault()));
				model.setZoomLevel(
						(Integer)IntegerConverter.INSTANCE.convertToObject(getRequest()
								.getParameter("zoom"), Locale.getDefault()));
				MoveEndEvent event = new MoveEndEvent();
				panel.processMoveEndEvent(event, target);
			}
		};
		add(moveendBehaviour);

		// click event
		//Nothing is provided to the generated page, this behaviour waits for calls generated
		//by other behaviours.
		final AbstractDefaultAjaxBehavior clickBehaviour = new AbstractDefaultAjaxBehavior()
		{
			/**
			 * Default serailVersionUID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target)
			{
				GMarker marker = (GMarker)GMarkerConverter.INSTANCE.convertToObject(getRequest()
						.getParameter("marker"), Locale.getDefault());
				GLatLng point = (GLatLng)GLatLngConverter.INSTANCE.convertToObject(getRequest()
						.getParameter("point"), Locale.getDefault());
				ClickEvent clickEvent = new ClickEvent(marker, point);
				panel.processClickEvent(clickEvent, target);
			}
		};
		add(clickBehaviour);

		// window.load event
		final AbstractDefaultAjaxBehavior windowLoadBehavior = new AbstractDefaultAjaxBehavior()
		{
			/**
			 * Default serailVersionUID
			 */
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response)
			{
				//Once the page is loaded, the client executes a javascript
				//that will call an ajax GET request to this behavior
				response.renderOnLoadJavascript(getCallbackScript().toString());
			}

			@Override
			protected void respond(AjaxRequestTarget target)
			{
				//Call all scripts nessessary to set up the GMap in the browser.
				target.appendJavascript(getAddGMapScript(model));
				for (Iterator<GControl> iterator = model.getControls().iterator(); iterator.hasNext();) {
					target.appendJavascript(getAddControls(iterator.next()));
				}
				for (Iterator<GOverlay> iterator = model.getOverlays().iterator(); iterator.hasNext();)
				{
					target.appendJavascript(getAddOverlays(iterator.next()));
				}
			}

			private String getAddControls(GControl control)
			{
				return "addControl(\"" + DivMapComponent.this.getParent().getMarkupId() + "\", "
				+ control.getJSConstructor()
				+ ")";
			}

			private String getAddOverlays(GOverlay element)
			{
				return "addOverlay(\"" + DivMapComponent.this.getParent().getMarkupId() + "\", "
				+ element.hashCode() + ", \""
				+ element.getJSConstructor()
				+ "\")";
			}

			private String getAddGMapScript(final GMap2 model)
			{
				return "addGMap(\"" + DivMapComponent.this.getParent().getMarkupId() + "\", "
						+ "\"" + DivMapComponent.this.getMarkupId() + "\", "
						+ model.getCenter().getLat() + ", "
						+ model.getCenter().getLng() + ", "
						+ model.getZoomLevel() + ", \""
						//provides the GMap with information which script to call
						//on a moveend or a click event.
						+ moveendBehaviour.getCallbackUrl() + "\", " + "\""
						+ clickBehaviour.getCallbackUrl() + "\")";
			}
		};
		add(windowLoadBehavior);
	}
}