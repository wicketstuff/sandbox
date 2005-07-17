package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;

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
