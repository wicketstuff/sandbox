dojo.provide("dojoWicket.widget.Calendar");

dojo.require("dojo.html.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.collections.ArrayList");
dojo.require("dojo.collections.Store");
dojo.require("dojo.json");

dojo.widget.defineWidget (
	"dojoWicket.widget.Calendar",
	dojo.widget.HtmlWidget,
	{
	
	templatePath: dojo.uri.dojoUri("../dojo-wicket/widget/template/Calendar.html"),
	templateCssPath: dojo.uri.dojoUri("../dojo-wicket/widget/template/Calendar.css"),
	
	/**
	 *
	 */
	//width and height of a slice time
	sliceTimeWidth: 100,
	sliceTimeHeight: 10,
	
	//true if mouse is down
	mouseDown: false,
	
	//tmp slice 
	current: null,
	
	//allSlice
	data: null,
	
	//delete for sliceTime
	remover: null,
	
	postCreate: function(args, fragment, parent){
		this.data = new dojo.collections.Store();
	
		this.domNode.style.width = 7 * this.sliceTimeWidth + "px";
		
		this._createDay("Sunday", 0);
		this._createDay("Monday", 1);
		this._createDay("Tuesday", 2);
		this._createDay("Wednesday", 3);
		this._createDay("Thursday", 4);
		this._createDay("Friday", 5);
		this._createDay("Saturday", 6);
		
		dojo.event.connect(document, "onmouseup", this, "mouseUpDocument");
		dojo.event.connect(this.domNode, "onmousemove", this, "mouseMoveOnCalendar");
	},
	
	_createDay: function(/**String*/ dayStr, /** int */ dayPos){
		var day = document.createElement("div");
		with(day.style){
			cssFloat = "left";
			position = "relative";
			zIndex= "1";
		}
		
		for(var i=0; i<48; i++){
			var div = document.createElement("div");
			div.className = "timeArea";
			with(div.style){
				cursor = "default"; 
				borderStyle = "solid"; 
				borderBottomWidth = "1px"; 
				borderLeftWidth = "1px"; 
				if(i%2 ==0){
					borderBottomStyle="dotted";
				}
				borderTopWidth = "0px"; 
				borderRightWidth = "0px"; 
				width = this.sliceTimeWidth -1 + "px"; 
				height = this.sliceTimeHeight -1 + "px"; 
				maxHeight = this.sliceTimeHeight -1 + "px"; 
				overflow = "hidden";
				if (i < 16 || i > 37){
					backgroundColor="#EEEEEE";
				}
			}
			var pos = this._getStartEndPosForI(i);
			div.setAttribute("pos", pos.pos);
			div.setAttribute("start", pos.start);
			div.setAttribute("end", pos.end);
			div.setAttribute("dayPos", dayPos);
			div.setAttribute("dayStr", dayStr);
			day.appendChild(div);
			
			this.domNode.appendChild(day);
			
			//connect events
			dojo.event.connect(div, "onmouseover", this, "mouseOverDiv");
			dojo.event.connect(div, "onmouseout", this, "mouseOutDiv");
		
			//event to create div
			dojo.event.connect(div, "onmousedown", this, "mouseDownDiv");
		}
		
		day.setAttribute("day", dayStr);
		day.setAttribute("dayPos", dayPos);
	},
	
	/**
	 * Transform a position on a time interval
	 */
	_getStartEndPosForI: function(i){
		var obj={};
		if(i%2 == 0){
			obj.start = this._toTwoDigits(parseInt(i/2)) + ":" + "00";
			obj.end   = this._toTwoDigits(parseInt(i/2)) + ":" + "30";
		}else{
			obj.start = this._toTwoDigits(parseInt(i/2)) + ":" + "30";
			obj.end   = this._toTwoDigits(parseInt(i/2) + 1) + ":" + "00";
		}
		obj.pos = this._toTwoDigits(i);
		return obj;
	},
	
	/**
	 * Get an int to return a string représenting the int on
	 * two digits
	 */
	_toTwoDigits: function(/**int*/ i){
		if( i<10 ){
			return "0" + i ;
		}
		return i + "";
	},
	
	_getDivUnderMouse: function(cursor){
		var pos = dojo.html.toCoordinateObject(this.domNode,true);
		var y = cursor.y - pos.top;
		var x = cursor.x - pos.left ;
		
		var divYPos = Math.floor(y/this.sliceTimeHeight);
		var divXPos = Math.floor(x/this.sliceTimeWidth) + 1;
		if (divYPos < 0 || divXPos < 0){
			return null;
		}
		return this.domNode.childNodes[divXPos].childNodes[divYPos];
	},
	
	/*********************************************/
	/**                 Events                  **/
	/*********************************************/
	mouseOverDiv: function(e){
		e.target.innerHTML = e.target.getAttribute("start") + " - " + e.target.getAttribute("end"); 
	},
	mouseOutDiv:  function(e){
		e.target.innerHTML = "";
	},
	
	mouseDownDiv: function(e){
		var div = e.target;
		var sliceTime = new dojoWicket.widget.SliceTime();
		sliceTime.sliceTimeWidth = this.sliceTimeWidth;
		sliceTime.sliceTimeHeight = this.sliceTimeHeight;
		sliceTime.begin(div, this.domNode);
		this.mouseDown = true;
		
		this.current = sliceTime;
		dojo.event.connect(sliceTime.time, "onclick", this, "onClickOnSliceTime");
		this.data.addData(sliceTime, this.data.get().length);
	},
	
	mouseUpDocument: function(e){
		this.mouseDown = false;
	},
	
	mouseMoveOnCalendar: function(e){
		if(this.mouseDown){
			var cursor = dojo.html.getCursorPosition(e);
			var div = this._getDivUnderMouse(cursor);
			if (div != null){
				this.current.resizeTo(div);
			}
		}
	},
	
	/***********************************************/
	/**            Data Management                **/
	/***********************************************/
	getData: function(){
		var jsonObj = new Array();
		this.data.forEachData(function(slice){
			jsonObj.push(slice.getData());
		});
		return jsonObj;
	},
	
	getDataAsJson: function(){
		return dojo.json.serialize(this.getData());
	},
	
	setDataFromJson: function(/** Json */ Json){
		//remove all elements
		this.data.forEachData(function(slice){
			slice.remove();
		});
		this.data.clearData();
		var jsonObj = dojo.json.evalJson(Json);
		for(var i=0; i<jsonObj.length; i ++){
			var sliceTime = new dojoWicket.widget.SliceTime();
			sliceTime.sliceTimeWidth = this.sliceTimeWidth;
			sliceTime.sliceTimeHeight = this.sliceTimeHeight;
			sliceTime = sliceTime.setData(jsonObj[i], this.domNode);
			dojo.event.connect(sliceTime.time, "onclick", this, "onClickOnSliceTime");
			sliceTime.onSelect = this.onClickOnSliceTime;
			this.data.addData(sliceTime, this.data.get().length);
		}
	},
	
	/***********************************************/
	/**           Event from sliceTime            **/
	/***********************************************/
	onClickOnSliceTime: function(/** Event */ e){
		this.data.forEachData(function(slice){
			slice.time.style.backgroundColor = "#AAAAFF";
			if(this.remover != null){
				this.remover.parentNode.removeChild(this.remover);
			}
			if(slice.time == e.target){
				e.target.style.background = "#df8aff";
				var deleter = document.createElement("img");
				deleter.src = "close.gif";
				slice.time.appendChild(deleter);
				with(deleter.style){
					position = "absolute";
					left = "88px";
					top= "20px";
					cursor= "default";
				}
				this.remover = deleter;
			}
		});		
	}
	
});

