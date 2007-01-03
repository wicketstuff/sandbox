package wicket.extensions.markup.html.beanedit;

import java.io.Serializable;

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
