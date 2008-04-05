package org.wicketstuff.jamon.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.wicketstuff.jamon.monitor.JamonRepository;
import org.wicketstuff.jamon.monitor.MonitorSpecification;

import com.jamonapi.Monitor;


/**
 * Jamon implementation of the {@link SortableDataProvider}.
 */
@SuppressWarnings({"serial", "unchecked"})
public class JamonProvider extends SortableDataProvider {
    private static final Iterator EMPTY_ITERATOR = new ArrayList().iterator();

    private JamonRepository jamonRepository;

    private final MonitorSpecification specification;

    public JamonProvider(MonitorSpecification specification) {
        this.specification = specification;
        jamonRepository = JamonRepository.getJamonRepository();
        setSort("label", true);
    }

    public Iterator iterator(int first, int count) {
        List<Monitor> allMonitors = jamonRepository.find(specification);
        int toIndex = allMonitors.size() < (first + count) ? allMonitors.size() : (first + count);
        if (first > toIndex) {
            return EMPTY_ITERATOR;
        } else {
            List<Monitor> monitors = allMonitors.subList(first, toIndex);
            final String sortProperty = this.getSort().getProperty();
            final boolean ascending = this.getSort().isAscending();
            Collections.sort(monitors, new PropertyModelObjectComparator(ascending, sortProperty));
            return monitors.iterator();
        }
    }

    public IModel model(Object object) {
        return new ThrowAwayModel(object);
    }

    public int size() {
        return jamonRepository.count();
    }
}
