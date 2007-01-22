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