package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.DateEditor;


public class DateProperty extends Property
{
	private String pattern = "MM/dd/yyyy";

	public DateProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}

	public DateProperty(String path, IModel label, String pattern)
	{
		this(path, label);
		this.pattern = pattern;
	}

	@Override
	public Component getEditor(String id, IModel object)
	{
		DateEditor editor = new DateEditor(id, new PropertyModel(object, getPath()), pattern);
		configure(editor);
		return editor;
	}


	@Override
	public Component getViewer(String id, IModel object)
	{
		return new DateLabel(id, new PropertyModel(object, getPath()), new PatternDateConverter(
				pattern, false));
	}

}
