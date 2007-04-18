dojo.provide("wicketstuff.dojodnd.DojoDragCopyContainer");

dojo.require("dojo.dnd.*");
dojo.require("dojo.dnd.HtmlDragCopy");

dojo.require("wicketstuff.dojodnd.DojoDragContainer");

dojo.declare("wicketstuff.dojodnd.DojoDragCopyContainer", wicketstuff.dojodnd.DojoDragContainer, {

	/**
	 * Constructor
	 */
	initializer: function(markupId, dragId, copyOnce) {
		// superclass constructor seems to be called automatically by dojo.
		// see http://dojotoolkit.org/node/7 for inheritance details
		
		this.copyOnce = copyOnce;
	},

	/**
	 * Override the 
	 */
	createDragSource: function() {
		var dl = dojo.byId(this.markupId);
		return new dojo.dnd.HtmlDragCopySource(dl, this.dragId, this.copyOnce);
	}
});
