package wicketstuff.crud;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * @author ivaynberg
 * 
 */
public interface ICreateBeanModelFactory extends Serializable
{
	IModel newModel();
}
