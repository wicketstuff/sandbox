dojo.provide("dojoWicket.widget.ErrorBubble");

dojo.require("dojoWicket.widget.Bubble");

dojo.widget.defineWidget ("dojoWicket.widget.ErrorBubble", dojoWicket.widget.Bubble, {

	templateCssPath : dojo.uri.dojoUri("../dojo-wicket/widget/template/ErrorBubble.css"),
	imageBubble : dojo.uri.dojoUri("../dojo-wicket/widget/template/images/ErrorBubble.png")
	
});