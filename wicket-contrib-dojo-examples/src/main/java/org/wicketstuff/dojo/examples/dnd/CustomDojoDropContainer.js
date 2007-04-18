dojo.provide("wicketstuff.examples.dnd.CustomDojoDropContainer");

dojo.require("wicketstuff.dojodnd.DojoDropContainer");

dojo.declare("wicketstuff.examples.dnd.CustomDojoDropContainer", wicketstuff.dojodnd.DojoDropContainer, {

	/**
	 * Override the initialization.
	 */
	initializeDropContainer: function() {
		// call the superclass' initializeDropContainer function
		this.constructor.superclass.initializeDropContainer.apply(this);
		
		this.createDropIndicator();
	},
	
	/**
	 * A custom class.
	 */
	getDropIndicatorClass: function() {
		return "custom-drop-indicator";
	},

	/**
	 * Handles the onDrop event.
	 */
	handleDrop: function(e) {
		dojo.debug("Another example howto override a function...");
		
		wicketAjaxGet(this.createUrl(e), 
			function() {},
			function() {});
	}
	
});
