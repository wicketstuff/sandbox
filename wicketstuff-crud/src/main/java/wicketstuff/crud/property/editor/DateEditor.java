package wicketstuff.crud.property.editor;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

import wicketstuff.crud.Editor;

public class DateEditor extends Panel implements Editor
{
	private final DateTextField field;

	public DateEditor(String id, IModel model, String pattern)
	{
		super(id);
		add(field = DateTextField.forDatePattern("text", model, pattern));
		field.add(new DatePicker());
	}

	public void setRequired(boolean required)
	{
		field.setRequired(required);
	}

	public void add(IValidator validator)
	{
		field.add(validator);
	}

	public void setLabel(IModel label)
	{
		field.setLabel(label);
	}


}