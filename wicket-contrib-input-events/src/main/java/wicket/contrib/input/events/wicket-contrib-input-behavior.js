function init() {
	shortcut.add("${keys}",function() {
		${wicketComponentId}.${event}();
	});
}
window.onload=init;
