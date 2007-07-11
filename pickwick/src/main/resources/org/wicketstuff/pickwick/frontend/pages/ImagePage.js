function resizeImage(id, parentid) {
	image = dojo.html.getContentBox(id);
	dojo.log.debug("image="+getProperties(image));
	parent = dojo.html.getContentBox(parentid);
	dojo.log.debug("parent="+getProperties(parent));

	if (image.height > parent.height) {
		newheight = parent.height;
		newwidth = newheight / image.height * image.width;
	} else {
		newheight = image.height;
		newwidth = image.width;
	}
	padding = newheight * 4 / 100;
	// Border has 2 pixels
	margin = padding * 2 + 2;
	newheight = newheight - margin;
	newwidth = newwidth - margin;
	var el = dojo.byId(id)
	el.style.padding = padding + "px";
	dojo.log.debug("padding="+padding)
	dojo.html.setContentBox(el, {width: newwidth, height: newheight});
	image = dojo.html.getContentBox(id);
	dojo.log.debug("newimage="+getProperties(image));
}
function getProperties(/*Object*/ obj){
    var msg = "";
    for (prop in obj){
        msg += prop + ": " + obj[prop] + "; ";
    }
    return msg;
}
