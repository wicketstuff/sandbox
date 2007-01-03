package wicket.extensions.markup.html.beanedit;

import java.lang.reflect.Field;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */

public class PropertyMeta implements IPropertyMeta {

	private String label;
	private String name;
	private int index ;
	private boolean readOnly;
	private Class type;
	
	public PropertyMeta( Field field, int ndx, boolean readOnly ) { 
		this.name = field.getName();
		this.type = field.getType();
		this.readOnly = readOnly;
		this.index = ndx;
		
		Label annotation = field.getAnnotation(Label.class);
		this.label = annotation != null ? annotation.value() : name;
	}

	public String getLabel() {
		return label;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public Class getType() {
		return type;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public boolean isVisible() {
		return true;
	}
	
}
