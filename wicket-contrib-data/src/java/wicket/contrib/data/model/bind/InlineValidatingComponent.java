package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.FormComponent;
import wicket.validation.IValidator;

/**
 * Used for any inline component that needs to be able to add validators to
 * itself.
 * 
 * @author Phil Kulak
 */
public abstract class InlineValidatingComponent<T> extends WebMarkupContainer<T>
{
	private FormComponent formComponent;

	/**
	 * @param id
	 *            the id of this component
	 */
	public InlineValidatingComponent(MarkupContainer parent, String id)
	{
		super(parent, id);
	}

	/**
	 * @param validator
	 *            the validator to add
	 * @return itself to allow chaining
	 */
	public InlineValidatingComponent add(IValidator validator)
	{
		formComponent.add(validator);
		return this;
	}

	/**
	 * @param c
	 *            the class type of the component
	 * @return itselft to allow chaining
	 */
	public InlineValidatingComponent setType(final Class c)
	{
		formComponent.setType(c);
		return this;
	}

	protected void setFormComponent(FormComponent formComponent)
	{
		this.formComponent = formComponent;
	}
}
