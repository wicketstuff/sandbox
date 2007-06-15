package wicket.contrib.mootools.plugins;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;

public abstract class MFXFishEyeItem extends Link {
	private ResourceReference icon;
	private String name;
	
	public MFXFishEyeItem(String name,ResourceReference icon) {
		super("item");
		this.icon = icon;
		this.name = name;
	}
	
	@Override
	protected void onComponentTag(ComponentTag arg0) {
		// TODO Auto-generated method stub
		super.onComponentTag(arg0);
		arg0.put("title",name);
		arg0.put("alt", RequestCycle.get().urlFor(icon));
	}
}
