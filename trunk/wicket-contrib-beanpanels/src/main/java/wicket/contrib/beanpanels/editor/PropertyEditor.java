package wicket.contrib.beanpanels.editor;

import wicket.Component;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;

public class PropertyEditor implements IPropertyEditor {

	/** boolean types. */
	protected static final Class[] BOOL_TYPES = new Class[] { Boolean.class, Boolean.TYPE };

	protected static final Class[] DATE_TYPES = new Class[] { java.util.Date.class, java.sql.Date.class };
	
	/** basic java types. */
	protected static final Class[] TEXT_TYPES = new Class[] { String.class, Number.class, Integer.TYPE, Double.TYPE, Long.TYPE, Float.TYPE, Short.TYPE, Byte.TYPE };

	/**
	 * Reduce constructor visibility to package level only, to avoid direct instantiation
	 */
	PropertyEditor() { }
	
	/**
	 * Does isAssignableFrom check on given class array for given type.
	 * @param types array of types
	 * @param type type to check against
	 * @return true if one of the types matched
	 */
	protected boolean checkAssignableFrom(Class[] types, Class type)
	{
		int len = types.length;
		for (int i = 0; i < len; i++)
		{
			if (types[i].isAssignableFrom(type))
			{
				return true;
			}
		}
		return false;
	}	
	
	public boolean isBaseType(Class type) { 
		return checkAssignableFrom( TEXT_TYPES, type ) ||
			   checkAssignableFrom( DATE_TYPES, type ) || 
			   checkAssignableFrom( BOOL_TYPES, type );
	}

	public Component create(String componentId, IPropertyMeta propertyMeta, BeanModel beanModel ) {
		return createEditor(componentId, propertyMeta, beanModel);
	}
	

	/**
	 * Gets a default property editor panel.
	 * @param panelId component id
	 * @param propertyMeta property descriptor
	 * @return a property editor
	 */
	protected Component createEditor(final String panelId, final IPropertyMeta propertyMeta, final BeanModel beanModel)
	{
		Component editor = null;
		final Class type = propertyMeta.getType();
	
		if( propertyMeta.getChoices() != null ) { 
			editor = new ChoiceFieldEditor(panelId, propertyMeta, beanModel);
		}
		else if (checkAssignableFrom(TEXT_TYPES, type))
		{
			editor = new TextFieldEditor(panelId, propertyMeta, beanModel);
		}
		else if (checkAssignableFrom(DATE_TYPES, type))
		{
			editor = new DateFieldEditor(panelId, propertyMeta, beanModel);
		}
		else if (checkAssignableFrom(BOOL_TYPES, type))
		{
			editor = new CheckFieldEditor(panelId, propertyMeta, beanModel);
		}
//		else { 
//			editor = new EmptyFieldEditor(panelId);
//		}
		return editor;
	}


	
	
}
