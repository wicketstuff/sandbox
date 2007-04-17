/**
 * Creates a drag copy source from the element with the given id.
 */
function initDragCopy(markupId, dragId, copyOnce) {
	var dl = dojo.byId(markupId);
	var drag = new dojo.dnd.HtmlDragCopySource(dl, dragId, copyOnce);
}