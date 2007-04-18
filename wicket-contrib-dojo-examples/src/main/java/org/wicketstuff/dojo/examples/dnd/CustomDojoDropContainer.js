dojo.provide("wicketstuff.examples.dnd.CustomDojoDropContainer");

dojo.require("wicketstuff.dojodnd.DojoDropContainer");

dojo.declare("wicketstuff.examples.dnd.CustomDojoDropContainer", wicketstuff.dojodnd.DojoDropContainer, {

	/**
	 * Constructor.
	 */
	initializer: function() {
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
		wicketAjaxGet(this.createUrl(e), 
			function() {},
			function() {});
	}
	
});
