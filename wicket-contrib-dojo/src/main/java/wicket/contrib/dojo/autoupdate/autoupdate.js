	function intervalCheck(interval, componentUrl, mimetype, nodeId, loadingId)
	{
	  //alert(nodeId);
	  setInterval("checkUpdate('" + componentUrl + "', '" + mimetype + "', '" + nodeId + "', '" + loadingId + "')" , interval);
	  //setInterval("alert(componentUrl)");
	}
	var requesting = false;
	
	function checkUpdate(componentUrl,mtype, nodeId, loadingId) { 
		//alert("hier!");
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

						if(data == 'UPDATE_ERROR')
						{
						  return false;
						} else {
	 					  if(loadingId != "")
	 					  {
	 					    loading(loadingId,false);
	 					  }
	 					  node.innerHTML = data;
	 					  //dojo.fx.html.fadeOut(node, 500, function(n) {
	 					  //node.innerHTML = data;
	 					  // dojo.fx.html.fadeIn(n, 500);
	 					  //});
						  return true;
						  //alert(data);
						}
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
	
		function loading(loadingId, state)
	{
	  loadNode = document.getElementById(loadingId);
	  //alert(loadNode.style.visibility);
	  if(state)
	  {
	    loadNode.style.visibility = 'visible';
	    
	  }
	  else
	  {
	    loadNode.style.visibility = 'hidden';
	    
	  }
	}