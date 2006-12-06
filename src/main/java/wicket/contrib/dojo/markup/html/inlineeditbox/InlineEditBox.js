dojo.hostenv.writeIncludes();

function saveHandler(newValue) {
	var wcall=wicketAjaxGet("${callbackUrl}&newTextValue=" + newValue, function() {}, function() {});
}

dojo.addOnLoad(function() {
		var editTable = dojo.widget.byId("${markupId}");
		dojo.event.connect(editTable, "onSave", "saveHandler");
	}
);