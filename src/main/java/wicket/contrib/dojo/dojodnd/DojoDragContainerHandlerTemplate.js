function initDrag(markupId, DragId){
	var dl = byId(markupId);
	var drag = new dojo.dnd.HtmlDragSource(dl, dragId);
}