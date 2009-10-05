package org.wicketstuff.table;

import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;

/**
 * Image attached on column header to display the ordering rule used to that
 * column.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class OrderingImage extends Image
{
	public static final ResourceReference ARROW_UP = new ResourceReference(Table.class,
			"res/arrow_up.png");
	public static final ResourceReference ARROW_OFF = new ResourceReference(Table.class,
			"res/arrow_off.png");
	public static final ResourceReference ARROW_DOWN = new ResourceReference(Table.class,
			"res/arrow_down.png");
	private Table table;
	private int columnIndex;

	public OrderingImage(String id, int columnIndex, Table table)
	{
		super(id);
		this.columnIndex = columnIndex;
		this.table = table;
	}

	@Override
	protected ResourceReference getImageResourceReference()
	{
		if (table.getRowSorter() != null && table.getRowSorter().getSortKeys() != null
				&& table.getRowSorter().getSortKeys().size() > 0
				&& ((SortKey)table.getRowSorter().getSortKeys().get(0)).getColumn() == columnIndex)
		{
			SortKey sortKey = (SortKey)table.getRowSorter().getSortKeys().get(0);
			if (sortKey.getSortOrder() == SortOrder.ASCENDING)
			{
				return ARROW_UP;
			}
			else if (sortKey.getSortOrder() == SortOrder.DESCENDING)
			{
				return ARROW_DOWN;
			}
			else
			{
				return ARROW_OFF;
			}
		}
		else
		{
			return null;
		}

	}

	@Override
	public boolean isVisible()
	{
		return getImageResourceReference() != null;
	}
}