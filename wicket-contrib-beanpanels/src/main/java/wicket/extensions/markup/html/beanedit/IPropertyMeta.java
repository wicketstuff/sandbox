package wicket.extensions.markup.html.beanedit;

import java.io.Serializable;

import wicket.model.IModel;

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
	
}
