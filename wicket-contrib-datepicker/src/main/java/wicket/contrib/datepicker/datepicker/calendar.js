/* ==============================================================================
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

/*
 * @author Igor Vaynberg (ivaynberg)
 * @author Karri-Pekka Laakso (kplaakso)
 */
 
// TODO: week numbers
// TODO: predefined locales
// TODO: css namespace
// TODO: error messages
// TODO: real parseException object for more precise error messages
// TODO: java connection
// TODO: day decorations
// TODO: rangepicker


if (Wicket == undefined) {
	var Wicket = {};
}

Wicket.Calendar = function() {
	this.initialize.apply(this, arguments);
}

Wicket.Calendar.instances = {};

Wicket.Calendar.getInstance = function(instanceId) {
	return Wicket.Calendar.instances[instanceId];
}

Wicket.Calendar.registerInstance = function(instance) {
	var instanceId = instance.containerId;
	Wicket.Calendar.instances[instanceId] = instance;
}

Wicket.Calendar.prototype = {
	
	initialize : function(inputId, containerId, format, locale) {
		this.input = document.getElementById(inputId);
		if (this.input == undefined) {
			throw("Calendar input control with id '"+inputId+"' was not found");
		}

		this.containerId = containerId;
		if (this.containerId == undefined) {
			throw("A container id must be defined");
		}
	
		if (format == undefined) {
			format = "yyyy-MM-dd";
		}

		this.locale = new Wicket.DateLocale(locale);		
		this.sdf = new Wicket.SimpleDateFormat(format, this.locale);
	
		Wicket.Calendar.registerInstance(this);
		
		this.shownWeeks = 12;
		this.visible = false;

		this.setFieldEventHandlers();
		this.setStartAndSelection();
		this.validationTimerId = null;
	
		return this;
	},

	getStartDayInMiddle : function(day) {
		if (!day) {
			day = new Date();
		}
		var startDay = new Date(day);
		startDay.addDays(-(this.shownWeeks/2)*7);
		startDay.setToFirstDateOfWeek(this.locale.getFirstDayOfWeek());
		return startDay;
	},

	getContainer : function() {
		var container = document.getElementById(this.containerId);
		if (container == null) {
			container = document.createElement("div");
			document.body.appendChild(container);
			container.id = this.containerId;
			container.style.display = "none";
			container.style.position = "absolute";
			container.style.zIndex = "10000";
		}
        
		return container;
	},
	
	getInputValue : function() {
		if (this.input.nodeName == "INPUT") {
			return this.input.value;
		}
		
		// It's a container, so pick the value from the first text node
		var value = "";
		for (var i=0; i < this.input.childNodes.length; i++) {
			var child = this.input.childNodes[i];
			if (child.nodeName == "#text") {
				value = child.nodeValue;
				break;
			}
		}
		return value;
	},
	
	setInputValue : function(value) {
		if (this.input.nodeName == "INPUT") {
			this.input.value = value;
		} else {
			try {
				this.input.innerHTML = value;
			} catch(e) { /* Do nothing */ }
		}
	},
    
    setStartAndSelection : function() {
		try {
			this.selectedDay = this.sdf.parse(this.getInputValue());
		} catch (parseException) {
			this.selectedDay = null;
		}
		this.startDay = this.getStartDayInMiddle(this.selectedDay);
    },
    
    setFieldEventHandlers : function () {
    	this.input.onfocus = this.getEventHandler("hide()");
    	this.input.onkeyup = this.getEventHandler("waitAndValidate()");
    },
    
	show : function() {
		this.setStartAndSelection();
		var pos = this.getPosition(this.input);
		var container = this.getContainer();
		container.style.left = pos.x+"px";
		container.style.top = (this.input.offsetHeight+pos.y)+"px";
		container.style.display = "";
		this.visible = true;
	},
	
	hide : function() {
		this.getContainer().style.display = "none";
		this.visible = false;
	},
	
	toggle : function() {
		if (this.visible) {
			this.hide();
		} else {
			this.show();
			this.draw();
		}
	},
	
	getPosition : function(obj) {
		var leftPosition = 0;
		var topPosition = 0;
		do {
			topPosition += obj.offsetTop  || 0;
			leftPosition += obj.offsetLeft || 0;
			obj = obj.offsetParent;
		} while (obj);
		return {x:leftPosition, y:topPosition};
	},
	
	
	getInstanceJS : function() {
		return "Wicket.Calendar.getInstance('"+this.containerId+"')";
	},

	getEventHandler : function(method) {
		return eval("function() {"+this.getInstanceJS()+"."+method+"}");
	},

	onPrevWeek : function() {
		this.startDay.addDays(-7);
		this.draw();
	},
	
	onNextWeek : function() {
		this.startDay.addDays(7);
		this.draw();
	},
	
	onPrevYear : function() {
		this.startDay.addYears(-1);
		this.startDay.setToFirstDateOfWeek(this.locale.getFirstDayOfWeek());
		this.draw();
	},
	
	onNextYear : function() {
		this.startDay.addYears(1);
		this.startDay.setToFirstDateOfWeek(this.locale.getFirstDayOfWeek());
		this.draw();
	},

	onPrevScreen : function() {
		this.startDay.addDays(-7 * (this.shownWeeks-2));
		this.draw();
	},
	
	onNextScreen : function() {
		this.startDay.addDays(7 * (this.shownWeeks-2));
		this.draw();
	},

	onSelect : function(time) {
		var day = new Date();
		day.setTime(time);
		this.setInputValue(this.sdf.format(day));
		this.setFieldValid(true);
		this.hide();
	},

	draw : function() {
		var lastDay = new Date(this.startDay.getTime());
		lastDay.addDays(this.shownWeeks*7 - 1); // FIXME

		var html = "";
		
		// header row
		
		html += '<table border="0" cellspacing="0" cellpadding="0" class="wicket-calendar">';
		html += '<thead>';
		html += '<tr>';
		html += '<th class="year">';
		html += '<div>';
		html += '<img src="src/images/arrow-left.gif" width="5" height="9" alt="" onclick="'+this.getInstanceJS()+'.onPrevYear()"/>';
		html += this.startDay.getFullYear();
		html += '<img src="src/images/arrow-right.gif" width="5" height="9" alt="" onclick="'+this.getInstanceJS()+'.onNextYear()"/>';
		html += '</div>';
		html += '</th>';
		
		// weekdays
		var i = this.locale.getShortWeekdayIterator();
		while (i.hasNext()) {
			html += '<th>';
			html += i.next();
			html += '</th>';
		}
		
		// Scroll column: close button
		html += '<th class="scroll close" onclick="'+this.getInstanceJS()+'.hide()">x</th>';

		html += '</tr>';
		html += '</thead>\n';		
		html += '<tbody>';		
	
		// day grid

		var day = new Date(this.startDay.getTime());
		var row = 0;
		var monthChanged = this.startDay.hasMonthChangedOnPreviousWeek(this.locale.getFirstDayOfWeek());
		var yearChanged = false;
		
		while (day <= lastDay) {
			if (day.getDate() == 1) {
				monthChanged = true;
				if (day.getMonth() == 0) {
					yearChanged = true;
				}
			}

			if (day.getDay() == this.locale.getFirstDayOfWeek()) {
				html += '<tr>';
				html += '<th class="'+this.getBackgroundClass(day)+'">';
				if (monthChanged) {
					html += this.locale.getMonth(day.getMonth());
					monthChanged = false;
				} else {
					if (yearChanged) {
						html += day.getFullYear();
						yearChanged = false;
					} else {
						html += '&nbsp;';
					}
				}
				html += '</th>';
			}

			var dayClass = this.getBackgroundClass(day);
			if (day.isToday()) {
				dayClass += ' today';
			}
			if (this.isSelected(day)) {
				dayClass += ' selected';
			}
			
			html += '<td class="'+dayClass+'" onclick="'+this.getInstanceJS()+'.onSelect('+day.getTime()+')">';
			html += day.getDate();
			html += '</td>';
			
			day.addDays(1);

			if (day.getDay() == this.locale.getFirstDayOfWeek()) {
				html += this.getScrollingContent(row);
				html += '</tr>\n';
				row++;
			}
		}
		
		html += '</tbody>';		
		html += '</table>';		
		
		this.getContainer().innerHTML = html;
	},

	getBackgroundClass : function(date) {
		var className = '';
		if (date.getMonth()%2 == 1) {
			className = 'odd';
		}
		return className;
	},
	
	isSelected : function(date) {
		var retVal = false;
		if (this.selectedDay) {
			retVal = (date.compareDateOnlyTo(this.selectedDay) == 0);
		}
		return retVal;
	},
		
	getScrollingContent : function(dateRow) {
		if (dateRow == 0) {
			return '<td class="scroll"><img src="src/images/arrow-more-up.gif" width="9" height="9" alt="" onclick="'+this.getInstanceJS()+'.onPrevScreen()"/></td>';
		}
		if (dateRow == 1) {
			return '<td class="scroll"><img src="src/images/arrow-up.gif" width="9" height="5" alt="" onclick="'+this.getInstanceJS()+'.onPrevWeek()"/></td>';
		}
		if (dateRow == 2) {
			return '<td rowspan="'+(this.shownWeeks-4)+'" class="empty scroll">&nbsp;</td>';
		}
		if (dateRow == this.shownWeeks-2) {
			return '<td class="scroll"><img src="src/images/arrow-down.gif" width="9" height="5" alt="" onclick="'+this.getInstanceJS()+'.onNextWeek()"/></td>';
		}
		if (dateRow == this.shownWeeks-1) {
			return '<td class="scroll"><img src="src/images/arrow-more-down.gif" width="9" height="9" alt="" onclick="'+this.getInstanceJS()+'.onNextScreen()"/></td>';
		}
		return '';
	},

	validateField : function() {
		try {
			this.sdf.parse(this.input.value);
			this.setFieldValid(true);
		} catch (parseException) {
			Wicket.Util.addClassName(this.input, "wicket-invalid");
			this.setFieldValid(false);
		}		
	},
	
	setFieldValid : function(valid) {
		if (valid) {
			Wicket.Util.removeClassName(this.input, "wicket-invalid");
		} else {
			Wicket.Util.addClassName(this.input, "wicket-invalid");
		}
	},
	
	waitAndValidate : function() {
		var VALIDATIONTIMEOUT = 300; // ms
		window.clearTimeout(this.validationTimerId);
		this.validationTimerId = window.setTimeout(this.getInstanceJS()+".validateField()", VALIDATIONTIMEOUT);
	}
}

if (Wicket.Util == undefined) {
	Wicket.Util = {};
}

Wicket.Util.removeClassName = function(element, name) {
	if (!element.className) {
		return;
	}
	var classes = element.className.split(' ');
	for (var i=0; i<classes.length; i++) {
		if (classes[i] == name) {
			classes[i] = null;
		}
	}
	element.className = classes.join(' ');
}

Wicket.Util.addClassName = function(element, name) {
	if (!element.className) {
		return;
	}
	Wicket.Util.removeClassName(element, name);
	element.className += ' ' + name;
}

Wicket.Calendar.Theme = function() {
	this.initialize.apply(this, arguments);
}

Wicket.Calendar.Theme.prototype = {
	
	initialize : function() {
		this.monthNames = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
		this.weekdayNames = ["Su","Mo","Tu","We","Th","Fr","Sa"];
	},
	
	getMonthName : function(month) {
		return this.monthNames[month];
	},
	
	getWeekdayName : function(day) {
		return this.weekdayNames[day];
	}
}
