package wicket.contrib.beanpanels;

import java.util.List;

import wicket.Component;
import wicket.contrib.beanpanels.model.BeanListModel;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Fragment;
import wicket.model.ResourceModel;

public class BeanListPanel extends AbstractBeanPanel {
	
	private BeanListModel beanListModel;
	
	/**
	 * Construct.
	 * @param id component id
	 * @param bean JavaBean to be edited or displayed
	 */
	public BeanListPanel(String id, List beans)
	{
		this(id, new BeanListModel(beans));
	}
	
	/**
	 * Construct.
	 * @param id component id
	 * @param beanModel model with the JavaBean to be edited or displayed
	 */
	public BeanListPanel(String id, BeanListModel beanListModel)
	{
		super(id, beanListModel);
		this.beanListModel = beanListModel;
		setRenderBodyOnly(true);

		if (beanListModel.getBeanModels() == null || beanListModel.isVoid()) {
			add(new Label("content", "No elements available"));	//TODO localizeme
		}
		else {
			add(new TableFragment("content", beanListModel));	
		}
	}
	
	private class TableFragment extends Fragment {
	
		TableFragment(String id, BeanListModel beanListModel) {
			super(id, "tableFragment");
			BeanModel firstBeanModel = ((BeanModel)(beanListModel.getBeanModels().get(0)));
			
			/*
			 * Add the bean panel table header
			 */
			Fragment header = newHeader("header", firstBeanModel);
			if (header == null)
			{
				throw new NullPointerException("header must be not null");
			}
			add( header );
			
			add( new ListView("propertyKeys", firstBeanModel.getProperties() ) {
				
				protected void populateItem(ListItem item) 
				{
					IPropertyMeta propertyMeta = (IPropertyMeta)item.getModelObject();
					ResourceModel labelModel = new ResourceModel(propertyMeta.getName(), propertyMeta.getLabel());
					item.add(new Label("displayName", labelModel));
				} } );
			
			add( new ListView("feeds", beanListModel.getBeanModels()) {
				protected void populateItem(ListItem item) 
				{
					final BeanModel beanModel = (BeanModel)item.getModelObject();
					item.add( new ListView("propertyValues", beanModel.getProperties() ) {
						
						protected void populateItem(ListItem item) 
						{
							IPropertyMeta propertyMeta = (IPropertyMeta)item.getModelObject();
							Component propertyEditor = newPropertyEditor("editor", propertyMeta, beanModel);
							if (propertyEditor == null)
							{
								throw new NullPointerException("propertyEditor must be not null");
							}
							item.add(propertyEditor);
						} } );
				} 
			});
		}

	}
	
	
	/**
	 * Gets a default property editor panel.
	 * @param panelId component id
	 * @param propertyMeta property descriptor
	 * @return a property editor
	 */
	protected WebMarkupContainer newDefaultEditor(final String panelId, final IPropertyMeta propertyMeta, final BeanModel beanModel)
	{
		return new ViewFieldFragment(panelId, propertyMeta, beanModel);
	}
	

	
}
