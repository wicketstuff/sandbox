package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

import wicketstuff.crud.Editor;

public abstract class FormComponentEditor extends Panel implements Editor
{

	public FormComponentEditor(String id)
	{
		super(id);
	}


	public FormComponentEditor(String id, IModel model)
	{
		super(id, model);
	}


	protected abstract FormComponent getFormComponent();


	public void setRequired(boolean required)
	{
		getFormComponent().setRequired(required);
	}

	public void add(IValidator validator)
	{
		getFormComponent().add(validator);
	}

	public void setLabel(IModel label)
	{
		getFormComponent().setLabel(label);
	}

	public IModel getLabel()
	{
		return getFormComponent().getLabel();
	}

	public boolean isRequired()
	{
		return getFormComponent().isRequired();
	}


}
