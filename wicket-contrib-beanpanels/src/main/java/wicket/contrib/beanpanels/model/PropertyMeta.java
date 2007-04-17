package wicket.contrib.beanpanels.model;

import java.lang.reflect.Field;
import java.util.List;

import wicket.Component;

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
	private boolean required = false;
	private Integer length;
	private List choices = null;

	public PropertyMeta( String name, Class type ) { 
		this.name = name;
		this.type = type;
	}
	
	public PropertyMeta( String name, Class type, String label ) { 
		this(name,type);
		this.label = label;
	}
	
	public PropertyMeta( String name, Class type, String label, int index ) { 
		this(name,type,label);
		this.index = index;
	}
	
	public PropertyMeta( String name, Class type, String label, int index, boolean readOnly ) { 
		this(name,type,label,index);
		this.readOnly = readOnly;
	}
	
	public PropertyMeta( Field field, int index ) { 
		this.name = field.getName();
		this.label = field.getName();
		this.type = field.getType();
		this.index = index;
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

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public List getChoices() {
		return choices;
	}

	public void setChoices(List choices) {
		this.choices = choices;
	}


}
