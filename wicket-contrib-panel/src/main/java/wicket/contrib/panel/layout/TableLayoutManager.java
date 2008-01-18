package wicket.contrib.panel.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Response;

import wicket.contrib.panel.LayoutManager;
import wicket.contrib.panel.LayoutModifier;

public class TableLayoutManager implements LayoutManager, Serializable {

	protected static final long serialVersionUID = 1L;
	protected int _columns;

	protected int _currentColumn;
	protected int _currentRow;
	protected boolean _debug;
	protected boolean _displayCellForHiddenComponent;
	protected int border;
	protected int width;
	protected String _rowStyle;
	protected int [] _columnWidth;
	protected java.util.List<LayoutModifier> modifiers;
	protected CellLayout cell;

	public TableLayoutManager() {
		this(1);
	}

	public TableLayoutManager(int columns) {
		setColumns(columns);
		//_debug = true;
		border = 0;
		width = CellLayout.WIDTH_REST;
		_displayCellForHiddenComponent = true;
	}

	public void setDisplayCellForHiddenComponent (boolean value) {
		_displayCellForHiddenComponent = value;
	}
	public boolean getDisplayCellForHiddenComponent () {
		return _displayCellForHiddenComponent;
	}

	public void setColumns(int columns) {
		if (columns <= 0) columns = 1;
		this._columns = columns;
		_columnWidth = new int[columns];
		for (int i = 0 ; i < columns; i ++) {
			_columnWidth[i]  = -1;
		}
	}
/**
 *
 * @param width in pixels
 * @param column 1 based
 * @param row not used
 */
	public TableLayoutManager setColumnWidth(int width, int column, int row) {
		if (column <= 0 || column > _columns) return this;
		_columnWidth[column - 1] = width;
		return this;
	}

	public void setRowStyle(String style, int row){
		_rowStyle = style;
	}

	public void startLayout(Response response) {
		_currentColumn = 0;
		_currentRow = 0;
		if (_debug) response.write("<!-- start layout -->\n");
		int border = this.border;
		if (_debug) {
			border = border + 1;
		}

		String layout = "<table";
		layout += " border=\"" + border + "\"";
		if (width > 0) {
			layout += " width=\"" + width + "\"";
		} else if (width == CellLayout.WIDTH_REST) {
			layout += " width=\"100%\"";
		}

		layout += " cellpadding=\"0\"";
		layout += " cellspacing=\"0\"";
		layout += ">\n";
		response.write(layout);
		if (_debug) response.write("<!-- END OF start layout -->\n");
	}

	public void startLayout(Response response, Component component) {
		if (!(getDisplayCellForHiddenComponent() || component.isVisible())) return;
		if (_debug) response.write("<!-- start component " + component.getMarkupId() + " layout -->\n");
		boolean merge = (cell!=null && cell.mergeWithFollowing);
		if (!merge) {
			_currentColumn ++;
			if (_currentColumn  == 1) {
				_currentRow ++;
				response.write("\t<tr" + (_rowStyle !=null ? " class=\"" + _rowStyle + "\"" : "" )+ ">\n");//FIXME saki htmlesacepe attribute
			}
		}

		cell = new CellLayout();
		cell.width = _columnWidth[_currentColumn - 1];
		java.util.List <LayoutModifier> modifiers = getModifiers(component);
		if (modifiers!=null) {
			for (LayoutModifier modifier : modifiers) {
				modifier.modify(cell);
			}
		}

		if (!merge) {
			if (cell.colspan == CellLayout.COLSPAN_REST)
				cell.colspan = _columns - _currentColumn + 1;
			String layout = "\t\t<td";
			if (cell.width > 0)
				layout += " width=\"" + cell.width + "\"";
			if (cell.width == CellLayout.WIDTH_REST) {
				layout += " width=\"rest\"";
			}
			if (cell.align > CellLayout.ALIGN_LEFT) {
				layout += " align=\"" + ( cell.align == CellLayout.ALIGN_CENTER ? "center" : "right" ) + "\"";
			}
			if (cell.colspan > 1) {
				_currentColumn += cell.colspan - 1;
				layout += " colspan=\"" + cell.colspan + "\"";
			}
			layout += ">";
			response.write(layout);
		}
		if (_debug) response.write("<!-- END OF start component " + component.getMarkupId() + " layout -->\n");
	}

	protected List<LayoutModifier> getModifiers(Component component) {
		if (modifiers == null) return null;
		java.util.List<LayoutModifier> list = null;
		for (LayoutModifier modifier : modifiers) {
			if (modifier.forComponent(component)) {
				if (list == null) list = new ArrayList<LayoutModifier>();
				list.add(modifier);
			}
		}
		return list;
	}

	public void endLayout(Response response, Component component) {
		boolean visible = component.isVisible();
		if (!getDisplayCellForHiddenComponent()) {
			if (!visible) return;
		} else {
			if (!visible)
				response.write("&nbsp;");
		}
		if (_debug) response.write("<!-- end component " + component.getMarkupId() + " layout -->\n");
		if (!cell.mergeWithFollowing) {
			response.write("</td>\n");
			if (_currentColumn  == _columns) {
				response.write("\t</tr>\n");
				_currentColumn = 0;
			}
		}
		if (_debug) response.write("<!-- END OF end component " + component.getMarkupId() + " layout -->\n");
	}

	public void endLayout(Response response) {
		if (_debug) response.write("<!-- end layout -->\n");
		response.write("</table>\n");
		if (_debug) response.write("<!-- END OF end layout -->");
		cell = null;
	}

	public void addModifier(LayoutModifier modifier) {
		if (modifiers == null) modifiers = new ArrayList<LayoutModifier>();
		modifiers.add(modifier);
	}

	public TableLayoutManager setBorder(int i) {
		border = i;
		return this;
	}

	public TableLayoutManager setWidth(int i) {
		width = i;
		return this;
	}

	public void setDebug(boolean value) {
		this._debug = value;
	}

	public boolean getDebug() {
		return this._debug;
	}
}
