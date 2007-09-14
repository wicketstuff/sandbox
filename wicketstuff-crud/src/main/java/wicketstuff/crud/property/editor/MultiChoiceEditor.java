package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

public class MultiChoiceEditor extends FormComponentEditor implements Editor
{
	private final ListMultipleChoice field;

	public MultiChoiceEditor(String id, IModel model, IModel choices, IChoiceRenderer renderer)
	{
		super(id);
		add(field = new ListMultipleChoice("choices", model, choices, renderer));
	}

	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}

}
