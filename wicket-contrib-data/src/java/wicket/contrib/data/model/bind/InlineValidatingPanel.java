package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.markup.html.panel.Panel;
import wicket.validation.IValidator;

/**
 * A panel that wraps a validating form component.
 * 
 * @author Phil Kulak
 */
public class InlineValidatingPanel<T> extends Panel<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	InlineValidatingComponent component;

	public InlineValidatingPanel(MarkupContainer parent, String id)
	{
		super(parent, id);
	}

	/**
	 * Sets the component that will optionally have valildators added.
	 */
	public void setComponent(InlineValidatingComponent component)
	{
		this.component = component;
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
