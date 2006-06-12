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
 
 
function Wicket() {};

Wicket.Calendar = function() {
	this.initialize.apply(this, arguments);
}

Wicket.Calendar.instances = {};

Wicket.Calendar.getInstance = function(instanceId) {
	return Wicket.Calendar.instances[instanceId];
}

Wicket.Calendar.registerInstance = function(instance) {
	var instanceId = instance.container.id;
	Wicket.Calendar.instances[instanceId] = instance;
}

Wicket.Calendar.prototype = {
	
	initialize : function(inputId, containerId, format) {
		this.input = document.getElementById(inputId);
		if (this.input == undefined) {
			throw("Calendar input control with id '"+inputId+"' was not found");
		}

		this.container = document.getElementById(containerId);
		if (this.container == undefined) {
			throw("Calendar container div with id '"+containerId+"' was not found");
		}
	
		if (format == undefined) {
			format = "MM/dd/yyyy";
		}

		this.sdf = new Wicket.SimpleDateFormat(format, new Wicket.DateLocale());//TODO add date locale as a param
	
		Wicket.Calendar.registerInstance(this);
		
		this.theme = new Wicket.Calendar.Theme();
		
		this.shownWeeks = 12;
		this.startDay = new Date(2006, 7, 13);
	
		this.visible = false;
	
		return this;
	},

	show : function() {
		var pos = this.getPosition(this.input);
		this.container.style.left = pos[0]+"px";
		this.container.style.top = (this.input.offsetHeight+pos[1])+"px";
		this.container.style.display = "";
		this.visible = true;
	},
	
	hide : function() {
		this.container.style.display = "none";
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
		return [leftPosition,topPosition];
	},
	
	
	getInstanceJS : function() {
		return "Wicket.Calendar.getInstance('"+this.container.id+"')";
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
		this.startDay.setFullYear(this.startDay.getFullYear()-1);
		this.draw();
	},
	
	onNextYear : function() {
		this.startDay.setFullYear(this.startDay.getFullYear()+1);
		this.draw();
	},

	onPrevScreen : function() {
		// this.year--;
		this.draw();
	},
	
	onNextScreen : function() {
		// this.year++;
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
		
		for (var i=0; i<7; i++) {
			html += '<th>';
			html += this.theme.getWeekdayName(i);
			html += '</th>';
		}
		
		// Scroll column
		html += '<th>&nbsp;</th>';

		html += '</tr>';
		html += '</thead>\n';		
		html += '<tbody>';		
	
		// day grid

		var day = new Date(this.startDay.getTime());
		var row = 0;
		var monthChanged = true;
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

			if (day.getDay() == 0) {
				html += '<tr>';
				html += '<th class="'+this.dayMonthClassName(day)+'">';
				if (monthChanged) {
					html += this.theme.getMonthName(day.getMonth());
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
			
			if (day.getDay() == 6) {
				html += this.getScrollingContent(row);
				html += '</tr>\n';
				row++;
			}
		
			day.addDays(1);
		}
		
		html += '</tbody>';		
		html += '</table>';		
		
		this.container.innerHTML = html;
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
			return '<td><img src="src/images/arrow-more-up.gif" width="9" height="9" alt="" onclick="'+this.getInstanceJS()+'.onPrevScreen()"/></td>';
		}
		if (dateRow == 1) {
			return '<td><img src="src/images/arrow-up.gif" width="9" height="5" alt="" onclick="'+this.getInstanceJS()+'.onPrevWeek()"/></td>';
		}
		if (dateRow == 2) {
			return '<td rowspan="'+(this.shownWeeks-4)+'" class="empty">&nbsp;</td>';
		}
		if (dateRow == this.shownWeeks-2) {
			return '<td><img src="src/images/arrow-down.gif" width="9" height="5" alt="" onclick="'+this.getInstanceJS()+'.onNextWeek()"/></td>';
		}
		if (dateRow == this.shownWeeks-1) {
			return '<td><img src="src/images/arrow-more-down.gif" width="9" height="9" alt="" onclick="'+this.getInstanceJS()+'.onNextScreen()"/></td>';
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
