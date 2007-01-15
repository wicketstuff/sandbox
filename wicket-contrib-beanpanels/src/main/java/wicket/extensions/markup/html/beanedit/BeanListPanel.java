package wicket.extensions.markup.html.beanedit;

import java.util.List;

import wicket.extensions.markup.html.beanedit.AbstractBeanPanel.ButtonToMoreDetails;
import wicket.extensions.markup.html.beanedit.AbstractBeanPanel.CheckFieldFragment;
import wicket.extensions.markup.html.beanedit.AbstractBeanPanel.DateFieldFragment;
import wicket.extensions.markup.html.beanedit.AbstractBeanPanel.TextFieldFragment;
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

		addProperties();
	}
	
	protected void addProperties() {
		
		if (beanListModel.getBeanModels() == null || beanListModel.isVoid())
			return;
		
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
		
		/*
		 * Add the properties
		 */
		add( new ListView("propertyKeys", firstBeanModel.getPropertiesList() ) {
			
			protected void populateItem(ListItem item) 
			{
				IPropertyMeta propertyMeta = (IPropertyMeta)item.getModelObject();
				item.add(new Label("displayName", propertyMeta.getLabel()));				
			} } );
		
		
		add( new ListView("feeds", beanListModel.getBeanModels()) {
			
			protected void populateItem(ListItem item) 
			{
				final BeanModel beanModel = (BeanModel)item.getModelObject();
				item.add( new ListView("propertyValues", beanModel.getPropertiesList() ) {
					
					protected void populateItem(ListItem item) 
					{
						IPropertyMeta propertyMeta = (IPropertyMeta)item.getModelObject();
						WebMarkupContainer propertyEditor = newPropertyEditor("editor", propertyMeta, beanModel);
						if (propertyEditor == null)
						{
							throw new NullPointerException("propertyEditor must be not null");
						}
						item.add(propertyEditor);
						
					} } );
			} 
		});
		
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
