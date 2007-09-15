package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Single-choice editor
 * 
 * @author igor.vaynberg
 * 
 */
public class ChoiceEditor extends FormComponentEditor implements Editor
{
	private final DropDownChoice field;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param choices
	 * @param renderer
	 */
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
