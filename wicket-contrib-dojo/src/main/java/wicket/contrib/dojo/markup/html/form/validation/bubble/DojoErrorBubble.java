package wicket.contrib.dojo.markup.html.form.validation.bubble;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.markup.html.Bubble.AbstractDojoBubble;
import wicket.markup.ComponentTag;
import wicket.model.IModel;

/**
 * 
 * @author Vincent Demay
 *
 */
public class DojoErrorBubble extends AbstractDojoBubble {

	public DojoErrorBubble(String id, IModel model) {
		super(id, model);
		add(new DojoErrorBubbleHandler());
	}

	public DojoErrorBubble(String id) {
		super(id);
		add(new DojoErrorBubbleHandler());
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_ERRORBUBBLE);
	}

}
