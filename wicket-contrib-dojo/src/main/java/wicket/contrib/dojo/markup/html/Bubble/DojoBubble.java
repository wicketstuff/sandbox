package wicket.contrib.dojo.markup.html.Bubble;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.widgets.StyleAttribute;
import wicket.markup.ComponentTag;
import wicket.model.IModel;

public class DojoBubble extends AbstractDojoBubble{
	
	public DojoBubble(String id, IModel model) {
		super(id, model);
		add(new DojoBubbleHandler());
		
	}

	public DojoBubble(String id) {
		super(id);
		add(new DojoBubbleHandler());
	}

	protected void onStyleAttribute(StyleAttribute styleAttribute) {
		super.onStyleAttribute(styleAttribute);
	}
	

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_BUBBLE);
	}
}
