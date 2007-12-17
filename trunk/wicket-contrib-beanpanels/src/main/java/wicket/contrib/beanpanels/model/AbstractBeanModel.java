package wicket.contrib.beanpanels.model;

import java.io.Serializable;
import java.util.List;

import wicket.Component;
import wicket.model.IModel;

public abstract class AbstractBeanModel implements IModel {

	private Serializable bean;

	protected AbstractBeanModel( Serializable bean ) { 
		this.bean = bean;
	}
	/**
	 * @see wicket.model.IModel#getNestedModel()
	 */
	public IModel getNestedModel()
	{
		return null;
	}

	/**
	 * @see wicket.model.IDetachable#detach()
	 */
	public void detach()
	{
	}

	/**
	 * @see wicket.model.IModel#getObject(wicket.Component)
	 */
	public Object getObject(Component component)
	{
		return bean;
	}

	/**
	 * Throws an {@link UnsupportedOperationException} as changing the bean is not permitted.
	 * @see wicket.model.IModel#setObject(wicket.Component, java.lang.Object)
	 */
	public void setObject(Component component, Object object)
	{
		throw new UnsupportedOperationException("BeanModel is read-only");
	}
	
	/**
	 * Convenience method.
	 * @return the bean
	 */
	public Serializable getBean()
	{
		return bean;
	}
	
	/**
	 * Return the target bean class type
	 * 
	 * @return
	 */
	public Class getType() { 
		return getBean().getClass();
	}	
	
	public abstract List<IPropertyMeta> getProperties();
}
