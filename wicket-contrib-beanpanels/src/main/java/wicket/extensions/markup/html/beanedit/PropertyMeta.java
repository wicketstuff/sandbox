package wicket.extensions.markup.html.beanedit;

import java.lang.reflect.Field;

import wicket.model.IModel;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */

public class PropertyMeta implements IPropertyMeta {

	protected IModel label;
	protected String name;
	protected int index ;
	protected boolean readOnly;
	protected Class type;
	
	public PropertyMeta( Field field, int ndx, boolean readOnly ) { 
		this.name = field.getName();
		this.type = field.getType();
		this.readOnly = readOnly;
		this.index = ndx;
		
		init(field);
	}

	protected void init(Field field) {

	}

	public IModel getLabel() {
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
