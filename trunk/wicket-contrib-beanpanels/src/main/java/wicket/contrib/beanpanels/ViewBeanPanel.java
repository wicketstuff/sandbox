package wicket.contrib.beanpanels;

import java.io.Serializable;

import wicket.contrib.beanpanels.editor.PropertyEditorFactory;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.PropertyModel;
import wicket.model.ResourceModel;
import wicket.util.lang.PropertyResolver;

public class ViewBeanPanel extends AbstractBeanPanel {
	
	final private BeanModel beanModel;
	final private boolean cascade;
	
	public ViewBeanPanel(final String id, final Serializable bean)
	{
		this(id, new BeanModel(bean), true);
	}	

	public ViewBeanPanel(final String id, final BeanModel beanModel )
	{
		this(id, beanModel, true );
	}	
	
	public ViewBeanPanel(final String id, final BeanModel beanModel, final boolean cascade) {
		super(id, beanModel);
		this.beanModel = beanModel;
		this.cascade = cascade;
		
		ListView properties = new ListView("properties", beanModel.getProperties() ) {
			protected void populateItem(ListItem item) 
			{
				item.setRenderBodyOnly(true);
				// the bean property meta-data
				IPropertyMeta meta = (IPropertyMeta)item.getModelObject();
				
				item.add(new Label("label", new ResourceModel(meta.getName(), meta.getLabel())));
				
				Object value = PropertyResolver.getValue(meta.getName(), beanModel.getBean());

				if( !cascade || value == null || PropertyEditorFactory.get().isBaseType(meta.getType()) ) { 
					item.add(new Label("field", new PropertyModel(beanModel.getBean(), meta.getName())));
				}
				else { 
					ViewBeanPanel view = new ViewBeanPanel("field", new BeanModel(value), false);
					view.setRenderBodyOnly(true);
					item.add(view);
				}

			} };

		add(properties);			

	}


}
