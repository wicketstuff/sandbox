package wicket.contrib.markup.html.yui.anim;

import org.apache.wicket.markup.html.basic.Label;

import wicket.contrib.markup.html.yui.AbstractYuiPanel;

/**
 * An AnimLabel contains the option's value
 * 
 * @author cptan
 */
public class AnimLabel extends AbstractYuiPanel {

	private static final long serialVersionUID = 1L;

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
