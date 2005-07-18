package wicket.contrib.data.model.bind;

import wicket.markup.html.form.validation.IValidator;
import wicket.markup.html.panel.Panel;

public class InlineValidatingPanel extends Panel
{
	InlineValidatingComponent component;
	
	public InlineValidatingPanel(String id)
	{
		super(id);
	}
	
	public void setComponent(InlineValidatingComponent component)
	{
		this.component = component;
		add(component);
	}
	
	public InlineValidatingPanel add(IValidator validator)
	{
		component.add(validator);
		return this;
	}
}
