function gmapRequest(componentUrl, componentId) {
	function success() {
		var srcComp = wicketGet(componentId);
    	var dstComp = wicketGet('dst'+componentId);
        dstComp.innerHTML = srcComp.innerHTML;
/*      s.append("\t\t\tsrcComp.style.display = \"none\";\n"); */
    }
    function failure() {
    	alert('ooops!');
    }
    wicketAjaxGet(componentUrl, success, failure);
}
