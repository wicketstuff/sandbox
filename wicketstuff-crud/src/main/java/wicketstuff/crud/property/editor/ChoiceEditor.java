package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

public class ChoiceEditor extends FormComponentEditor implements Editor
{
	private final DropDownChoice field;

	public ChoiceEditor(String id, IModel model, IModel choices, IChoiceRenderer renderer)
	{
		super(id);
		add(field = new DropDownChoice("choice", model, choices, renderer));
	}

	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}
}
