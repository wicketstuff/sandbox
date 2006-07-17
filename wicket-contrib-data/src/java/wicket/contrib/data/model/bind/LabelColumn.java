package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * A column to represent a read-only field.
 * 
 * @author Phil Kulak
 */
public class LabelColumn<T> extends AbstractColumn<T>
{
	private static final long serialVersionUID = 1L;

	public LabelColumn(String displayName, String ognlPath)
	{
		super(displayName, ognlPath);
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return new LabelPanel(parent, id, makePropertyModel(model));
	}
}
