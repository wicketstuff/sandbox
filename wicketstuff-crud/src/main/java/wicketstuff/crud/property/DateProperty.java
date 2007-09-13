package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.TextEditor;


public class DateProperty extends Property
{
	public DateProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}

	@Override
	public Component getEditor(String id, IModel object)
	{
		TextEditor editor = new TextEditor(id, new PropertyModel(object, getPath()));
		configure(editor);
		return editor;
	}

}
