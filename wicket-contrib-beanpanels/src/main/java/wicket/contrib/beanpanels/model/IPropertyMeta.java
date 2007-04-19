package wicket.contrib.beanpanels.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */
public interface IPropertyMeta extends Serializable {

	
	Class getType();
	String getName();
	String getLabel();
	
	int getIndex();
	
	boolean isReadOnly();
	boolean isVisible();
	
	boolean isRequired();
	
	Integer getLength();
	
	List getChoices(); 
}
