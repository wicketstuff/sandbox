package wicket.contrib.beanpanels;

import java.io.Serializable;

import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.PropertyModel;
import wicket.model.ResourceModel;

public class ViewBeanPanel extends AbstractBeanPanel {

	public ViewBeanPanel(final String id, final Serializable bean)
	{
		this(id, new BeanModel(bean));
	}	
	
	public ViewBeanPanel(final String id, final BeanModel beanModel) {
		super(id, beanModel);
		
		WebMarkupContainer table = new WebMarkupContainer("table");
		WebMarkupContainer colLabel = new WebMarkupContainer("col-label");
		WebMarkupContainer colField = new WebMarkupContainer("col-field");

		add(table);
		table.add(colLabel);
		table.add(colField);

		ListView properties = new ListView("properties", beanModel.getPropertiesList() ) {
			protected void populateItem(ListItem item) 
			{
				// the bean property meta-data
				IPropertyMeta meta = (IPropertyMeta)item.getModelObject();

				item.add(new Label("label", new ResourceModel(meta.getName(), meta.getLabel())));

				item.add(new Label("field", new PropertyModel(beanModel.getBean(), meta.getName())));
			} };
		table.add(properties);
	}

}
