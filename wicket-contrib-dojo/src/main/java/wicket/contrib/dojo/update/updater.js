/**
 * Make the update if request is not in the flight
 */
function update(componentUrl, mtype, loadingId) { 
	var loadingNode = document.getElementById(loadingId);
	
	
	loadingState(loadingNode, true);
	
	dojo.io.bind({
		url: componentUrl,
		mimetype: mtype,
		load: function(type, data, evt) {
			if(data == '')
			{
				return false;
			}
			else
			{
				//define in dojo-ajax-updater
				updatePage(data);
				
				if (loadingNode){
					loadingState(loadingNode, false);
				}
				
				return true;
			}
			
		}
	});
}

/**
 * Set loading visible or not
 */
function loadingState(loadNode, state)
{
	if (loadNode != null){
		if(state)
		{
			loadNode.style.visibility = 'visible';
		}
		else
		{
			loadNode.style.visibility = 'hidden';
		}
	}
}