package wicket.contrib.markup.html.yui.anim;

import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import wicket.markup.html.basic.Label;

/**
 * An AnimLabel contains the option's value
 * 
 * @author cptan
 * 
 */
public class AnimLabel extends AbstractYuiPanel {

	/**
	 * Creates an AnimLabel
	 * 
	 * @param id -
	 *            wicket id
	 * @param text -
	 *            value of the option
	 */
	public AnimLabel(String id, String text) {
		super(id);
		add(new Label("label", text));
	}
}
