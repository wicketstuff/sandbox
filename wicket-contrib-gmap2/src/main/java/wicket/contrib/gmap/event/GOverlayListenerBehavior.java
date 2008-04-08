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

import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;

public abstract class GOverlayListenerBehavior extends GEventListenerBehavior {

	private GOverlay overlay;

	protected GOverlay getOverlay() {
		return this.overlay;
	}

	public void setOverlay(GOverlay overlay) {
		this.overlay = overlay;
	}

	public String getJSaddListener() {
		return getGMap2().getJSinvoke(
				"addOverlayListener('" + getEvent() + "', '" + overlay.getId()
						+ "', '" + getCallbackUrl() + "')");
	}
	
	protected void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		String latLngParameter = request.getParameter("overlay.latLng");
		if (latLngParameter != null) {
			GMarker marker = (GMarker)overlay;

			marker.setLagLng(GLatLng.parse(latLngParameter));
		}		
	}
}
