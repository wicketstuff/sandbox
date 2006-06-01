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
		
};

Date.getDayOfYear = function(year, month, day) {
	var days=0;
	for (var i=0;i<month-1;i++) {
		days+=Date.getDaysInMonth(year, i);
	}
	days+=day;
	return days;
}

Date.prototype.getDaysInMonth = function() {
    return Date.getDaysInMonth(this.getFullYear(), this.getMonth());
}

Date.prototype.getDayOfYear = function(year, month, day) {
	return Date.getDayOfYear(this.getFullYear(), this.getMonth(), this.getDate());
}

Date.prototype.addDays = function(days) {
	this.setTime(this.getTime()+days*Date.DAY);
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


Date.prototype.getWeekInYear = function() {
    var totalDays = 0;
    
    var year=this.getFullYear();

    for (var i = 0; i < this.getMonth()-1; i++) {
        totalDays = totalDays + Date.getDaysInMonth(year, i);
    }
    
    totalDays = totalDays + this.getDate();

    var firstDay=new Date();
    firstDay.setMonth(0);
    firstDay.setDate(1);
    firstDay.setYear(this.getYear());
    firstDay=firstDay.getDay();
        
    var diff=6-firstDay;
    var week = Math.round((totalDays+diff-firstDay)/7);
    return week;
}

Date.prototype.getWeekInMonth = function() {
    var totalDays = this.getDate();
    
    var firstDay=new Date();
    firstDay.setYear(this.getFullYear());
    firstDay.setMonth(this.getMonth());
    firstDay.setDate(1);
    firstDay=firstDay.getDay();

    var diff=6-firstDay;
    var week = Math.round((totalDays+diff-firstDay)/7);
    return week;
}
