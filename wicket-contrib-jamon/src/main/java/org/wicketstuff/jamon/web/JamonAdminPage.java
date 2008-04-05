package org.wicketstuff.jamon.web;

import static org.wicketstuff.jamon.web.JamonMonitorTable.DEFAULT_ROWS_PER_PAGE;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.jamon.monitor.AlwaysSatisfiedMonitorSpecification;


/**
 * Main page of the JAMon admin interface. The monitors are shown in a Pageable table.
 * @author lars
 *
 */
@SuppressWarnings("serial")
public class JamonAdminPage extends WebPage {
    
    public static final String PATH_TO_STATISTICS_TABLE = "jamonStatistics";
    
    public static final String PATH_TO_MONITOR_DETAILS = "monitorDetails";

    /**
     * Construct.
     * @param rowsPerPage How many monitors per page should be rendered?
     */
    public JamonAdminPage(int rowsPerPage) {
        add(new JamonAdminPanel("adminPanel"));
        add(new JamonMonitorTable(PATH_TO_STATISTICS_TABLE, new AlwaysSatisfiedMonitorSpecification(), rowsPerPage));
        add(new EmptyMarkupContainer(PATH_TO_MONITOR_DETAILS));
    }
    /**
     * Construct. It will use the {@link JamonMonitorTable#DEFAULT_ROWS_PER_PAGE} for
     * determining how many monitors on one page are shown.
     */
    public JamonAdminPage() {
        this(DEFAULT_ROWS_PER_PAGE);
    }

}