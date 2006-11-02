/**
 * Get a dojo-Ajax response (to XmlHttpRequest) to update the
 * Html document
 * @param response string representing the response
 */
function updatePage(/** String */ response){

	var responseDoc = dojo.dom.createDocumentFromText(response, "text/xml");
	var ajaxRequest = dojo.dom.firstElement(responseDoc,"ajax-response");
	
	updateComponents(ajaxRequest);
	updateScripts(ajaxRequest);					  
}


function updateComponents(/** node */ ajaxRequest){
	 //get the first component in ajaxRequest
	 var component = dojo.dom.firstElement(ajaxRequest, "component");

	 var currentId;
	 var currentContent;
	 var node;
	 
	 while (component != null){
	 	currentId = component.getAttribute("id");
	 	//get content...
	 	currentContent = dojo.dom.innerXML(component.firstChild);
	 	//...and remove CDATA
	 	currentContent = currentContent.substring(9, currentContent.length - 3);
	 
	 	//find the node to replace in document 
	 	node = document.getElementById(currentId);
	 	
	 	//replace it
	 	var range = node.ownerDocument.createRange();
	  	range.selectNode(node);
		var fragment = range.createContextualFragment(currentContent);
		node.parentNode.replaceChild(fragment, node);
		 
		//get next component
		component = dojo.dom.nextElement(component, "component");
	 }
}

function updateScripts(/** node */ ajaxRequest){
	//TODO
}