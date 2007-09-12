package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidator;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.TextEditor;


public class StringProperty extends Property
{
	private int maxLength;

	public StringProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}

	public Component getEditor(String id, IModel object)
	{
		TextEditor editor = new TextEditor(id, new PropertyModel(object, getPath()))
				.setMaxLength(maxLength);
		editor.setRequired(isRequired());
		for (IValidator validator : getValidators())
		{
			editor.addValidator(validator);
		}
		return editor;
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
