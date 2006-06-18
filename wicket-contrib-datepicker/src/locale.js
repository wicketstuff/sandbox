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

if (Wicket == undefined) {
	var Wicket = {};
}

Wicket.DateLocale = function() {
	this.initialize.apply(this, arguments);
}

Wicket.DateLocale.prototype = {
	initialize : function(locale) {
	  this.months = ["January","February","March","April","May","June","July","August","September","October","November","December"];
	  this.shortMonths = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
	  this.weekdays = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
	  this.shortWeekdays = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
	  this.firstDayOfWeek = 0;
	  if (locale) {
		  this.setAllLocaleData(locale);
	  }
	},
	
	setAllLocaleData : function(data) {
		for (var i in data) {
			if (this[i] == undefined) {
				throw("Undefined locale attribute '"+i+"'");
			}
			this[i] = data[i];
		}	
	},
	
	getMonth : function(m) { return this.months[m]; },
	getShortMonth : function(m) { return this.shortMonths[m]; },
	getWeekday : function(w) { return this.weekdays[w]; },
	getShortWeekday : function(w) { return this.shortWeekdays[w]; },
	getFirstDayOfWeek : function() { return this.firstDayOfWeek; },
	setMonths : function(m) { this.months = m.split(","); },
	setShortMonths : function(m) { this.shortMonths = m.split(","); },
	setWeekdays : function(w) { this.weekdays = w.split(","); },
	setShortWeekdays : function(w) { this.shortWeekdays = w.split(","); },
	
	setFirstDayOfWeek : function(day) {
		if (day >= 0 && day < 7) {
			this.firstDayOfWeek = day;
		} 
	},
	
	getWeekdayIterator : function() { return new Wicket.WeekdayIterator(this); },
	
	getShortWeekdayIterator : function() {
		var i = new Wicket.WeekdayIterator(this);
		i.nextItemFromLocale = eval("function() { this.nextItem = this.locale.getShortWeekday(this.dayNumber); }");
		i.nextItemFromLocale();
		return i;
	}
}


Wicket.WeekdayIterator = function() {
	this.initialize.apply(this, arguments);
}

Wicket.WeekdayIterator.prototype = {
	initialize : function(dateLocale) {
		this.locale = dateLocale;
		this.dayNumber = this.locale.getFirstDayOfWeek();
		this.nextItemFromLocale();
	},
	next : function() {
		var retVal = this.nextItem;
		
		if (this.hasNext()) {
			this.dayNumber++;
			if (this.dayNumber > 6) {
				this.dayNumber = 0;
			}
			
			if (this.dayNumber == this.locale.getFirstDayOfWeek()) {
				this.nextItem = null;
			} else {
				this.nextItemFromLocale();
			}
		}
		
		return retVal;
	},
	hasNext : function() {
		return (this.nextItem != null);
	},
	nextItemFromLocale : function() {
		this.nextItem = this.locale.getWeekday(this.dayNumber);
	}
}
