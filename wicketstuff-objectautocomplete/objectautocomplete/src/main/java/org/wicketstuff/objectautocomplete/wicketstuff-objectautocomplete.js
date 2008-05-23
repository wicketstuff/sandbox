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

if (typeof(Wicketstuff) == "undefined") {
	var Wicketstuff = { };
}

Wicketstuff.ObjectAutoComplete=function(elementId, objectElementId, callbackUrl, cfg){

    // Initialize with parent constructor
    Wicket.AutoComplete.call(this,elementId,callbackUrl,cfg);

    // Remove the autocompletion menu if still present from
    // a previous call. This is required to properly register
    // the mouse event handler again (using the new stateful 'mouseactive'
    // variable which just gets created)
    var choiceDiv=document.getElementById(this.getMenuId());
    if (choiceDiv != null) {
        choiceDiv.parentNode.parentNode.removeChild(choiceDiv.parentNode);
    }

    // Register key listener for ESC to revert to previous state
    this.updateValue = function() {
        var objElement = wicketGet(objectElementId);
        var textElement = wicketGet(elementId);
        var selected = this.getSelectedValue();
        objElement.value = selected['idvalue'];
        textElement.value = selected['textvalue'];
    }

    this.getSelectedValue = function() {
        var element= this.getSelectedElement();
        var attr = element.attributes['textvalue'];
        var idAttr = element.attributes['idvalue'];
        var value;
        if (attr == undefined) {
            value = element.innerHTML;
        } else {
            value = attr.value;
        }
        return { 'textvalue': value.replace(/<[^>]+>/g,""), 'idvalue' : idAttr.value };
    }
}

// Inherit without calling constructor of Wicket.AutoComplete
//
var tmpClass = function() {};
tmpClass.prototype = Wicket.AutoComplete.prototype;
Wicketstuff.ObjectAutoComplete.prototype = new tmpClass();
