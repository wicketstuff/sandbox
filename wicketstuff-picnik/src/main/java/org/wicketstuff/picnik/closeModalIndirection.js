function WicketStuffPicnikCloseModal() {
	var oc = document.getElementById('${closeModalLinkId}').getAttribute('onclick');
	eval("var f = function(){" + oc + "};f();");
}