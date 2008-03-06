package org.wicketstuff.jamon;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;


/**
 * Main page that lists all the Jamon monitors.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class StatisticsPage extends WebPage {

    public StatisticsPage() {
        
        IColumn[] columns = createColumns();

        DefaultDataTable table = new DefaultDataTable("jamonStatistics", columns, new JamonProvider(), 1);
        add(table);
    }

    private IColumn[] createColumns() {
        IColumn monitorName = new PropertyColumn(new Model("Monitor name"), "label");
        IColumn avg = new PropertyColumn(new Model("Avarage"), "avg");
        IColumn[] columns = new IColumn[] {monitorName, avg};
        return columns;
    }
}
