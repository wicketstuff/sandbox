dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");

dojo.provide ("dojo.widget.LazyTable");

dojo.widget.defineWidget ("dojo.widget.LazyTable",dojo.widget.HtmlWidget,
{
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

	//position of the real scroll bar (it is hidden)
	realScrollPosition: 0,
	
	//the real container
	contentCoord: null,
	//fake scroll
	scrollFakeCoordCoord: null,

	//debug purpose loading number
	loaded: 0,
	
	templatePath: dojo.uri.dojoUri("../LazyTable.htm"),
	
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
		dojo.event.connect(this.scroller, "onscroll", this, "scrollMoved");
	},
	
	/**
	 * Triggered on fake scroll srolled
	 */
	scrollMoved: function(){
		// coordonate of the scroller - used to know which range of item should be displayed
		var scrollFakeCoord = dojo.html.toCoordinateObject(this.scroller,false);
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
		this.content.scrollTop = (this.top - this.knewItemStart) * this.rowHeight;
		
		//reload content if necessary
		this.reload();
	},

	//TODO comment, make variable and check it
	reload: function(){
		if (this.top < this.knewItemStart || this.top + this.rowNumber > this.knewItemEnd){
			var start =  this.top + 1  - this.knewItemSize/2 ; 
			var end   = start + this.knewItemSize
			this.reloadData(start, end);
		}
	},


	reloadData: function(from, to){
		this.infoArea.innerHTML += " from " + from + " - to " + to;
		this.knewItemStart = from;
		this.knewItemEnd   = this.knewItemStart + this.knewItemSize;
		//make reload Here...
		if (to < 0){ to = 0; }
		//TODO if (to > 
		this.fakeReload(from, to);
		
		//and set Table position
		//this.centerTable();
		this.placeTable();
	},


	/*centerTable: function(){
		this.realScrollPosition = ((this.knewItemSize-this.rowNumber )/2 + 1);
		this.content.scrollTop = this.rowHeight * this.realScrollPosition;
		this.placeTable();
	},*/


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
	