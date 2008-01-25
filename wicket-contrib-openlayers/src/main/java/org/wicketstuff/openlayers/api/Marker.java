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
package org.wicketstuff.openlayers.api;

import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.openlayers.js.Constructor;

/**
 * Represents an Openlayers API's
 * http://dev.openlayers.org/apidocs/files/OpenLayers/Marker-js.html
 */
public class Marker extends Overlay {
	private static final long serialVersionUID = 1L;

	private LonLat latLng;

	private Panel popup = null;

	private MarkerOptions options;

	/**
	 * @param gLatLng
	 *            the point on the map where this marker will be anchored
	 */
	public Marker(LonLat gLatLng) {
		this(gLatLng, null,null);
	}

	public Marker(LonLat gLatLng, MarkerOptions options) {
		super();
		this.latLng = gLatLng;
		this.options = options;
	}

	public Marker(LonLat gLatLng, MarkerOptions options, Panel popup) {
		this(gLatLng, options);
		this.popup = popup;
	}

	public Marker(LonLat gLatLng, Panel popup) {
		this(gLatLng, null,popup);
	}

	public LonLat getLagLng() {
		return latLng;
	}

	public void setLagLng(LonLat gLatLng) {
		this.latLng = gLatLng;
	}

	public MarkerOptions getMarkerOptions() {
		return this.options;
	}

	@Override
	protected String getJSconstructor() {
		Constructor constructor = new Constructor("OpenLayers.Marker")
				.add(latLng.getJSconstructor());
		if (options != null) {
			constructor.add(options.getJSconstructor());
		}
		return constructor.toJS();
	}

	public Panel getPopup() {
		return popup;
	}
}
