dojo.provide("dojo.widget.DayCalendar");

dojo.require("dojo.html.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.collections.ArrayList");
dojo.require("dojo.json");
dojo.require("dojo.lfx.rounded");

dojo.widget.defineWidget (
	"dojo.widget.DayCalendar",
	dojo.widget.HtmlWidget,
	{

	templatePath: dojo.uri.dojoUri("../DayCalendar.htm"),
	
	//first selected div in day
	firstSelected: null,
	
	//last selected div in day
	lastSelected: null,
	
	//div  : current slice time
	current: null,
	
	//div : containing text;
	timeDiv: null,
	
	//is the mouse down?
	mousedown: false,
	
	//list of slice time
	timeRange: null,
	
	//incremented on each new Slice
	slicePartId: 0,
	
	//Selected Slice
	selectedTimeRange: null,
	
	//if a weekCalendar exsit add it here
	weekCalendar : null,
	
	tmpx: null,
	
	postCreate: function(args, fragment, parent){
		this.timeRange =  new dojo.collections.ArrayList();
		var body = document.getElementsByTagName("body")[0];
		var onMouseUp = body.getAttribute("onMouseUp");
		if (onMouseUp == null){ onMouseUp = ""}
		onMouseUp += "dojo.widget.byId('" + this.widgetId + "').fixTimeRange();";
		document.getElementsByTagName("body")[0].setAttribute("onMouseUp",onMouseUp);
		var onKeyPress = body.getAttribute("onkeypress");
		if (onKeyPress == null){ onKeyPress = ""}
		onKeyPress += "dojo.widget.byId('" + this.widgetId + "').keyPressed(event);";
		document.getElementsByTagName("body")[0].setAttribute("onkeypress",onKeyPress);
		
		//add listener to slice
		var inners = this.domNode.getElementsByTagName("div");
		for(var i=0; i<inners.length; i++){
			inners[i].setAttribute("onmouseover","this.innerHTML= this.getAttribute('start') + ' - ' + this.getAttribute('end'); dojo.widget.byId('" + this.widgetId + "').expandTimeRange(this)");
			inners[i].setAttribute("onmouseout","this.innerHTML=''");;
			inners[i].setAttribute("onmousedown","dojo.widget.byId('" + this.widgetId + "').createTimeRange(this)");
			var pos = i + "";
			if (pos.length == 1){ pos = "0" + i} 
			inners[i].setAttribute("pos", pos);
		}
	},
	
	
	createTimeRange: function(div){
		var pos = dojo.html.toCoordinateObject(div,true);
		this.current = document.createElement("div");
		this.timeDiv = document.createElement("div");
		with (this.current.style){
			borderStyle = "solid";
			borderColor = "#0000FF";
			backgroundColor = "#AAAAFF";
			borderWidth = "1px";
			height = pos.height - 3 +"px";
			width = pos.width - 3 + "px";
			position = "absolute";
			top = pos.top + "px";
			left = pos.left + "px"; 
		}
		with (this.timeDiv.style){
			fontSize = "9px";
			textAlign = "center";
			fontWeight = "bold";
		}
		this.current.appendChild(this.timeDiv);
		dojo.body().appendChild(this.current);
		this.current.setAttribute("onMouseMove","dojo.widget.byId('" + this.widgetId + "').reduceTimeRange(event)");
		var id = this.widgetId + "_timeSlice_" + this.slicePartId++;
		this.current.setAttribute("id", id);
		this.current.setAttribute("onClick","dojo.widget.byId('" + this.widgetId + "').selectTimeRange(this)");
		dojo.html.setOpacity(this.current, 0.8, false);
		//dojo.lfx.rounded({}, [id]);
		this.firstSelected = div;
		this.mousedown = true;
		this.expandTimeRange(this.firstSelected);
	},

	getDivUnderMouse: function(cursor){
		var pos = dojo.html.toCoordinateObject(this.domNode,true);
		var y = cursor.y - pos.top;
		
		var divPos = Math.floor(y/10);

		return this.domNode.getElementsByTagName("div")[divPos];
	},

	reduceTimeRange: function(e){
		if (this.mousedown){
			var cursor = dojo.html.getCursorPosition(e);
			this.lastSelected = this.getDivUnderMouse(cursor);
			if (this.lastSelected != null){
				this.doExpend();
				
			}else{
				//no div under cursor
			}
			
		}
	},

	expandTimeRange: function(div){
		if (this.mousedown){
			div.innerHTML='';
			this.lastSelected = div;
			this.doExpend();
		}
	},

	fixTimeRange: function (){
		if (this.mousedown){
			this.timeRange.add(this.current);
			this.firstSelected = null;
			this.lastSelected = null;
			with (this.current.style){
				backgroundColor = "#4444FF";
			}
			var val = this.current.getElementsByTagName('div')[0].innerHTML;
			var start = val.substring(0,5);
			var end = val.substring(8,val.length);
			this.onCreate(start, end, this.widgetId);
			if (this.weekCalendar != null){
				this.weekCalendar.onCreate(start, end, this.widgetId);
			}
			this.current = null;
			this.mousedown = false;
			this.tmpx = null;
			
		}
	},
	
	onCreate: function(start, end, widgetId){
	},

	doExpend: function (){
		if (this.firstSelected !=null){
			var newTop;
			var newLeft;
			var newHeigth;
			var newWidth;
			var firstPos = dojo.html.toCoordinateObject(this.firstSelected,true);
			var lastPos = dojo.html.toCoordinateObject(this.lastSelected,true);
			
			if(this.firstSelected.getAttribute("pos") < this.lastSelected.getAttribute("pos")){
				newTop = firstPos.top;
				newHeight = lastPos.top - firstPos.top + lastPos.height;
			}else{
				newTop = lastPos.top;
				newHeight = firstPos.top - lastPos.top + firstPos.height;
			}
			
			
			with (this.current.style){
				borderStyle = "solid";
				borderColor = "#0000FF";
				borderWidth = "1px";
				height = newHeight -3 +"px";
				width = firstPos.width - 3 + "px";
				position = "absolute";
				top = newTop + "px";
				left = firstPos.left + "px"; 
			}
			this.createTimeDiv()
		}
	},
	
	createTime: function(){
		var firstPos = this.firstSelected.getAttribute("pos");
		var lastPos = this.lastSelected.getAttribute("pos");
		
		var time;
		
		if(firstPos < lastPos){
			time = this.firstSelected.getAttribute("start") + " - " + this.lastSelected.getAttribute("end");
		}else{
			time = this.lastSelected.getAttribute("start") + " - " + this.firstSelected.getAttribute("end");
		}
		return time;
	},
	
	createTimeDiv: function(){
		this.timeDiv.innerHTML = this.createTime();
	},
	
	removeTimeRange: function(div){
		var val = div.getElementsByTagName('div')[0].innerHTML;
		var start = val.substring(0,5);
		var end = val.substring(8,val.length);
		this.onRemove(start, end, this.widgetId);
		if (this.weekCalendar != null){
			this.weekCalendar.onRemove(start, end, this.widgetId);
		}
		this.timeRange.remove(div);
		document.getElementsByTagName("body")[0].removeChild(div);
		
	},
	
	onRemove: function(start, end, widgetId){
	},
	
	selectTimeRange: function(div){
		if (this.selectedTimeRange != null && div == this.selectedTimeRange){
			//remove selected Style
			with (this.selectedTimeRange.style){
				backgroundColor = "#4444FF";
			}
			this.selectedTimeRange = null;
		} else {
			if (this.selectedTimeRange != null ){
				with (this.selectedTimeRange.style){
					backgroundColor = "#4444FF";
				}
			}
			this.selectedTimeRange = div;
			if (div != null){
				with (div.style){
					backgroundColor = "#FF44FF";
				}
				this.onSelect(div);
			}
		}
	},
	
	/**
	 * Listener on sliceTime selection
	 */
	onSelect: function(div){},
	
	keyPressed: function(e){
		if (e.keyCode == 46 && this.selectedTimeRange != null){  //DEL
			this.removeTimeRange(this.selectedTimeRange);
			this.selectedTimeRange = null;
		}
	},
	
	// ------------------------------------------------------------------ //
	//                          MODEL                                     //
	
	/**
	 * return the json object of the Day
	 */
	getValue: function(){
		var toReturn = new Array();
		this.timeRange.forEach(function(item){
			var toAdd = {};
			var val = item.getElementsByTagName('div')[0].innerHTML;
			toAdd.start = val.substring(0,5);
			toAdd.end = val.substring(8,val.length);
			toReturn.push(toAdd);
		});
		return toReturn;
	},
	
	addTimeRange: function(start, end){
		var inners = this.domNode.getElementsByTagName("div");
		for(var i=0; i<inners.length; i++){
			if (inners[i].getAttribute("start") == start){
				this.createTimeRange(inners[i]);
			}
			if (inners[i].getAttribute("end") == end){
				this.expandTimeRange(inners[i]);
				if (this.mousedown){
					this.timeRange.add(this.current);
					this.firstSelected = null;
					this.lastSelected = null;
					with (this.current.style){
						backgroundColor = "#4444FF";
					}
					this.current = null;
					this.mousedown = false;
					this.tmpx = null;
				}
			}
		}
	},
	
	setValue: function(obj){
		for(var i=0; i < obj.length; i++){
			this.addTimeRange(obj[i].start, obj[i].end);
		}
	},
});