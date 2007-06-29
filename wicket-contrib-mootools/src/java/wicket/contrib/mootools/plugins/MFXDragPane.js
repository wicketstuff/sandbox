	function makeDraggables(){
		
			// WebBoxes shared vars
			var draggables = $$('.MFXDragPane');
		    var webBoxMarker = new Element('div').addClass('webBoxMarker').setStyles({'display': 'none'}).injectInside($E('body'));
		    var webBoxCols = $('webBoxContainer').getChildren()[0].getChildren().getChildren()[0];
        
        // WebBoxes drag behavior for each
			draggables.each(function(el){
				
				
				var callbackUrl = el.getProperty('callbackUrl');
				//var callbackScript = el.getProperty('callbackScript');
				var componentId = el.id;
				
				var closeButton = el.getElementsBySelector('.closeButton')[0];
				
				closeButton.addEvent("click", function() { 
	  		  		//var getData = "&callback=close&id="+componentId+"&column="+columnId;
					//new Ajax(callbackUrl,{method: 'get', data: getData }).request();
					//el.setStyle('display','none');
	  		  	});
				

				// Make each webBox draggable using the handle
				el.makeDraggable({
					handle: el.getElementsBySelector('.dragBar')[0],
					'onBeforeStart': function() {
						// Introduce the marking box, change the draging box to absolute
						// The order the next 4 lines seems to be important for it to work right in Firefox
						webBoxMarker.injectAfter(el).setStyles({'display': 'block', 'height': el.getStyle('height')});
						el.setStyles({'opacity': '0.55', 'z-index': '3', 'width': el.getStyle('width'), 'position': 'absolute'});
						el.injectInside($E('body'));
						el.setStyles({'top': webBoxMarker.getCoordinates().top + "px", 'left': webBoxMarker.getCoordinates().left + "px"});
					},
					'onComplete': function() {
						// Replace the draging webBox
						el.injectBefore(webBoxMarker).setStyles({
						    'opacity': '1', 'z-index': '1', 'margin': '0 0 10px 0', 
						    'position': 'relative', 'top': '0', 'left': '0'});
						
						// Remove the marking box
						webBoxMarker.injectInside($E('body')).setStyles({'display': 'none'});
						
						// Callback to wicket
						//eval(callbackScript);
						//alert(callbackScript);
						var columnId = el.getParent().id;
						var getData = "&callback=drop&id="+componentId+"&column="+columnId;
						var response = callbackUrl+getData;
						wicketAjaxGet(response, function() { }.bind(this), function() { }.bind(this));
						//new Ajax(callbackUrl,{method: 'get', data: getData }).request();
					},
					'onDrag': function() {
					    var mouseX = this.mouse.now.x; var mouseY = this.mouse.now.y;
					    
					     // Work from first column out and top down
					    webBoxTargetCol = webBoxCols[0];
					    webBoxTargetDiv = null;
					    
						// X - Which column?
					    webBoxCols.each(function(el, i){
					        if (mouseX > el.getCoordinates().left) webBoxTargetCol = el;
					    });
					    
					    // Y - If we're half way or more past this webBox then insert it after this one
					    webBoxTargetCol.getChildren().each(function(el, i){
				            if (mouseY > (el.getCoordinates().top + Math.round(el.getCoordinates().height / 2))) webBoxTargetDiv = el;
					    });

						// Place the marker
						if (webBoxTargetDiv == null)
						{
							// On top
							if (webBoxTargetCol.getChildren()[0] != webBoxMarker) webBoxMarker.injectTop(webBoxTargetCol);
						}
						else
						{
							// Or after a webBox
							if ((webBoxTargetDiv != webBoxMarker) && (webBoxTargetDiv != webBoxMarker.getPrevious()))                     webBoxMarker.injectAfter(webBoxTargetDiv);
						}
					}
				});
			});

		}
		
		window.onload=makeDraggables;