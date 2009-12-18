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
Event = function(oldValue, isSelection, row) {
	this.oldValue = oldValue;
	this.isSelection = isSelection;
	this.row = row;
}
function updateRow(markupId, selected) {
	var row = Wicket.$(markupId);
	if (!row.started) {
		row.isRow = true;
		row.listeners = new Array();
		row.addListener = function(listener) {
			this.listeners[this.listeners.length] = listener;
		}
		row.onselectstart = function() {
			return window.event.srcElement.tagName.toLowerCase() == "input";
		} // ie
		row.onmousedown = function(e) {
			var tag = e.target.tagName.toLowerCase();
			return e != null && (tag == "input" || tag == "button");
		} // mozilla
		row.originalClass = row.className;
		row.onmouseover = function(e) {
			row.className = 'onMouseOver';
		};
		row.onmouseout = function(e) {
			row.className = row.isSelected ? 'selected' : row.originalClass;
		};
		row.started = true;
	}
	var selectionEvent = new Event(row.isSelected, selected, row);
	row.isSelected = selected;
	row.className = selected ? 'selected' : row.originalClass;
	for (i = 0; i < row.listeners.length; i++) {
		row.listeners[i].call(this, selectionEvent);
	}
}
function getRow(comp) {
	var parent = comp.parentNode;
	while (!parent.isRow) {
		parent = parent.parentNode;
	}
	return parent;
}
function handleRowSelection(textFieldId) {
	var textField = Wicket.$(textFieldId);
	textField.oldBackground = textField.style.background;
	textField.oldBorder = textField.style.border;
	textField.update = function(rowSelected) {
		if (rowSelected) {
			this.style.background = this.oldBackground;
			this.style.border = this.oldBorder;
		} else {
			this.style.background = 'none';
			this.style.border = 'none';
		}
	}
	var row = getRow(textField);
	textField.update(row.isSelected);
	var selectionHandler = function(selectionEven) {
		textField.update(selectionEven.isSelection);
	}
	row.addListener(selectionHandler);
}
function turnRowTotalizer(compId) {
	var comp = Wicket.$(compId);
	comp.listeners = new Array();
	comp.fireUpdate = function() {
		for (i = 0; i < comp.listeners.length; i++) {
			comp.listeners[i].call(this);
		}
	}
	var trParent = comp.parentNode;
	while ('TR' != trParent.tagName.toUpperCase()) {
		trParent = trParent.parentNode;
	}
	var updateValue = function() {
		comp.innerHTML = null;
		comp.innerHTML = calcChildrenTotal(trParent);
		comp.fireUpdate();
	}
	updateValue();
	var addHandler = function(children) {
		if (children.tagName && children.tagName.toUpperCase() == 'INPUT') {
			Wicket.Event.add(children, 'change', updateValue);
		}
	};
	travel(this, trParent, addHandler);
}
function turnColumnTotalizer(totalCompId, columnsCompIds) {
	var updateColumnTotal = function() {
		var totalComp = Wicket.$(totalCompId);
		totalComp.innerHTML = calcTotalFrom(columnsCompIds);
	};
	updateColumnTotal();
	for (i = 0; i < columnsCompIds.length; i++) {
		var toSumComp = Wicket.$(columnsCompIds[i]);
		toSumComp.listeners[toSumComp.listeners.length] = updateColumnTotal;
	}
}
function calcTotalFrom(compIds) {
	var total = 0;
	for (i = 0; i < compIds.length; i++) {
		var toSumComp = Wicket.$(compIds[i]);
		total += getValue(toSumComp);
	}
	return total;
}
function calcChildrenTotal(parent) {
	var total = 0;
	var sum = function(children) {
		var childrenValue = getValue(children);
		if (childrenValue) {
			total += childrenValue;
		}
	}
	travel(this, parent.firstChild, sum);
	return total;
}
function getValue(comp) {
	var value;
	try {
		if (!isNaN(parseFloat(comp.value)))
			value = parseFloat(comp.value);
	} catch (e) {
		// it was just a try
	}
	try {
		if (!isNaN(parseFloat(comp.innerHTML)))
			value = parseFloat(comp.innerHTML);
	} catch (e) {
		// it was just a try
	}
	return value;
}
function travel(caller, n, f) {
	if (n)
		f.call(caller, n);
	if (n.firstChild)
		travel(caller, n.firstChild, f)
	if (n.nextSibling)
		travel(caller, n.nextSibling, f)
}