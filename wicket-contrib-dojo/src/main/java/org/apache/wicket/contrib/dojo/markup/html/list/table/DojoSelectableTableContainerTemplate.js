function getSelectableTableSelection(id) {
	var container = document.getElementById(id);
	var body = container.getElementsByTagName('tbody')[0];
	var rows=body.getElementsByTagName('tr')
	var selection = '';
	var index = 0;
	for(var i=0; i<rows.length; i++) {
		if(rows[i].parentNode==body) {
			if(dojo.html.getAttribute(rows[i],'selected')=='true') {
				selection += '&select=' + index;
			}
			index++;
		}
	}
	return selection;
}

/** Store last selected selectableTable */
var currentSelectableTable = null;

function handleKeyPressed(e){
	

	if (currentSelectableTable != null){
	
		var table = dojo.widget.byId(currentSelectableTable);
		
		var maxSize = 0;
		var body = table.domNode.getElementsByTagName('tbody')[0];
		var rows=body.getElementsByTagName('tr');
		for(var i=0; i<rows.length; i++) {
			if(rows[i].parentNode==body) {
				maxSize ++;
			}
		}
			
		if (e.keyCode == e.KEY_UP_ARROW){
			if (table.getFirstSelected() != null && table.getFirstSelected() != 0){
				var selected = parseInt(table.getFirstSelected()) - 1;
				table.selectIndexes([selected]);
				table.onSelect();
			}
			e.stopPropagation();
			e.preventDefault();
		}
		if (e.keyCode == e.KEY_DOWN_ARROW){
			if (table.getFirstSelected() != null && table.getFirstSelected() < (maxSize -1)){
				var selected = parseInt(table.getFirstSelected()) + 1;
				table.selectIndexes([selected]);
				table.onSelect();
			}
			e.stopPropagation();
			e.preventDefault();
		}
	}
}


dojo.require("dojo.event.*");

dojo.event.connect(window, "onkey", function(e){	
	handleKeyPressed(e);
});


function getProperties(/*Object*/ obj){
    var msg = "";
    for (prop in obj){
        msg += "property : " + prop + "\t\t value : " + obj[prop] + "\n";
    }
    alert(msg);
}