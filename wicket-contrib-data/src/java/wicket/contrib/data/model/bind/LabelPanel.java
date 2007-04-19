package wicket.contrib.data.model.bind;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * A panel that simply renders the string version of its model.
 * 
 * @author Phil Kulak
 */
public class LabelPanel extends Panel
{
	public LabelPanel(String id, IModel model)
	{
		super(id);
		Label label = new Label("label", model);
		label.setRenderBodyOnly(true);
		add(label);
	}

	public LabelPanel(String id, String label)
	{
		this(id, new Model(label));
	}
}
