function wipe(id, duration)
{
	//alert("wiping1: " + wiping + " wipedout: " + wipedout);
	if(wiping==0)
	{

		node = document.getElementById(id);
		if(wipedout==1)
		{
			wiping = 1;
			wipedout = 0;
			dojo.fx.html.wipeIn(node, duration, function(){wiping=0});

			//alert("wiping1: " + wiping + " wipedout: " + wipedout);
		} else {
			wiping = 1;
			wipedout = 1;
			dojo.fx.html.wipeOut(node, duration, function(){wiping=0});

		}
	}
}