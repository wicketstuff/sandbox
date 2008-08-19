var DojoSharedFormJS = {
	receive: function(message) {
    dijit.byId('${form}').setValues(message.data);
	},
	
	send: function() {
	  dojox.cometd.publish("/${channel}",
	      dijit.byId('${form}').getValues());
	}
};