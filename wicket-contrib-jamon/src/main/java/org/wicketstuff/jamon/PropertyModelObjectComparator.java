/**
 * 
 */
package org.wicketstuff.jamon;

import java.util.Comparator;

import org.apache.wicket.model.PropertyModel;

import com.jamonapi.Monitor;
/**
 * Used to compare two {@link PropertyModel}s. As we know all property values
 * from a {@link Monitor} are {@link Comparable} we can safely cast them to {@link Comparable}.
 * This code is taken from: 
 * http://cwiki.apache.org/WICKET/simple-sortable-datatable-example.html
 *  
 * @author lars
 *
 */
@SuppressWarnings("unchecked")
final class PropertyModelObjectComparator implements Comparator {
    private final boolean ascending;

    private final String sortProperty;

    PropertyModelObjectComparator(boolean ascending, String sortProperty) {
        this.ascending = ascending;
        this.sortProperty = sortProperty;
    }

    public int compare(Object o1, Object o2) {
        PropertyModel model1 = new PropertyModel(o1, sortProperty);
        PropertyModel model2 = new PropertyModel(o2, sortProperty);
        
        int compare = ((Comparable) model1.getObject()).compareTo(model2.getObject());
        
        return ascending ? compare : compare * -1;
    }
}