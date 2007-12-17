dojo.provide("dojoWicket.widget.ColorPicker");


dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");

dojo.widget.defineWidget ("dojoWicket.widget.ColorPicker", 
		dojo.widget.HtmlWidget, {

	//is it nn6
	nn6: document.getElementById&&!document.all,
	
	//slider pos
	sliderX: null,
	sliderY: null,
	
	//color selector
	dotX: null,
	dotY: null,
	
	//are we dragging.
	isdrag : false,
	
	//HSV composant
	H:0,
	S:0,
	V:0,
	
	//rgb and hex color
	rgb:{},
	hex:{},
	hsv:{},
	
	value: "FFFFFF",

	templatePath: dojo.uri.dojoUri("../dojo-wicket/widget/template/ColorPicker.html"),
	templateCssPath: dojo.uri.dojoUri("../dojo-wicket/widget/template/ColorPicker.css"),
	
	fillInTemplate: function(args, frag) {
		dojo.event.connect(this.domNode, "onmousedown", this, "mouseDown");
		dojo.event.connect(document, "onmouseup", this, "mouseUp");
		this.arrow.style.top = 0;
		this.colorSelector.style.left = 0;
		this.colorSelector.style.top = 0;
		this.setColor(this.value);
	},
	
	setColor: function(/**String rgb*/rgb){
		//setAllValues
		this.rgb = this.HexToRGB(rgb);
		this.hex = this.RGBToHex(this.rgb['red'],this.rgb['green'],this.rgb['blue']);
		this.RGBToHSV(this.rgb['red'],this.rgb['green'],this.rgb['blue']);
		
		//display colors
		this.hexaColor.value = this.hex;
		this.displayColor.style.backgroundColor = "#"+this.hex;
		this._updateBackgroundSelector()
		
		//update positions
		this.setSliderPosition();
		this.setSelectorPositon();
	},
	
	getColor: function(){
		return this.hexaColor.value;
	},
	
	//TODO remove and use Dojo
	checkIt: function(string) {
		var place = navigator.userAgent.toLowerCase().indexOf(string) + 1;
		return place;
	},
	
	_inSelectionArea: function(){
		return 0 <= this.dotX && this.dotX <= 150 && 0 <= this.dotY && this.dotY <= 150;
	},
	
	_inSliderArea: function(){
		return 0 <= this.sliderX && this.sliderX <= 35 && 0 <= this.sliderY && this.sliderY <= 150;
	},
	
	_updateColor: function(){
		this.rgb = this.HSVToRGB(this.H,this.S,this.V);
		this.hex = this.RGBToHex(this.rgb['red'],this.rgb['green'],this.rgb['blue']);
			
		this.hexaColor.value = this.hex;
		this.displayColor.style.backgroundColor = "#"+this.hex;
		
		this.onValueChanged();
	},
	
	/**
	 * Event value changed
	 */
	 onValueChanged: function(){
	 },
	
	_adjustPositionning: function(mouseX, mouseY){
		var pos = dojo.html.toCoordinateObject(this.domNode,true);
		
		this.dotX = mouseX - 24 - pos.left;
		this.dotY = mouseY - 53 - pos.top;
		this.sliderX = mouseX - 185 - pos.left;
		this.sliderY = mouseY - 52 - pos.top;
	},
	
	_updateSliderAndDot: function(){
		this.sliderY = parseInt(this.arrow.style.top);
		this.dotX = parseInt(this.colorSelector.style.left);
		this.dotY = parseInt(this.colorSelector.style.top);

		if (this.dotX == '') {
			this.dotX = 120;
		}
		
		if (this.dotY == '') {
			this.dotY = 80;
		}
		//compute again HSV
		this.H = Math.round(this.YToH(this.sliderY));
		this.S = this.XToS(this.dotX);
		this.V = this.YToV(this.dotY);
	},
	
	_manageMouseOnSelection: function(){
		this.colorSelector.style.left = this.dotX + "px";
		this.colorSelector.style.top = this.dotY + "px";

		this._updateSliderAndDot();
		this._updateColor();
		
		dojo.event.connect(document, "onmousemove" , this, "dragSV");
	
	},
	
	_manageMouseOnSlider: function(){
		this.arrow.style.top = this.dotY + "px";
		
		this._updateSliderAndDot();
		this._updateColor();
		this._updateBackgroundSelector();
		
		dojo.event.connect(document, "onmousemove" , this, "dragH");
	},
	
	/**
	 * When the mouse is down on the widget
	 */
	mouseDown: function(/** event*/ e) {
		document.body.style.cursor = 'pointer';

		//Adjust for positioning
		var mouseX = this.nn6 ? e.clientX : event.clientX;
		var mouseY = this.nn6 ? e.clientY : event.clientY;
		this._adjustPositionning(mouseX, mouseY);
		
		//Check to see if mouse is in the selection area
		if (this._inSelectionArea()) {
			this.isdrag = true;
			this._manageMouseOnSelection();
			
		//Or in the slider area
		} else if (this._inSliderArea()) {
			isdrag = true;
			this._manageMouseOnSlider();
		} 
	},
	
	/**
	 * When the mouse is up on the document
	 * We need to remove all listener
	 */
	mouseUp: function(e) {
		isdrag=false;
		document.body.style.cursor = 'auto';
		dojo.event.disconnect(document, "onmousemove" , this, "dragH");
		dojo.event.disconnect(document, "onmousemove" , this, "dragSV");
	},	
	
	
	/** Drag color selector and Hue selector*/
	dragSV: function(e) {
		if (this.isdrag) {
			var mouseX = this.nn6 ? e.clientX : event.clientX;
			var mouseY = this.nn6 ? e.clientY : event.clientY;
	
			var pos = dojo.html.toCoordinateObject(this.domNode,true);
		
			var xlimit = mouseX - 24 - pos.left;
			var ylimit = mouseY - 52 - pos.top;
			
			if (xlimit<= 1)
				xlimit = 1;
			else if (xlimit >= 150)
				xlimit = 150;
			if (ylimit<= 1)
				ylimit = 1;
			else if (ylimit >= 150)
				ylimit = 150;
			this.colorSelector.style.left = xlimit + "px";
			this.colorSelector.style.top = ylimit + "px";			
			
			this._updateSliderAndDot();
			this._updateColor();
		}
	},
	dragH: function(e) {
		if (this.isdrag) {
			var mouseY = this.nn6 ? e.clientY : event.clientY;
			var pos = dojo.html.toCoordinateObject(this.domNode,true);
			this.sliderY = mouseY - 52 - pos.top;
			
			if (this.sliderY < 0)
				this.sliderY = 0;
			if (this.sliderY > 150)
				this.sliderY = 150;	
			this.arrow.style.top = this.sliderY + "px";
		
			this._updateBackgroundSelector();
			this._updateSliderAndDot();
			this._updateColor();
		}
	},
	
	/**
	 * Update background of the selector
	 */
	_updateBackgroundSelector: function(){
		this.rgb = this.HSVToRGB(this.H,'100','100');
		this.hex = this.RGBToHex (this.rgb['red'],this.rgb['green'],this.rgb['blue']);
		this.selectorBg.style.backgroundColor = "#"+this.hex;
	},
	
	/** Graphic to color convertion **/
	XToS: function(dotX) {
		return (dotX/1.5);
	},
	YToV: function(dotY) {
		return (100-(dotY/1.5));
	},
	SToX: function(S) {
		return S*1.5;
	},
	VToY: function (V) {
		return (-V+100)*1.5;
	},
	HToY: function(H) {
		return (H/360)*150;
	},
	YToH: function(sliderY) {
		return (sliderY/150)*360;
	},
	
	/** Color to graphic convertion */
	setSliderPosition: function(){
		this.sliderY = Math.round((this.H / 360)*150);
		this.arrow.style.top = this.sliderY + "px";
	},
	
	setSelectorPositon: function(){
		this.dotX = this.S * 1.5;
		this.dotY = (100 - this.V) * 1.5;
		
		this.colorSelector.style.left = this.dotX + "px";
		this.colorSelector.style.top = this.dotY + "px";
	},
	
	/** COLOR CONVERTIONS **/
	HSVToRGB: function(H, S, V) {
		H = H/360;
		S = S/100;
		V = V/100;
		var R,G,B,F,P,Q,T;
		
		if (S <= 0) {
			V = Math.round(V*255);
			this.rgb['red'] = V;
			this.rgb['green'] = V;
			this.rgb['blue'] = V;
			return this.rgb;
		} else {
			if (H >= 1.0) {
				H = 0;
			}
			H = 6 * H;
			F = H - Math.floor(H);
			P = Math.round(255 * V * (1.0 - S));
			Q = Math.round(255 * V * (1.0 - (S * F)));
			T = Math.round(255 * V * (1.0 - (S * (1.0 - F))));
			V = Math.round(255 * V);
			switch (Math.floor(H)) {
				 case 0:
					R = V;
					G = T;
					B = P;
					break;
				 case 1:
					R = Q;
					G = V;
					B = P;
					break;
				 case 2:
					R = P;
					G = V;
					B = T;
					break;
				 case 3:
					R = P;
					G = Q;
					B = V;
					break;
				 case 4:
					R = T;
					G = P;
					B = V;
					break;
				 case 5:
					R = V;
					G = P;
					B = Q;
					break;
			}
			this.rgb['red'] = R;
			this.rgb['green'] = G;
			this.rgb['blue'] = B;
			return this.rgb;
		}
	},
	RGBToHex: function(R,G,B) {
		return (this.toHex(R)+this.toHex(G)+this.toHex(B));
	},
	toHex: function(N) {
		if (N==null) 
			return "00";
		N=parseInt(N); 
		if (N==0 || isNaN(N)) 
			return "00";
		N=Math.max(0,N); 
		N=Math.min(N,255); 
		N=Math.round(N);
		return "0123456789ABCDEF".charAt((N-N%16)/16) + "0123456789ABCDEF".charAt(N%16);
	},
	HexToRGB: function(H) { 
		var hexR = H.substr(0,2);
		this.rgb['red'] = parseInt((hexR).substring(0,2),16);
		var hexG = H.substr(2,2);
		this.rgb['green'] = parseInt((hexG).substring(0,2),16);
		var hexB = H.substr(4,2);
		this.rgb['blue'] = parseInt((hexB).substring(0,2),16);
		return this.rgb;
	},
	RGBToHSV: function(R,G,B) {
		var max = Math.max(R,G,B);
		var min = Math.min(R,G,B);
		var delta = max-min;
		this.V = Math.round((max / 255) * 100);
		if(max != 0){
			this.S = Math.round(delta/max * 100);
		}else{
			this.S = 0;
		}
		
		if(this.S == 0){
			this.H = 0;
		}else{
			if(R == max){
				this.H = (G - B)/delta;
			}else if(G == max){
				this.H = 2 + (B - R)/delta;
			}else if(B == max){
				this.H = 4 + (R - G)/delta;
			}
			this.H = Math.round(this.H * 60);
			if(this.H > 360){
				this.H = 360;
			}
			if(this.H < 0){
				this.H += 360;
			}
		}
		this.hsv['hue'] = this.H;
		this.hsv['sat'] = this.S;
		this.hsv['val'] = this.V;
		return this.hsv;
	}	

});