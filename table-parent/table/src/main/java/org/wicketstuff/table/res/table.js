function changeStyleOnOnMouseOver(markupId, mouseOverClass) {
	var element = document.getElementById(markupId);
	element.onselectstart = function() {
		return window.event.srcElement.tagName.toLowerCase() == "input";
	} // ie
	element.onmousedown = function(e) {
		return e != null && e.target.tagName.toLowerCase() == "input";
	} // mozilla
	element.originalClass = element.className;
	element.onmouseover = function(e) {
		element.className = mouseOverClass;
	};
	element.onmouseout = function(e) {
		element.className = element.originalClass;
	};
}