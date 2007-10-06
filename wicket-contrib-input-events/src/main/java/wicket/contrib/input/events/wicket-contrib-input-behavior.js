function init${wicketComponentId}() {
	shortcut.add("${keys}",function() {
		${wicketComponentId}.${event}();
	},{
	'disable_in_input':${disable_in_input},
	'type':'${type}',
	'propagate':${propagate},
	'target':${target}
	
	});
}
init${wicketComponentId}();


