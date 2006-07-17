package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * A panel that shows a check mark or a check box, depending on the state of its
 * enclosing form.
 * 
 * @author Phil Kulak
 */
public class CheckBoxPanel extends Panel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckBoxPanel(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id);
		new InlineCheckBox(this, "inlineCheckBox", model);
	}
}
