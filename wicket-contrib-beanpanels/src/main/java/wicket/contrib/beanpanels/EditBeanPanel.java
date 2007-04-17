package wicket.contrib.beanpanels;

import java.io.Serializable;

import wicket.Component;
import wicket.contrib.beanpanels.editor.PropertyEditorFactory;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.PropertyModel;
import wicket.model.ResourceModel;
import wicket.util.lang.PropertyResolver;

public class EditBeanPanel extends AbstractBeanPanel {

	public EditBeanPanel( final String id, final Serializable bean) {
		this(id, new BeanModel(bean));
	}
	
	public EditBeanPanel( final String id, final BeanModel model ) { 
		this(id,model,true);
	}
			
	public EditBeanPanel( final String id, final BeanModel model, final boolean cascade ) { 
		super(id,model);

		ListView properties = new ListView("properties", model.getProperties() ) {
			protected void populateItem(ListItem item) 
			{
				item.setRenderBodyOnly(true);
				// the bean property meta-data
				IPropertyMeta meta = (IPropertyMeta)item.getModelObject();
				
				item.add(new Label("label", new ResourceModel(meta.getName(), meta.getLabel())));
				
				Component editor = PropertyEditorFactory.get().create("editor", meta, model);
				if( editor != null ) { 
					item.add(editor);
				}
				else if( cascade ) { 
					Object obj = PropertyResolver.getValue(meta.getName(), model.getBean());
					//TODO here it is required a strategy to handle null object, an alternative could be to create a new object instance to enable data entry 
					if( obj != null ) { 
						EditBeanPanel view = new EditBeanPanel("editor", new BeanModel(obj), false);
						view.setRenderBodyOnly(true);
						item.add(view);
					}
					else { 
						item.add(new Label("editor", "(null object)"));
					}
				} else { 
					item.add(new Label("editor", new PropertyModel(model.getBean(), meta.getName())));
				}

			} };

		add(properties);		
	}

}
