package wicketstuff.crud;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * Factory that creates models that contain a bean used to populate a new
 * instance of the bean
 * 
 * @author ivaynberg
 * 
 */
public interface ICreateBeanModelFactory extends Serializable
{
	/**
	 * 
	 * @return model that contains a bean that will be used to populate values
	 *         of a new instance
	 */
	IModel newModel();
}
