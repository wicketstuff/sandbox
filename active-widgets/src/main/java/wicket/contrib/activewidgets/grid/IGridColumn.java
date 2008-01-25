package wicket.contrib.activewidgets.grid;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import wicket.contrib.activewidgets.data.IGridSortState;
import wicket.contrib.activewidgets.system.SizeUnit;

/**
 * Represents a column in a {@link GridExtended}.
 * 
 * @author Matej Knopp
 * @author otaranenko
 */

public interface IGridColumn extends IDetachable {

	/**
	 * Returns the column id. Each column must have a unique Id. The only allowed characters in a
	 * column id are alphanumeric characters, dash, dot and underscore.
	 * 
	 * @return column identifier
	 */
	public String getId();

	/**
	 * Result of this method determines whether the column is sortable and in case it is, also
	 * determines the sort property. If the column is sortable and user sorts by it, the sort
	 * property can then be obtained through {@link IGridSortState}.
	 * 
	 * @see IGridSortState.ISortStateColumn#getPropertyName()
	 * @return sort property or <code>null</code> if the column is not sortable
	 */
	public String getSortProperty();

	/**
	 * Returns whether user will be able to resize this column. If the column is resizable,
	 * {@link #getSizeUnit()} must return {@link SizeUnit#PX}, otherwise an
	 * {@link IllegalStateException} will be thrown.
	 * 
	 * @return <code>true</code> if the column is resizable, <code>false</code> otherwise.
	 */
	public boolean isResizable();

	/**
	 * Returns the initial size of column. The unit is determined by {@link #getSizeUnit()}.
	 * 
	 * @return initial column size
	 */
	public int getInitialSize();

	/**
	 * Returns the unit in which sizes are specified. If the column is resizable, this method must
	 * always return {@link SizeUnit#PX}.
	 * 
	 * @return size unit
	 */
	public SizeUnit getSizeUnit();

	/**
	 * Returns the minimal size of resizable column. Return values not greater than zero will be
	 * ignored.
	 * 
	 * @return minimal size
	 */
	public int getMinSize();

	/**
	 * Returns the maximal size of resizable column. Return values not greater than zero will be
	 * ignored.
	 * 
	 * @return maximal size
	 */
	public int getMaxSize();

	/**
	 * Returns whether user will be allowed to reorder this column (i.e. change column position
	 * relative to other columns).
	 * 
	 * @return <code>true</code> if the column is reorderable, <code>false</code> otherwise
	 */
	public boolean isReorderable();

	/**
	 * Returns the CSS class for this column header. The class is applied to the appropriate
	 * &lt;th&gt; element in the grid.
	 * 
	 * @return CSS class for this column header or <code>null</code>
	 */
	public String getHeaderCssClass();

	/**
	 * Returns the cell specified by rowModel. The class is applied to the appropriate &lt;td&gt;
	 * element in the grid.
	 * 
	 * @param rowModel
	 *            model for given row
	 * @param rowNum
	 *            index of row for {@link DataGrid}, -1 for {@link TreeGrid}
	 * @return cell style class or <code>null</code>
	 */
	public String getCellCssClass(final IModel rowModel, final int rowNum);

	/**
	 * Returns the spanning value for cell specified by rowModel. The cell can span over certain
	 * number of adjacent cells. The value determines how many cell will this column take. Values
	 * less than 2 mean that no extra spanning will be done.
	 * 
	 * @param rowModel
	 *            model for given row
	 * @return colspan value
	 */
	public int getColSpan(IModel rowModel);

	/**
	 * Determines the behavior when there is more text in cell than it fits in it. If the method
	 * returns <code>true</code>, the text will be wrapped and row height increased. If the
	 * method returns <code>false</code>, the remaining part of text will be hidden.
	 * 
	 * @return whether this colulmn's text should wrap or not
	 */
	public boolean getWrapText();
	
}