/**
 * TODO : optimize with setting domNode as a class attribute setting up during each resize
 */
dojo.widget.defineWidget (
	"dojoWicket.widget.SliceTime",
	null,
	{
	
	domNode: null,
	first: null,
	last: null,
	
	//time is the anchor, other are additionnals ones
	time: null,
	
	//width and height of a slice time
	sliceTimeWidth: 0,
	sliceTimeHeight: 0,
	
	//div to resize
	resizer:null,
	
	//header
	header:null,
	
	//isMouseDown on resizer?
	mouseDownOnResizer : false,
	//isMouseDown on header?
	mouseDownOnHeader : false,
	
	/** EVENTS **/
	
	mouseDownOnResizerNode: function(e){
		this.mouseDownOnResizer = true;
	},
	
	mouseDownOnHeaderNode: function(e){
		this.mouseDownOnHeader = true;
	},
	
	mouseUpOnDocument: function(e){
		this.mouseDownOnResizer = false;
		this.mouseDownOnHeader = false;
		//switch first and last if in the bad order
		var startPos = parseInt(this.first.getAttribute("pos"), 10);
		var endPos = parseInt(this.last.getAttribute("pos"), 10);
		if(startPos > endPos){
			dojo.debug("switch")
			this._switchStartEnd();	
		}
	},

	mouseMoveOnDomNode: function(e){
		if(this.mouseDownOnResizer){
			var cursor = dojo.html.getCursorPosition(e);
			var div = this._getDivUnderMouse(cursor);
			this.resizeTo(div);
		}else if(this.mouseDownOnHeader){
			var cursor = dojo.html.getCursorPosition(e);
			var div = this._getDivUnderMouse(cursor);
			
			if (div !=null){
				//store old start and end
				var oldStartPos = parseInt(this.first.getAttribute("pos"), 10);
				var oldStartPosDay = parseInt(this.first.getAttribute("dayPos"), 10);
				var oldEndPos = parseInt(this.last.getAttribute("pos"), 10);
				var oldEndPosDay = parseInt(this.last.getAttribute("dayPos"), 10);
				var pos = dojo.html.toCoordinateObject(div,true);
				var domNodePos = dojo.html.toCoordinateObject(this.domNode,true);
				
				var computedTop = pos.top - domNodePos.top;
				
				with(this.time.style){
					// Do not drag if
					// bottom is bigger than down limit
					if (computedTop + parseInt(this.time.style.height) + this.sliceTimeHeight < domNodePos.top + domNodePos.height){
						top = computedTop + "px";
					}
					left = pos.left - domNodePos.left + "px";
				}
				
				if (computedTop + parseInt(this.time.style.height) + this.sliceTimeHeight < domNodePos.top + domNodePos.height){
					this.first = div;
				}else{
					this.first = this.domNode.childNodes[parseInt(div.getAttribute("dayPos"), 10) +1].childNodes[this.first.getAttribute("pos")];
				}
				
				var startPosMove = parseInt(this.first.getAttribute("pos"),10) - oldStartPos;
				var startPosDayMove = parseInt(this.first.getAttribute("dayPos"),10) - oldStartPosDay + 1;
				
				if (startPosMove != 0 || startPosDayMove !=0 ){
					var div = this.domNode.childNodes[oldEndPosDay + startPosDayMove].childNodes[(oldEndPos + startPosMove)];
					this.resizeTo(div, domNodePos);
				}
			}
			
		}
	},
	
	/** FUNCTIONS **/
	_getDivUnderMouse: function(cursor){
		var pos = dojo.html.toCoordinateObject(this.domNode,true);
		var y = cursor.y - pos.top;
		var x = cursor.x - pos.left ;
		
		var divYPos = Math.floor(y/this.sliceTimeHeight);
		var divXPos = Math.floor(x/this.sliceTimeWidth) + 1;
		if (divYPos < 0 || divXPos < 0){
			return null;
		}
		return this.domNode.childNodes[divXPos].childNodes[divYPos];
	},
	
	remove: function(){
		this.time.parentNode.removeChild(this.time);
		this.resizer.parentNode.removeChild(this.resizer);
		this.header.parentNode.removeChild(this.header);
	},

	begin: function(div, parent){
		dojo.debug("create")
		this.first = div;
		this.last = div;
		this.domNode = parent;
		this.time = this._createDivSlice(div);
		
		//create a resizer to resize the SliceTime
		this._createResizer();
		this._placeResizer(this.sliceTimeHeight);
		//create the header and place it
		this._createHeader();
		this._setInnerHead(div);
	},
	
	_createDivSlice: function(div){
		var pos = dojo.html.toCoordinateObject(div,true);
		var domNodePos = dojo.html.toCoordinateObject(this.domNode,true);
		var slice = document.createElement("div");
		with (slice.style){
			borderStyle = "solid";
			borderColor = "#0000FF";
			backgroundColor = "#AAAAFF";
			borderWidth = "1px";
			height = pos.height - 3 +"px";
			width = pos.width - 3 + "px";
			position = "absolute";
			top = pos.top - domNodePos.top + "px";
			left = pos.left - domNodePos.left + "px";
			zIndex = "2"; 
		}
		this.domNode.appendChild(slice);
		dojo.html.setOpacity(slice, 0.8, false);
		
		return slice;
	},
	
	_createResizer: function(){
		this.resizer = document.createElement("div");
		this.time.appendChild(this.resizer);
		with(this.resizer.style){
			width = this.sliceTimeWidth - 4 + "px";
			height = this.sliceTimeHeight - 2 + "px";
			overflow = "hidden";
			textAlign = "center";
			position = "absolute";
			zIndex = 5;
		}
		this.resizer.innerHTML="=";
		
		dojo.event.connect(this.resizer, "onmousedown", this, "mouseDownOnResizerNode");
		dojo.event.connect(this.domNode, "onmousemove", this, "mouseMoveOnDomNode");
		dojo.event.connect(document, "onmouseup", this, "mouseUpOnDocument");
	},
	
	_placeResizer: function(/** int */ newHeight){
		with(this.resizer.style){
			dojo.debug(newHeight)
			top = newHeight - this.sliceTimeHeight - 2 +"px";
		}
	},
	
	_createHeader: function(){
		this.header = document.createElement("div");
		this.time.appendChild(this.header);
		with(this.header.style){
			width = this.sliceTimeWidth - 2 + "px";
			height = this.sliceTimeHeight + "px";
			overflow = "hidden";
			textAlign = "center";
			position = "absolute";
			zIndex = 4;
			color = "white";
			backgroundColor = "blue";
			cursor = "move";
		}
		
		dojo.event.connect(this.header, "onmousedown", this, "mouseDownOnHeaderNode");
	},
	
	_setInnerHead: function(/** DomNode */ onTo){
		this.header.innerHTML= onTo.getAttribute("start") + " - ";
		if (this.last && onTo==this.first){
			this.header.innerHTML +=  this.last.getAttribute("end");
		}else{
			this.header.innerHTML +=  this.first.getAttribute("end");
		}
	},
	
	_switchStartEnd: function(){
		var start = this.first;
		this.first = this.last;
		this.last = start;
	},
	
	resizeTo: function(div, domNodePos){
		if (div != null){
			this.last = div;
		
			var newTop;
			var newLeft;
			var newHeigth;
			var newWidth;
			if (domNodePos == null){
				domNodePos = dojo.html.toCoordinateObject(this.domNode,true);
			}
			var firstDayPos = parseInt(this.first.getAttribute("dayPos"));
			var lastDayPos  = parseInt(this.last.getAttribute("dayPos"));
			
			//make some calculation to avoid to use toCoordinateObject... a little bit overkill ;)
			var firstPosTop = parseInt(this.first.getAttribute("pos"), 10)*this.sliceTimeHeight + domNodePos.top;
			var firstPosLeft = firstDayPos*this.sliceTimeWidth + domNodePos.left + 1;
			var lastPosTop  = parseInt(this.last.getAttribute("pos"), 10)*this.sliceTimeHeight +  domNodePos.top;
			
			//calculate this.last to stay on the same day
			if(this.first.getAttribute("dayPos") != this.last.getAttribute("dayPos")){
				this.last = this.domNode.childNodes[parseInt(this.first.getAttribute("dayPos")) + 1].childNodes[this.last.getAttribute("pos")];
			}
			//the 2 divs are on the same day
			if(this.first.getAttribute("pos") < this.last.getAttribute("pos")){
				newTop = firstPosTop;
				newHeight = lastPosTop - firstPosTop + this.sliceTimeHeight;
				this._placeResizer(newHeight);
				this._setInnerHead(this.first);
			}else{
				newTop = lastPosTop;
				newHeight = firstPosTop - lastPosTop + this.sliceTimeHeight;
				this._placeResizer(newHeight);
				this._setInnerHead(this.last);
			}
			
			with (this.time.style){
				borderStyle = "solid";
				borderColor = "#0000FF";
				borderWidth = "1px";
				height = newHeight -3 +"px";
				width = this.sliceTimeWidth - 3 + "px";
				position = "absolute";
				top = newTop - domNodePos.top + "px";
				left = firstPosLeft - domNodePos.left + "px"; 
			}
		}
	},
	
	_createEntireDivOn: function(/**int*/ i){
		var div = this.domNode.childNodes[i].childNodes[0];
		var slice = this._createDivSlice(div);
		with (slice.style){
		 	height = 48 * this.sliceTimeHeight -3 + "px";
		}
		return slice;
	},
	
	/***********************************************/
	/**            Data Management                **/
	/***********************************************/
	
	_getIndexForTime: function(/** String */ time){
		var values = time.split(":");
		var min = 0;
		if (values[1]=="30"){
			min=1;
		}
		dojo.debug(parseInt(values[0],10)*2 + min);
		return parseInt(values[0],10)*2 + min;
	},
	
	getData: function(){
		var jsonObj = {};
		jsonObj.dayIndex = this.first.getAttribute("dayPos");
		jsonObj.dayStr   = this.first.getAttribute("dayStr");
		jsonObj.start    = this.first.getAttribute("start");
		jsonObj.end      = this.last.getAttribute("end");
		return jsonObj;
	},
	
	getDataAsJson: function(){
		return dojo.json.serialize(this.getData())
	},
	
	setDataFromJson: function(/** Json */ json){
		this.setData(dojo.json.evalJson(json));
	},
	
	setData: function(/** Obj */ jsonObj, /** domNode */domNode){
		var day = parseInt(jsonObj.dayIndex);
		var start = this._getIndexForTime(jsonObj.start);
		var end = this.	_getIndexForTime(jsonObj.end);
		var startNode = domNode.childNodes[day +1].childNodes[start];
		var endNode   = domNode.childNodes[day +1].childNodes[end - 1];
		this.begin(startNode, domNode);
		this.resizeTo(endNode);
		return this;
	}

});