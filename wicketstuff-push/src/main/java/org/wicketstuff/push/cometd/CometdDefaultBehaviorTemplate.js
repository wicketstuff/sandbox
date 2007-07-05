function onEventFor${markupId}(message){
	if (message.data.proxy = "true"){
		var addToUrl = "" 
		for (prop in message.data){
			//add each in url
			addToUrl = addToUrl + "&" + prop + "=" + message.data[prop];
		}
		//it is a default event 
		var wcall=wicketAjaxGet('${url}' + addToUrl, function() { }, function() { });
	}
}