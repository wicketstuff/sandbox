/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
/*
 * Wicket GMap2
 *
 * @author Martin Funk
 */

// Wicket Namespace
if (typeof(Wicket) == "undefined") {
	Wicket = { };
}

Wicket.GMap2 = {
	addMap: function(id, center, zoom) {
		var map = new GMap2(document.getElementById(id));
		map.setCenter(eval(center), zoom);
		this[id] = map;
	},

	getMap: function(id) {
		return this[id];
	},

	addMoveendListener: function(id, callBack) {
		var map = this.getMap(id);
		GEvent.addListener( map , 'moveend',
			function () {wicketAjaxGet(callBack 
				+ '&center=' + map.getCenter()
				+ '&bounds=' + map.getBounds()
				+ '&zoom=' + map.getZoom()),
			function(){},
			function(){alert("ooops!")}}
		);
	},

	addClickListener: function(id, callBack) {
		var map = this.getMap(id);
		GEvent.addListener( map , 'click',
			function (marker, gLatLng) {wicketAjaxGet(callBack 
				+ '&center=' + map.getCenter()
				+ '&bounds=' + map.getBounds()
				+ '&zoom=' + map.getZoom()
				+ '&marker=' + (marker == null ? "" : marker.overlayId)
				+ '&gLatLng=' + gLatLng),
			function(){},
			function(){alert("ooops on ClickDeff of!" + map)}}
		);
	},

	setZoom: function(id, level) {
		var map = this.getMap(id);
		map.setZoom(level);
	},

	setCenter: function(id, center) {
		var map = this.getMap(id);
		map.setCenter(eval(center));
	},

	panDirection: function(id, dx, dy) {
		var map = this.getMap(id);
		map.panDirection(dx, dy);
	},

	zoomOut: function(id) {
		var map = this.getMap(id);
		map.zoomOut();
	},

	zoomIn: function(id) {
		var map = this.getMap(id);
		map.zoomIn();
	},

	addControl: function(id, controlId, control) {
		var map = this.getMap(id);
		map[controlId] = eval(control);
		map.addControl(map[controlId]);
	},

	removeControl: function(id, controlId) {
		var map = this.getMap(id);
		if (map[controlId] != null) {
			map.removeControl(map[controlId]);
			map[controlId] = null;
		}
	},

	addOverlay: function(id, overlayId, overlay) {
		var map = this.getMap(id);
		map[overlayId] = eval(overlay);
		map[overlayId].overlayId = overlayId;
		map.addOverlay(map[overlayId]);
	},

	removeOverlay: function(id, overlayId) {
		var map = this.getMap(id);
		if (map[overlayId] != null) {
			map.removeOverlay(map[overlayId]);
			map[overlayId] = null;
		}
	},

	openInfoWindowTabs: function(id, latLng, tabs) {
		var map = this.getMap(id);
		map.openInfoWindowTabs(eval(latLng), eval(tabs));
	},

	openMarkerInfoWindowTabs: function(id, markerId, tabs) {
		var map = this.getMap(id);
		map[markerId].openInfoWindowTabs(eval(tabs));
	},

	closeInfoWindow: function(id) {
		var map = this.getMap(id);
		map.closeInfoWindow();
	}
}