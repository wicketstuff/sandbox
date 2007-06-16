dojo.require("dojo.widget.*");
dojo.require("dojo.collections.Store");
dojo.require("dojo.dnd.*");
dojo.require("dojo.dnd.HtmlDragMove");
dojo.require("dojo.json");

dojo.provide ("dojoWicket.widget.PercentSelector");


dojo.widget.defineWidget ("dojoWicket.widget.PercentSelector",dojo.widget.HtmlWidget,{

	templatePath: dojo.uri.dojoUri("../dojo-wicket/widget/template/PercentSelector.htm"),
	templateCssPath: dojo.uri.dojoUri("../dojo-wicket/widget/template/PercentSelector.css"),

	//current selected handler
	currentSelectorList: null,
	minSelected: 0,
	maxSelected: 0,
	
	//handlerSize
	handlerSize: 18,
	
	//selectorSize
	selectorSize: 800,
	
	//select mover
	moveLeader: null,
	
	_handleMove: null,
	
	//
	lastPercentage: null,
	firstPercentage: null,
	
	//total
	totalPercentage:0,

	
	postCreate: function(args, fragment, parent){
		this.currentSelectorList = new dojo.collections.Store();
		this.dragStarted = false;
		this._restartSelected();
		
	},
	
	onValueChange: function(){
	},
	
	/**
	 * Triggered on click on a selector
	 */
	_leftClickOnHandler: function(evt){
		var target = evt.target;
		
		var pos = target.getAttribute('pos');
		if(evt.metaKey||evt.ctrlKey||evt.shiftKey){
			//multiple selection activate
			this.addSelectorInSelection(target);
			for (var i=this.minSelected; i <= this.maxSelected; i++){
				this.addSelectorInSelection(this._getElementByPos(i));
			}
		}else{
			//simple selection
			//clear all
			this.currentSelectorList.forEachData(function removeAll(item){
				item.className="handler";
				this.removeData(item);
			});
			this._restartSelected();
			//select clicked
			this.addSelectorInSelection(target);
		}
		this._runDrag(evt);
			
	},
	
	_runDrag: function(evt){
		var target = evt.target;
		this.moveLeader = target;
		this._handleMove = new dojo.widget._HandlerDragMoveSource(target);
		this._handleMove.setParent(this.handlerContainer);
		this._handleMove.setWidget(this);
	},
	
	_getLastSelected: function(){
		return this.currentSelectorList.getDataByKey(this.maxSelected);
	},
	
	_getFirstSelected: function(){
		return this.currentSelectorList.getDataByKey(this.minSelected);
	},
	
	_getElementByPos: function(pos){
		try{ 
			return this.handlerContainer.getElementsByTagName("div")[parseInt(pos) -1];
		}catch(e){
			return null
		}
	},
	
	
	addSelectorInSelection: function(selector){
		this.currentSelectorList.addData(selector, selector.getAttribute('pos'));
		selector.className="handlerSelected"
		var pos = parseInt(selector.getAttribute('pos'))
		if (pos > this.maxSelected){
			this.maxSelected = pos;
		}
		if (pos < this.minSelected){
			this.minSelected = pos;
		}
	},
	
	_restartSelected: function(){
		this.maxSelected = 0;
		this.minSelected = 100000000;
	},
	
	removeSelectorInSelection: function(selector){
		this.currentSelectorList.removeDataByKey(selector.getAttribute('pos'));
		selector.className="handler";
	},
	
	setLabelPosition: function(){
		var current = this.firstPercentage;
		while(current!= null){
			current.setLabelDivPosition(0);
			current = current.next;
		}
	},
	
	addRange: function(/*int*/ percentage, /*String*/ label){
		if (this.lastPercentage == null){
			//it is the first added;
			var item = new dojo.widget._Percentage();;
			item.selector = this;
			item.label = label;
			item.percentage = percentage;
			item.pos = 1;	
			item.totalPercentage = percentage;
			item.create();
			this.lastPercentage = item;
			this.firstPercentage = item;
			
		}else{
			var item = new dojo.widget._Percentage();;
			item.selector = this;
			item.label = label;
			item.percentage = percentage;
			item.previous = this.lastPercentage;
			item.pos = this.lastPercentage.pos + 1;		
			item.totalPercentage = item.previous.totalPercentage + percentage;
			item.create();
			this.lastPercentage.next = item;
			this.lastPercentage = item;
		}
	},
	
	
	getJson: function(){
		var toReturn = {};
		var item = this.firstPercentage;
		while(item != null){
			toReturn[item.label] = item.percentage
			item = item.next;
		}
		return dojo.json.serialize(toReturn);
	},
	
	setJson:function(/*Sring*/ json){
		var obj = dojo.json.evalJson(json);
		for (prop in obj){
			this.addRange(obj[prop], prop);
		}
	}
	

});

/*************************************************************************************/

