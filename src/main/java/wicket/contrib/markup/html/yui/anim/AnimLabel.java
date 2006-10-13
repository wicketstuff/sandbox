package wicket.contrib.markup.html.yui.anim;

import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import wicket.markup.html.basic.Label;

/**
 * Represent the option's value
 * @author cptan
 *
 */
public class AnimLabel extends AbstractYuiPanel{

	/**
	 * Constructor
	 * @param id
	 * @param text
	 */
	public AnimLabel(String id, String text){
		super(id);
		add(new Label("label", text));
	}
}
