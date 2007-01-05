dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");
dojo.require("dojo.event.*");

dojo.provide ("dojo.widget.LazyTable");

dojo.widget.defineWidget ("dojo.widget.LazyTable",dojo.widget.HtmlWidget,
{
	isContainer: true,
	//current top Item
	top: 0,
	//displayed rowNumber
	rowNumber: 10,
	//row height in pixel
	rowHeight: 22,

	//real table size
	knownItemSize: 30,
	//real started item
	knownItemStart: 0,
	//real ended item
	knownItemEnd: null,
	//possible max item
	rowCount: 1000,

	//position of the real scroll bar (it is hidden)
	realScrollPosition: 0,
	
	//the real container
	contentCoord: null,
	//fake scroll
	scrollFakeCoordCoord: null,

	//debug purpose loading number
	loaded: 0,
	
	templatePath: dojo.uri.dojoUri("../LazyTable.htm"),
	
	requestTimeOut: null,
	
	//last request interval
	from: null,
	//already turn element
	turnElt: 0,
	backTurnElt:0,
	
	/**
	 * Important note : 
	 *   ** this.content is node containing visible values
	 *   ** this.infoArea is node where info are displayed
	 *   ** this.scroller is the fake scroll container
	 *   ** this.scrollerContent is the fake div used to calculate the fake scroll
	 *
	 *********************************************
	 * The onReloadMethod should be implemented  *
	 *********************************************
	 */
	 
	 /**
	 * Overwrite this method to do what you want on updating.
	 * Be carrefull your new method should call postUpdate at the end of traitement
	 * In order to test this widget you can use fakeReload on this method
	 */
	onReload: function(from, to){
	},
	
	postCreate: function(args, fragment, parent){
		//initialize element range
		this.knownItemEnd = this.knownItemStart + this.knownItemSize;
		//keep initial contentCoord
		this.contentCoord = dojo.html.toCoordinateObject(this.content,false);
		this.scrollerContent.style.height = (this.rowCount * this.rowHeight) + "px";
		dojo.event.connect(this.scroller, "onscroll", this, "scrollMoved");
		
		if(this.content.addEventListener){
			// dojo.event.connect() doesn't seem to work with DOMMouseScroll
			this.content.addEventListener('DOMMouseScroll', dojo.lang.hitch(this, "_mouseWheeled"), false); // Mozilla + Firefox + Netscape
		}
		
		//fix a firefoxBug : 2 tbody are rendered at the first time : 
		var toRemove = this.content.getElementsByTagName("tbody")["0"];
		var toKeep = toRemove.getElementsByTagName("tbody")["0"];
		this.contentTable.appendChild(toKeep);
		this.contentTable.removeChild(toRemove);
	},
	
	_mouseWheeled: function(evt){
		var scrollAmount = 0;
		if(typeof evt.wheelDelta == 'number'){ // IE
			scrollAmount = evt.wheelDelta;
		}else if (typeof evt.detail == 'number'){ // Mozilla+Firefox
			scrollAmount = -evt.detail;
		}
		if (scrollAmount > 0){
			//scrollUP : 
			this.scroller.scrollTop = this.scroller.scrollTop - this.rowHeight;
		}
		if (scrollAmount < 0){
			//scrollDown
			this.scroller.scrollTop = this.scroller.scrollTop + this.rowHeight;
		}
		dojo.event.browser.stopEvent(evt);
	},
	
	/**
	 * Triggered on fake scroll srolled
	 */
	scrollMoved: function(){
		// coordonate of the scroller - used to know which range of item should be displayed
		var scrollFakeCoord = dojo.html.toCoordinateObject(this.scrollerContent,false);
		// calculate the min and max of displayed datas
		var positionDifference = (this.contentCoord.top) - (scrollFakeCoord.top + 1) ;
		var min = Math.round(positionDifference / this.rowHeight);
		var max = min + this.rowNumber - 1;  //start from 0
		this.top = min;
		this.infoArea.innerHTML = "min : " + min + "max : " + max
		
		this.placeTable();
	},


	/**
	 * This method place the real table (content) on the right position to display on the top item with this.top position
	 */
	placeTable: function(){
		var newScrollTop = (this.top - this.knownItemStart) * this.rowHeight;
		this.content.scrollTop = newScrollTop;
		if (this.top - 1 > this.knownItemEnd - this.rowNumber){		
			this.turnTable(this.top -this.knownItemEnd + this.rowNumber);
		}
		if (this.top + 1 < this.knownItemStart){
			this.backTurnTable(this.knownItemStart - this.top);
		}
		
		//reload content if necessary
		this.reload();
	},
	
	/**
	 * fake scroll adding empty line at the bottom
	 */ 
	turnTable: function(number){
		for (var i=0; i<number - this.turnElt; i++){
			var toRemove = this.contentTable.getElementsByTagName("tbody")[0].firstChild;
			var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
			this.contentTable.getElementsByTagName("tbody")[0].removeChild(toRemove);
			var tr = document.createElement('tr');
			for (var j=0; j < columns; j++){
				var td = document.createElement('td');
				td.appendChild(document.createTextNode(' '));
				tr.appendChild(td);
			}
			this.contentTable.getElementsByTagName("tbody")[0].appendChild(tr)
		}
		if (number > this.turnElt){
			this.turnElt = number;
		}
	},
	
	/**
	 * fake scroll adding emty line at the beggining
	 */
	backTurnTable: function(number){
		for (var i=0; i<number - this.backTurnElt; i++){
			var toRemove = this.contentTable.getElementsByTagName("tbody")[0].lastChild;
			var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
			this.contentTable.getElementsByTagName("tbody")[0].removeChild(toRemove);
			var tr = document.createElement('tr');
			for (var j=0; j < columns; j++){
				var td = document.createElement('td');
				td.appendChild(document.createTextNode(' '));
				tr.appendChild(td);
			}
			this.contentTable.getElementsByTagName("tbody")[0].insertBefore(tr, this.contentTable.getElementsByTagName("tbody")[0].firstChild)
		}
		if (number > this.backTurnElt){
			this.backTurnElt = number;
		}
	},

	//Make the reload
	reload: function(){
		if (this.top < this.knownItemStart || this.top + this.rowNumber > this.knownItemEnd){
			var start =  this.top + 1  - this.knownItemSize/2 ; 
			var end   = start + this.knownItemSize
			this.from = start;
			this.reloadData(start, end);
		}
	},


	reloadData: function(from, to){
		this.infoArea.innerHTML += " from " + from + " - to " + to;
		
		//make reload Here...
		if (from < 0){from = 0; }
		if (to > this.rowCount-1){to = this.rowCount}

		this.reloadItems(from, to);
	},
	
	/**
	 * Do not send to many request at same time, timeOut should avoid to send useless request
	 */
	reloadItems: function(from, to){
		if (this.requestTimeOut){
			clearTimeout(this.requestTimeOut);
		}
		this.requestTimeOut = setTimeout('dojo.widget.byId("' + this.widgetId + '").reloadAndPlace(' + from + ', ' + to + ')', 200);
	},
	
	reloadAndPlace: function(from, to ){
		this.onReload(from, to);
	},
	
	/**
	 * All stuff to do after reloading
	 */
	postUpdate: function(){
		this.knownItemStart = this.from; 
		this.knownItemEnd = this.knownItemStart + this.knownItemSize;
		
		var numberLine = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr").length
		
		if (this.top <  this.knownItemSize && numberLine < this.knownItemSize){
			//some tr are to be create on the top of the list
			var trToBeCreated = this.knownItemSize - numberLine;
			var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
		
			for (var i=0; i< trToBeCreated; i++){
				var tr = document.createElement('tr');
				for (var j=0; j < columns; j++){
					var td = document.createElement('td');
					td.appendChild(document.createTextNode(' '));
					tr.appendChild(td);
				}
				this.contentTable.getElementsByTagName("tbody")[0].insertBefore(tr, this.contentTable.getElementsByTagName("tbody")[0].firstChild)			
			}
		}
		
		if (this.top > this.rowCount - (2 * this.rowNumber) && numberLine < this.knownItemSize){
			//some tr should be happen at the end
			var trToBeCreated = this.knownItemSize - numberLine;
			var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
		
			for (var i=0; i< trToBeCreated; i++){
				var tr = document.createElement('tr');
				for (var j=0; j < columns; j++){
					var td = document.createElement('td');
					td.appendChild(document.createTextNode(' '));
					tr.appendChild(td);
				}
				this.contentTable.getElementsByTagName("tbody")[0].appendChild(tr);			
			}
		}
		this.turnElt = 0;
		this.backTurnElt = 0;
		this.placeTable();
	},

	/**
	 * this method is used to simulate a reload
	 */
	fakeReload: function(start){
		var begin = start;
		var trs = this.content.getElementsByTagName("tr");
		var begin = start;
		this.loaded ++;
		for(var i=0; i <trs.length; i++){
			trs[i].innerHTML="<td  class='cell'>" + begin++ +"</td><td class='cell'>++Loading time = " + this.loaded + "++</td>";
		}
		this.postUpdate();
	}
});
	