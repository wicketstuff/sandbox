package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LabelPanel(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id);
		Label label = new Label(this, "label", model);
		label.setRenderBodyOnly(true);
	}

	public LabelPanel(MarkupContainer parent, String id, String label)
	{
		this(parent, id, new Model(label));
	}
}
