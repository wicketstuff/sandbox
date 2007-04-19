dojo.provide("wicketstuff.dojodnd.DojoDropContainer");

dojo.declare("wicketstuff.dojodnd.DojoDropContainer", null, {

	/**
	 * Constructor.
	 */
	initializer: function(markupId, dropIds, url) {
		this.markupId = markupId;
		this.dropIds = dropIds;
		this.url = url;
	},

	/**
	 * Initialize the drop container (should be called after construction of a new
	 * instance of this class, to be sure all constructors are called).
	 */
	initializeDropContainer: function() {
		var dl = dojo.byId(this.markupId);
		this.dropTarget = new dojo.dnd.HtmlDropTarget(dl, this.dropIds);
		dojo.event.connect(this.dropTarget, 'onDrop', this, "handleDrop");

		// should we create a drop indicator by default?
		//this.createDropIndicator();

		dojo.debug("Initialized drop contianer for " + this.markupId + ", dropIds: " + this.dropIds + ", ");
		dojo.debug("Callback url base: " + this.url);
	},
	
	/**
	 * Create a custom drop indicator.
	 */
	createDropIndicator: function() {
		var dropIndicatorClass = this.getDropIndicatorClass();
		this.dropTarget.createDropIndicator = function() {
			this.dropIndicator = null;
			this.dropIndicator = document.createElement("div");
			this.dropIndicator.className = dropIndicatorClass;
			with (this.dropIndicator.style) {
				position = "absolute";
				zIndex = 1;
				width = dojo.html.getBorderBox(this.domNode).width + "px";
				left = dojo.html.getAbsolutePosition(this.domNode).x + "px";
			}
		};
	},
	
	/**
	 * The default createDropIndicator class uses this function to set the
	 * class of the drop target. Override this to specify your own class.
	 */
	getDropIndicatorClass: function() {
		return "drop-indicator";
	},
	
	/**
	 * Handles the onDrop event.
	 */
	handleDrop: function(e) {
		wicketAjaxGet(this.createUrl(e), 
			function() {},
			function() {});
	},
	
	/**
	 * Creates an URL with the drop position and the drag source ID encoded.
	 */
	createUrl: function(e) {
		var dragId = e.dragObject.domNode.id;
	
		var child = dojo.dom.getFirstChildElement(e.dragObject.domNode.parentNode);
		var position = 0;
		var found = false;
		while (child != null && !found) {
			if (child == e.dragObject.domNode) {
				found = true;
				break;
			}
			position++;
			child = dojo.dom.nextElement(child);
		}
		
		var queryString = "&dragSource=" + dragId + "&oldPosition=" + e.dragObject.domNode.getAttribute("pos");
		if (found) {
			queryString += "&position=" + position;
			dojo.debug("Item inserted at position " + position);
		} else {
			dojo.debug("Unable to determine new position");
		}
		
		dojo.debug("Query string: " + queryString);
		return this.url + queryString;
	}

});
