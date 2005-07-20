package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.markup.html.form.validation.AbstractValidator;
import wicket.markup.html.form.validation.IValidator;
import wicket.model.IModel;

/**
 * A column that can have validators added to it. Any subclass must call
 * {@link #prepare(InlineValidatingPanel)} before returning a component
 * from {@link IColumn#getComponent(String, wicket.model.IModel)}.
 * 
 * @author Phil Kulak
 */
public abstract class ValidatingColumn extends AbstractColumn
{
	private List validators = new ArrayList();
	
	public ValidatingColumn(String displayName, String modelPath)
	{
		super(displayName, modelPath);
	}
	
	public ValidatingColumn add(AbstractValidator validator)
	{
		validators.add(validator);
		return this;
	}
	
	protected InlineValidatingPanel prepare(InlineValidatingPanel panel, IModel model)
	{
		for (Iterator i = validators.iterator(); i.hasNext();)
		{
			AbstractValidator validator = (AbstractValidator) i.next();
			
			// Build the resource key.
			String resourceKey = model.getClass().getName() + "."
				+ getModelPath() + "." + validator.getClass().getName();
			
			validator.setResourceKey(resourceKey);
			
			panel.add(validator);
		}
		return panel;
	}
}
