package wicket.contrib.data.model.bind;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.model.IModel;
import wicket.validation.IValidator;
import wicket.validation.validator.AbstractValidator;

/**
 * A column that can have validators added to it. Any subclass must call
 * {@link #prepare(InlineValidatingPanel, IModel)} before returning a component
 * from {@link IColumn#getComponent(String, wicket.model.IModel)}.
 * 
 * @author Phil Kulak
 */
public abstract class ValidatingColumn extends AbstractColumn
{
	private List validators = new ArrayList();

	private Class type;

	public ValidatingColumn(String displayName, String modelPath)
	{
		super(displayName, modelPath);
	}

	public ValidatingColumn add(AbstractValidator validator)
	{
		validators.add(validator);
		return this;
	}

	/**
	 * Sets the conversion type for this component.
	 */
	public ValidatingColumn setType(Class type)
	{
		this.type = type;
		return this;
	}

	protected InlineValidatingPanel prepare(InlineValidatingPanel panel, IModel model)
	{
		for (Iterator i = validators.iterator(); i.hasNext();)
		{
			final AbstractValidator validator = (AbstractValidator) i.next();

			// Build the resource key.
			final String resourceKey = model.getClass().getName()
					+ "." + getModelPath() + "." + validator.getClass().getName();

			IValidator proxiedValidator = (IValidator) Proxy.newProxyInstance(validator
					.getClass().getClassLoader(), new Class[] {IValidator.class},
					new InvocationHandler()
					{
						public Object invoke(Object proxy, Method method, Object[] args)
								throws Throwable
						{
							if (method.getName().equals("resourceKey"))
							{
								return resourceKey;
							}
							return method.invoke(validator, args);
						}
					});

			panel.add(proxiedValidator);
		}

		if (type != null)
		{
			panel.setType(type);
		}
		return panel;
	}
}
