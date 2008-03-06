package org.wicketstuff.jamon;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Jamon implementation of the {@link SortableDataProvider}.
 */
@SuppressWarnings("serial")
public class JamonProvider extends SortableDataProvider {
    private JamonRepository jamonRepository;

    public JamonProvider() {
        jamonRepository = newJamonRepository();
    }

    @SuppressWarnings("unchecked")
    public Iterator iterator(int first, int count) {
        //TODO implement sublisting
        return jamonRepository.getAll().iterator();
    }

    public IModel model(Object object) {
        return new Model((Serializable) object);
    }

    public int size() {
        return jamonRepository.count();
    }
    
    /**
     * Hook for subclasses to override the way the JamonRespository is created.
     * @return
     */
    protected JamonRepository newJamonRepository() {
        return new JamonRepository();
    }
}
