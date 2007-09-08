package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.property.editor.TextEditor;


public class StringProperty extends AbstractProperty
{
	private int maxLength;

	public StringProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}

	public Component getEditor(String id, IModel object)
	{
		return new TextEditor(id, new PropertyModel(object, getPath())).setMaxLength(maxLength);
	}

	public int getMaxLength()
	{
		return maxLength;
	}

	public StringProperty setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}


}
