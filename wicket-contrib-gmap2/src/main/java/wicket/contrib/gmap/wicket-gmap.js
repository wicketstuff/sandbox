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
var Wicket;
if(!Wicket)
{
	Wicket = {}
} else if (typeof Wicket != "object")
{
	throw new Error("Wicket already exists and is not an object");
}

// Wicket.GMap2 Namespace
if(Wicket.GMap2)
{
	throw new Error("Wicket.GMap2 already exists");
}

// Now create and populate it.
Wicket.GMap2 = {
	addMap: function(id) {
		var map = new GMap2(document.getElementById(id));
		this[id] = map;
	},

	getMap: function(id) {
		return this[id];
	},

	ajaxGet: function(id, callBack, params) {
		var map = this.getMap(id);
		
		params['center'] = map.getCenter();
		params['bounds'] = map.getBounds();
		params['zoom'] = map.getZoom();
		params['hidden'] = map.getInfoWindow().isHidden();
		
		for (var key in params) {
			callBack = callBack + '&' + key + '=' + params[key];
		}
		
		wicketAjaxGet(
			callBack,
			function() {
			},
			function() {
			}
		);
	},

	addListener: function(id, event, handler) {
		var map = this.getMap(id);
		GEvent.addListener(map, event, handler);
	},
	
	addMoveListener: function(id, callBack) {
		this.addListener(
			id,
			'moveend',
			function () {
				Wicket.GMap2.ajaxGet(id, callBack, {});
			}
		);
	},

	addDragListener: function(id, callBack) {
		this.addListener(
			id,
			'dragend',
			function () {
				Wicket.GMap2.ajaxGet(id, callBack, {});
			}
		);
	},

	addClickListener: function(id, callBack) {
		this.addListener(
			id,
			'click',
			function (marker, gLatLng) {
				Wicket.GMap2.ajaxGet(id, callBack, {'marker':(marker == null ? "" : marker.overlayId), 'latLng':gLatLng});
			}
		);
	},

	addInfoWindowListener: function(id, callBack) {
		this.addListener(
			id,
			'infowindowclose',
			function () {
				Wicket.GMap2.ajaxGet(id, callBack, {});
			}
		);
	},

	setDraggingEnabled: function(id, enabled) {
		var map = this.getMap(id);
		if (enabled) {
			map.enableDragging(true);
		} else {
			map.disableDragging(true);
		}
	},

	setDoubleClickZoomEnabled: function(id, enabled) {
		var map = this.getMap(id);
		if (enabled) {
			map.enableDoubleClickZoom(true);
		} else {
			map.disableDoubleClickZoom(true);
		}
	},

	setScrollWheelZoomEnabled: function(id, enabled) {
		var map = this.getMap(id);
		if (enabled) {
			map.enableScrollWheelZoom(true);
		} else {
			map.disableScrollWheelZoom(true);
		}
	},

	setMapType: function(id, mapType) {
		var map = this.getMap(id);
		map.setMapType(mapType);
	},

	setZoom: function(id, level) {
		var map = this.getMap(id);
		map.setZoom(level);
	},

	setCenter: function(id, center) {
		var map = this.getMap(id);
		map.setCenter(center);
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
		map[controlId] = control;
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
		map[overlayId] = overlay;
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
		map.openInfoWindowTabs(latLng, tabs);
	},

	openMarkerInfoWindowTabs: function(id, markerId, tabs) {
		var map = this.getMap(id);
		map[markerId].openInfoWindowTabs(tabs);
	},

	closeInfoWindow: function(id) {
		var map = this.getMap(id);
		map.closeInfoWindow();
	}
}