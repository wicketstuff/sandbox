package wicket.contrib.markup.html.yui.anim;

import org.apache.wicket.AttributeModifier;
import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * An AnimText contains the selected value(s)
 * 
 * @author cptan
 * 
 */
public class AnimText extends AbstractYuiPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create an AnimText
	 * 
	 * @param id -
	 *            wicket id
	 */
	public AnimText(final String id) {
		super(id);
		TextField text = new TextField("textfield");
		text.add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			public Object getObject() {
				return id;
			}
		}));
		add(text);
	}
}
