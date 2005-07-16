package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.validation.IValidator;

/**
 * Used for any inline component that needs to be able to add validators
 * to itself.
 * 
 * @author Phil Kulak
 */
public abstract class InlineValidatingComponent extends InlineComponent {
	private FormComponent formComponent;
	
	public InlineValidatingComponent(String id) {
		super(id);
	}
	
	public InlineValidatingComponent add(IValidator validator) {
		formComponent.add(validator);
		return this;
	}
	
	protected void setFormComponent(FormComponent formComponent) {
		this.formComponent = formComponent;
		add(formComponent);
	}
}
