var priorCenterItem = 1;
var lastRan = -1;

/**
 * Since carousel.addItem uses an HTML string to create the interface
 * for each carousel item, this method formats the HTML for an LI.
 **/

var fmtItem = function(imgUrl, url, title, i) {
  	var innerHTML = 
  		'<img id="carousel-image-' + i + '" src="' + 
  		imgUrl +
		'" width="' +
		75 +
		'" height="' +
		75+
		'"/><a id="carousel-anchor-' + i + '" href="' + 
  		url + 
  		'">' + 
  		title + '<\/a>';
  
	return innerHTML;
	
};

/**
 * Custom inital load handler. Called when the carousel loads the initial
 * set of data items. Specified to the carousel as the configuration
 * parameter: loadInitHandler
 **/
var loadInitialItems = function(type, args) {
	var start = args[0];
	var last = args[1]; 

	load(this, start, last);
	spotlight(this);	
	preview(this);
};

/**
 * Custom load next handler. Called when the carousel loads the next
 * set of data items. Specified to the carousel as the configuration
 * parameter: loadNextHandler
 **/
var loadNextItems = function(type, args) {
	// get the last middle item and turn off spotlight
	var li = this.getItem(priorCenterItem);
	
	var start = args[0];
	var last = args[1]; 
	var alreadyCached = args[2];

	if(!alreadyCached) {
		load(this, start, last);
	}
	spotlight(this);
	preview(this);
};

/**
 * Custom load previous handler. Called when the carousel loads the previous
 * set of data items. Specified to the carousel as the configuration
 * parameter: loadPrevHandler
 **/

var loadPrevItems = function(type, args) {
	// get the last middle item and turn off spotlight
	var li = this.getItem(priorCenterItem);

	var start = args[0];
	var last = args[1]; 
	var alreadyCached = args[2];
	
	if(!alreadyCached) {
		load(this, start, last);
	}
	spotlight(this);
	preview(this);
};

var load = function(carousel, start, last) {
	
	for(var i=start;i<=last;i++) {
		carousel.addItem(i, fmtItem(imageList[i], urlList[i], "Number " + i, i), 'non-spotlight');
		
		// Image click will scroll to the corresponding carousel item.
		YAHOO.util.Event.addListener('carousel-image-'+i, 'click', function(evt) {
			this.carousel.scrollTo(this.index-2);
		}, {carousel:carousel,index:i}, true);
/*
		// Example of an alternate way to add an item (passing an element instead of html string)
		var p = document.createElement("P");
		var t = document.createTextNode("Item"+i);
		p.appendChild(t);
		carousel.addItem(i, p );
*/
		
	}
};


/**
 * Custom button state handler for enabling/disabling button state. 
 * Called when the carousel has determined that the previous button
 * state should be changed.
 * Specified to the carousel as the configuration
 * parameter: prevButtonStateHandler
 **/
var handlePrevButtonState = function(type, args) {

	var enabling = args[0];
	var leftImage = args[1];
	if(enabling) {
		leftImage.src = "images/left-enabled.gif";	
	} else {
		leftImage.src = "images/left-disabled.gif";
	}
	
};

/**
 * Custom button state handler for enabling/disabling button state. 
 * Called when the carousel has determined that the next button
 * state should be changed.
 * Specified to the carousel as the configuration
 * parameter: nextButtonStateHandler
 **/
var handleNextButtonState = function(type, args) {

	var enabling = args[0];
	var rightImage = args[1];
	if(enabling) {
		rightImage.src = "images/right-enabled.gif";	
	} else {
		rightImage.src = "images/right-disabled.gif";
	}
	
};

function completeHandler(type, args) {
	// instead of doing the preview in the loadNext and loadPrev you can
	// wait for the animation scroll to stop before showing the preview
	//preview(this);
}

var current = 0;

