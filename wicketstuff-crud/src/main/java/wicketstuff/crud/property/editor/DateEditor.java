package wicketstuff.crud.property.editor;

import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.model.IModel;

public class DateEditor extends TextEditor
{
	public DateEditor(String id, IModel model)
	{
		super(id, model);
		getField().add(new DatePicker());
	}


}
