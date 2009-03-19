
function wicketDojoCometdEval(message) {
	console.debug("eval", message)
	evilscript = message.data['evalscript']
	if (evilscript)	eval(evilscript);
}

function wicketDojoCometdCallback(message, url) {
	console.debug("callback", message, url)
	if (message.data.proxy == "true"){
		var addToUrl = "" 
		for (prop in message.data){
			addToUrl = addToUrl + "&" + prop + "=" + message.data[prop];
		}
		
		var wcall=wicketAjaxGet(url + addToUrl, function() { }, function() { });
	}
}