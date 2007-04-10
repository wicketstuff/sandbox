//FIXME : DROPTARGET[ manager.dropTargets.length -1] should be replace by getting the right node

function lookupScrolling(scrollId /**dropId*/){
	var position = dojo.html.toCoordinateObject(scrollId);
	var currentScroll = dojo.byId(scrollId).scrollTop;
	dojo.byId(scrollId).scrollTop = 0;
	var timeout = null;
	var manager = dojo.dnd.dragManager;
	
	dojo.event.connectOnce(manager.dropTargets[ manager.dropTargets.length -1], 'onDragMove', function(e) {
		
		if (position.top + currentScroll + 60 > e.pageY ){
			dojo.byId(scrollId).scrollTop = dojo.byId(scrollId).scrollTop - 4;
			timeout = computePosition(timeout);
		}
		
		if (position.top + position.height + currentScroll - 60 < e.pageY ){
			dojo.byId(scrollId).scrollTop = dojo.byId(scrollId).scrollTop + 4;
			timeout = computePosition(timeout);
		}
	});
}

function computePosition(timeout, currentScroll){
	
	if(timeout != null){
		window.clearTimeout(timeout);
	}
	
	timeout = window.setTimeout(function(){
		var manager = dojo.dnd.dragManager;	
			var current = manager.dropTargets[ manager.dropTargets.length -1];
			current.childBoxes = [];
			for (var i = 0, child; i < current.domNode.childNodes.length; i++) {
				child = current.domNode.childNodes[i];
				if (child.nodeType != dojo.html.ELEMENT_NODE) { continue; }
				var pos = dojo.html.getAbsolutePosition(child, true);
				var inner = dojo.html.getBorderBox(child);
				current.childBoxes.push({top: pos.y, bottom: pos.y+inner.height,
					left: pos.x, right: pos.x+inner.width, height: inner.height, 
					width: inner.width, node: child});
			}
	}, 200);
	
	return timeout;
}