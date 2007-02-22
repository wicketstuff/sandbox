dojo.provide("dojoWicket.widget.ErrorDisplayer");

dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");
dojo.require("dojo.event.*");
dojo.require("dojo.lfx.*");


dojo.widget.defineWidget ("dojoWicket.widget.ErrorDisplayer", dojo.widget.HtmlWidget, {

	templatePath : dojo.uri.dojoUri("../dojo-wicket/widget/template/ErrorDisplayer.htm"),
	templateCssPath : dojo.uri.dojoUri("../dojo-wicket/widget/template/ErrorDisplayer.css"),
	
	isContainer: true,
	
	bgIframe: null,
	
	postCreate: function(args, fragment, parent){
		this.hide();
	},
	
	stickTo: function(/**NodeId*/ id){
		var pos = dojo.html.toCoordinateObject(id,true);
		this.setPosition(pos.left, pos.top, pos.width);
	},
	
	setPosition: function(x, y, w){
		this.domNode.style.width = w - 5 +"px";
		var messagePos = dojo.html.toCoordinateObject(this.containerNode,true);
		this.domNode.style.left = x + "px";
		this.domNode.style.top  = y - messagePos.height - 3 + "px";
	},
	
	show: function(){
		//bgframe for IE
		if(dojo.render.html.ie){
			if(!this.bgIframe){
				this.bgIframe = new dojo.html.BackgroundIframe(this.containerNode);
				this.bgIframe.setZIndex(this.containerNode);
			}
			this.bgIframe.onResized();
			this.bgIframe.show();
		}
		
		dojo.lfx.html.fadeShow(this.domNode, 300).play();
	},
	
	hide: function(){
		if(this.bgIframe){
			this.bgIframe.hide();
		}
		//dojo.lfx.html.wipeOut(this.domNode, 100).play();
		dojo.html.hide(this.domNode);
	},
	
	setMessage: function(message){
		this.containerNode.innerHTML = message;
	}
	
});