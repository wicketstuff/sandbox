 function maximize(id){
 	var el = dojo.byId(id);
    var viewport = dojo.html.getViewport();
 	dojo.html.setContentBox(el, {width: viewport.width, height: viewport.height});
 }