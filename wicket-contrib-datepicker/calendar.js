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
 
 
Wicket={}

Wicket.Calendar=function() {
	this.initialize.apply(this, arguments);
}

Wicket.Calendar.instances={};

Wicket.Calendar.getInstance=function(instanceId) {
	return Wicket.Calendar.instances[instanceId];
}

Wicket.Calendar.registerInstance=function(instance) {
	var instanceId=instance.container.id;
	Wicket.Calendar.instances[instanceId]=instance;
}

Wicket.Calendar.prototype={
	
	initialize:function(inputId, containerId, format) {
		this.input=document.getElementById(inputId);
		if (this.input==undefined) {
			throw("Calendar input control with id '"+inputId+"' was not found");
		}

		this.container=document.getElementById(containerId);
		if (this.container==undefined) {
			throw("Calendar container div with id '"+containerId+"' was not found");
		}
	
	    if (format==undefined) {
	        format="MM/dd/yyyy";
	    }
	    
	    this.sdf=new Wicket.SimpleDateFormat(format, new Wicket.DateLocale());//TODO add date locale as a param
	
		Wicket.Calendar.registerInstance(this);
		
		this.theme=new Wicket.Calendar.Theme();
		
		this.year=2006;
		this.month=4;
	
		this.visible=false;
	
		return this;
	},

	show : function() {
		var pos=this.getPosition(this.input);
		this.container.style.left=pos[0]+"px";
		this.container.style.top=(this.input.offsetHeight+pos[1])+"px";
		this.container.style.display="";
		this.visible=true;
	},
	
	hide : function() {
		this.container.style.display="none";
		this.visible=false;
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
		var leftPosition=0;
		var topPosition=0;
		do {
      			topPosition += obj.offsetTop  || 0;
      			leftPosition += obj.offsetLeft || 0;
      			obj = obj.offsetParent;
		 } while (obj);
		return [leftPosition,topPosition];
	},
	
	
	getInstanceJS:function() {
		return "Wicket.Calendar.getInstance(\""+this.container.id+"\")";
	},

	onPrevMonth:function() {
		this.month--;
		if (this.month<0) {
		    this.month=11;
		    this.year--;
		}
		
		this.draw();
	},
	
	onNextMonth:function() {
		this.month++;
		if (this.month>11) {
		    this.month=0;
		    this.year++;
		}
		
		this.draw();
	},
	
	onPrevYear:function() {
		this.year--;
		this.draw();
	},
	
	onNextYear:function() {
		this.year++;
		this.draw();
	},

    onSelect:function(time) {
        var day=new Date();
        day.setTime(time);
        this.input.value=this.sdf.format(day);
        this.hide();
    },

	draw:function() {
		var firstDay=new Date(this.year, this.month, 1);
		firstDay.addDays(0-firstDay.getDay());
		
		var lastDay=new Date(this.year, this.month, 1);
		lastDay.addDays(lastDay.getDaysInMonth()-1);
	    lastDay.addDays(6-lastDay.getDay());
	
		var html="";
		
		// header row
		
		html+="<table class='wc-header'>";
		html+="<tr>";
		
		html+="<td class='wc-header-py'>";
		html+="<a href='#' onclick='"+this.getInstanceJS()+".onPrevYear()'>&lt;&lt;</a>";
		html+="</td>";
		
		html+="<td class='wc-heder-pm'>";
		html+="<a href='#' onclick='"+this.getInstanceJS()+".onPrevMonth()'>&lt;</a>";
		html+="</td>";
		
		
		html+="<td class='wc-header-disp'>";
		html+=this.theme.getMonthName(this.month);
		html+=", ";
		html+=this.year;
		html+="</td>";
		
		
		html+="<td class='wc-header-nm'>";
		html+="<a href='#' onclick='"+this.getInstanceJS()+".onNextMonth()'>&gt;</a>";
		html+="</td>";
		
		html+="<td class='wc-header-ny'>";
		html+="<a href='#' onclick='"+this.getInstanceJS()+".onNextYear()'>&gt;&gt;</a>";
		html+="</td>";
		
		html+="</tr>";
		html+="</table>";
		
		
		html+="<table class='wc-days'>";
		
		// weekday row
		
		html+="<tr class='wc-day-title-row'>";
		for (var i=0;i<7;i++) {
		    html+="<td class='wc-day-title'>";
		    html+=this.theme.getWeekdayName(i);
		    html+="</td>";
		}
		html+="<tr>";
		
		// day grid
		
		var day=firstDay;
		while (day<=lastDay) {
			if (day.getDay()==0) {
				html+="<tr>";
			}
			
			var dayClass='wc-day';
			if (day.getMonth()!=this.month) {
			    dayClass+=' wc-day-outer';
			}
			if (day.getDay()==0||day.getDay()==6) {
			    if (day.getMonth()!=this.month) {
			        dayClass+=' wc-day-outer-weekend';
			    } else {
			        dayClass+=' wc-day-weekend';
			    }
			}
            if (day.compareDateOnlyTo(new Date())==0) {
                dayClass+=' wc-day-today';
            }
			
			
			html+="<td class='"+dayClass+"'>";
			
			html+="<a href='#' onclick='"+this.getInstanceJS()+".onSelect("+day.getTime()+")'>"
			html+=day.getDate();
			html+="</a>";
			
			html+="</td>";
			if (day.getDay()==6) {
				html+="</tr>";
			}
		
			day.addDays(1);
		
		}
		
		html+="</table>";
		
		this.container.innerHTML=html;
		
	}
}

Wicket.Calendar.Theme=function() {
	this.initialize.apply(this, arguments);
}

Wicket.Calendar.Theme.prototype={
	
	initialize : function() {
		this.monthNames=["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
		this.weekdayNames=["Su","Mo","Tu","We","Th","Fr","Sa"];
	},
	
	getMonthName : function(month) {
		return this.monthNames[month];
	},
	
	getWeekdayName : function(day) {
	    return this.weekdayNames[day];
	}
	
}
