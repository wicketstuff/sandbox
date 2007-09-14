package wicketstuff.crud.property.editor;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

public class DateEditor extends FormComponentEditor implements Editor
{
	private final DateTextField field;

	public DateEditor(String id, IModel model, String pattern)
	{
		super(id);
		add(field = DateTextField.forDatePattern("text", model, pattern));
		field.add(new DatePicker());
	}

	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}


}
