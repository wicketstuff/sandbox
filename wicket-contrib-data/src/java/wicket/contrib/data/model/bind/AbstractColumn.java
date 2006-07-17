package wicket.contrib.data.model.bind;

import wicket.model.IModel;
import wicket.model.PropertyModel;

/**
 * A convenient place to extend from to create new {@link IColumn}s.
 * 
 * @author Phil Kulak
 */
public abstract class AbstractColumn<T> implements IColumn<T>
{
	private String displayName;

	private String modelPath;

	private String orderByPath;

	private boolean allowOrderBy = true;

	/**
	 * Constructor that sets the orderByPath to the modelPath. Call
	 * {@link #setOrderByPath(String)} to change this default behavior.
	 * 
	 * @param displayName
	 *            the name that will be displayed as the header for this column
	 * @param modelPath
	 *            the OGNL path to the model
	 */
	public AbstractColumn(String displayName, String modelPath)
	{
		if (modelPath == null)
		{
			setAllowOrderBy(false);
		}
		
		if(displayName == null)
		{
			displayName = "";
		}
		
		this.displayName = displayName;
		this.modelPath = modelPath;
		this.orderByPath = modelPath;
	}

	public boolean allowOrderBy()
	{
		return allowOrderBy;
	}

	/**
	 * Sets weather or not users are allowed to order by this column.
	 * 
	 * @param allowOrderBy
	 * @return an IColumn to support chaining
	 */
	public IColumn<T> setAllowOrderBy(boolean allowOrderBy)
	{
		this.allowOrderBy = allowOrderBy;
		return this;
	}

	/**
	 * @see IColumn#getOrderByPath()
	 * @param orderByPath
	 * @return an IColumn to support chaining
	 */
	public IColumn<T> setOrderByPath(String orderByPath)
	{
		this.orderByPath = orderByPath;
		return this;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getModelPath()
	{
		return modelPath;
	}

	public String getOrderByPath()
	{
		return orderByPath;
	}
	
	protected PropertyModel<T> makePropertyModel(IModel<T> model)
	{
		return new PropertyModel<T>(model, getModelPath());
	}
}
