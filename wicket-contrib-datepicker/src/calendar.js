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
 */
 
// TODO: parse date
// TODO: selected day
// TODO: css namespace
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
			format = "MM/dd/yyyy";
		}

		this.locale = new Wicket.DateLocale(locale);		
		this.sdf = new Wicket.SimpleDateFormat(format, this.locale);
	
		Wicket.Calendar.registerInstance(this);
		
		this.shownWeeks = 12;
		this.startDay = this.getStartTodayInMiddle();
		this.visible = false;
	
		return this;
	},

	getStartTodayInMiddle : function() {
		var day = new Date();
		day.addDays(-(this.shownWeeks/2)*7);
		day.setToFirstDateOfWeek(this.locale.getFirstDayOfWeek());
		return day;
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
    
	show : function() {
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
		this.input.value = this.sdf.format(day);
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
		
		while (day<=lastDay) {
			var dayClass = this.dayMonthClassName(day);

			if (day.isToday()) {
				dayClass += ' today';
			}
			
			if (day.getDate() == 1) {
				monthChanged = true;
				if (day.getMonth() == 0) {
					yearChanged = true;
				}
			}

			if (day.getDay() == this.locale.getFirstDayOfWeek()) {
				html += '<tr>';
				html += '<th class="'+this.dayMonthClassName(day)+'">';
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
		// DEBUG
		document.getElementById("pastebin").value = html;
	},

	dayMonthClassName : function(date) {
		var className = '';
		if (date.getMonth()%2 == 1) {
			className = 'odd';
		}
		return className;
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
	}


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
