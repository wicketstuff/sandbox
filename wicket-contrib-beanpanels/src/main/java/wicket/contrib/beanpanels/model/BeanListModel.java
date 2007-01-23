package wicket.contrib.beanpanels.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.Component;
import wicket.model.IModel;

public class BeanListModel implements IModel {
	
	private List beanModels = new ArrayList();
	private List beans;
	
	/**
	 * Construct.
	 * @param bean the javabean to edit
	 */
	public BeanListModel(List beans)
	{
		this.beans = beans;
		for (Iterator beanIter = beans.iterator(); beanIter.hasNext(); )
		{
			Serializable bean = (Serializable) beanIter.next();
			beanModels.add(new BeanModel(bean));
		}
	}
	
	/**
	 * Construct.
	 * @param bean the javabean to edit
	 */
	public BeanListModel(Serializable bean)
	{
		this.beans = new ArrayList();
		this.beans.add(bean);
		this.beanModels.add(new BeanModel(bean));
	}

	public IModel getNestedModel() {
		return null;
	}

	public Object getObject(Component component) {
		return beans;
	}

	public void setObject(Component component, Object object) {
		throw new UnsupportedOperationException("BeanListModel is read-only");
	}

	public void detach() {
	}
	
	public List getBeanModels() {
		return this.beanModels;
	}

	public boolean isVoid() {
		return beans == null || (beans.size() == 0);
	}

}
