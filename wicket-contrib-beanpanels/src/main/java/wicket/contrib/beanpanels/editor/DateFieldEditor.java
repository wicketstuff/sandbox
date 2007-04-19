package wicket.contrib.beanpanels.editor;

import wicket.ResourceReference;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;
import wicket.extensions.markup.html.datepicker.DatePicker;
import wicket.extensions.markup.html.datepicker.DatePickerSettings;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.PropertyModel;

public class DateFieldEditor extends Panel {

	public DateFieldEditor( String panelId, IPropertyMeta propertyMeta, BeanModel beanModel ) { 
		super(panelId);
		setRenderBodyOnly(true);
		Class type = propertyMeta.getType();
		IModel model = new PropertyModel(beanModel.getBean(),propertyMeta.getName());
		TextField field = new TextField("field", model, type);
		field.setEnabled( !propertyMeta.isReadOnly() );
		add(field);
		
		// .. and the date picker
		DatePickerSettings settings = new DatePickerSettings();
		settings.setStyle( settings.newStyleWinter() );
		settings.setIcon( new ResourceReference(this.getClass(), "calendar.gif") );
		add(new DatePicker( "datePicker", field, settings));	
		
		
	}
}
