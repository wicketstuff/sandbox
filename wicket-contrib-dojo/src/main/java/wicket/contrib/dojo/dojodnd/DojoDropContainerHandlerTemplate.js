function byId(id){
	return document.getElementById(id);
}

function createUrl(e){
	var dragId = e.dragSource.domNode.id;
	var all = e.dragSource.domNode.parentNode.getElementsByTagName('div')
	var position = 0;
	while (all[position] != e.dragSource.domNode){
		position++;
	}
	return '${CallbackUrl}&dragSource=' + dragId + '&position=' + position
}



function initDrop${MarkupId}(){
	var dl = byId('${MarkupId}');
	var drop = new dojo.dnd.HtmlDropTarget(dl, ['${DropId}']);
	dojo.event.connect(drop, 'onDrop', function(e) {
		wicketAjaxGet(createUrl(e),function(){},function(){});
	});
}
dojo.event.connect(dojo, 'loaded', 'initDrop${MarkupId}');