function initDrag(markupId, dragId){
	var dl = dojo.byId(markupId);
	var drag = new dojo.dnd.HtmlDragSource(dl, dragId);
}
