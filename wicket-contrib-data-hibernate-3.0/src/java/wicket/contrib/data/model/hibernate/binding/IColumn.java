package wicket.contrib.data.model.hibernate.binding;

import java.io.Serializable;

import wicket.Component;
import wicket.model.IModel;

/**
 * An IColumn represents one column in a list of objects.
 * 
 * @author Phil Kulak
 */
public interface IColumn extends Serializable
{
	/**
	 * @return the name to display in the header of this column
	 */
	public String getDisplayName();

	/**
	 * @return the OGNL path to the model for this column
	 */
	public String getModelPath();

	/**
	 * @return the OGNL path to the field that this column's ordering should be
	 *         based on
	 */
	public String getOrderByPath();

	/**
	 * @return true if users are allowed to order by this column
	 */
	public boolean allowOrderBy();

	/**
	 * Columns are responsible for providing their own component representation.
	 * 
	 * @param id
	 *            the the returned component should use
	 * @param model
	 *            the model the returned component should use
	 * @return a component representing each data cell for this column
	 */
	public Component getComponent(String id, IModel model);
}
