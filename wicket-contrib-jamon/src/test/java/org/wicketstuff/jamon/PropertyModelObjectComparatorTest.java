package org.wicketstuff.jamon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class PropertyModelObjectComparatorTest {
    @Test
    public void shouldReturnZeroIfObjectsAreEqual() {
        PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(true, "name");
        assertEquals(0, ascComparator.compare(new PropertyModelObject("ben"), new PropertyModelObject("ben")));

        PropertyModelObjectComparator descComparator = new PropertyModelObjectComparator(false, "name");
        assertEquals(0, descComparator.compare(new PropertyModelObject("ben"), new PropertyModelObject("ben")));
    }
    
    @Test
    public void shouldReturnNegativeIfObject1IsConsideredLessThanObject2AndSortOrderIsAscending() {
        PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(true, "name");
        assertEquals(-1, ascComparator.compare(new PropertyModelObject("ben1"), new PropertyModelObject("ben2")));
    }
    @Test
    public void shouldReturnPositiveIfObject1IsConsideredLessThanObject2AndSortOrderIsDescending() {
        PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(false, "name");
        assertEquals(1, ascComparator.compare(new PropertyModelObject("ben1"), new PropertyModelObject("ben2")));
    }
    @Test
    public void shouldReturnPositiveIfObject1IsConsideredMoreThanObject2AndSortOrderIsAscending() {
        PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(true, "name");
        assertEquals(1, ascComparator.compare(new PropertyModelObject("ben2"), new PropertyModelObject("ben1")));
    }
    @Test
    public void shouldReturnNegativeIfObject1IsConsideredMoreThanObject2AndSortOrderIsDescending() {
        PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(false, "name");
        assertEquals(-1, ascComparator.compare(new PropertyModelObject("ben2"), new PropertyModelObject("ben1")));
    }
}
