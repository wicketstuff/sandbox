// author: Victor Igumnov

// Abstraction written by me for Textile.js
var MFXTextile = new Class({
	initialize: function() {
	}
});

MFXTextile.implement({
	attach: function(textid,previewid) {
		var clazz = this;
		$(textid).addEvent('keyup', function() {
			if($(previewid).getStyle('display') == 'block') {
    			$(previewid).innerHTML = clazz.textilize($(textid).get('value'));
			}
		});
	},

	textilize: function(s) {
	var r = s;
    // quick tags first
    qtags = [['\\*', 'strong'],
             ['\\?\\?', 'cite'],
             ['\\+', 'ins'],  //fixed
             ['~', 'sub'],   
             ['\\^', 'sup'], // me
             ['@', 'code']];
    for (var i=0;i<qtags.length;i++) {
        ttag = qtags[i][0]; htag = qtags[i][1];
        re = new RegExp(ttag+'\\b(.+?)\\b'+ttag,'g');
        r = r.replace(re,'<'+htag+'>'+'$1'+'</'+htag+'>');
    }
    // underscores count as part of a word, so do them separately
    re = new RegExp('\\b_(.+?)_\\b','g');
    r = r.replace(re,'<em>$1</em>');
	
	//jeff: so do dashes
    re = new RegExp('[\s\n]-(.+?)-[\s\n]','g');
    r = r.replace(re,'<del>$1</del>');

    // links
    re = new RegExp('"\\b(.+?)\\(\\b(.+?)\\b\\)":([^\\s]+)','g');
    r = r.replace(re,'<a href="$3" title="$2">$1</a>');
    re = new RegExp('"\\b(.+?)\\b":([^\\s]+)','g');
    r = r.replace(re,'<a href="$2">$1</a>');

    // images
    re = new RegExp('!\\b(.+?)\\(\\b(.+?)\\b\\)!','g');
    r = r.replace(re,'<img src="$1" alt="$2">');
    re = new RegExp('!\\b(.+?)\\b!','g');
    r = r.replace(re,'<img src="$1">');
    
    // block level formatting
	
		// Jeff's hack to show single line breaks as they should.
		// insert breaks - but you get some....stupid ones
	    re = new RegExp('(.*)\n([^#\*\n].*)','g');
	    r = r.replace(re,'$1<br />$2');
		// remove the stupid breaks.
	    re = new RegExp('\n<br />','g');
	    r = r.replace(re,'\n');
	
    lines = r.split('\n');
    nr = '';
    for (var i=0;i<lines.length;i++) {
        line = lines[i].replace(/\s*$/,'');
        changed = 0;
        if (line.search(/^\s*(bq\.|>)\s+/) != -1) { line = line.replace(/^\s*(bq\.|>)\s+/,'\t<blockquote>')+'</blockquote>'; changed = 1; }
		
		// jeff adds h#.
        if (line.search(/^\s*h[1-6]\.\s+/) != -1) { 
	    	re = new RegExp('h([1-6])\.(.+)','g');
	    	line = line.replace(re,'<h$1>$2</h$1>');
			changed = 1; 
		}
		
		if (line.search(/^\s*\*\s+/) != -1) { line = line.replace(/^\s*\*\s+/,'\t<liu>') + '</liu>'; changed = 1; } // * for bullet list; make up an liu tag to be fixed later
        if (line.search(/^\s*#\s+/) != -1) { line = line.replace(/^\s*#\s+/,'\t<lio>') + '</lio>'; changed = 1; } // # for numeric list; make up an lio tag to be fixed later
        if (!changed && (line.replace(/\s/g,'').length > 0)) line = '<p>'+line+'</p>';
        lines[i] = line + '\n';
    }
	
    // Second pass to do lists
    inlist = 0; 
	listtype = '';
    for (var i=0;i<lines.length;i++) {
        line = lines[i];
        if (inlist && listtype == 'ul' && !line.match(/^\t<liu/)) { line = '</ul>\n' + line; inlist = 0; }
        if (inlist && listtype == 'ol' && !line.match(/^\t<lio/)) { line = '</ol>\n' + line; inlist = 0; }
        if (!inlist && line.match(/^\t<liu/)) { line = '<ul>' + line; inlist = 1; listtype = 'ul'; }
        if (!inlist && line.match(/^\t<lio/)) { line = '<ol>' + line; inlist = 1; listtype = 'ol'; }
        lines[i] = line;
    }

    r = lines.join('\n');
	// jeff added : will correctly replace <li(o|u)> AND </li(o|u)>
    r = r.replace(/li[o|u]>/g,'li>');

    return r;
	}
});

var Logger = new Class({
    initialize: function() {
	this.setDebug = false;
	this.setInfo = true;
	this.setWarn = true;
	this.setTrace = false;
	this.logBox = $('log');
	if(this.logBox == null) {
	    //alert('No DOM element named "log" found, logger will not work.');
	} else {
	    this.logBox.setStyle('font-size','12px');
	    this.logBox.setStyle('color','#a3a3a3');
	    this.logBox.setStyle('font-family','arial');
	}
    }
});
Logger.implement({
    log: function(txt) {
	if(this.logBox != null) {
	    var current = this.logBox.innerHTML;
	    this.logBox.setHTML(current + '<br/>' + txt);
	}
    },
    info: function(txt) {
	if(this.setInfo == true) {
	    this.log(txt);
	}
    },
    warn: function(txt) {
	if(this.setWarn == true) {
	    this.log(txt);
	}
    },
    trace: function(txt) {
	if(this.setTrace == true) {
	    this.log(txt);
	}
    },
    debug: function(txt) {
	if(this.setDebug == true) {
	    this.log(txt);
	}
    }
});

var MFXDialog = new Class({
	initialize: function(dialog) {
		this.initialize(dialog,null);
	},
	initialize: function(dialog,logger) {
		this.dialog = dialog;
		if(logger == null) {
			this.logger = new Logger();
		} else {
			this.logger = logger;
		}
	}
});
MFXDialog.implement({
	makeScreenDark : function () {
			var bg = new Element('div', {'style' : 
				'display: block; z-index:99; position: absolute; top:0; left:0; background-color: #000; opacity: 0.4;',
				'id':'mfxbg'});
			bg.setStyle('width',Window.getScrollWidth()); 
			bg.setStyle('height', Window.getScrollHeight());
			bg.inject(document.body);
	},
	closeDialog: function () {
    	try {
    		var bg = $('mfxbg');
    		bg.setStyle('display','none'); bg.remove();
		} catch (err) {}
		var win =  this.dialog;
		win.setStyle('display','none');
	},
	showDialog : function () {
		var win = this.dialog;
		
		var bar = win.getElementsBySelector('.MFXDialogBar')[0];
		win.makeDraggable( { handle: bar, 
		'onBeforeStart':  function() {win.setStyle('opacity',0.5); },  
		'onComplete': function() { win.setStyle('opacity',1); }});
		
		if(window.ie == true) {
			var winw = document.body.offsetWidth;
			var winh = document.body.offsetHeight;
		} else {
			var winw = window.getWidth();
			var winh = window.getHeight();
		}
		
		win.setStyle('width','300px');
		win.setStyle('left',(winw-300)/2)
		win.setStyle('top',winh/2 + (window.getScrollTop() - 300) );
		win.setStyle('margin-top',200);
		
		win.setStyle('display','block');
		
		var style = null;
		if(MooTools.version == '1.11') {
			style = new Fx.Style(win,'margin-top', { duration: 1000, transition: Fx.Transitions.backInOut });
		} else {
    		style = new Fx.Tween(win,'margin-top', { duration: 'long', transition: 'back:inOut' });
		}
		style.start(0,200);
	}
});


var BoxFactory = new Class({
    initialize: function(picture,size, callback ) {
    	this.initialize(picture,size,callback, null);
    },
    initialize: function(picture,size, callback, logger) {
	this.picture = picture;
	var frame = this.picture.getParent();
	frame.setStyle('width',this.picture.getStyle('width'));
	frame.setStyle('height',this.picture.getStyle('height'));
	this.callbackUrl = callback;
	this.boxes = new Array(size);
	this.count = 0;
	this.open = false;

	if(logger == null) {
	    this.logger = new Logger();
	} else {
	    this.logger = logger; 
	}

	this.logger.debug('init boxFactory with ' + this.count);
	this.attachShowIn(this.picture.getParent());
	this.attachShowOut(this.picture.getParent());

    }
});
BoxFactory.implement({
    showBoxes: function(fac) {
	for(var i = 0;i <= fac.count; i = i + 1) {
	    var el = fac.boxes[i];
	    try {
		el[0].setStyle('display','block');
	    } catch(err) {}
	}
	fac.logger.trace('called showBoxes');
    },
    hideBoxes: function(fac) {
	for(var i = 0 ; i <= fac.count; i = i + 1) {
	    var el = fac.boxes[i];
	    try {
    		el[0].setStyle('display','none');
	    } catch(err) { }
	}
	fac.logger.trace('called hideBoxes');
    },
    attachShowIn: function(el) {
	var fac = this;
	el.addEvent('mouseover', function() {
	    fac.showBoxes(fac);
	});
    },
    attachShowOut: function(el) {
	var fac = this;
	el.addEvent('mouseout', function() {
	   fac.hideBoxes(fac);
	});
    },
    loadBox: function(x,y,txt) {
	this.count = this.count + 1;
	this.logger.debug('upgraded counter '+this.count);

	var factory = new DOMFactory(this.logger);
	var staticBox = factory.getDisplayBox(x,y,txt);
	
	var info = factory.getDisplayText(x, y+staticBox.getStyle('height').toInt()+10, txt);
	info.setStyle('display','none');
	info.inject(this.picture.getParent());
	

	staticBox.addEvent('mouseover', function() { 
		info.setStyle('display','block');
	});
	staticBox.addEvent('mouseout', function() {
		info.setStyle('display','none');
	});
	staticBox.inject(this.picture.getParent());

	tagElement = new Array(3);
	tagElement[0] = staticBox;
	tagElement[0].setStyle('display','none');
	this.boxes[this.count] = tagElement;
	
    },
    saveAction: function(tagElement) {
	    this.count = this.count + 1;
	    this.logger.debug('upgraded counter '+this.count);

	    // potentially expensive?
	    var factory = new DOMFactory(this.logger);
	    var staticBox = factory.getDisplayBox(tagElement[0].getRelativePosition().x,tagElement[0].getRelativePosition().y);

	    this.logger.debug('setting ' + tagElement[0].getRelativePosition().x + ' and ' + tagElement[0].getRelativePosition().y);

	    var savedText= tagElement[1].getValue();
	    var xCord = tagElement[0].getRelativePosition().x;
	    var yCord = tagElement[0].getRelativePosition().y;
	    

	    var info = factory.getDisplayText(xCord,factory.getOffsetY(tagElement[0],10), savedText);
	    info.setStyle('display','none');
	    info.inject(this.picture.getParent()); 
	    staticBox.addEvent('mouseover', function() { 
		info.setStyle('display','block');
	    });
	    staticBox.addEvent('mouseout', function() {
		info.setStyle('display','none');
	    });


	    staticBox.inject(this.picture.getParent());
	    this.picture.getParent().removeChild(tagElement[0]);
	    tagElement[0] = staticBox;
	    tagElement[0].setStyle('display','none');
	    tagElement[1].setStyle('display','none');
	    tagElement[2].setStyle('display','none');
	    tagElement[3].setStyle('display','none');
	    this.logger.debug('saving as '+this.count);
	    this.open=false;
	    this.boxes[this.count] = tagElement; 
	    
	    // wicket callback...
	    

	    var getData = "&callback="+savedText+"&x="+xCord+"&y="+yCord;
		var response = this.callbackUrl+getData;
		wicketAjaxGet(response, function() { }.bind(this), function() { }.bind(this));
    },
    cancelAction: function(tagElement) {
	    this.logger.debug('canceling action ');
	    tagElement[0].getParent().removeChild(tagElement[0]);
	    tagElement[1].getParent().removeChild(tagElement[1]);
	    tagElement[2].getParent().removeChild(tagElement[2]);
	    tagElement[3].getParent().removeChild(tagElement[3]);
	    this.open=false;
    },
    showTagBox: function () {
	if(this.open == true) {
	    return;
	}

	var frame = this.picture.getParent();
	var tagElement = new Array(3);
	factory = new DOMFactory(this.logger);
	var boxes = this.boxes;
	this.open = true;

	var boxArea = factory.getDisplayBox(this.picture.getRelativePosition().x+30,this.picture.getRelativePosition().y+30);
	tagElement[0]=boxArea;
	this.attachShowIn(boxArea);
	this.attachShowOut(boxArea);
	
	boxArea.inject(frame);

	var textArea = factory.getTextArea(boxArea.getRelativePosition().x, factory.getOffsetY(boxArea,10) ); 
	tagElement[1] = textArea;
	this.attachShowIn(textArea);
	this.attachShowOut(textArea);
	textArea.inject(frame);

	var fac = this;
	var saveBtn = factory.getButton(boxArea.getRelativePosition().x, factory.getOffsetY(textArea,20) ,'Save', function() { fac.saveAction(tagElement) });
	tagElement[2]=saveBtn;
	this.attachShowIn(saveBtn);
	this.attachShowOut(saveBtn);
	saveBtn.inject(frame);

	var cancelBtn = factory.getButton(boxArea.getRelativePosition().x+70, factory.getOffsetY(textArea,20) ,'Cancel', function() { fac.cancelAction(tagElement) });
	tagElement[3]=cancelBtn;
	this.attachShowIn(cancelBtn);
	this.attachShowOut(cancelBtn);
	cancelBtn.inject(frame);


	    new Drag.Move(boxArea, {container: frame, onDrag: function () {
	    
	        textArea.setStyle('left',boxArea.getRelativePosition().x);
    		textArea.setStyle('top', factory.getOffsetY(boxArea,10));
    
    		saveBtn.setStyle('left',boxArea.getRelativePosition().x);
    		saveBtn.setStyle('top',factory.getOffsetY(textArea,20) );
    
    		cancelBtn.setStyle('left',boxArea.getRelativePosition().x+70);
    		cancelBtn.setStyle('top', factory.getOffsetY(textArea,20) );
	    

    	    }, onStart: function () { }, onComplete: function() {  }
    	}); 
    }
});


var DOMFactory = new Class({
    initialize: function() {
	this.initialize(null);
    },
    initialize: function(logger) {
	if(logger != null) {
	    this.logger=logger;
	} else {
	    this.logger = new Logger();
	}
    }
});
DOMFactory.implement({
    getDisplayText : function(x,y,txt) {
	this.logger.debug('Returning DisplayText');

	var displayText = new Element('div');
	displayText.setStyle('background','#FFF6BF');
	displayText.setStyle('float','left');
	displayText.setStyle('position','absolute');
	displayText.setStyle('padding','5px');
	displayText.setStyle('font-family','arial');
	displayText.setStyle('font-size','12px');

	displayText.setStyle('left',x);
	displayText.setStyle('top',y);

	displayText.setHTML(txt);

	return displayText;
    },
    getDisplayBox: function (x,y) {
	return this.getDisplayBox(x,y,'none');
    },
    getDisplayBox : function (x,y,clazzName) {
	this.logger.debug('Creating new Display Box.');
	var boxArea = new Element('div');
	var boxArea2 = new Element('div');

	boxArea.setStyle('border','1px solid');
	boxArea.setStyle('height','50px');
	boxArea.setStyle('width','50px');
	boxArea.setStyle('position','absolute');
	boxArea.setStyle('cursor','pointer');
	boxArea.setAttribute('class',clazzName);
	boxArea.setStyle('background','url(inv.gif)');
	//boxArea.setStyle('background','#aaa');
	//boxArea.setStyle('opacity','0.4');

	boxArea2.setStyle('border','1px #fff solid');
	boxArea2.setStyle('height','50px');
	boxArea2.setStyle('width','50px');
	boxArea2.setStyle('position','absolute');
	boxArea2.setStyle('cursor','pointer');
	boxArea2.setAttribute('class',clazzName+'inner');

	boxArea2.inject(boxArea);
	
	boxArea.setStyle('left',x);
	boxArea.setStyle('top',y);

	return boxArea;
    },
    getTextArea: function(x,y) {
	this.logger.debug('Creating new TextArea');
	var textArea = new Element('textarea');
	textArea.setStyle('border','1px #a3a3a3 solid');
	textArea.setStyle('position','absolute');
	textArea.setAttribute('id','thetext');
	textArea.setStyle('background','#FFF6BF');
	textArea.setStyle('padding','5px');
	textArea.setStyle('width','180px');
	textArea.setStyle('height','60px');
	textArea.setStyle('font-size','12px');
	textArea.setStyle('font-family','arial');
	textArea.innerHTML="Add your note here.";
	textArea.setStyle('left',x);
	textArea.setStyle('top',y);
	return textArea;
    },
    getButton: function(x,y,txt,cl) {
	this.logger.debug('Creating new input button');
	var btn = new Element('input');
	btn.setStyle('position','absolute');
	btn.setStyle('border','1px #a3a3a3 solid');
	btn.setStyle('width','60px');
	btn.setAttribute('type','button');
	btn.setAttribute('value',txt);
	btn.setStyle('left',x);
	btn.setStyle('top',y);
	btn.addEvent('click', cl);
	return btn;
    },
    getOffsetY: function(el,y)  {
	var offset = el.getStyle('height').toInt()+y;
	return el.getRelativePosition().y + offset;
    },
    getOffsetX: function(el,x) {
	var offset = el.getStyle('width').toInt()+x;
	return el.getRelativePosition().x + offset;
    }
});

// NAWTE textile buttons...

function textile_bold() {
	sel = this.getSelection(); this.replaceSelection('*' + sel + '*'); 
}

function textile_italic() {
	sel = this.getSelection(); this.replaceSelection('_' + sel + '_'); 
}

function textile_image() {
	var selection = this.getSelection();  
	var response = prompt('Enter Image URL','');  
	if(response == null)  {
		return;  
	}
	
	if(response != null) {
		if(!response.match('^http\:\/\/')) {
			response = 'http://'+response;
		}
	}
	
	this.replaceSelection('!'+ (response == '' ? 'http://link_url/' : response) + '('+(selection == '' ? 'image description' : selection)+')!');
}

function textile_link() {
	var selection = this.getSelection();  
	var response = prompt('Enter Link URL','');  
	if(response == null)  {
		return;  
	}
	
	if(response != null) {
		if(!response.match('^http\:\/\/')) {
			response = 'http://'+response;
		}
		if(!response.match('\/$')) {
			response = response + '/';
		}
	}
	
	this.replaceSelection('"'+selection+'":'+ (response == '' ? 'http://link_url/' : response) );
}

function textile_quote() {
	sel = this.getSelection(); this.replaceSelection('bq. ' + sel); 
}

function textile_heading() {
	sel = this.getSelection(); this.replaceSelection('h3. ' + sel); 
}

function textile_trademark() {
	this.replaceSelection('(TM)');
}

function textile_copyright() {
	this.replaceSelection('(C)');
}

function textile_registered() {
	this.replaceSelection('(R)');
}


// NAWTE 0.1 by Jean-Nicolas Jolivet (http://www.silverscripting.com)
// Not quite a WYSIWYM editor, for Mootools 1.1+
// Credits to Ryan Johnson for the original idea at http://livepipe.net/projects/control_textarea/

Element.extend({
	
	getSelectedText: function() {
		if(window.ie) return document.selection.createRange().text;
		return this.getValue().substring(this.selectionStart, this.selectionEnd);
	},
	
	replaceSelectedText: function(newtext) {
		var scroll_top = this.scrollTop;
		
		if(window.ie) {
			this.focus();
			var range = document.selection.createRange();
			range.text = newtext;
			range.select();
			this.range.select();
		}
		else {
			originalStart = this.selectionStart;
			originalEnd = this.selectionEnd;
			this.value = this.value.substring(0, originalStart) + newtext + this.value.substring(originalEnd);
			this.setSelectionRange(originalStart + newtext.length, originalStart + newtext.length);
		}
		
		this.focus();
		this.scrollTop = scroll_top;
	}
});

var nawte = new Class({
	initialize: function(element, list) {
		this.el = $(element);
		if(! $defined(list) || list == "") {
			list = new Element('li');
			list.injectBefore(this.el);
			this.list = list;
		}
		else {
			this.list = $(list);
		}
	},
	
	getSelection: function() {
		return this.el.getSelectedText();
	},
	
	wrapSelection: function(wrapper) {
		this.el.replaceSelectedText(wrapper + this.el.getSelectedText() + wrapper);
	},
	
	insertBefore: function(insertText) {
		this.el.replaceSelectedText(insertText + this.el.getSelectedText());
	},
	
	insertAfter: function(insertText) {
		this.el.replaceSelectedText(this.el.getSelectedText() + insertText);
	},
	
	replaceSelection: function(newText) {
		this.el.replaceSelectedText(newText);
	},
	
	processEachLine: function(callback) {
		var lines = this.el.getSelectedText().split("\n");
		var newlines = [];
		lines.each(function(line) {
			if (line != "") 
				newlines.push(callback.attempt(line, this));
			else
				newlines.push("");
		}.bind(this));
		
		this.el.replaceSelectedText(newlines.join("\n"));
	},
		
	addFunction: function(name, callback, args) {
		var item = new Element('li');
		var itemlink = new Element('a', {
			'events': {
				'click': function(e){
					new Event(e).stop();
					callback.attempt(null, this);
				}.bind(this)
			},
			'href': '#'
		});
		itemlink.setHTML('<span>' + name + '</span>');
		itemlink.setProperties(args || {});
		itemlink.injectInside(item);
		item.injectInside(this.list);
	}
});
