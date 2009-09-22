function changeStyleOnOnMouseOver(markupId, mouseOverClass) {
	var element = document.getElementById(markupId);
	element.onselectstart = function() {
		return false;
	} // ie
	element.onmousedown = function() {
		return false;
	} // mozilla
	element.setAttribute('originalClass', element.getAttribute('class'));
	element.onmouseover = function(e) {
		element.className = mouseOverClass;
	};
	element.onmouseout = function(e) {
		element.className = element.getAttribute('originalClass');
	};
}