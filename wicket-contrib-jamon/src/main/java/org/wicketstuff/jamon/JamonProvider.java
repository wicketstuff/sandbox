package org.wicketstuff.jamon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.iterators.EmptyIterator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


/**
 * Jamon implementation of the {@link SortableDataProvider}.
 */
@SuppressWarnings({"serial", "unchecked"})
public class JamonProvider extends SortableDataProvider {
    private static final Iterator EMPTY_ITERATOR = new ArrayList().iterator();

    private JamonRepository jamonRepository;

    public JamonProvider() {
        jamonRepository = newJamonRepository();
    }

    public Iterator iterator(int first, int count) {
        List<SerializableMonitor> allMonitors = jamonRepository.getAll();
        int toIndex = allMonitors.size() < (first + count) ? allMonitors.size() : (first + count);
        if (first > toIndex) {
            return EMPTY_ITERATOR;
        } else {
            return allMonitors.subList(first, toIndex).iterator();
        }
    }

    public IModel model(Object object) {
        return new Model((Serializable) object);
    }

    public int size() {
        return jamonRepository.count();
    }

    /**
     * Hook for subclasses to override the way the JamonRespository is created.
     * 
     * @return
     */
    protected JamonRepository newJamonRepository() {
        return new JamonRepository();
    }
}
