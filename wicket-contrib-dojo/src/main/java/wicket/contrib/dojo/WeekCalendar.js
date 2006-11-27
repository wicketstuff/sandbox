dojo.provide("dojo.widget.WeekCalendar");

dojo.require("dojo.widget.DayCalendar");

dojo.widget.defineWidget (
	"dojo.widget.WeekCalendar",
	dojo.widget.HtmlWidget,
	{

	templatePath: dojo.uri.dojoUri("../WeekCalendar.htm"),
	
	sunday: null,
	monday: null,
	tuesday: null,
	wednesday: null,
	thursday: null,
	friday: null,
	saturday: null,
	
	//containings last values
	start: null,
	end: null,
	day:null,
	
	
	postCreate: function(args, fragment, parent){
		this.sunday    = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'sunday'},dojo.byId("Sunday"));
		this.monday    = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'monday'},dojo.byId("Monday"));
		this.tuesday   = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'tuesday'},dojo.byId("Tuesday"));
		this.wednesday = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'wednesday'},dojo.byId("Wednesday"));
		this.thursday  = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'thursday'},dojo.byId("Thursday"));
		this.friday    = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'friday'},dojo.byId("Friday"));
		this.saturday  = dojo.widget.createWidget("dojo:DayCalendar",{widgetId:'saturday'},dojo.byId("Saturday"));
		
		this.sunday.weekCalendar = this;	
		this.monday.weekCalendar = this;
		this.tuesday.weekCalendar = this;
		this.wednesday.weekCalendar = this;
		this.thursday.weekCalendar = this;
		this.friday.weekCalendar = this;
		this.saturday.weekCalendar = this;
		
		
		this.sunday.onSelect = function(div){
			this.weekCalendar.monday.selectTimeRange(null);
			this.weekCalendar.tuesday.selectTimeRange(null);
			this.weekCalendar.wednesday.selectTimeRange(null);
			this.weekCalendar.thursday.selectTimeRange(null);
			this.weekCalendar.friday.selectTimeRange(null);
			this.weekCalendar.saturday.selectTimeRange(null);
		};
		this.monday.onSelect = function(div){
			this.weekCalendar.sunday.selectTimeRange(null);
			this.weekCalendar.tuesday.selectTimeRange(null);
			this.weekCalendar.wednesday.selectTimeRange(null);
			this.weekCalendar.thursday.selectTimeRange(null);
			this.weekCalendar.friday.selectTimeRange(null);
			this.weekCalendar.saturday.selectTimeRange(null);
		};
		this.tuesday.onSelect = function(div){
			this.weekCalendar.sunday.selectTimeRange(null);
			this.weekCalendar.monday.selectTimeRange(null);
			this.weekCalendar.wednesday.selectTimeRange(null);
			this.weekCalendar.thursday.selectTimeRange(null);
			this.weekCalendar.friday.selectTimeRange(null);
			this.weekCalendar.saturday.selectTimeRange(null);
		};
		this.wednesday.onSelect = function(div){
			this.weekCalendar.sunday.selectTimeRange(null);
			this.weekCalendar.monday.selectTimeRange(null);
			this.weekCalendar.tuesday.selectTimeRange(null);
			this.weekCalendar.thursday.selectTimeRange(null);
			this.weekCalendar.friday.selectTimeRange(null);
			this.weekCalendar.saturday.selectTimeRange(null);
		};
		this.thursday.onSelect = function(div){
			this.weekCalendar.sunday.selectTimeRange(null);
			this.weekCalendar.monday.selectTimeRange(null);
			this.weekCalendar.tuesday.selectTimeRange(null);
			this.weekCalendar.wednesday.selectTimeRange(null);
			this.weekCalendar.friday.selectTimeRange(null);
			this.weekCalendar.saturday.selectTimeRange(null);
		};
		this.friday.onSelect = function(div){
			this.weekCalendar.sunday.selectTimeRange(null);
			this.weekCalendar.monday.selectTimeRange(null);
			this.weekCalendar.tuesday.selectTimeRange(null);
			this.weekCalendar.wednesday.selectTimeRange(null);
			this.weekCalendar.thursday.selectTimeRange(null);
			this.weekCalendar.saturday.selectTimeRange(null);
		};
		this.saturday.onSelect = function(div){
			this.weekCalendar.sunday.selectTimeRange(null);
			this.weekCalendar.monday.selectTimeRange(null);
			this.weekCalendar.tuesday.selectTimeRange(null);
			this.weekCalendar.wednesday.selectTimeRange(null);
			this.weekCalendar.thursday.selectTimeRange(null);
			this.weekCalendar.friday.selectTimeRange(null);
		};	
	},
	
	onCreate: function(start, end, day){
		this.start = start;
		this.end = end;
		this.day = day;
	},
	
	onRemove: function(start, end, day){
		this.start = start;
		this.end = end;
		this.day = day;
	},
	
	/**
	 * return the json object of the week
	 */
	getValue: function(){
		var data = {};
		data.sunday = this.sunday.getValue();
		data.monday = this.monday.getValue();
		data.tuesday = this.tuesday.getValue();
		data.wednesday = this.wednesday.getValue();
		data.thursday = this.thursday.getValue();
		data.friday = this.friday.getValue();
		data.saturday = this.saturday.getValue();
		
		return data;
	},
	
	setValue: function (obj){
		this.sunday.setValue(obj.sunday);
		this.monday.setValue(obj.monday);
		this.tuesday.setValue(obj.tuesday);
		this.wednesday.setValue(obj.wednesday);
		this.thursday.setValue(obj.thursday);
		this.friday.setValue(obj.friday);
		this.saturday.setValue(obj.saturday);
	},
	
	setJson: function(serialized){
		this.setValue(dojo.json.evalJson(serialized));
	},
	
	getJson: function(){
		return dojo.json.serialize(this.getValue())
	},
	
});