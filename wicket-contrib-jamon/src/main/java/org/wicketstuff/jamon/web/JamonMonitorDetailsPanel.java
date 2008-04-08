package org.wicketstuff.jamon.web;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.wicketstuff.jamon.monitor.JamonRepository.getJamonRepository;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.jamonapi.FrequencyDist;
import com.jamonapi.Monitor;


/**
 * Panel that shows the details of a Monitor.
 * 
 * @author lars
 * 
 */
@SuppressWarnings( {"serial", "unchecked"})
public class JamonMonitorDetailsPanel extends Panel {

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
        /*
         * Keep the previous because we need to show the range from begin to end.
         */
        private transient FrequencyDist previous = null;

        private transient int index = 0;

        private FrequencyListView(String id, List list) {
            super(id, new ThrowAwayModel(list));
        }

        @Override
        protected void populateItem(ListItem item) {
            FrequencyDist frequencyDist = (FrequencyDist) item.getModelObject();
            item.add(new TimeWindowLabel("timeWindow", frequencyDist, previous));
            item.add(new Label("numberOfHits", String.valueOf(frequencyDist.getHits())));
            IndexBasedMouseOverMouseOutSupport.add(item, index);
            item.add(new AttributeModifier("class", true, new Model((index % 2 == 0) ? "even" : "odd")));
            previous = frequencyDist;
            index++;
        }
    }

    /*
     * Label for displaying the time frames.
     */
    private final static class TimeWindowLabel extends Label {

        public TimeWindowLabel(String id, FrequencyDist current, FrequencyDist previous) {
            super(id);
            String label = format("%s - %s", (previous == null ? "0.0" : valueOf(previous.getEndValue() + 0.1)),
                    (current.getEndValue() == Double.MAX_VALUE ? "MAX" : valueOf(current.getEndValue())));
            setModel(new ThrowAwayModel(label));
        }
    }

    /**
     * Construct.
     * 
     * @param id The id.
     * @param monitorLabel The label of the {@link Monitor} to show the details of.
     */
    public JamonMonitorDetailsPanel(String id, String monitorLabel) {
        super(id);
        setOutputMarkupId(true);
        setMarkupId(id);
        final Monitor monitor = getJamonRepository().findMonitorByLabel(monitorLabel);
        FrequencyDist[] frequencyDists = monitor.getRange().getFrequencyDists();
        add(new FrequencyListView(ID_OF_LIST_VIEW, asList(frequencyDists)));
        add(new Label(ID_OF_TITLE, format("Detail of Monitor %s in %s", monitorLabel, monitor.getUnits())));
    }
}
