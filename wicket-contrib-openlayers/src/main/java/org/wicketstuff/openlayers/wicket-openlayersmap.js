
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
 * Wicket Map2
 *
 * @author Nino Martinz
 */

// Wicket Namespace
var Wicket;
if (!Wicket) {
	Wicket = {};
} else {
	if (typeof Wicket != "object") {
		throw new Error("Wicket already exists and is not an object");
	}
}
Wicket.omaps = {};
function WicketOMap(id, options) {
	// Default seems to be 0
	OpenLayers.IMAGE_RELOAD_ATTEMPTS = 5;
	Wicket.omaps[id] = this;
	if (options !== null) {
		this.map = new OpenLayers.Map(id, options);
	} else {
		this.map = new OpenLayers.Map(id);
	}
	this.controls = {};
	this.overlays = {};
	this.openOverlays = new OpenLayers.Layer.Markers("markers" + id);
	this.map.addLayer(this.openOverlays);
	this.popup = null;
	
	this.onEvent = function (callBack, params) {
		params["center"] = this.map.getCenter();
		params["bounds"] = this.map.getExtent();
		params["zoom"] = this.map.getZoomForExtent(this.map.getExtent(), false);
		// could not find
		params["hidden"] = this.map.getInfoWindow().isHidden();
		for (var key in params) {
			callBack = callBack + "&" + key + "=" + params[key];
		}
		wicketAjaxGet(callBack, function () {
		}, function () {
		});
	};
	this.addLayer = function (layer) {
		var self = this;
		self.map.addLayer(layer);
	};
	this.zoomToMaxExtent = function () {
		var self = this;
		self.map.zoomToMaxExtent();
	};
	this.addListener = function (event, callBack) {
		var self = this;
		if (event == "click" || event == "dblclick") {
			Events.register(this.map, event, function (marker, gLatLng) {
				self.onEvent(callBack, {"marker":(marker === null ? "" : marker.overlayId), "latLng":gLatLng});
			});
		} else {
			Events.register(this.map, event, function () {
				self.onEvent(callBack, {});
			});
		}
	};
	this.addMarkerListener = function (event, callBack, marker) {
		marker.events.register(event, marker, function (evt) {
			var self = this;
			var call = callBack;
			if (self.popup != null) {
				if (!self.popup.visible()) {
					self.map.removePopup(self.popup);
					self.popup.destroy();
					self.popup = null;
				}
			}
			if (self.popup == null) {
				var wcall=wicketAjaxGet(call,null,null,null);
				self.popup = new OpenLayers.Popup("chicken", marker.lonlat, new OpenLayers.Size(200, 200), document.getElementById("infoWindow"), true);
				self.popup.setContentHTML(call);
				self.popup.setBackgroundColor("yellow");
				self.popup.setOpacity(0.7);
				self.map.addPopup(self.popup);
			} else {
				self.map.removePopup(self.popup);
				self.popup.destroy();
				self.popup = null;
			}
			OpenLayers.Event.stop(evt);
		});
	};
	this.addGOverlayListener = function (event, overlayID, callBack) {
		var self = this;
		if (event == "dragend") {
			var overlay = this.overlays[overlayID];
			Events.register(overlay, event, function () {
				self.onEvent(callBack, {"marker":overlayID, "latLng":overlay.getLatLng()});
			});
		}
	};
//	this.setDraggingEnabled = function (enabled) {
//		if (enabled) {
//			// to be fixed!
//			//this.map.enableDragging(true);
//		} else {
//			// to be fixed!
//			//this.map.disableDragging(false);
//		}
//	};
//	this.setDoubleClickZoomEnabled = function (enabled) {
//		if (enabled) {
//			// to be fixed!
//			//this.map.enableDoubleClickZoom(true);
//		} else {
//			//this.map.disableDoubleClickZoom(false);
//		}
//	};
//	this.setScrollWheelZoomEnabled = function (enabled) {
//		if (enabled) {
//			//this.map.enableScrollWheelZoom(true);
//		} else {
//			//this.map.disableScrollWheelZoom(false);
//		}
//	};
//	this.setMapType = function (mapType) {
//		//this.map.setMapType(mapType);
//	};
//	this.setZoom = function (level) {
//		this.map.zoomTo(level);
//	};
//	this.setCenter = function (center) {
//		this.map.setCenter(center);
//	};
//	this.panDirection = function (dx, dy) {
//		this.map.pan(dx, dy);
//	};
//	this.zoomOut = function () {
//		this.map.zoomOut();
//	};
//	this.zoomIn = function () {
//		this.map.zoomIn();
//	};
	this.addControl = function (controlId, control) {
		this.controls[controlId] = control;
		this.map.addControl(control);
	};
	this.removeControl = function (controlId) {
		if (this.controls[controlId] !== null) {
			this.map.removeControl(this.controls[controlId]);
			this.controls[controlId] = null;
		}
	};
//	//marker?
	this.addOverlay = function (overlayId, overlay) {
		this.overlays[overlayId] = overlay;
		overlay.overlayId = overlayId;
		this.openOverlays.addMarker(overlay);
	};
	this.removeOverlay = function (overlayId) {
		if (this.overlays[overlayId] !== null) {
			this.openOverlays.removeMarker(this.overlays[overlayId]);
			this.overlays[overlayId] = null;
		}
	};
	this.clearOverlays = function () {
		this.overlays = {};
		markers.destroy();
		markers = new OpenLayers.Layer.Markers("markers" + this.div.getId());
	};
//	this.openInfoWindowTabs = function (latLng, tabs) {
//		this.map.openInfoWindowTabs(latLng, tabs);
//	};
//	this.openMarkerInfoWindowTabs = function (markerId, tabs) {
//		this.overlays[markerId].openInfoWindowTabs(tabs);
//	};
//	this.closeInfoWindow = function () {
//		this.map.closeInfoWindow();
//	};
}

