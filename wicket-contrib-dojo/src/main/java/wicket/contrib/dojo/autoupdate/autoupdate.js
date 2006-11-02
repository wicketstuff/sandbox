	/**
 * Run update every interval ms
 */
function intervalCheck(interval, componentUrl, mimetype, nodeId, loadingId)
{
	setInterval("checkUpdate('" + componentUrl + "', '" + mimetype + "', '" + nodeId + "', '" + loadingId + "')" , interval);
}
var requesting = false;

/**
 * Make the update if request is not in the flight
 */
function checkUpdate(componentUrl,mtype, nodeId, loadingId) { 
	if(requesting == false)
	 {
	  requesting = true;
		
		dojo.io.bind({
			url: componentUrl,
			mimetype: mtype,
			load: function(type, data, evt) {
				requesting = false;
				if(data == '')
				{
					return false;
				}
				else
				{
					//define in dojo-ajax-updater
					updatePage(data);
					loading(loadingId, false);
				}
				
			}
		});
		if(loadingId != "")
 		{
 		    loading(loadingId,true);
 		}
	  }
	  else
	  {
	   setTimeout("checkUpdate('" + componentUrl + "', '" + mtype + "', '" + nodeId + "', '" + loadingId + "')", 3000);
	   //alert("nodeid: " + nodeId);
	  }
}

/**
 * Set loading visible or not
 */
function loading(loadingId, state)
{
	loadNode = document.getElementById(loadingId);
	if(state)
	{
		loadNode.style.visibility = 'visible';
	}
	else
	{
		loadNode.style.visibility = 'hidden';
	}
}