dojo.declare (
	"dojo.widget._Percentage",
	null,
{

	selector: null,
	label:null,
	previous: null,
	next: null,
	percentage: null,
	pos: null,
	handler: null,
	labelDiv: null,
	totalPercentage:0,
	
	create: function(){
		if (this.totalPercentage < 100){
			var div = document.createElement('div');
			div.setAttribute('pos', this.pos);
			with(div.style){
				zIndex = this.pos; 
				top = "0px"; 
				position = "absolute"; 
				width = "18px";
				
				var start = 0;
					
				if (this.previous != null){
					start = parseInt(this.previous.handler.style.left.substring(0, this.previous.handler.style.left.length -2)) + this.selector.handlerSize/2;
				}
				
				left = ((start + (this.selector.selectorSize/100)* this.percentage) - this.selector.handlerSize/2) + "px";
				height = "26px";
			}
			div.className="handler";
			this.selector.handlerContainer.appendChild(div);
			dojo.event.connect(div, "onmousedown", this.selector, "_leftClickOnHandler");
			this.handler = div;
		}
		
		this.createLabelDiv();
		
	},
	
	createLabelDiv: function(){
		var div = document.createElement('div');
		div.className="label";
		this.selector.labelContainer.appendChild(div);
		this.labelDiv = div;
		with(div.style){
			height = "50px";
		}
		this.labelDiv = div;
		this.setLabelDivPosition();
	},
	
	setLabelDivPosition: function(){
		with(this.labelDiv.style){
			var start = -1* this.selector.handlerSize/2;
			if (this.previous != null){
				start = parseInt(this.previous.handler.style.left.substring(0, this.previous.handler.style.left.length -2));
			}
			
			var end = this.selector.selectorSize;
			
			if (this.handler != null){
				end = parseInt(this.handler.style.left.substring(0, this.handler.style.left.length -2));
			}
			if (this.totalPercentage >= 100){
				end = this.selector.selectorSize*0.99 -1;
			}
				
			//left = (start) + "px";
			
			width = (end - start) + "px";
			zIndex = "2";
			
			//percentage value
			this.percentage = Math.round (((end - start)/this.selector.selectorSize)*100);
			this.labelDiv.innerHTML =  "<div class='percentValue'>" + this.percentage +"%</div><div class='percentLabel'>" + this.label + "</div>"
		}
	}
	
	
	
});


/****************************************************************************************/


/**
 * This class extends the HtmlDragMoveSource class to provide
 * features for the slider handle.
 */
dojo.declare (
	"dojo.widget._HandlerDragMoveSource",
	dojo.dnd.HtmlDragMoveSource,
{
	container: null,
	selector: null,

	/** Setup the handle for drag
	 *  Extends dojo.dnd.HtmlDragMoveSource by creating a SliderDragMoveSource */
	onDragStart: function(/*Event*/ evt){
		var containerPos = dojo.html.toCoordinateObject(this.selector.handlerContainer, true);
		var dragObj = this.createDragMoveObject ();
		dragObj.minX = containerPos.left - 2;
		dragObj.maxX = containerPos.left + containerPos.width - 2;
		return dragObj;
	},

	onDragEnd: function(/*Event*/ evt){
		this.selector.moveLeader = null;
		this._handleMove = null;
		this.selector.onValueChange();
	},

	createDragMoveObject: function (){
		var dragObj = new dojo.widget._HandlerDragMoveObject (this.dragObject, this.type);
		dragObj.selector = this.selector;

		if (this.dragClass){ 
			dragObj.dragClass = this.dragClass; 
		}

		return dragObj;
	},

	setParent: function (/*Widget*/ container){
		this.container = container;
	},
	
	setWidget: function(/*Widget*/ selector){
		this.selector = selector;
	}
});


dojo.declare (
	"dojo.widget._HandlerDragMoveObject",
	dojo.dnd.HtmlDragMoveObject,
{

	selector: null,
	minX: 0,
	maxX: 0,


	/** Moves the node to follow the mouse.
	 *  Extends functon HtmlDragObject by adding functionality to snap handle
	 *  to a discrete value */
	onDragMove: function(/*Event*/ evt){
		this.updateDragOffset ();
		var pos = evt.pageX - this.minX
		
		var selectedValue = Math.round (pos / (this.selector.selectorSize/100));
		pos = Math.round (selectedValue * (this.selector.selectorSize/100));
		
		var decal = pos - this.selector.handlerSize/2;
		
		var leader = this.selector.moveLeader 
		var leaderBeginLeft = leader.style.left.substring(0, leader.style.left.length -2);
		
		var min = this.selector._getFirstSelected().style.left.substring(0, this.selector._getFirstSelected().style.left.length -2);
		var max = this.selector._getLastSelected().style.left.substring(0, this.selector._getLastSelected().style.left.length -2);
		
		//get next and previous position
		var previous = this.selector._getElementByPos(parseInt(this.selector.minSelected) - 1);
		var next = this.selector._getElementByPos(parseInt(this.selector.maxSelected) + 1);
		if (previous != null){
			var prevPos = previous.style.left.substring(0, previous.style.left.length -2);
			if (parseInt(min) + parseInt(decal - leaderBeginLeft) < prevPos) return;
		}
		if (next != null){
			var nextPos = next.style.left.substring(0, next.style.left.length -2);
			if (parseInt(max) + parseInt(decal - leaderBeginLeft) > nextPos) return;
		}
		
		//do not go out the selection area
		if (parseInt(max) + parseInt(decal - leaderBeginLeft) > this.maxX - this.minX - (this.selector.handlerSize /2)) return
		if (parseInt(min) + parseInt(decal - leaderBeginLeft) < 0 - (this.selector.handlerSize /2)) return 
		
		this.selector.currentSelectorList.forEachData(function removeAll(item){
			if (item == leader){
				with (item.style){
					left = decal + "px";
				}
			}else{
				//it isn't the leader... right, we need to move it
				var currentLeft = parseInt(item.style.left.substring(0, item.style.left.length -2));
				with (item.style){
					left =  currentLeft + (decal - leaderBeginLeft ) + "px";
				}
			}
		});
		this.selector.setLabelPosition();
	}
});