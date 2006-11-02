/**
 * Make the update if request is not in the flight
 */
function update(componentUrl, mtype) { 
	dojo.io.bind({
		url: componentUrl,
		mimetype: mtype,
		load: function(type, data, evt) {
			if(data == '')
			{
				return false;
			}
			else
			{
				//define in dojo-ajax-updater
				updatePage(data);
				return true;
			}
			
		}
	});
}