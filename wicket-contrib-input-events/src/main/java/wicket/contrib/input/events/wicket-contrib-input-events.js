
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
/**
 * Wicket Contrib Input Events
 *
 * @author Nino Martinez(nino.martinez@jayway.dk)
 */
 
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
/**
 * Wicket Contrib Input Events
 *
 * @author Nino Martinez(nino.martinez@jayway.dk)
 */
function getKeyPressed(e) {
	if (!e) {
    //if the browser did not pass the event information to the
    //function, we will have to obtain it from the event register
		if (window.event) {
      //Internet Explorer
			e = window.event;
		} else {
      //total failure, we have no way of referencing the event
			return;
		}
	}
	if (typeof (e.keyCode) == "number") {
    //DOM
		e = e.keyCode;
	} else {
		if (typeof (e.which) == "number") {
    //NS 4 compatible
			e = e.which;
		} else {
			if (typeof (e.charCode) == "number") {
    //also NS 6+, Mozilla 0.9+
				e = e.charCode;
			} else {
    //total failure, we have no way of obtaining the key code
				return;
			}
		}
	}
	return String.fromCharCode(e);
}
function keyPressed(event, keyCode, componentID, componentEvent) {
	var keyPressed = getKeyPressed(event);
	if (keyPressed == keyCode) {
		alert("keyPressed:" + keyPressed);
		var component = document.getElementById(componentId);
		component.componentEvent;
	}
}
function mousepressed(event, desiredClick, componentID, componentEvent) {
}
function pokeComponent(component) {
	component.submit;
}
function focusComponent(component) {
	component.focus;
}



