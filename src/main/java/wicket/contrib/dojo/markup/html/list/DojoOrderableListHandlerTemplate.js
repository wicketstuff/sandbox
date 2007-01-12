function initDrag${MarkupId}(){
	var children = document.getElementById('${MarkupId}').getElementsByTagName('div');
	for(var i=0;  children.length > i ; i++){
		var drag = new dojo.dnd.HtmlDragSource(children[i], '*');
	}
}