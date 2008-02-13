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

import org.wicketstuff.openlayers.js.Constructor;

/**
 * Represents an Openlayers API's
 * http://dev.openlayers.org/apidocs/files/OpenLayers/Marker-js.html
 */
public class Marker extends Overlay {
	private static final long serialVersionUID = 1L;

	private LonLat lonLat;

	private PopupWindowPanel popup = null;

	private MarkerOptions options;
	
	private Icon icon=null;

	/**
	 * @param gLatLng
	 *            the point on the map where this marker will be anchored
	 */
	public Marker(LonLat gLatLng) {
		this(gLatLng, null, null);
	}

	public Marker(LonLat lonLat, MarkerOptions options) {
		super();
		this.lonLat = lonLat;
		this.options = options;
	}

	public Marker(LonLat lonLat, MarkerOptions options, PopupWindowPanel popup) {
		this(lonLat, options);
		this.popup = popup;
	}
	

	public Marker(LonLat lonLat, PopupWindowPanel popup, MarkerOptions options,
			Icon icon) {
		super();
		this.lonLat = lonLat;
		this.popup = popup;
		this.options = options;
		this.icon = icon;
	}

	public Marker(LonLat gLatLng, PopupWindowPanel popup) {
		this(gLatLng, null, popup);
	}

	public LonLat getLagLng() {
		return lonLat;
	}

	public void setLagLng(LonLat gLatLng) {
		this.lonLat = gLatLng;
	}

	public MarkerOptions getMarkerOptions() {
		return this.options;
	}

	@Override
	protected String getJSconstructor() {
		Constructor constructor = new Constructor("OpenLayers.Marker")
				.add(lonLat.getJSconstructor());
		if (options != null) {
			constructor.add(options.getJSconstructor());
		}
		if (icon != null) {
			constructor.add(icon.getId());
		}

		return constructor.toJS();
	}

	public PopupWindowPanel getPopup() {
		return popup;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}
}
