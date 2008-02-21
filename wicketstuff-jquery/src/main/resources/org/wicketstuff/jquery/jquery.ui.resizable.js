(function($) {
	
	$.fn.resizable = function(options) {
		return this.each(function() {
			var args = Array.prototype.slice.call(arguments, 1);
			
			if (typeof options == "string") {
				var resize = $.data(this, "ui-resizable");
				resize[options].apply(resize, args);

			} else if(!$(this).is(".ui-resizable"))
				new $.ui.resizable(this, options);
				
		});
	};
	
	$.ui.resizable = function(element, options) {
		//Initialize needed constants
		var self = this;
		
		this.element = $(element);
		
		$.data(element, "ui-resizable", this);
		this.element.addClass("ui-resizable");
		
		//Prepare the passed options
		this.options = $.extend({
			preventDefault: true,
			transparent: false,
			minWidth: 10,
			minHeight: 10,
			aspectRatio: false,
			disableSelection: true,
			preserveCursor: true,
			autohide: false
		}, options);
		
		this.options._aspectRatio = !!(this.options.aspectRatio);
		
		$(element).bind("setData.resizable", function(event, key, value){
			self.options[key] = value;
		}).bind("getData.resizable", function(event, key){
			return self.options[key];
		});
	
		var o = this.options;
	
		//Default Theme
		var aBorder = '1px solid #DEDEDE';
	
		o.defaultTheme = {
			'ui-resizable': { display: 'block' },
			'ui-resizable-handle': { position: 'absolute', background: '#F5F5F5' },
			'ui-resizable-n': { cursor: 'n-resize', height: '4px', left: '0px', right: '0px', borderTop: aBorder },
			'ui-resizable-s': { cursor: 's-resize', height: '4px', left: '0px', right: '0px', borderBottom: aBorder },
			'ui-resizable-e': { cursor: 'e-resize', width: '4px', top: '0px', bottom: '0px', borderRight: aBorder },
			'ui-resizable-w': { cursor: 'w-resize', width: '4px', top: '0px', bottom: '0px', borderLeft: aBorder },
			'ui-resizable-se': { cursor: 'se-resize', width: '4px', height: '4px', borderRight: aBorder, borderBottom: aBorder },
			'ui-resizable-sw': { cursor: 'sw-resize', width: '4px', height: '4px', borderBottom: aBorder, borderLeft: aBorder },
			'ui-resizable-ne': { cursor: 'ne-resize', width: '4px', height: '4px', borderRight: aBorder, borderTop: aBorder },
			'ui-resizable-nw': { cursor: 'nw-resize', width: '4px', height: '4px', borderLeft: aBorder, borderTop: aBorder }
		};
	
		//Position the node
		if(!o.proxy && (this.element.css('position') == 'static' || this.element.css('position') == ''))
			this.element.css('position', 'relative');
	
		o._nodeName = element.nodeName;
	
		//Wrap the element if it cannot hold child nodes
		if(o._nodeName.match(/textarea|input|select|button|img/i)) {
	
			//Create a wrapper element and set the wrapper to the new current internal element
			this.element.wrap('<div class="ui-wrapper"	style="overflow: hidden; position: relative; width: '+this.element.outerWidth()+'px; height: '+this.element.outerHeight()+';"></div>');
			var oel = this.element; element = element.parentNode; this.element = $(element);
	
			//Move margins to the wrapper
			this.element.css({ marginLeft: oel.css("marginLeft"), marginTop: oel.css("marginTop"),
				marginRight: oel.css("marginRight"), marginBottom: oel.css("marginBottom")
			});
	
			oel.css({ marginLeft: 0, marginTop: 0, marginRight: 0, marginBottom: 0});
	
			//Prevent Safari textarea resize
			if ($.browser.safari && o.preventDefault) oel.css('resize', 'none');
	
			o.proportionallyResize = oel.css({ position: 'static', zoom: 1, display: 'block' });
	
			// fix handlers offset
			this._proportionallyResize();
		}
	
		if(!o.handles) o.handles = !$('.ui-resizable-handle', element).length ? "e,s,se" : { n: '.ui-resizable-n', e: '.ui-resizable-e', s: '.ui-resizable-s', w: '.ui-resizable-w', se: '.ui-resizable-se', sw: '.ui-resizable-sw', ne: '.ui-resizable-ne', nw: '.ui-resizable-nw' };
		if(o.handles.constructor == String) {
	
			if(o.handles == 'all')
				o.handles = 'n,e,s,w,se,sw,ne,nw';
	
			var n = o.handles.split(","); o.handles = {};
	
			o.zIndex = o.zIndex || 1000;
	
			var insertions = {
				handle: 'overflow:hidden; position:absolute;',
				n: 'top: 0pt; width:100%;',
				e: 'right: 0pt; height:100%;',
				s: 'bottom: 0pt; width:100%;',
				w: 'left: 0pt; height:100%;',
				se: 'bottom: 0pt; right: 0px;',
				sw: 'bottom: 0pt; left: 0px;',
				ne: 'top: 0pt; right: 0px;',
				nw: 'top: 0pt; left: 0px;'
			};
	
			for(var i = 0; i < n.length; i++) {
				var d = jQuery.trim(n[i]), t = o.defaultTheme, hname = 'ui-resizable-'+d;
	
				var rcss = $.extend(t[hname], t['ui-resizable-handle']), 
						axis = $(["<div class=\"ui-resizable-handle ",hname,"\" style=\"",insertions[d], insertions.handle,"\"></div>"].join("")).css(/sw|se|ne|nw/.test(d) ? { zIndex: ++o.zIndex } : {});
	
				o.handles[d] = '.ui-resizable-'+d;
	
				this.element.append(
					//Theme detection, if not loaded, load o.defaultTheme
					axis.css( !$.ui.css(hname) ? rcss : {} )
				);
			}
		}
	
		this._renderAxis = function(target) {
			target = target || this.element;
	
			for(var i in o.handles) {
				if(o.handles[i].constructor == String) 
					o.handles[i] = $(o.handles[i], element).show();
	
				if (o.transparent)
					o.handles[i].css({opacity:0});
	
				//Apply pad to wrapper element, needed to fix axis position (textarea, inputs, scrolls)
				if (this.element.is('.ui-wrapper') && 
					o._nodeName.match(/textarea|input|select|button/i)) {
	
					var axis = $(o.handles[i], element), padWrapper = 0;
	
					//Checking the correct pad and border
					padWrapper = /sw|ne|nw|se|n|s/.test(i) ? axis.outerHeight() : axis.outerWidth();
	
					//The padding type i have to apply...
					var padPos = [ 'padding', 
						/ne|nw|n/.test(i) ? 'Top' :
						/se|sw|s/.test(i) ? 'Bottom' : 
						/^e$/.test(i) ? 'Right' : 'Left' ].join(""); 
	
					if (!o.transparent)
						target.css(padPos, padWrapper);
	
					this._proportionallyResize();
				}
				if(!$(o.handles[i]).length) continue;
			}
		};
			
		this._renderAxis(this.element);
		o._handles = $('.ui-resizable-handle', self.element);
		
		if (o.disableSelection)
			o._handles.each(function(i, e) { $.ui.disableSelection(e); });
		
		//Matching axis name
		o._handles.mouseover(function() {
			if (!o.resizing) {
				if (this.className) 
					var axis = this.className.match(/ui-resizable-(se|sw|ne|nw|n|e|s|w)/i);
				//Axis, default = se
				o.axis = axis && axis[1] ? axis[1] : 'se';
			}
		});
				
		//If we want to auto hide the elements
		if (o.autohide) {
			o._handles.hide();
			$(self.element).addClass("ui-resizable-autohide").hover(function() {
				$(this).removeClass("ui-resizable-autohide");
				o._handles.show();
			},
			function(){
				if (!o.resizing) {
					$(this).addClass("ui-resizable-autohide");
					o._handles.hide();
				}
			});
		}
	
		//Initialize mouse events for interaction
		this.element.mouseInteraction({
			executor: this,
			delay: 0,
			distance: 0,
			dragPrevention: ['input','textarea','button','select','option'],
			start: this.start,
			stop: this.stop,
			drag: this.drag,
			condition: function(e) {
				if(this.disabled) return false;
				for(var i in this.options.handles) {
					if($(this.options.handles[i])[0] == e.target) return true;
				}
				return false;
			}
		});
	};

	$.extend($.ui.resizable.prototype, {
		plugins: {},
		ui: function() {
			return {
				instance: this,
				axis: this.options.axis,
				options: this.options
			};
		},
		_renderProxy: function() {
			var el = this.element, o = this.options;
			this.offset = el.offset();
	
			if(o.proxy) {
				this.helper = this.helper || $('<div style="overflow:hidden;"></div>');
	
				// fix ie6 offset
				var ie6 = $.browser.msie && $.browser.version  < 7, ie6offset = (ie6 ? 3 : 0),
				pxyoffset = ( ie6 ? 2 : -1 );
	
				this.helper.addClass(o.proxy).css({
					width: el.outerWidth() + pxyoffset,
					height: el.outerHeight() + pxyoffset,
					position: 'absolute',
					left: this.offset.left - ie6offset +'px',
					top: this.offset.top - ie6offset +'px',
					zIndex: ++o.zIndex
				});
	
				this.helper.appendTo("body");
	
				if (o.disableSelection)
					$.ui.disableSelection(this.helper.get(0));
	
			} else {
				this.helper = el; 
			}
		},
		propagate: function(n,e) {
			$.ui.plugin.call(this, n, [e, this.ui()]);
			return this.element.triggerHandler(n == "resize" ? n : ["resize", n].join(""), [e, this.ui()], this.options[n]);
		},
		destroy: function() {
			this.element
			.removeClass("ui-resizable ui-resizable-disabled")
			.removeMouseInteraction()
			.removeData("ui-resizable")
			.unbind(".resizable");
		},
		enable: function() {
			this.element.removeClass("ui-resizable-disabled");
			this.disabled = false;
		},
		disable: function() {
			this.element.addClass("ui-resizable-disabled");
			this.disabled = true;
		},
		start: function(e) {
			var o = this.options, iniPos = this.element.position(), el = this.element;
			o.resizing = true;
			o.documentScroll = { top: $(document).scrollTop(), left: $(document).scrollLeft() };
	
			// buf fix #1749
			if (el.is('.ui-draggable') || (/absolute/).test(el.css('position'))) {
				// sOffset decides if document scrollOffset will be added to the top/left of the resizable element
				var sOffset = $.browser.msie && !o.containment && (/absolute/).test(el.css('position')) && !(/relative/).test(el.parent().css('position'));
				var dscrollt = sOffset ? o.documentScroll.top : 0, dscrolll = sOffset ? o.documentScroll.left : 0;
				el.css({ position: 'absolute', top: (iniPos.top + dscrollt), left: (iniPos.left + dscrolll) });
			}
	
			//Opera fixing relative position
			if (/relative/.test(el.css('position')) && $.browser.opera)
			el.css({ position: 'relative', top: 'auto', left: 'auto' });
	
			this._renderProxy();
	
			var curleft = parseInt(this.helper.css('left'),10) || 0, curtop = parseInt(this.helper.css('top'),10) || 0;
	
				//Store needed variables
			$.extend(o, {
				originalSize: { width: el.outerWidth(), height: el.outerHeight() },
				originalPosition: { left: curleft, top: curtop },
				originalMousePosition: { left: e.pageX, top: e.pageY },
				currentSize: { width: el.outerWidth(), height: el.outerHeight() },
				currentPosition: { left: curleft,top: curtop },
				currentSizeDiff: { width: el.outerWidth() - el.width(), height: el.outerHeight() - el.height() },
				_currentSize: { width: el.outerWidth(), height: el.outerHeight() },
				_currentPosition: { left: curleft,top: curtop }
			});
	
			//Aspect Ratio
			o.aspectRatio = (typeof o.aspectRatio == 'number') ? o.aspectRatio : ((o.originalSize.height / o.originalSize.width)||1);
	
			if (o.preserveCursor)
				$('body').css('cursor', o.axis + '-resize');
				
			if (o.containment) {
					var oc = o.containment,
						ce = (oc instanceof jQuery) ? oc.get(0) : 
							(/parent/.test(oc)) ? el.parent().get(0) : null;
				if (ce) {
					var co = $(ce).offset(), ch = $(ce).innerHeight(), cw = $(ce).innerWidth();
					o.cdata = { e: ce, l: co.left, t: co.top, w: ($.ui.hasScroll(ce, "left") ? ce.scrollWidth : cw ), h: ($.ui.hasScroll(ce) ? ce.scrollHeight : ch) };
				}
				if (/document/.test(oc) || oc == document) o.cdata = { e: document, l: 0, t: 0, w: $(document).width(), h: $(document).height() };
			}
			this.propagate("start", e); 	
			return false;
		},
		stop: function(e) {
			this.options.resizing = false;
			var o = this.options;
	
			if(o.proxy) {
				var style = { 
					width: (this.helper.width() - o.currentSizeDiff.width) + "px",
					height: (this.helper.height() - o.currentSizeDiff.height) + "px",
					top: ((parseInt(this.element.css('top'),10) || 0) + ((parseInt(this.helper.css('top'),10) - this.offset.top)||0)),
					left: ((parseInt(this.element.css('left'),10) || 0) + ((parseInt(this.helper.css('left'),10) - this.offset.left)||0))
				};
				this.element.css(style);
				if (o.proxy) this._proportionallyResize();
				this.helper.remove();
			}
	
			if (o.preserveCursor)
			$('body').css('cursor', 'auto');
	
			this.propagate("stop", e);	
			return false;
		},
		change: {
			e: function(e, dx, dy) {
				return { width: this.options.originalSize.width + dx };
			},
			w: function(e, dx, dy) {
				var o = this.options, cs = o.originalSize, sp = o.originalPosition;
				return { left: sp.left + dx, width: cs.width - dx };
			},
			n: function(e, dx, dy) {
				var o = this.options, cs = o.originalSize, sp = o.originalPosition;
				return { top: sp.top + dy, height: cs.height - dy };
			},
			s: function(e, dx, dy) {
				return { height: this.options.originalSize.height + dy };
			},
			se: function(e, dx, dy) {
				return $.extend(this.change.s.apply(this, arguments), this.change.e.apply(this, [e, dx, dy]));
			},
			sw: function(e, dx, dy) {
				return $.extend(this.change.s.apply(this, arguments), this.change.w.apply(this, [e, dx, dy]));
			},
			ne: function(e, dx, dy) {
				return $.extend(this.change.n.apply(this, arguments), this.change.e.apply(this, [e, dx, dy]));
			},
			nw: function(e, dx, dy) {
				return $.extend(this.change.n.apply(this, arguments), this.change.w.apply(this, [e, dx, dy]));
			}
		},
		
		drag: function(e) {
			//Increase performance, avoid regex
			var el = this.helper, o = this.options, props = {}, pRatio = o._aspectRatio || e.shiftKey, 
			self = this, pRatio = o._aspectRatio || e.shiftKey, smp = o.originalMousePosition;

			var dx = (e.pageX-smp.left)||0, dy = (e.pageY-smp.top)||0;
			var trigger = this.change[o.axis];
			if (!trigger) return false;
		 
			// Calculate the attrs that will be change
			var data = trigger.apply(this, [e, dx, dy]), ie6 = $.browser.msie && $.browser.version < 7, csdif = o.currentSizeDiff;
		 
			// Adjust currentSizeDiff on resize
			if (data.width) data.width = data.width - (!o.proxy && !ie6 ? csdif.width : 0);
			if (data.height) data.height = data.height - (!o.proxy && !ie6 ? csdif.height : 0);
		 
			// Update internal current information
			var cpos = o._currentPosition, csize = o._currentSize;
			if (data.left) cpos.left = data.left;
			if (data.top) cpos.top = data.top;
			if (data.height) csize.height = data.height;
			if (data.width) csize.width = data.width;
		 
			// Ratio preservation
			if (pRatio) {
				if (data.height) data.width = csize.height / o.aspectRatio;
				else if (data.width)  data.height = csize.width * o.aspectRatio;
	
				if (o.axis == 'sw') {
					data.left = cpos.left + (csize.width - data.width);
					data.top = null;
				}
				if (o.axis == 'nw') { 
					data.top = cpos.top + (csize.height - data.height);
					data.left = cpos.left + (csize.width - data.width);
				}
			}
			// Avoid set height/width to 0 on misscalculation
			data.width = data.width || null;
			data.height = data.height || null;
			
			this.cssData = data;
			this.propagate("resize", e);
			data = this.cssData;
			
			// Containment
			if (o.containment && o.cdata.e) {
				if (data.left && data.left < 0) {
					data.left = 0; data.width = null;
				}
				if (data.top && data.top < 0) {
					data.top = 0;	data.height = null;
				}
				var woset = cpos.left + csdif.width, hoset = cpos.top + csdif.height;
				if (data.width && woset + data.width >= o.cdata.w) data.width = o.cdata.w - woset;
				if (data.height && hoset + data.height >= o.cdata.h) data.height = o.cdata.h - hoset;
				
			}
		 
			// Max/Min width/height control
			var ismaxw = data.width && o.maxWidth && o.maxWidth <= data.width, ismaxh = data.height && o.maxHeight && o.maxHeight <= data.height,
				isminw = data.width && o.minWidth && o.minWidth >= data.width, isminh = data.height && o.minHeight && o.minHeight >= data.height;
	
			if (ismaxw || isminw) {
				data.width = null; data.left = null;
				if (pRatio) { data.height = null; data.top = null; }
			}
			if (ismaxh || isminh) {
				data.height = null; data.top = null;
				if (pRatio) { data.width = null; data.left = null; }
			}
			// fixing jump error on top/left - bug #2330
			var isNotwh = !data.width && !data.height;
			if (isNotwh && !data.left && data.top) data.top = null;
			else if (isNotwh && !data.top && data.left) data.left = null;
		 
			this.cssData = data;
			
			el.css(this.cssData);
		 
			if (!o.proxy && o.proportionallyResize)
				this._proportionallyResize();
		 
			// Update current information
			if (data.left) o.currentPosition.left = this.cssData.left;
			if (data.top) o.currentPosition.top = this.cssData.top;
			if (data.height) o.currentSize.height = this.cssData.height;
			if (data.width) o.currentSize.width = this.cssData.width;
			return false;
		},
		
		_proportionallyResize: function() {
			var o = this.options;
			if (!o.proportionallyResize) return;
			var prel = o.proportionallyResize, el = this.element;
		 
			if (!o.borderDif) {
				var b = [prel.css('borderTopWidth'), prel.css('borderRightWidth'), prel.css('borderBottomWidth'), prel.css('borderLeftWidth')],
					p = [prel.css('paddingTop'), prel.css('paddingRight'), prel.css('paddingBottom'), prel.css('paddingLeft')];
				 
				o.borderDif = $.map(b, function(v, i) {
					var border = parseInt(v,10)||0, padding = parseInt(p[i],10)||0;
					return border + padding; 
				});
			}
			prel.css({
				height: (el.height() - o.borderDif[0] - o.borderDif[2]) + "px",
				width: (el.width() - o.borderDif[1] - o.borderDif[3]) + "px"
			});
			}
		});

})(jQuery);
