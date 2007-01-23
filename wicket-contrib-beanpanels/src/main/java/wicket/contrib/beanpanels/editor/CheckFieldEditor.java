package wicket.contrib.beanpanels.editor;

import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.form.CheckBox;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.PropertyModel;

public class CheckFieldEditor extends Panel {

	public CheckFieldEditor(String panelId, IPropertyMeta propertyMeta, BeanModel beanModel) {
		super(panelId);
		setRenderBodyOnly(true);
		IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());
		CheckBox field = new CheckBox("field",model);
		field.setEnabled( !propertyMeta.isReadOnly() );
		add(field);
	}

}
