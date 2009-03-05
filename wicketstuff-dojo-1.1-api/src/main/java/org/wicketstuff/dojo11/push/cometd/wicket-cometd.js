
function wicketDojoCometdEval(message) {
	evilscript = message.data['evalscript']
	if (evilscript)	eval(evilscript);
}

function wicketDojoCometdCallback(message, url) {
	if (message.data.proxy = "true"){
		var addToUrl = "" 
		var doRoundTrip = "true";
		for (prop in message.data){
			addToUrl = addToUrl + "&" + prop + "=" + message.data[prop];
		}
		
		var wcall=wicketAjaxGet(url + addToUrl, function() { }, function() { });
	}
}