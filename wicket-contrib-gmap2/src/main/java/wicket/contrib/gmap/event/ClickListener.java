/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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
package wicket.contrib.gmap.event;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;

/**
 * TODO should we put 'click' and 'dblclkick' together in this listener?
 */
public abstract class ClickListener extends GMap2.ListenerBehavior
{

	@Override
	protected String getJSadd() {
		return "addClickListener";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		String markerString = request.getParameter("marker");
		if ("".equals(markerString))
		{
			GLatLng latLng = GLatLng.parse(request.getParameter("latLng"));

			// TODO decide between mapClick/mapDblClick
			onMapClick(target, latLng);
		}
		else
		{
			for (GOverlay overlay : getGMap2().getOverlays())
			{
				if (overlay.getJSIdentifier().equals(markerString))
				{
					onMarkerClick(target, (GMarker)overlay);
					break;
				}
			}
		}
	}

	/**
	 * Override this method to provide handling of a click on the map.
	 * 
	 * @param latLng
	 *            the clicked GLatLng
	 * @param target
	 *            the target that initiated the click
	 */
	protected void onMapClick(AjaxRequestTarget target, GLatLng latLng)
	{
	}

	/**
	 * Override this method to provide handling of a click on the map.
	 * 
	 * @param latLng
	 *            the clicked GLatLng
	 * @param target
	 *            the target that initiated the click
	 */
	protected void onMapDblClick(AjaxRequestTarget target, GLatLng latLng)
	{
	}
	
	/**
	 * Override this method to provide handling of a click on a GMarker.
	 * 
	 * @param marker
	 *            the clicked marker
	 * @param target
	 *            the target that initiated the click
	 */
	protected void onMarkerClick(AjaxRequestTarget target, GMarker marker)
	{
	}

}