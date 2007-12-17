 function maximize(id, vertmargin, horizmargin, vertratio, horizratio){
 	var el = dojo.byId(id);
	var viewport = dojo.html.getViewport();
 	dojo.html.setContentBox(el, {width: viewport.width * horizratio - horizmargin, height: viewport.height * vertratio - vertmargin});
 }
