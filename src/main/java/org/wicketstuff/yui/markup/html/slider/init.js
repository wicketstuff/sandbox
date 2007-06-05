var ${javaScriptId};
var ${javaScriptId}_changed;
function init${javaScriptId}(startValue) 
{
	${javaScriptId} = YAHOO.widget.Slider.getHorizSlider("${backGroundElementId}", "${imageElementId}", ${leftUp}, ${rightDown}, ${tick});
   	${javaScriptId}.onChange = function(offsetFromStart) 
   	{
	   	if (${javaScriptId}_changed) 	
			document.getElementById("${formElementId}").value = Math.round(offsetFromStart / ${divisor});
		${javaScriptId}_changed = true;
	}
	
	if (startValue == null) 
		document.getElementById("${formElementId}").value = "";
	else
		${javaScriptId}.setValue(Math.round(startValue * ${divisor}));
}