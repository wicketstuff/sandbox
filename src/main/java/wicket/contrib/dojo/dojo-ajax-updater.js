/**
 * Get a dojo-Ajax response (to XmlHttpRequest) to update the
 * Html document
 * @param response string representing the response
 */
 
function dojoUpdate(componentUrl, onStart, onSuccess, onFailure, loadingId){
	var updater = new dojoUpdater(componentUrl, onStart, onSuccess, onFailure, loadingId);
	updater.send();
}

function dojoAutoUpdate(interval, componentUrl, onStart, onSuccess, onFailure, loadingId){
	setInterval("new dojoUpdater('" + componentUrl + "'," + onStart+ "," + onSuccess + "," + onFailure + "," + loadingId + ").send()",interval);
}
 
/**
 * Make the update if request is not in the flight
 */
function dojoUpdater(componentUrl, onStart, onSuccess, onFailure, loadingId){

	var loadingNode = document.getElementById(loadingId);

	
	function updatePage(/** String */ response) {
		var responseDoc = dojo.dom.createDocumentFromText(response, "text/xml");
		var ajaxRequest = dojo.dom.firstElement(responseDoc,"ajax-response");
		
		updateComponents(ajaxRequest);
		updateScripts(ajaxRequest);					  
	};
	
	function updateComponents(/** node */ ajaxRequest) {
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
	};
	
	function updateScripts(/** node */ ajaxRequest) {
		var evaluate = dojo.dom.firstElement(ajaxRequest, "evaluate");
		while (evaluate != null){
			//get content...
		 	currentContent = dojo.dom.innerXML(evaluate.firstChild);
		 	//...and remove CDATA
		 	currentContent = currentContent.substring(9, currentContent.length - 3);
			currentContent = currentContent.replace("^", "");
			dj_eval(currentContent);
			evaluate = dojo.dom.nextElement(evaluate, "evaluate");
		}
	};
	
	
	function show() {
		if (loadingNode){
			loadingNode.style.visibility = 'visible';
		}
	};
	
	function hide() {
		if (loadingNode){
			loadingNode.style.visibility = 'hidden';
		}
	};
	
	this.send = function() {
		show();
		onStart();
		
		dojo.io.bind({
			url: componentUrl,
			mimetype: "text/plain",
			load : function(type, data, evt) {
				if(data == '')
				{
					onFailure();
					return false;
				}
				else
				{
					updatePage(data);
					hide();
					onSuccess();
					
					return true;
				}
			}
		});
	};

}
