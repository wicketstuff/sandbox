	function intervalCheck(interval, componentUrl, mimetype, nodeId)
	{
	  //alert(nodeId);
	  setInterval("checkUpdate('" + componentUrl + "', '" + mimetype + "', '" + nodeId + "')" , interval);
	  //setInterval("alert(componentUrl)");
	}
	var requesting = false;
	
	function checkUpdate(componentUrl,mtype, nodeId) { 
		if(requesting == false)
		 {
		  requesting = true;
		  node = document.getElementById(nodeId);
			//	alert(componentUrl);
			//alert("checkupate!");
			
			dojo.io.bind({
				url: componentUrl,
				mimetype: mtype,
				load: function(type, data, evt) {
					//alert("requ: " + requesting);
					requesting = false;
					if(data == '')
					{
						return false;
					}
					else
					{
						//alert(data);
						//alert(node.innerHTML);
						if(data == 'Unable to retrieve feed.')
						{
						  return false;
						} else {
	 					  node.innerHTML = data;
						  return true;
						}
					}
					
				}
			});
		  }
		  else
		  {
		   setTimeout("checkUpdate('" + componentUrl + "', '" + mtype + "', '" + nodeId + "')", 3000);
		   //alert("nodeid: " + nodeId);
		  }
	}