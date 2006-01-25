	function setIFrame(ttip, iframe)
	{
		iframe.style.top = ttip.style.top;
		iframe.style.left = ttip.style.left;
		iframe.style.width = ttip.offsetWidth;
		iframe.style.height = ttip.offsetHeight;
		iframe.style.zIndex = ttip.style.zIndex-1;
		iframe.style.visibility = ttip.style.visibility;
		//alert("top: " + ttip.style.top + "left: " + ttip.style.left + "width: " + ttip.offsetWidth + "height: " + ttip.offsetHeight + "zindex: " + ttip.style.zIndex + "display: " + ttip.style.visibility);
		//alert("top: " + iframe.style.top + "left: " + iframe.style.left + "width: " + iframe.style.width + "height: " + iframe.style.height + "zindex: " + iframe.style.zIndex + "display: " + iframe.style.visibility);
	}


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

	function xstooltip_show(tooltipId, parentId, posX, posY, iFrameId)
	{

		it2=document.getElementById(tooltipId);
		iframe = document.getElementById(iFrameId);
		
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
			//x = dojo.style.getAbsoluteX(img) + posX;
			//y = dojo.style.getAbsoluteY(img) + posY;
			//alert("x = absx + posx : " + x  + "="+ xstooltip_findPosX(img) + "+"  + posX);
			//alert("y = absy + posy : " + y  + "="+ xstooltip_findPosY(img) + "+"  + posY);

			
			it2.style.top = y;
			it2.style.left = x;
			//alert("2:top: " + it2.style.top + " left: " + it2.style.left + " viss: " + it2.style.visibility);
			
		}
		it2.style.visibility = 'visible';
		setIFrame(it2, iframe);

		
	}

	function xstooltip_hide(id, iFrameId)
	{
		var it3 = document.getElementById(id);
		it3.style.visibility = 'hidden';
		setIFrame(it3, iframe);
		
	}