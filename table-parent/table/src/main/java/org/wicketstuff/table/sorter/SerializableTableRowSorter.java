package org.wicketstuff.table.sorter;

import java.io.Serializable;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Adapter class to TableRowSorte since it has to be serializable to work with
 * Table
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class SerializableTableRowSorter extends TableRowSorter implements Serializable {

    public SerializableTableRowSorter(TableModel tableModel) {
	super(tableModel);
    }

}
