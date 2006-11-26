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
	sliceTimes: null,
	
	//incremented on each new Slice
	slicePartId: 0,
	
	//Selected Slice
	selectedSlice: null,
	
	//if a weekCalendar exsit add it here
	weekCalendar : null,
	
	postCreate: function(args, fragment, parent){
		this.sliceTimes =  new dojo.collections.ArrayList();
		var body = document.getElementsByTagName("body")[0];
		var onMouseUp = body.getAttribute("onMouseUp");
		if (onMouseUp == null){ onMouseUp = ""}
		onMouseUp += "dojo.widget.byId('" + this.widgetId + "').fixDiv();";
		document.getElementsByTagName("body")[0].setAttribute("onMouseUp",onMouseUp);
		var onKeyPress = body.getAttribute("onkeypress");
		if (onKeyPress == null){ onKeyPress = ""}
		onKeyPress += "dojo.widget.byId('" + this.widgetId + "').keyPressed(event);";
		document.getElementsByTagName("body")[0].setAttribute("onkeypress",onKeyPress);
		
		//add listener to slice
		var inners = this.domNode.getElementsByTagName("div");
		for(var i=0; i<inners.length; i++){
			inners[i].setAttribute("onmouseover","dojo.widget.byId('" + this.widgetId + "').expandDiv(this)");
			inners[i].setAttribute("onmousedown","dojo.widget.byId('" + this.widgetId + "').createDiv(this)");
			var pos = i + "";
			if (pos.length == 1){ pos = "0" + i} 
			inners[i].setAttribute("pos", pos);
		}
	},
	
	
	createDiv: function(div){
		var pos = dojo.html.toCoordinateObject(div,true);
		this.current = document.createElement("div");
		this.timeDiv = document.createElement("div");
		with (this.current.style){
			borderStyle = "solid";
			borderColor = "#0000FF";
			backgroundColor = "#4444FF";
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
		this.current.setAttribute("onMouseMove","dojo.widget.byId('" + this.widgetId + "').reduceDiv(event)");
		var id = this.widgetId + "_timeSlice_" + this.slicePartId++;
		this.current.setAttribute("id", id);
		this.current.setAttribute("onClick","dojo.widget.byId('" + this.widgetId + "').selectDiv(this)");
		dojo.html.setOpacity(this.current, 0.8, false);
		//dojo.lfx.rounded({}, [id]);
		this.firstSelected = div;
		this.mousedown = true;
	},

	getDivUnderMouse: function(cursor){
		var inners = this.domNode.getElementsByTagName("div");
		for(var i=0; i<inners.length; i++){
			if (this.inDiv(inners[i], cursor)){
				return inners[i];
			}
		}
		return null;
	},

	reduceDiv: function(e){
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

	expandDiv: function(div){
		if (this.mousedown){
			this.lastSelected = div;
			this.doExpend();
		}
	},

	fixDiv: function (){
		if (this.mousedown){
			this.sliceTimes.add(this.current);
			this.firstSelected = null;
			this.lastSelected = null;
			this.current = null;
			this.mousedown = false;
		}
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

	inDiv: function(div, cursor){
		var pos = dojo.html.toCoordinateObject(div,true);
		if ( ((pos.left + pos.width) > cursor.x) && (cursor.x > pos.left) &&
		     ((pos.top  + pos.height)> cursor.y) && (cursor.y > pos.top)  )
		{
			return true;
		}
		return false;
	},
	
	removeSlice: function(div){
		this.sliceTimes.remove(div);
		document.getElementsByTagName("body")[0].removeChild(div);
	},
	
	selectDiv: function(div){
		if (this.selectedSlice != null && div == this.selectedSlice){
			//remove selected Style
			with (this.selectedSlice.style){
				backgroundColor = "#4444FF";
			}
			this.selectedSlice = null;
		} else {
			if (this.selectedSlice != null ){
				with (this.selectedSlice.style){
					backgroundColor = "#4444FF";
				}
			}
			this.selectedSlice = div;
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
		if (e.keyCode == 46 && this.selectedSlice != null){  //DEL
			this.removeSlice(this.selectedSlice);
			this.selectedSlice = null;
		}
	},
	
	// ------------------------------------------------------------------ //
	//                          MODEL                                     //
	
	/**
	 * return the json object of the Day
	 */
	getValue: function(){
		var toReturn = new Array();
		this.sliceTimes.forEach(function(item){
			var toAdd = {};
			var val = item.getElementsByTagName('div')[0].innerHTML;
			toAdd.start = val.substring(0,5);
			toAdd.end = val.substring(8,val.length);
			toReturn.push(toAdd);
		});
		return toReturn;
	},
	
	addSliceTime: function(start, end){
		var inners = this.domNode.getElementsByTagName("div");
		for(var i=0; i<inners.length; i++){
			if (inners[i].getAttribute("start") == start){
				this.createDiv(inners[i]);
			}
			if (inners[i].getAttribute("end") == end){
				this.expandDiv(inners[i]);
				this.fixDiv();
			}
		}
	},
	
	setValue: function(obj){
		for(var i=0; i < obj.length; i++){
			this.addSliceTime(obj[i].start, obj[i].end);
		}
	},
});