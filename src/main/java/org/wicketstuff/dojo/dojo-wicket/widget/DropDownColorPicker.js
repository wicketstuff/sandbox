dojo.provide("dojoWicket.widget.DropdownColorPicker");

dojo.require("dojo.widget.DropdownContainer");
dojo.require("dojoWicket.widget.ColorPicker");
dojo.require("dojo.event.*");
dojo.require("dojo.html.*");
dojo.require("dojo.string.common");

dojo.widget.defineWidget(
	"dojoWicket.widget.DropdownColorPicker",
	dojo.widget.DropdownContainer,
	{
		iconURL: dojo.uri.dojoUri("../dojo-wicket/widget/template/images/colorIcon.gif"),
		zIndex: "10",

		//String|Date
		//	form value property if =='today' will default to todays date
		value: "", 
		//String
		// 	name of the form element, used to create a hidden field by this name for form element submission.
		name: "",
		
		colorPicker : null,

		
		templatePath: dojo.uri.dojoUri("../dojo-wicket/widget/template/ColorPicker.html"),
		templateCssPath: dojo.uri.dojoUri("../dojo-wicket/widget/template/ColorPicker.css"),

		fillInTemplate: function(args, frag){
			// summary: see dojo.widget.DomWidget
			dojoWicket.widget.DropdownColorPicker.superclass.fillInTemplate.call(this, args, frag);
			//attributes to be passed on to DatePicker
			
			var dpArgs = {widgetContainerId: this.widgetId,value: this.value, 
				templateCssPath:  this.templateCssPath, templatePath: this.templatePath
				};
			
			
			//build the args for ColorPicker based on the public attributes of DropdownDatePicker
			this.colorPicker = dojo.widget.createWidget("ColorPicker", dpArgs, this.containerNode, "child");
			dojo.event.connect(this.colorPicker, "onValueChanged", this, "onSetColor");
			
			if(this.value){
				this.setColor();
			}
			this.containerNode.style.zIndex = this.zIndex;
			this.containerNode.explodeClassName = "colorBodyContainer";
			this.valueNode.name=this.name;
			
		},
		
		onIconClick: function(evt){
			dojoWicket.widget.DropdownColorPicker.superclass.onIconClick.call(this, evt);
			this.colorPicker.setColor(this.inputNode.value);
		},

		getValue: function(){
			return this.valueNode.value;
		},

		setValue: function(color){
			//summary: set the current date from RFC 3339 formatted string or a date object, synonymous with setDate
			this.onSetColor(color);
		},

		setColor: function(){
			this.colorPicker.setColor(this.value);
			this.inputNode.value = this.colorPicker.getColor();
		},
	
		onSetColor: function(){
			this.inputNode.value = this.colorPicker.getColor();
			this.onValueChanged(this.value);
		},

		onValueChanged: function(/*Date*/dateObj){
		//summary: triggered when this.value is changed
		}
		
	}
);
