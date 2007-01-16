function initDrag(markupId, dragId){
	var dl = byId(markupId);
	var drag = new dojo.dnd.HtmlDragSource(dl, dragId);
}