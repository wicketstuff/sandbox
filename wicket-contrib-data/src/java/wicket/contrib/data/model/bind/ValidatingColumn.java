package wicket.contrib.data.model.bind;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import wicket.markup.html.form.validation.AbstractValidator;
import wicket.markup.html.form.validation.IValidator;
import wicket.model.IModel;

/**
 * A column that can have validators added to it. Any subclass must call
 * {@link #prepare(InlineValidatingPanel, IModel)} before returning a component
 * from {@link IColumn#getComponent(String, wicket.model.IModel)}.
 * 
 * @author Phil Kulak
 */
public abstract class ValidatingColumn<T> extends AbstractColumn<T>
{
	private List<AbstractValidator> validators = new ArrayList<AbstractValidator>();

	private Class<T> type;

	public ValidatingColumn(String displayName, String modelPath)
	{
		super(displayName, modelPath);
	}

	public ValidatingColumn add(AbstractValidator validator)
	{
		validators.add(validator);
		return this;
	}

	protected InlineValidatingPanel prepare(InlineValidatingPanel panel, IModel<T> model)
	{
		for (Object element : validators)
		{
			final AbstractValidator validator = (AbstractValidator) element;

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

	/**
	 * Sets the conversion type for this component.
	 */
	public ValidatingColumn setType(Class<T> type)
	{
		this.type = type;
		return this;
	}
}
