package wicket.contrib.data.model.bind;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * A panel that shows a check mark or a check box, depending on the state of its
 * enclosing form.
 * 
 * @author Phil Kulak
 */
public class CheckBoxPanel extends Panel
{
	public CheckBoxPanel(String id, IModel model)
	{
		super(id);
		add(new InlineCheckBox("inlineCheckBox", model));
	}
}
