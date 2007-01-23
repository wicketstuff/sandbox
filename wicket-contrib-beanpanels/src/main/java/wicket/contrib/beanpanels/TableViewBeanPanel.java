package wicket.contrib.beanpanels;

import java.io.Serializable;

import wicket.contrib.beanpanels.editor.PropertyEditorFactory;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Fragment;
import wicket.model.PropertyModel;
import wicket.model.ResourceModel;
import wicket.util.lang.PropertyResolver;

public class TableViewBeanPanel extends AbstractBeanPanel {
	
	final private BeanModel beanModel;
	private boolean cascade;
	
	public TableViewBeanPanel(final String id, final Serializable bean)
	{
		this(id, new BeanModel(bean), true);
	}	

	public TableViewBeanPanel(final String id, final BeanModel beanModel )
	{
		this(id, beanModel, true );
	}	
	
	public TableViewBeanPanel(final String id, final BeanModel beanModel, boolean cascade) {
		super(id, beanModel);
		this.beanModel = beanModel;
		this.cascade = cascade;
		
		if( cascade ) { 
			add( new TableFragment("content") );
		}
		else { 
			add( new BodyFragment("content") );
		}
	}

	class TableFragment extends Fragment {

		public TableFragment(String id) {
			super(id, "TableFragment");
			setRenderBodyOnly(true);
			
			add( new BodyFragment("tbody") );
		} 
		
	}
	
	class BodyFragment extends Fragment {

		public BodyFragment(String id) {
			super(id, "BodyFragment");
			setRenderBodyOnly(true);
			
			ListView properties = new ListView("properties", beanModel.getProperties() ) {
				protected void populateItem(ListItem item) 
				{
					item.setRenderBodyOnly(true);
					// the bean property meta-data
					IPropertyMeta meta = (IPropertyMeta)item.getModelObject();
					Object value = PropertyResolver.getValue(meta.getName(), beanModel.getBean());

					if( !cascade || value == null || PropertyEditorFactory.get().isBaseType(meta.getType()) ) { 
						item.add( new SimpleFragment("row", meta) );
					}
					else { 
						item.add( new CascadeFragment("row", meta) );
					}

				} };

			add(properties);			
		} 
	}
	
	class SimpleFragment extends Fragment {

		public SimpleFragment(String id, IPropertyMeta meta) {
			super(id, "SimpleRow");
			setRenderBodyOnly(true);
			
			add(new Label("label", new ResourceModel(meta.getName(), meta.getLabel())));
			add(new Label("field", new PropertyModel(beanModel.getBean(), meta.getName())));
		} 
		
	}
	
	class CascadeFragment extends Fragment {

		public CascadeFragment(String id, IPropertyMeta meta) {
			super(id, "ComplexRow");
			setRenderBodyOnly(true);

			add(new Label("label", new ResourceModel(meta.getName(), meta.getLabel())));

			Object val = PropertyResolver.getValue(meta.getName(), beanModel.getBean());
			TableViewBeanPanel view = new TableViewBeanPanel("view", new BeanModel(val), false);
			view.setRenderBodyOnly(true);
			add(view);
		} 
		
	}
}
