package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ChoiceEditor extends Panel
{
	private final DropDownChoice field;

	public ChoiceEditor(String id, IModel model, IModel choices, IChoiceRenderer renderer)
	{
		super(id);
		add(field = new DropDownChoice("choice", model, choices, renderer));
	}
}
