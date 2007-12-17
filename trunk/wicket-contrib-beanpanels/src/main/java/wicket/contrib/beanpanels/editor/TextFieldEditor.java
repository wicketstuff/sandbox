package wicket.contrib.beanpanels.editor;

import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.PropertyModel;

public class TextFieldEditor extends Panel {

	public TextFieldEditor( String panelId, IPropertyMeta propertyMeta, BeanModel beanModel ) {
		super(panelId);
		setRenderBodyOnly(true);
		Class type = propertyMeta.getType();
		IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());
		TextField field = new TextField("field", model, type);
		field.setEnabled( !propertyMeta.isReadOnly() );
		add(field);
	}

}
