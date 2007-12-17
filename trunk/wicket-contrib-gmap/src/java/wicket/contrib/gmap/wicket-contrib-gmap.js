
function addClickListener(map, callbackUrl) {
	GEvent.addListener(map, "click", function (marker, point) {
		var url = callbackUrl + '&x=' + point.x + '&y=' + point.y;
		var wcall = wicketAjaxGet(url, function() { }.bind(this), function() { }.bind(this));
	});
}
