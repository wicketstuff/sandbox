
	function xstooltip_findPosX(obj)
	{
	  var curleft = 0;
	  if (obj.offsetParent)
	  {
		while (obj.offsetParent)
			{
				curleft += obj.offsetLeft
				obj = obj.offsetParent;
			}
		}
		else if (obj.x)
			curleft += obj.x;
		return curleft;
	}

	function xstooltip_findPosY(obj)
	{
		var curtop = 0;
		if (obj.offsetParent)
		{
			while (obj.offsetParent)
			{
				curtop += obj.offsetTop
				obj = obj.offsetParent;
			}
		}
		else if (obj.y)
			curtop += obj.y;
		return curtop;
	}

	function xstooltip_show(tooltipId, parentId, posX, posY)
	{

		it2=document.getElementById(tooltipId);
		
		if ((it2.style.top == '' || it2.style.top == 0)
			&& (it2.style.left == '' || it2.style.left == 0))
		{
			// need to fixate default size (MSIE problem)
			it2.style.width = it2.offsetWidth;
			it2.style.height = it2.offsetHeight;

			img = document.getElementById(parentId);

			//TODO: line below appears not to work! if anyone can fix problem with too-wide-tooltip, please be free to do so
			// if tooltip is too wide, shift left to be within parent
			//if (posX + it2.offsetWidth > img.offsetWidth) posX = img.offsetWidth - it2.offsetWidth;
			if (posX < 0 ) posX = 0;

			x = xstooltip_findPosX(img) + posX;
			y = xstooltip_findPosY(img) + posY;

			it2.style.top = y;
			it2.style.left = x;
		}
		it2.style.visibility = 'visible';
		//alert("y: " + xstooltip_findPosY(it) + "x: " + xstooltip_findPosX(it) + " height : " + it.offsetHeight + " width: " + it.offsetWidth + " sum " + (it.offsetHeight + it.offsetWidth));
	}

	function xstooltip_hide(id)
	{
		var it3 = document.getElementById(id);
		it3.style.visibility = 'hidden';
	}