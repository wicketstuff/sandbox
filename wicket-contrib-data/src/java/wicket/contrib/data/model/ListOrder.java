package wicket.contrib.data.model;

import java.io.Serializable;

/**
 * A simple class to hold the ordering information of one field in a list.
 * 
 * @author Phil Kulak
 */
public class ListOrder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean ascending = true;

	private String field;
	
	/**
	 * Creats a new ListOrder on the given field.
	 */
	public ListOrder(String field)
	{
		this.field = field;
	}
	
	/**
	 * Reverses the order.
	 */
	public void switchOrder()
	{
		ascending = !ascending;
	}
	
	/**
	 * Returns true if the current field is marked "ascending".
	 */
	public boolean isAscending()
	{
		return ascending;
	}

	/**
	 * Returns the string representation of the field.
	 */
	public String getField()
	{
		return field;
	}
	
	/**
	 * Compares based on field name.
	 */
	@Override
	public boolean equals(Object rhs)
	{
		if (!(rhs instanceof ListOrder))
		{
			return false;
		}
		return field.equals(((ListOrder) rhs).getField());
	}
}
