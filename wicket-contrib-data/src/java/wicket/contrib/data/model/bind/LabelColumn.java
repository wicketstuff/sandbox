package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.model.IModel;

/**
 * A column to represent a read-only field.
 * 
 * @author Phil Kulak
 */
public class LabelColumn extends AbstractColumn
{
	public LabelColumn(String displayName, String ognlPath)
	{
		super(displayName, ognlPath);
	}

	public Component getComponent(String id, IModel model)
	{
		return new LabelPanel(id, model);
	}
}
