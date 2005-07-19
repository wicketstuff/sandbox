package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.markup.html.form.validation.IValidator;

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
	
	public ValidatingColumn add(IValidator validator)
	{
		validators.add(validator);
		return this;
	}
	
	protected InlineValidatingPanel prepare(InlineValidatingPanel panel)
	{
		for (Iterator i = validators.iterator(); i.hasNext();)
		{
			panel.add((IValidator) i.next());
		}
		return panel;
	}
}
