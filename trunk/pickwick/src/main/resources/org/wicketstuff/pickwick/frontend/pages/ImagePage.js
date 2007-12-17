/**
 * Function to deal with transparency of the navigator
 */
 
var timeout = null;
var fadeIn = false;
 
function connectProgresTransparency(imgId, id){
	var nav = dojo.byId(id);
	var img = dojo.byId(imgId);
	
	var viewPort = dojo.html.getViewport();
	nav.style.top= "100px";
	nav.style.left = (viewPort.width/2 - 150) + "px";
	
	img.onmouseover = function() {
		if (!fadeIn){
			dojo.lfx.html.fade(nav,  {start:0.1,end:1.0}, 400, null, function(){fadeIn = true}).play();
		}
	}
	
	nav.onmousemove = function() {
		if (!fadeIn){
			fadeIn = true;
			dojo.lfx.html.fade(nav,  {start:0.1,end:1.0}, 400, null, function(){fadeIn = true}).play();
		}
	}
	
	img.onmouseout = function() {
		if (timeout == null){
			timeout = window.setTimeout(getFadeOut(id), 200);
		}
	}
	
	nav.onmouseover = function() {
		if (timeout != null){
			window.clearTimeout(timeout);
			timeout = null;
		}
	}
	
	/*nav.onmouseout = function() {
		if (timeout == null){
			timeout = window.setTimeout(getFadeOut(id), 200);
		}
	}*/
	
	
	
}

function getFadeOut(imgId){
	return function(){
		var img = dojo.byId(imgId);
		dojo.lfx.html.fade(img, {start:1.0,end:0.1} , 400, null, function(){fadeIn = false}).play();
		timeout=null;
	}
}
 
 
//-----------------------------------------------------------------------------



var baseImage;

function resizeImage(id, parentid) {
	if (!baseImage){
		baseImage = dojo.html.getContentBox(id);
	}
	dojo.log.debug("image="+getProperties(baseImage));
	var parent = dojo.html.getContentBox(parentid);
	dojo.log.debug("parent="+getProperties(parent));
	var newheight;
	var newwidth;

	if (baseImage.height > parent.height) {
		newheight = parent.height;
		newwidth = newheight / baseImage.height * baseImage.width;
	} else {
		newheight = baseImage.height;
		newwidth = baseImage.width;
	}
	var padding = newheight * 4 / 100;
	// Border has 2 pixels
	var margin = padding * 2 + 2;
	newheight = newheight - margin;
	newwidth = newwidth - margin;
	var el = dojo.byId(id)
	el.style.padding = padding + "px";
	dojo.log.debug("padding="+padding)
	dojo.html.setContentBox(el, {width: newwidth, height: newheight});
	var image = dojo.html.getContentBox(id);
	dojo.log.debug("newimage="+getProperties(image));
}
function getProperties(/*Object*/ obj){
    var msg = "";
    for (prop in obj){
        msg += prop + ": " + obj[prop] + "; ";
    }
    return msg;
}
