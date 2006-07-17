package wicket.contrib.data.model.bind;

import java.io.Serializable;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * An IColumn represents one column in a list of objects.
 * 
 * @author Phil Kulak
 */
public interface IColumn<T> extends Serializable
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
	 * Note that the model is NOT a property model, but the entire bean for
	 * the row. It's up to the column to decide how to map itself to a field(s).
	 * 
	 * @param id
	 *            the id the returned component should use
	 * @param model
	 *            the model the returned component should use
	 * @return a component representing each data cell for this column
	 */
	public Component getComponent(MarkupContainer parent, String id, IModel<T> model);
}
