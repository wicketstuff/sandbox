package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.Component;
import wicket.model.IModel;

/**
 * A column for a drop down choice (select list).
 * 
 * @author Phil Kulak
 */
public class DropDownChoiceColumn extends ValidatingColumn
{
	private List choices;
	
	public DropDownChoiceColumn(String displayName, String ognlPath, List choices)
	{
		super(displayName, ognlPath);
		this.choices = choices;
	}
	
	public Component getComponent(String id, IModel model)
	{
		return prepare(new DropDownChoicePanel(id, makePropertyModel(model), choices));
	}
}
