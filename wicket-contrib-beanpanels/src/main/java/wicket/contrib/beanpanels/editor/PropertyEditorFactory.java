package wicket.contrib.beanpanels.editor;

public class PropertyEditorFactory {

	private static IPropertyEditor propertyEditor = new PropertyEditor(); 

	public static IPropertyEditor get() { 
		return propertyEditor;
	}
	
}
