package wicketstuff.crud;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an object that can provide a list of properties to the crud panel
 * 
 * @author igor.vaynberg
 * 
 */
public interface PropertySource extends Serializable
{
	/**
	 * @return list of properties
	 */
	List<Property> getProperties();
}
