function createUrl(e, url){
	var dragId = e.dragSource.domNode.id;
	var all = e.dragSource.domNode.parentNode.getElementsByTagName('div')
	var position = 0;
	while (all[position] != e.dragSource.domNode){
		position++;
	}
	return url + '&dragSource=' + dragId + '&oldPosition=' + e.dragSource.domNode.getAttribute('pos') + '&position=' + position
}

function initDrop(markupId, dropId, url){
	var dl = dojo.byId(markupId);
	var drop = new dojo.dnd.HtmlDropTarget(dl, [dropId]);
	dojo.event.connect(drop, 'onDrop', function(e) {
		wicketAjaxGet(createUrl(e, url),function(){},function(){});
	});
}