function preview(carousel) {
	var firstVisible = carousel.getProperty("firstVisible");
	var middle = firstVisible + 2;
	
	var anchor = YAHOO.util.Dom.get('carousel-anchor-' + middle);
	
	var toDelete = YAHOO.util.Dom.get('div_' + (current-1));
	var previous = YAHOO.util.Dom.get('div_' + current);
	var next = document.createElement('div');
	next.id="div_" + (++current);
	next.style.zIndex=1;
	next.style.position="absolute";
	next.innerHTML = '<img src="' + anchor.href + '" height="500px"/>'
	YAHOO.util.Dom.get('preview').appendChild(next);
	var previewRegion = YAHOO.util.Dom.getRegion('preview');
	var previewWidth = previewRegion.right - previewRegion.left;
	var nextRegion = YAHOO.util.Dom.getRegion(next);
	var nextWidth = nextRegion.right - nextRegion.left; 
	next.style.left = (previewWidth - nextWidth)/2 + "px";
	
	if(previous != undefined){
		previous.style.zIndex=2;
		var animSlide = new YAHOO.util.Anim(previous, { opacity: { from: 1, to: 0 } }, 1, YAHOO.util.Easing.easeOut);
        animSlide.animate();
	}
	if(toDelete != undefined){
		YAHOO.util.Dom.get('preview').removeChild(toDelete);
	}
}

function spotlight(carousel) {
	var firstVisible = carousel.getProperty("firstVisible");
	var start = firstVisible;
	var revealAmount = carousel.getProperty("revealAmount");
	var size = carousel.getProperty("size");
	
	if(revealAmount && firstVisible > 1) {
		start = firstVisible - 1;
	}
	var lastVisible = firstVisible + carousel.getProperty("numVisible") - 1;
	var end = lastVisible;
	if(revealAmount && lastVisible < size) {
		end = lastVisible + 1;
	}
	
	var middle = firstVisible + 2;
	
	for(var i=start; i<=end; i++) {
		var li = carousel.getItem(i);
		
		if(i == middle) {
			YAHOO.util.Dom.replaceClass(li, 'non-spotlight', 'spotlight');
			priorCenterItem = i;
		} else {
			YAHOO.util.Dom.replaceClass(li, 'spotlight', 'non-spotlight');
		}
	}
}

/**
 * You must create the carousel after the page is loaded since it is
 * dependent on an HTML element (in this case 'dhtml-carousel'.) See the
 * HTML code below.
 **/

var carousel; // for ease of debugging; globals generally not a good idea
var pageLoad = function() 
{
	YAHOO.util.Dom.get('stop-button').style.display="none";
	var preload = YAHOO.util.Dom.get('preload');
	for(var i=0;i<=urlList.length;i++) {
		//preload
		preload.innerHTML += '<img src="' + urlList[i] + '"/>'
	}
	carousel = new YAHOO.extension.Carousel("dhtml-carousel", 
		{
			numVisible:         5,
			animationSpeed:     0.6,
			/*animationMethod:    YAHOO.util.Easing.backBoth,*/
			scrollInc:          1,
			navMargin:          40,
			scrollBeforeAmount: 4,
			firstVisible:       -1,
			size:               diapoSize,
			scrollAfterAmount:  4,
			prevElement:        "prev-arrow",
			nextElement:        "next-arrow",
			loadInitHandler:    loadInitialItems,
			loadNextHandler:    loadNextItems,
			loadPrevHandler:    loadPrevItems,
			prevButtonStateHandler:   handlePrevButtonState,
			nextButtonStateHandler:   handleNextButtonState,
			animationCompleteHandler: completeHandler
		}
	);
	
	//carousel.loadNextHandlerEvt.subscribe(beforeNext, carousel);
};

YAHOO.util.Event.addListener(window, 'load', pageLoad);

/**
 * Illustrates stop autoplay
 */
var stopAutoPlay = function(e) {
	YAHOO.util.Dom.get('stop-button').style.display="none";
	YAHOO.util.Dom.get('start-button').style.display="block";
	carousel.stopAutoPlay();
};

/**
 * Illustrates start autoplay
 */
var startAutoPlay = function(e) {
	YAHOO.util.Dom.get('stop-button').style.display="block";
	YAHOO.util.Dom.get('start-button').style.display="none";
	carousel.startAutoPlay(2000);
};

YAHOO.util.Event.addListener("stop-button", 'click', stopAutoPlay);
YAHOO.util.Event.addListener("start-button", 'click', startAutoPlay);
