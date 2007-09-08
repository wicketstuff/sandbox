package wicketstuff.crud;

import java.io.Serializable;
import java.util.List;

public interface PropertySource extends Serializable
{
	List<Property> getProperties();
}
