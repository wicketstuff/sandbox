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

function addGMap(id, lat, lng, zoom, moveendCallBack, clickCallBack) {
	initGMaps();
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById(id));
		map.setCenter(new GLatLng(lat, lng), zoom);
		var moveendCall = function () {wicketAjaxGet( 
				moveendCallBack 
				+ '&center=' + map.getCenter()
				+ '&bounds=' + map.getBounds()
				+ '&size=' + map.getSize()
				+ '&zoom=' + map.getZoom()),
				function(){},
				function(){alert("ooops!")}};
		GEvent.addListener( map , 'moveend', moveendCall );
						
		var clickCall = function (marker, point) {wicketAjaxGet( 
				clickCallBack 
				+ '&marker=' + (marker == null ? "" : marker.overlayId)
				+ '&point=' + point),
				function(){},
				function(){alert("ooops on ClickDeff of!" + map)}};
		GEvent.addListener( map , 'click', clickCall );
		
		Wicket.gmaps[id] = map;
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

function addOverlay(id, overlayId, jsConstructor) {
    Wicket.gmaps[id][overlayId] = eval(jsConstructor);
    Wicket.gmaps[id][overlayId].overlayId = overlayId;
	Wicket.gmaps[id].addOverlay(Wicket.gmaps[id][overlayId]);
}

function removeOverlay(id, overlayId) {
	if (Wicket.gmaps[id][overlayId] != null) {
		Wicket.gmaps[id].removeOverlay(Wicket.gmaps[id][overlayId]);
		Wicket.gmaps[id][overlayId] = null;
	}
}

function openInfoWindow(id, point, domId) {
	Wicket.gmaps[id].openInfoWindow(eval(point), document.getElementById(domId));
}
