package wicket.contrib.markup.html.yui.animselect;

import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import wicket.markup.html.basic.Label;

/**
 * Represent the option's value
 * @author cptan
 *
 */
public class AnimSelectOptionLabel extends AbstractYuiPanel{

	/**
	 * Constructor
	 * @param id
	 * @param text
	 */
	public AnimSelectOptionLabel(String id, String text){
		super(id);
		add(new Label("label", text));
	}
}
