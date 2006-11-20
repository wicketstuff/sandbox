package wicket.contrib.data.model.bind;

import wicket.markup.html.form.validation.IValidator;
import wicket.markup.html.panel.Panel;

/**
 * A panel that wraps a validating form component.
 * 
 * @author Phil Kulak
 */
public class InlineValidatingPanel extends Panel
{
	InlineValidatingComponent component;
	
	public InlineValidatingPanel(String id)
	{
		super(id);
	}
	
	/**
	 * Sets the component that will optionally have valildators added.
	 */
	public void setComponent(InlineValidatingComponent component)
	{
		this.component = component;
		add(component);
	}
	
	/**
	 * Adds a validator to the inner form component.
	 */
	public InlineValidatingPanel add(IValidator validator)
	{
		component.add(validator);
		return this;
	}
	
	/**
	 * Sets the type for the inner form component.
	 */
	public InlineValidatingPanel setType(Class c)
	{
		component.setType(c);
		return this;
	}
}
