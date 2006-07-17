package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * A column for a drop down choice (select list).
 * 
 * @author Phil Kulak
 */
public class DropDownChoiceColumn<T> extends ValidatingColumn<T>
{
	private static final long serialVersionUID = 1L;

	private List<T> choices;

	public DropDownChoiceColumn(String displayName, String ognlPath, List<T> choices)
	{
		super(displayName, ognlPath);
		this.choices = choices;
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return prepare(new DropDownChoicePanel<T>(parent, id, makePropertyModel(model),
				choices), model);
	}
}
