package wicket.contrib.markup.html.yui.animselect;

import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Represent the option's value
 * 
 * @author cptan
 */
public class AnimSelectOptionLabel extends AbstractYuiPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param text
	 */
	public AnimSelectOptionLabel(String id, String text) {
		super(id);
		add(new Label("label", text));
	}
}
