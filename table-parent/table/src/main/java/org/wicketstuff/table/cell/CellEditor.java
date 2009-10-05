package org.wicketstuff.table.cell;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;

public interface CellEditor extends Serializable
{
	/**
	 * 
	 * @param model
	 *            Represent an cell position from TableModel. Read and write
	 *            operations will to be delegated to TableModel API.
	 * @param parent
	 *            The selectable list item where cell component will to be
	 *            attached. Has informations like selection that may be useful.
	 */
	Component getEditorComponent(String id, IModel model, SelectableListItem parent, int row,
			int column);
}
