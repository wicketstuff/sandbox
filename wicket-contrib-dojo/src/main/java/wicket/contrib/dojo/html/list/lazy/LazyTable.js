dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");

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
	knewItemSize: 30,
	//real started item
	knewItemStart: 0,
	//real ended item
	knewItemEnd: null,
	//possible max item
	maxKnewItem: 1000,

	//position of the real scroll bar (it is hidden)
	realScrollPosition: 0,
	
	//the real container
	contentCoord: null,
	//fake scroll
	scrollFakeCoordCoord: null,

	//debug purpose loading number
	loaded: 0,
	
	requestUrl: "",
	
	templatePath: dojo.uri.dojoUri("../LazyTable.htm"),
	
	requestTimeOut: null,
	
	//last request interval
	from: null,
	
	/**
	 * Important note : 
	 *   ** this.content is node containing visible values
	 *   ** this.infoArea is node where info are displayed
	 *   ** this.scroller is the fake scroll container
	 *   ** this.scrollerContent is the fake div used to calculate the fake scroll
	 */
	
	postCreate: function(args, fragment, parent){
		//initialize element range
		this.knewItemEnd = this.knewItemStart + this.knewItemSize;
		//keep initial contentCoord
		this.contentCoord = dojo.html.toCoordinateObject(this.content,false);
		this.scrollerContent.style.height = (this.maxKnewItem * this.rowHeight) + "px";
		dojo.event.connect(this.scroller, "onscroll", this, "scrollMoved");
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
		var newScrollTop = (this.top - this.knewItemStart) * this.rowHeight;
		this.content.scrollTop = newScrollTop;
		if (this.top - 1 > this.knewItemEnd - this.rowNumber){		
			this.turnTable();
		}
		
		if (this.top + 1 < this.knewItemStart){
			this.backTurnTable();
		}
		
		//reload content if necessary
		this.reload();
	},

	//TODO comment, make variable and check it
	reload: function(){
		if (this.top < this.knewItemStart || this.top + this.rowNumber > this.knewItemEnd){
			var start =  this.top + 1  - this.knewItemSize/2 ; 
			var end   = start + this.knewItemSize
			this.from = start;
			this.reloadData(start, end);
		}
	},


	reloadData: function(from, to){
		this.infoArea.innerHTML += " from " + from + " - to " + to;
		
		//this.knewItemStart = from;
		//this.knewItemEnd   = this.knewItemStart + this.knewItemSize;
		
		//make reload Here...
		if (from < 0){from = 0; }
		
		if (to > this.maxKnewItem-1){to = this.maxKnewItem}
		//this.fakeReload(from, to);
		
		//this.clearTable();
		
		//this.turnTable();
		this.reloadItems(from, to);
	},
	
	turnTable: function(){
		var toRemove = this.contentTable.getElementsByTagName("tbody")[0].firstChild;
		var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
		this.contentTable.getElementsByTagName("tbody")[0].removeChild(toRemove);
		var tr = document.createElement('tr');
		for (var j=0; j < columns; j++){
			tr.appendChild(document.createElement('td'));
		}
		this.contentTable.getElementsByTagName("tbody")[0].appendChild(tr)
	},
	
	backTurnTable: function(){
		var toRemove = this.contentTable.getElementsByTagName("tbody")[0].lastChild;
		var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
		this.contentTable.getElementsByTagName("tbody")[0].removeChild(toRemove);
		var tr = document.createElement('tr');
		for (var j=0; j < columns; j++){
			tr.appendChild(document.createElement('td'));
		}
		this.contentTable.getElementsByTagName("tbody")[0].insertBefore(tr, this.contentTable.getElementsByTagName("tbody")[0].firstChild)
	},
	
	/**
	 * Do not send to many request at same time, timeOut should avoid to send useless request
	 */
	reloadItems: function(from, to){
		if (this.requestTimeOut){
			clearTimeout(this.requestTimeOut);
		}
		this.requestTimeOut = setTimeout('dojo.widget.byId("' + this.widgetId + '").reloadAndPlace(' + from + ', ' + to + ')', 1000);
	},
	
	reloadAndPlace: function(from, to ){
		this.onReload(from, to);
		//and set Table position
		//this.placeTable();
	},
	
	onReload: function(from, to){
	},
	
	postUpdate: function(){
		this.knewItemStart = this.from; 
		this.knewItemEnd = this.knewItemStart + this.knewItemSize;
		
		var numberLine = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr").length
		
		if (this.top <  this.knewItemSize && numberLine < this.knewItemSize){
			//some tr are to be create on the top of the list
			var trToBeCreated = this.knewItemSize - numberLine;
			var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
		
			for (var i=0; i< trToBeCreated; i++){
				var tr = document.createElement('tr');
				for (var j=0; j < columns; j++){
					tr.appendChild(document.createElement('td'));
				}
				this.contentTable.getElementsByTagName("tbody")[0].insertBefore(tr, this.contentTable.getElementsByTagName("tbody")[0].firstChild)			
			}
		}
		
		if (this.top > this.maxKnewItem - (2 * this.rowNumber) && numberLine < this.knewItemSize){
			//some tr should be happen at the end
			var trToBeCreated = this.knewItemSize - numberLine;
			var columns = this.contentTable.getElementsByTagName("tbody")[0].getElementsByTagName("tr")[0].getElementsByTagName('td').length;
		
			for (var i=0; i< trToBeCreated; i++){
				var tr = document.createElement('tr');
				for (var j=0; j < columns; j++){
					tr.appendChild(document.createElement('td'));
				}
				this.contentTable.getElementsByTagName("tbody")[0].appendChild(tr);			
			}
		}
		
		this.placeTable();
	},



//debug purpose
	fakeReload: function(start){
		var begin = start;
		var trs = this.content.getElementsByTagName("tr");
		var begin = start;
		this.loaded ++;
		for(var i=0; i <trs.length; i++){
			trs[i].innerHTML="<td  class='cell'>" + begin++ +"</td><td class='cell'>++Loading time = " + this.loaded + "++</td>";
		}
	}
});
	