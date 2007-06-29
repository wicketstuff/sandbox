package wicket.contrib.mootools.plugins;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.string.JavascriptUtils;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.events.MFXWindowLoad;

public abstract class MFXFishEye extends Panel {
	private ResourceReference FISHEYE_JS = new ResourceReference(MFXFishEye.class,"FishEye.js");
	private ResourceReference FISHEYE_CSS = new ResourceReference(MFXFishEye.class,"FishEye.css");
	private ResourceReference FISHEYE_SHIM = new ResourceReference(MFXFishEye.class,"shim.gif");
	private static final long serialVersionUID = 1L;
	private String id;
	
	public abstract List<MFXFishEyeItem> populateItems(List<MFXFishEyeItem> items);
	
	
	public class Shim extends AbstractBehavior {
		private static final long serialVersionUID = 1L;
		@Override
		public void renderHead(IHeaderResponse response) {
			response.renderString(JavascriptUtils.SCRIPT_OPEN_TAG);
			response.renderString("var mfxshim = '"+RequestCycle.get().urlFor(FISHEYE_SHIM)+"';");
			response.renderString(JavascriptUtils.SCRIPT_CLOSE_TAG);
			super.renderHead(response);
		}
	}
	
	public MFXFishEye(String id) {
		super(id);
		this.id = id;
		add(new IncludeMooToolsStateless());
		add(new Shim());
		add(HeaderContributor.forJavaScript(FISHEYE_JS));
		add(HeaderContributor.forCss(FISHEYE_CSS));
		MFXWindowLoad load = new MFXWindowLoad();
		load.addAction("var fisheye = new fisheyeClass($('mfxdock'));");
		add(load);
		
		RepeatingView rp = new RepeatingView("items");
		List<MFXFishEyeItem> items = populateItems(new ArrayList<MFXFishEyeItem>());
		for(int i = 0 ; i < items.size() ; i++)
		{
			MFXFishEyeItem item = items.get(i);
			rp.add(new WebMarkupContainer("item"+i).add(item));
		}	
		
		add(rp);
	}
}
