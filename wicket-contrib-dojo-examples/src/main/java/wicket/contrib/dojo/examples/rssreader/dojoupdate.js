
	if(requesting == 0)
	{ 
		var requesting = false;
	}
	
	function render_update(componentUrl,mtype, nodeId, loadingId) { 
		if(requesting == false)
		 {
		  requesting = true;
		  node = document.getElementById(nodeId);
			//	alert(componentUrl);
			//alert("checkupate!");
			//node.innerHTML = "<center>Loading description......</center>";
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
						if(data == 'UPDATE_ERROR')
						{
						  return false;
						} else {
	 					    node.innerHTML = data;
			    			loading(loadingId,false);	   
						  return true;
						}
					}
					
				}
			});
			loading(loadingId,true);
			//node.innerHTML = "<div STYLE='text-align: center; width: 100%;'><b>Loading.....</b></div>" + node.innerHTML;
		  }
		  else
		  {
		   setTimeout("checkUpdate('" + componentUrl + "', '" + mtype + "', '" + nodeId + "','"+ loadingId + "')", 3000);
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