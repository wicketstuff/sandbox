package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * A column that's represented by a check mark or a check box.
 * 
 * @author Phil Kulak
 */
public class CheckBoxColumn<T> extends AbstractColumn<T>
{
	private static final long serialVersionUID = 1L;

	public CheckBoxColumn(String displayName, String ognlPath)
	{
		super(displayName, ognlPath);
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return new CheckBoxPanel(parent, id, makePropertyModel(model));
	}
}
