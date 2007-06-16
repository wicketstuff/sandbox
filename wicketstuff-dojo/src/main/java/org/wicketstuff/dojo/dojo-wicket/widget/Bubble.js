dojo.provide("dojoWicket.widget.Bubble");

dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");
dojo.require("dojo.event.*");
dojo.require("dojo.lfx.*");


dojo.widget.defineWidget ("dojoWicket.widget.Bubble", dojo.widget.HtmlWidget, {

	templatePath : dojo.uri.dojoUri("../dojo-wicket/widget/template/Bubble.htm"),
	
	imageBubble : dojo.uri.dojoUri("../dojo-wicket/widget/template/images/bubble.png"),
	imageTransparent : dojo.uri.dojoUri("../dojo-wicket/widget/template/images/transparent.gif"),
	imageClose : dojo.uri.dojoUri("../dojo-wicket/widget/template/images/close.gif"),
	imageMaximize : dojo.uri.dojoUri("../dojo-wicket/widget/template/images/maximize.gif"),
	imageRestore : dojo.uri.dojoUri("../dojo-wicket/widget/template/images/restore.gif"),
	
	isContainer: true,
	
	postCreate: function(args, fragment, parent){
		//this.stickTo("stick");
		this.hide();
	},
	
	stickTo: function(/**NodeId*/ id){
		pos = dojo.html.toCoordinateObject(id,true);
		this.setPosition(pos.left + pos.width/2, pos.top);
	},
	
	setPosition: function(x, y){
		this.mainPositionner.style.left = (x - 85) + "px";
		this.mainPositionner.style.top = (y - 155) + "px";
	},
	
	show: function(){
		//dojo.lfx.fadeIn(this.widgetId, 200).play();
		this.domNode.style.display="block";
		/*setTimeout(function(this){
			dojo.event.connect(document, "onclick", arguments[0], "hide");
		}, 100);*/
	},
	
	hide: function(){
		//dojo.lfx.fadeOut(this.widgetId, 200).play();
		this.domNode.style.display="none";
	},
	
	setMessage: function(message){
		this.containerNode.innerHTML = message;
	}
	
});