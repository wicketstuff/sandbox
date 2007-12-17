package wicket.contrib.beanpanels.editor;

import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.panel.Panel;
import wicket.model.PropertyModel;

public class ChoiceFieldEditor extends Panel {

	public ChoiceFieldEditor( String id, IPropertyMeta property, BeanModel model ) { 
		super(id);
		// add the drop down field
		DropDownChoice field = new DropDownChoice("field", new PropertyModel(model.getBean(),property.getName()));
		field.setChoices(property.getChoices());
		field.setEnabled( !property.isReadOnly() );
		add(field);
	}
}
