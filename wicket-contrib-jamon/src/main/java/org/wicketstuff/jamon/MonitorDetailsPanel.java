package org.wicketstuff.jamon;

import static java.lang.String.format;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import com.jamonapi.FrequencyDist;
import com.jamonapi.Monitor;
/**
 * Panel that shows the details of the monitor.
 * 
 * @author lars
 *
 */
@SuppressWarnings({"serial", "unchecked"})
public class MonitorDetailsPanel extends Panel {

    /**
     * ID of the title of this panel.
     */
    private static final String ID_OF_TITLE = "detailTitle";
    /**
     * ID of the {@link ListView} of the frequencies.
     */
    private static final String ID_OF_LIST_VIEW = "detailRow";

    /*
     * ListView that shows the Frequencies.
     */
    private final static class FrequencyListView extends ListView {
        /** 
         * Keep the previous because we need to show the range
         * from begin to end.
         */
        private transient FrequencyDist previous = null;
        
        private FrequencyListView(String id, List list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem item) {
            FrequencyDist frequencyDist = (FrequencyDist) item.getModelObject();
            item.add(new TimeWindowLabel("timeWindow", frequencyDist, previous));
            item.add(new Label("numberOfHits", String.valueOf(frequencyDist.getHits())));
            previous = frequencyDist;
        }
        
        public void refresh(List<FrequencyDist> items) {
            previous = null;
            setModel(new ThrowAwayModel(items));
        }
    }

    /*
     * Label for displaying the time frames.
     */
    private final static class TimeWindowLabel extends Label {

        public TimeWindowLabel(String id, FrequencyDist current, FrequencyDist previous) {
            super(id);
            String label = format("%s - %s", (previous == null ? "0.0" : valueOf(previous.getEndValue() + 0.1)), (current.getEndValue() == Double.MAX_VALUE ? "MAX" : valueOf(current.getEndValue())));
            setModel(new ThrowAwayModel(label));
        }
    }

    /**
     * Construct.
     * @param id The id.
     */
    public MonitorDetailsPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        setMarkupId(id);
        add(new FrequencyListView(ID_OF_LIST_VIEW, new ArrayList<FrequencyDist>()));
        add(new Label(ID_OF_TITLE, "Click a Monitor to see its details..."));
    }

    /**
     * Refreshes this {@link MonitorDetailsPanel}. Typically called from an Ajax update.
     * @param monitorLabel The label of the {@link Monitor} to show the details of.
     */
    public void refresh(String monitorLabel) {
        final Monitor monitor = new JamonRepository().findMonitorByLabel(monitorLabel);
        FrequencyDist[] frequencyDists = monitor.getRange().getFrequencyDists();
        ((FrequencyListView) get(ID_OF_LIST_VIEW)).refresh(Arrays.asList(frequencyDists));
        ((Label) get(ID_OF_TITLE)).setModelObject(format("Detail of Monitor %s in %s", monitorLabel, monitor.getUnits()));
    }
}
