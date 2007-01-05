function immediateTextField(componentUrl, componentPath, val) {
	dojo.io.bind({
	  url: componentUrl + '&' + componentPath + '=' + val,
	  mimetype: "text/plain",
	  load: function(type, data, evt) {}
	});
}