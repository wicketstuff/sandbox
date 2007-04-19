function initDragTable(markupId){
	var children = document.getElementById(markupId).getElementsByTagName('div');
	for(var i=0;  children.length > i ; i++){
		var drag = new dojo.dnd.HtmlDragSource(children[i], '*');
	}
}