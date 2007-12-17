dojo.provide("wicketstuff.examples.dnd.CustomDojoDragContainer");

dojo.require("dojo.dnd.*");
dojo.require("wicketstuff.dojodnd.DojoDragContainer");

dojo.declare("wicketstuff.examples.dnd.CustomDojoDragContainer", wicketstuff.dojodnd.DojoDragContainer, {

	/**
	 * Constructor
	 */
	initializer: function(markupId, dragId, dragClass) {
		this.dragClass = dragClass;
	},

	/**
	 * Override the initialize method.
	 */	
	initializeDragContainer: function() {
		// call the superclass' initializeDragContainer
		this.constructor.superclass.initializeDragContainer.apply(this);
		
		// set the drag class
		this.dragSource.dragClass = this.dragClass;
		dojo.debug("Custom drag source initialized, using dragClass " + this.dragClass);
	}
});
