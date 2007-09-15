package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

import wicketstuff.crud.Editor;

/**
 * Editor that is based on a single {@link FormComponent}
 * 
 * @author igor.vaynberg
 * 
 */
public abstract class FormComponentEditor extends Panel implements Editor
{

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public FormComponentEditor(String id)
	{
		super(id);
	}


	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 */
	public FormComponentEditor(String id, IModel model)
	{
		super(id, model);
	}


	/**
	 * Returns form component created by the editor
	 * 
	 * @return
	 */
	protected abstract FormComponent getFormComponent();


	/** {@inheritDoc} */
	public void setRequired(boolean required)
	{
		getFormComponent().setRequired(required);
	}

	/** {@inheritDoc} */
	public void add(IValidator validator)
	{
		getFormComponent().add(validator);
	}

	/** {@inheritDoc} */
	public void setLabel(IModel label)
	{
		getFormComponent().setLabel(label);
	}

	/** {@inheritDoc} */
	public IModel getLabel()
	{
		return getFormComponent().getLabel();
	}

	/** {@inheritDoc} */
	public boolean isRequired()
	{
		return getFormComponent().isRequired();
	}


}
