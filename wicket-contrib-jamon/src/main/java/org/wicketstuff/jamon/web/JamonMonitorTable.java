package org.wicketstuff.jamon.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.jamon.monitor.MonitorSpecification;

import com.jamonapi.Monitor;


/**
 * {@link DefaultDataTable} that will create a row for each {@link Monitor}.
 * 
 * @author lars
 * 
 */
@SuppressWarnings("serial")
public class JamonMonitorTable extends DefaultDataTable {
    public static final int DEFAULT_ROWS_PER_PAGE = 40;

    public JamonMonitorTable(String id, MonitorSpecification specification, int maxRowsPerPage) {
        super(id, createColumns(), new JamonProvider(specification), maxRowsPerPage);
        setOutputMarkupId(true);
        setMarkupId(id);
    }

    private static IColumn[] createColumns() {
        List<IColumn> cols = new ArrayList<IColumn>();
        cols.add(createColumnWithLinkToDetail("label", "label"));
        cols.add(createColumn("hits", "hits"));
        cols.add(createColumn("average", "avg"));
        cols.add(createColumn("total", "total"));
        cols.add(createColumn("stdDev", "stdDev"));

        cols.add(createColumn("min", "min"));
        cols.add(createColumn("max", "max"));

        cols.add(createColumn("active", "active"));
        cols.add(createColumn("avgActive", "avgActive"));
        cols.add(createColumn("maxActive", "maxActive"));

        cols.add(createColumn("firstAccess", "firstAccess"));
        cols.add(createColumn("lastAccess", "lastAccess"));
        cols.add(createColumn("lastValue", "lastValue"));

        return cols.toArray(new IColumn[cols.size()]);
    }

    @Override
    protected Item newRowItem(String id, int index, IModel model) {
        Item rowItem = super.newRowItem(id, index, model);
        return IndexBasedMouseOverMouseOutSupport.add(rowItem, rowItem.getIndex());
    }

    private static PropertyColumn createColumn(String resourceKey, String propertyName) {
        return new PropertyColumn(getResourceModelForKey(resourceKey), propertyName, propertyName);
    }

    private static PropertyColumn createColumnWithLinkToDetail(String resourceKey, String propertyName) {
        return new PropertyColumn(getResourceModelForKey(resourceKey), propertyName, propertyName) {
            @Override
            public void populateItem(Item item, String componentId, IModel model) {
                item.add(new LinkToDetailPanel(componentId, createLabelModel(model)));
            }

        };
    }

    private static ResourceModel getResourceModelForKey(String resourceKey) {
        return new ResourceModel(String.format("wicket.jamon.%s", resourceKey));
    }

}
