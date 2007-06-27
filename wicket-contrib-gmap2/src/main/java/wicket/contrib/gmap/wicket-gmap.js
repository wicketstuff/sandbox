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
if (Function.prototype.bind == null) {
	Function.prototype.bind = function(object) {
		var __method = this;
		return function() {
			return __method.apply(object, arguments);
		}
	}
}

// Wicket Namespace
if (typeof(Wicket) == "undefined")
	Wicket = { };


Wicket.GMaps = Wicket.Class.create();
Wicket.GMaps.prototype = {
	initialize: function() {
		//this.maps = new Array();
	}
}

function initGMaps() {
	if (typeof(Wicket.gmaps) == "undefined") {
		Wicket.gmaps = new Wicket.GMaps();
	}
}

function addGMap(id, lat, lng, zoom) {
	initGMaps();
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById(id));
		map.setCenter(new GLatLng(lat, lng), zoom);
		Wicket.gmaps[id] = map;
	}
}

function addListener(id, event, callBack) {
	if (GBrowserIsCompatible()) {
		var map = Wicket.gmaps[id];
		// TODO this is so procedural, someone ought
		// to optimize this.
		if (event == 'moveend') {
			var call = function () {wicketAjaxGet( 
					callBack 
					+ '&center=' + map.getCenter()
					+ '&bounds=' + map.getBounds()
					+ '&size=' + map.getSize()
					+ '&zoom=' + map.getZoom()),
					function(){},
					function(){alert("ooops!")}};
			GEvent.addListener( map , event, call);
		} else if (event == 'click') {
			var call = function (marker, gLatLng) {wicketAjaxGet( 
					callBack 
					+ '&marker=' + (marker == null ? "" : marker.overlayId)
					+ '&gLatLng=' + gLatLng),
					function(){},
					function(){alert("ooops on ClickDeff of!" + map)}};
			GEvent.addListener( map , event, call);
		}
	}
}

						


function setZoom(id, level) {
	Wicket.gmaps[id].setZoom(level);
}

function setCenter(id, center) {
	Wicket.gmaps[id].setCenter(eval(center));
}

function addControl(id, controlId, control) {
	Wicket.gmaps[id][controlId] = eval(control);
	Wicket.gmaps[id].addControl(Wicket.gmaps[id][controlId]);
}

function removeControl(id, controlId) {
	if (Wicket.gmaps[id][controlId] != null) {
		Wicket.gmaps[id].removeControl(Wicket.gmaps[id][controlId]);
		Wicket.gmaps[id][controlId] = null;
	}
}

function addOverlay(id, overlayId, overlay) {
    Wicket.gmaps[id][overlayId] = eval(overlay);
    Wicket.gmaps[id][overlayId].overlayId = overlayId;
	Wicket.gmaps[id].addOverlay(Wicket.gmaps[id][overlayId]);
}

function removeOverlay(id, overlayId) {
	if (Wicket.gmaps[id][overlayId] != null) {
		Wicket.gmaps[id].removeOverlay(Wicket.gmaps[id][overlayId]);
		Wicket.gmaps[id][overlayId] = null;
	}
}

function openInfoWindow(id, gLatLng, domId) {
	Wicket.gmaps[id].openInfoWindow(eval(gLatLng), document.getElementById(domId));
}
