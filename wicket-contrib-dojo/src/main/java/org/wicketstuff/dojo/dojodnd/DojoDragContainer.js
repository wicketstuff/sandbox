dojo.provide("wicketstuff.dojodnd.DojoDragContainer");

dojo.require("dojo.dnd.*");

dojo.declare("wicketstuff.dojodnd.DojoDragContainer", null, {

	/**
	 * Constructor
	 */
	initializer: function(markupId, dragId) {
		this.markupId = markupId;
		this.dragId = dragId;
	},

	/**
	 * Initialize the drag container.
	 */
	initializeDragContainer: function() {
		this.dragSource = this.createDragSource();
		dojo.debug("Initialized drag contianer for " + this.markupId + ", dragId: " + this.dragId);
	},
	
	/**
	 * Create the drag source. Override this if you need to create a specific drag
	 * source (e.g. drag-copy or drag-move).
	 */
	createDragSource: function() {
		var dl = dojo.byId(this.markupId);
		return new dojo.dnd.HtmlDragSource(dl, this.dragId);
	}
	
});
