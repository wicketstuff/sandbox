package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * A column that represent String fields, or any field that can be represented
 * as a String.
 * 
 * @author Phil Kulak
 */
public class TextFieldColumn<T> extends ValidatingColumn<T>
{
	private static final long serialVersionUID = 1L;

	public TextFieldColumn(String displayName, String ognlPath)
	{
		super(displayName, ognlPath);
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return prepare(new TextFieldPanel(parent, id, makePropertyModel(model)), model);
	}
}
