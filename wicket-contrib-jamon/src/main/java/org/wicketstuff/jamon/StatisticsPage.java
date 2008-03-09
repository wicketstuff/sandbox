package org.wicketstuff.jamon;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.ResourceModel;


/**
 * Main page that lists all the Jamon monitors.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class StatisticsPage extends WebPage {
    
    public final static int DEFAULT_ROWS_PER_PAGE = 40;
    public StatisticsPage(int rowsPerPage) {
        
        IColumn[] columns = createColumns();
        DefaultDataTable table = new DefaultDataTable("jamonStatistics", columns, new JamonProvider(), rowsPerPage);
        add(table);
    }
    public StatisticsPage() {
        this(DEFAULT_ROWS_PER_PAGE);
    }

    private IColumn[] createColumns() {
        List<IColumn> cols = new ArrayList<IColumn>();
        cols.add(createColumn("label", "label"));
        cols.add(createColumn("hits", "hits"));
        cols.add(createColumn("average", "avg"));
        cols.add(createColumn("total", "total"));
        cols.add(createColumn("stdDev", "stdDev"));

        cols.add(createColumn("min", "min"));
        cols.add(createColumn("max", "max"));
        
        cols.add(createColumn("active", "active"));
        cols.add(createColumn("avgActive", "avgActive"));
        cols.add(createColumn("maxActive", "maxActive"));

        //TODO What do these mean?
//        cols.add(createColumn("avgGlobalActive", "avgGlobalActive"));
//        cols.add(createColumn("avgPrimaryActive", "avgPrimaryActive"));
        
        cols.add(createColumn("firstAccess", "firstAccess"));
        cols.add(createColumn("lastAccess", "lastAccess"));
        cols.add(createColumn("lastValue", "lastValue"));
        
//        cols.add(createColumn("units", "units"));
        
        return cols.toArray(new IColumn[cols.size()]);
    }

    private PropertyColumn createColumn(String resourceKey, String propertyName) {
        return new PropertyColumn(new ResourceModel(String.format("wicket.jamon.%s", resourceKey)), propertyName);
    }
}
