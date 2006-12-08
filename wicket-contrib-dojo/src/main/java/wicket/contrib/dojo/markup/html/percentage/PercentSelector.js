dojo.require("dojo.widget.*");
dojo.require("dojo.collections.Store");
dojo.require("dojo.dnd.*");
dojo.require("dojo.dnd.HtmlDragMove");

dojo.provide ("dojo.widget.PercentSelector");

dojo.widget.defineWidget ("dojo.widget.PercentSelector",dojo.widget.HtmlWidget,{

	templatePath: dojo.uri.dojoUri("../PercentSelector.html"),
	templateCssPath: dojo.uri.dojoUri("../PercentSelector.css"),

	//current selected handler
	currentSelectorList: null,
	minSelected: 0,
	maxSelected: 0,
	
	//handlerSize
	handlerSize: 4,
	
	//select mover
	moveLeader: null,
	
	_handleMove: null,

	
	postCreate: function(args, fragment, parent){
		this.currentSelectorList = new dojo.collections.Store();
		this.dragStarted = false;
		this._restartSelected();
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
		dragObj.minX = containerPos.left;
		dragObj.maxX = containerPos.left + containerPos.width;

		return dragObj;
	},

	onDragEnd: function(/*Event*/ evt){
		this.selector.moveLeader = null;
		this._handleMove = null;
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
		var decal = evt.pageX - this.minX;
		
		var leader = this.selector.moveLeader 
		var leaderBeginLeft = leader.style.left.substring(0, leader.style.left.length -2);
		
		var min = this.selector._getFirstSelected().style.left.substring(0, this.selector._getFirstSelected().style.left.length -2);
		var max = this.selector._getLastSelected().style.left.substring(0, this.selector._getLastSelected().style.left.length -2);
		
		//get next and previous position
		var previous = this.selector._getElementByPos(parseInt(this.selector.minSelected) - 1);
		var next = this.selector._getElementByPos(parseInt(this.selector.maxSelected) + 1);
		if (previous != null){
			document.getElementById("log").innerHTML = previous;
			var prevPos = previous.style.left.substring(0, previous.style.left.length -2);
			if (parseInt(max) + parseInt(decal - leaderBeginLeft) < prevPos) return;
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
					left = decal
				}
			}else{
				//it isn't the leader... right, we need to move it
				var currentLeft = parseInt(item.style.left.substring(0, item.style.left.length -2));
				with (item.style){
					left =  currentLeft + (decal - leaderBeginLeft);
				}
			}
		});
	}
});

/*******************************************************************************************************************/
/** UTILITIES */

function getLeft(/*Node*/ node){
	var pos = dojo.html.getAbsolutePosition(node, true);
	return pos.x
}