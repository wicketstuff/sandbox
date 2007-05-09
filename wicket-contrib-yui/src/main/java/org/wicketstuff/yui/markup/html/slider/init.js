var ${javaScriptId};
function init${javaScriptId}() {
   ${javaScriptId} = YAHOO.widget.Slider.getHorizSlider("${backGroundElementId}", "${imageElementId}", ${leftUp}, ${rightDown}, ${tick});
   ${javaScriptId}.onChange = function(offsetFromStart) {
		document.getElementById("${formElementId}").value = offsetFromStart;
	}
}
