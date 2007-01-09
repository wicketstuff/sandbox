 function maximize(id){
 	var el = dojo.byId(id);
    var viewport = dojo.html.getViewport();
 	dojo.html.setContentBox(el, {width: dojo.html.getContentBox(el).width, height: viewport.height});
 	
 }