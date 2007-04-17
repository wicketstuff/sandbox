package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.model.IModel;

/**
 * A column that's represented by a check mark or a check box.
 * 
 * @author Phil Kulak
 */
public class CheckBoxColumn extends AbstractColumn
{
	public CheckBoxColumn(String displayName, String ognlPath)
	{
		super(displayName, ognlPath);
	}

	public Component getComponent(String id, IModel model)
	{
		return new CheckBoxPanel(id, makePropertyModel(model));
	}
}
