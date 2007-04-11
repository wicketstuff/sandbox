cometd.init({}, "/cometd");

var receiver = {
	receive: function(message){
		dojo.widget.byId('${form}').setValues(message.data);
	},
	
	send: function(){
		return dojo.widget.byId('${form}').getValues();
	}

};

function sendSharedForm(){
	cometd.publish("${channel}", receiver.send());
}
    	
cometd.subscribe("${channel}", false, receiver, "receive");