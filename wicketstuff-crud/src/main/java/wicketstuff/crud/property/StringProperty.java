package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.TextEditor;


/**
 * String property
 * 
 * @author igor.vaynberg
 * 
 */
public class StringProperty extends Property
{
	private int maxLength = 0;

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public StringProperty(String path, IModel label)
	{
		super(path, label);
	}

	/** {@inheritDoc} */
	@Override
	public Component getEditor(String id, IModel object)
	{
		TextEditor editor = new TextEditor(id, new PropertyModel(object, getPath()));
		if (maxLength > 0)
		{
			editor.setMaxLength(maxLength);
			editor.add(StringValidator.maximumLength(maxLength));
		}
		configure(editor);
		return editor;
	}


	/**
	 * Sets max length of string
	 * 
	 * @param maxLength
	 * @return
	 */
	public StringProperty setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}


}
