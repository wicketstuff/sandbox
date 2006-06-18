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
 
Date.DAYS_IN_MONTH = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]; 
Date.SECOND = 1000;             
Date.MINUTE = 60 * Date.SECOND; 
Date.HOUR   = 60 * Date.MINUTE; 
Date.DAY    = 24 * Date.HOUR;   
Date.WEEK   =  7 * Date.DAY;    

Date.getDaysInMonth = function(year, month) {;
	if (((0 == (year%4)) && ( (0 != (year%100)) || (0 == (year%400)))) && month == 1) {
		return 29;
	} else {
		return Date.DAYS_IN_MONTH[month];
	}
}

Date.getDayInYear = function(year, month, day) {
	var days=0;
	for (var i=0; i<month; i++) {
		days += Date.getDaysInMonth(year, i);
	}
	days += day;
	return days;
}

Date.prototype.getDaysInMonth = function() {
    return Date.getDaysInMonth(this.getFullYear(), this.getMonth());
}

Date.prototype.getDayInYear = function() {
	return Date.getDayInYear(this.getFullYear(), this.getMonth(), this.getDate());
}

Date.prototype.addDays = function(days) {
	var hours = this.getHours();
	this.setTime(this.getTime() + days*Date.DAY);

	// Fix the date offset caused by daylight saving time
	var delta = hours - this.getHours();
	if (delta != 0) {
		// Correct the delta to be between [-12, 12] 
		if (delta > 12) { 
		    delta -= 24;
		}
		if (delta < -12) { 
		    delta += 24;
		}
		this.setTime(this.getTime() + (delta*Date.HOUR));
	}
}

Date.prototype.addYears = function(years) {
	this.setFullYear(this.getFullYear() + years);
	return this;
} 

Date.prototype.stripTime = function() {
    this.setHours(0);
    this.setMinutes(0);
    this.setSeconds(0);
    this.setMilliseconds(0);
}    

Date.prototype.compareTo = function(date) {
    var lhs=this.getTime();
    var rhs=date.getTime();
    
    if (lhs<rhs) {
        return -1;
    } else if (lhs>rhs) {
        return 1;
    } else {
        return 0;
    }
}

Date.prototype.compareDateOnlyTo = function(date) {
    var lhs=new Date();
    lhs.setTime(this.getTime());
    lhs.stripTime();
    
    var rhs=new Date();
    rhs.setTime(date.getTime());
    rhs.stripTime();
    
    return lhs.compareTo(rhs);
}

Date.prototype.isToday = function() {
	return (this.compareDateOnlyTo(new Date()) == 0);
}

Date.prototype.getWeekInYear = function(firstDayOfWeek) {
	if (typeof(firstDayOfWeek) == "undefined") {
		firstDayOfWeek = 0;
	}

	if (firstDayOfWeek == 0) {
		return Math.ceil(this.getDayInYear() / 7);
	} else {
		return Math.ceil(Date.getDayInYear(this.getFullYear(), this.getMonth(), this.getDate() + (7-firstDayOfWeek)) / 7);
	}
}

Date.prototype.getWeekInMonth = function(firstDayOfWeek) {
	if (typeof(firstDayOfWeek) == "undefined") {
		firstDayOfWeek = 0;
	}
	
	var firstDay = new Date(this.getFullYear(), this.getMonth(), 1).getDay();
	var totalDays = this.getDate() + firstDay - firstDayOfWeek - 1;
	return Math.floor(totalDays / 7) + 1;
}

Date.prototype.setToFirstDateOfWeek = function(firstDayOfWeek) {
	if (firstDayOfWeek < this.getDay()) {
		this.addDays(firstDayOfWeek - this.getDay());
	}
	else if (firstDayOfWeek > this.getDay()) {
		this.addDays(firstDayOfWeek - this.getDay() - 7);
	}
	return this;
}

Date.prototype.hasMonthChangedOnPreviousWeek = function(firstDayOfWeek) {
	var thisFirst = new Date(this.getTime());
	thisFirst.setToFirstDateOfWeek(firstDayOfWeek);
	
	var lastFirst = new Date(thisFirst.getTime());
	lastFirst.addDays(-7);
	
	return (thisFirst.getMonth() != lastFirst.getMonth());
}