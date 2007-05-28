package wicket.contrib.mootools.plugins;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;
import wicket.contrib.mootools.MFXOptions;

public class MFXImageMenu extends AbstractRequireMooStatelessBehavior  {
	private static final long serialVersionUID = 1L;
	List<WebMarkupContainer> components = new ArrayList();
	private int foldOutWidth = 500;
	private MFXOptions mfxOptions = new MFXOptions();
	private int foldedWidth = 50;
	private String unit = "px";
	
	
	@Override
	public String mooFunction() {
		StringBuffer buf = new StringBuffer();
		buf.append("var progress = 0;");
	
		Boolean firstUp = true;
		getMfxOptions().setOnComplete("progress = 0;");
		for(WebMarkupContainer cont : components) {
			String compid = cont.getMarkupId();
			String styleid = compid+"style";
			buf.append("var "+styleid+"= new Fx.Style('"+compid+"','width',"+getMfxOptions().writeOptions()+");");
			buf.append("var "+compid+" = $('"+compid+"');");
		
			buf.append(compid+".addEvent('mouseover', function(e) {");
			buf.append("if(progress == 1 || "+compid+".getStyle('width') == '"+foldOutWidth+unit+"') { return; }");
			
			if(!firstUp)
				buf.append("progress=1;");
			else
				firstUp=false;
			
			for(WebMarkupContainer contInt : components) {
				String compidInt = contInt.getMarkupId();
				String styleidInt = compidInt+"style";
				if(compidInt.equals(compid))
					buf.append(compidInt+".getStyle('width') != '"+foldOutWidth+unit+"' ? "+styleidInt+".start("+compidInt+".getStyle('width'),"+foldOutWidth+") : false;");
				else
					buf.append(compidInt+".getStyle('width') != '"+foldedWidth+unit+"' ? "+styleidInt+".start("+compidInt+".getStyle('width'),"+foldedWidth+") : false;");
			}
			
			buf.append("});");
		}
		
		return buf.toString();
		
	}
	
	@Override
	public void onRendered(Component arg0) {
		addMooDomFunction(mooFunction());
		super.onRendered(arg0);
	}
	
	public MFXImageMenu addComponent(WebMarkupContainer cont) {
		cont.setOutputMarkupId(true);
		components.add(cont);
		return this;
	}

	public MFXImageMenu setFoldedWidth(int foldedWidth) {
		this.foldedWidth = foldedWidth;
		return this;
	}

	public int getFoldedWidth() {
		return foldedWidth;
	}

	public MFXImageMenu setFoldOutWidth(int foldOutWidth) {
		this.foldOutWidth = foldOutWidth;
		return this;
	}

	public int getFoldOutWidth() {
		return foldOutWidth;
	}

	public MFXImageMenu setUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public MFXImageMenu setMfxOptions(MFXOptions mfxOptions) {
		this.mfxOptions = mfxOptions;
		return this;
	}

	public MFXOptions getMfxOptions() {
		return mfxOptions;
	}
	
// original code base for this imagemenu
//	window.addEvent("domready", function() {
//		var progress = 0;
//		var menu110 = new Fx.Style('menu1','width',{ onComplete: function() { progress = 0; }});
//		var menu220 = new Fx.Style('menu2','width',{onComplete: function() { progress = 0; }});
//		var menu330 = new Fx.Style('menu3','width',{onComplete: function() { progress = 0; }});
//		
//		var menu1 = $('menu1');
//		var menu2 = $('menu2');
//		var menu3 = $('menu3');
//		
//		menu1.addEvent('mouseover', function(e) {
//			if(progress == 1 || menu1.getStyle('width') == "500px") { return;  }
//			menu1.getStyle('width')  != "500px" ? menu110.start(menu1.getStyle('width'),500) : false;
//			menu2.getStyle('width')  != "50px" ? menu220.start(menu2.getStyle('width'),50) : false;
//			menu3.getStyle('width')  != "50px" ? menu330.start(menu3.getStyle('width'),50) : false;
//		});
//		
//		menu2.addEvent('mouseover', function(e) {
//			if(progress == 1 || menu2.getStyle('width') == "500px") { return; }
//			progress=1;
//			menu1.getStyle('width')  != "50px" ? menu110.start(menu1.getStyle('width'),50) : false;
//			menu2.getStyle('width')  != "500px" ? menu220.start(menu2.getStyle('width'),500) : false;
//			menu3.getStyle('width')  != "50px" ? menu330.start(menu3.getStyle('width'),50) : false;
//		});
//		
//		menu3.addEvent('mouseover', function(e) {
//			if(progress == 1 || menu3.getStyle('width') == "500px" ) { return; }
//			progress=1;
//			menu1.getStyle('width')  != "50px" ? menu110.start(menu1.getStyle('width'),50) : false;
//			menu2.getStyle('width')  != "50px" ? menu220.start(menu2.getStyle('width'),50) : false;
//			menu3.getStyle('width')  != "500px" ? menu330.start(menu3.getStyle('width'),500) : false;
//		});
//	
//	});

}
