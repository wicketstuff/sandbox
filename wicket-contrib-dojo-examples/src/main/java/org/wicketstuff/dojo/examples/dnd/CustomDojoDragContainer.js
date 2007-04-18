dojo.provide("wicketstuff.examples.dnd.CustomDojoDragContainer");

dojo.require("dojo.dnd.*");
dojo.require("wicketstuff.dojodnd.DojoDragContainer");

dojo.declare("wicketstuff.examples.dnd.CustomDojoDragContainer", wicketstuff.dojodnd.DojoDragContainer, {

	/**
	 * Constructor
	 */
	initializer: function(markupId, dragId, dragClass) {
		this.dragSource.dragClass = dragClass;
	},
	
});
