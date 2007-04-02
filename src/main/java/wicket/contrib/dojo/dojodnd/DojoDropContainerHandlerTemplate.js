function createUrl(e, url){
	var dragId = e.dragObject.domNode.id;

	var child = dojo.dom.getFirstChildElement(e.dragObject.domNode.parentNode);
	var position = 0;
	var found = false;
	while (child != null && !found) {
		if (child == e.dragObject.domNode) {
			found = true;
			break;
		}
		position++;
		child = dojo.dom.nextElement(child);
	}
	
	var queryString = "&dragSource=" + dragId + "&oldPosition=" + e.dragObject.domNode.getAttribute("pos");
	if (found) {
		queryString += "&position=" + position;
		dojo.debug("Item inserted at position " + position);
	} else {
		dojo.debug("Unable to determine new position");
	}
	
	dojo.debug("Query string: " + queryString);
	return url + queryString;
}

function initDrop(markupId, dropIds, url){
	var dl = dojo.byId(markupId);
	var drop = new dojo.dnd.HtmlDropTarget(dl, dropIds);
	dojo.event.connect(drop, 'onDrop', function(e) {
		wicketAjaxGet(createUrl(e, url),function(){},function(){});
	});
}