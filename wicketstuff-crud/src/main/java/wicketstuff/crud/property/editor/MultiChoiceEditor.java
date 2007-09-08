package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class MultiChoiceEditor extends Panel
{
	private final ListMultipleChoice field;

	public MultiChoiceEditor(String id, IModel model, IModel choices, IChoiceRenderer renderer)
	{
		super(id);
		add(field = new ListMultipleChoice("choices", model, choices, renderer));
	}
}
