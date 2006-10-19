var dd${id}_${javaScriptId} = new YAHOO.example.DDSwap${isIntersect}(${classId} , ${groupId});

function fn_toShift_dd${id}_${javaScriptId}(){
	alert("dd${id}_${javaScriptId}");
}

//how to determine the target?

YAHOO.util.Event.addListener("dd${id}_${javaScriptId}", "mouseover", fn_${javaScriptId});
YAHOO.util.Event.addListener("dd${id}_${javaScriptId}", "mousedown", fn_toShift_dd${id}_${javaScriptId});