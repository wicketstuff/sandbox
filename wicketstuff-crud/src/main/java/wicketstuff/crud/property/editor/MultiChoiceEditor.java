package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Multiple-choice editor
 * 
 * @author igor.vaynberg
 * 
 */
public class MultiChoiceEditor extends FormComponentEditor implements Editor
{
	private final ListMultipleChoice field;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param choices
	 * @param renderer
	 */
	public MultiChoiceEditor(String id, IModel model, IModel choices, IChoiceRenderer renderer)
	{
		super(id);
		add(field = new ListMultipleChoice("choices", model, choices, renderer));
	}

	/** {@inheritDoc} */
	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}

}
