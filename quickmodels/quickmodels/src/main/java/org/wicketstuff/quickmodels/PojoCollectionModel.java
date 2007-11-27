/*
 * PojoCollectionModel.java
 * 
 * Created on Jul 25, 2007, 6:22:56 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wicketstuff.quickmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.persistence.*;
import org.wicketstuff.persistence.NewObjectFactory;
import org.wicketstuff.persistence.queries.QueryElement;

/**
 * A detachable model which wraps a collection of detachable models.  Useful
 * in conjunction with PojoCollectionModelDataProvider to very simply create
 * repeating views matching some database query.
 * Typically you don't instantiate this class directly, but rather call
 * Queries.SOMETHING.builder(type).multi() to get one.
 * <p/>
 * Objects in the database are lazily resolved, and when the model is dormant
 * (it is detached) stored only by uid, to ensure lightweight-ness.
 *
 * @see org.netbeans.lib.persistence.wicketmodels.PojoCollectionModelDataProvider
 * @see org.netbeans.lib.persistence.wicketmodels.Queries
 * @see org.netbeans.lib.persistence.wicketmodels.ModelBuilder
 *
 * @author Tim Boudreau
 */
public final class PojoCollectionModel<T> extends AbstractReadOnlyModel implements GenericModel <List<PojoModel<T>>>, Iterable<T> {

    private final List<PojoModel<T>> mdls;
    public PojoCollectionModel(List<PojoModel<T>> mdls) {
        this.mdls = mdls;
    }
    
    public PojoCollectionModel(PojoModel<T> mdl) {
        this(Collections.<PojoModel<T>>singletonList(mdl));
    }
    /**
     * Get the list of models this model wraps
     * @return A list of models
     */
    @SuppressWarnings("unchecked")
    public List<PojoModel<T>> get() {
        return (List<PojoModel<T>>) getObject();
    }

    /**
     * Get an iterator that iterates over the actual objects inside the 
     * models
     * @return
     */
    public Iterator<T> iterator() {
        return new Iter<T>(get().iterator());
    }
    
    /**
     * Determine if no matches were found for the query that created this model
     * @return true if no models are present
     */
    public boolean isEmpty() {
        return mdls.isEmpty();
    }

    public Object getObject() {
        return mdls;
    }
    
    /**
     * Find the PojoModel that goes with a particular object
     * @param t An object wrapped by one of the models inside this collection model
     * @return A model or null
     */
    public PojoModel<T> modelFor(T t) {
        for (Iterator<PojoModel<T>> i = mdls.iterator(); i.hasNext();) {
            PojoModel<T> pm = i.next();
            if (t.equals(pm.get().get())) {
                return pm;
            }
        }
        return null;
    }
    
    /**
     * Detach all models this model wraps, and discard them, forcing them
     * to be re-fetched on the next call to get().
     * PENDING: deleteme?
     */
    public void refresh() {
        for (Iterator<PojoModel<T>> i = mdls.iterator(); i.hasNext();) {
            PojoModel<T> pm = i.next();
            if (pm.isDeleted()) {
                pm.detach();
                i.remove();
            }
        }
    }

    /**
     * Detach all models
     */
    @Override
    public void detach() {
        for (IModel mdl : mdls) {
            mdl.detach();
        }
    }
    
    public int indexOf (PojoModel <T> mdl) {
        return mdls.indexOf(mdl);
    }
    
    /**
     * Get a sorted list of the models contained in this model.  If the
     * objects in this model do not implement Comparable, this method will
     * throw a ClassCastException.
     */ 
    public List <PojoModel<T>> sort() {
        return sort ((Comparator<T>)new ComparableComparator());
    }
    
    public List <PojoModel<T>> sort(Comparator <T> c) {
        Map <T, PojoModel<T>> map = new HashMap<T, PojoModel<T>>();
        List <PojoModel<T>> l = new ArrayList <PojoModel<T>>(mdls.size());
        List <T> ts = new ArrayList<T>();
        for (PojoModel<T> m : mdls) {
            map.put (m.getPojo(), m);
            ts.add (m.getPojo());
        }
        Collections.sort(ts, c);
        for (T t : ts) {
            l.add (map.get(t));
        }
        return l;
    }
    
    private static final class ComparableComparator implements Comparator <Comparable> {
        @SuppressWarnings("unchecked")
        public int compare(Comparable a, Comparable b) {
            return a.compareTo(b);
        }
    }
    
    private static class Iter<T> implements Iterator<T> {
        private final Iterator<PojoModel<T>> proxy;
        Iter(Iterator<PojoModel<T>> proxy) {
            this.proxy = proxy;
        }
        public boolean hasNext() {
            return proxy.hasNext();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            PojoModel<T> m = proxy.next();
            boolean wasAttached = m.isAttached();
            T t = m.get().get();
            if (!wasAttached) {
                m.detach();
            }
            return t;
        }
    }
    /*
    public interface Filter <T> extends Serializable {
        public boolean accept (T t);
    }
    
    private static final class AllPass<T> implements Filter<T>{
        public boolean accept(T o) {
            return true;
        }
    }
     */ 
    
    static abstract class Template<T> {
        protected final Class<T> type;
        protected final LoadFailurePolicy policy;
        protected final NewObjectFactory<T> factory;
        Template(Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            this.type = type;
            this.policy = policy;
            this.factory = factory;
        }
        public final <ContainerType> List<PojoModel<T>> load(Db<?> db) {
            Collection <PersistenceFacade<T>> l = getFacades(db);
            List <PojoModel<T>> result = new ArrayList<PojoModel<T>>(l.size());
            for (PersistenceFacade<T> p : l) {
                result.add (new PojoModel<T>(p));
            }
            return result;
        }
        protected abstract <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db);
        
        public final <ContainerType> PojoCollectionModel create(Db <ContainerType> db) {
            List<PojoModel<T>> mdls = load(db);
            return new PojoCollectionModel(mdls);
        }
    }
    
    static class ComplexQueryTemplate<T> extends Template<T>{
        private final QueryElement queryElement;
        private final Comparator<T> comparator;
        public ComplexQueryTemplate(Class<T> type, QueryElement queryElement, Comparator<T> comparator, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, policy, factory);
            this.queryElement = queryElement;
            this.comparator = comparator;
        }

        @Override
        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forComplexQuery(type, queryElement,
                    comparator, policy, factory);
        }
    }
    
    static final class UidTemplate<T> extends Template<T> {
        private final Collection<Long> uids;
        UidTemplate (Collection<Long> uids, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, policy, factory);
            this.uids = new ArrayList<Long>(uids);
        }
        
        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forUids(uids, type, policy, factory);
        }
    }
    
   static final class UUidTemplate<T> extends Template<T> {
        private final Collection<String> uuids;
        UUidTemplate (Collection<String> uuids, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, policy, factory);
            this.uuids = new ArrayList<String>(uuids);
        }

        protected <ContainerType> List<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forUuids(uuids, type, policy, factory);
        }
    }
    
    static final class AllOfTypeTemplate<T> extends Template<T> {
        private Comparator<T> compare;
        AllOfTypeTemplate (Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, policy, factory);
        }
        
        AllOfTypeTemplate (Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare) {
            super (type, policy, factory);
            this.compare = compare;
        }

        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forAllOfType(type, policy, factory, compare);
        }
    }
    
    static final class PrototypeTemplate<T> extends Template<T> {
        private Comparator<T> compare;
        private final T prototype;
        @SuppressWarnings("unchecked")
        PrototypeTemplate (T prototype, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super ((Class<T>)prototype.getClass(), policy, factory);
            this.prototype = prototype;
        }
        
        PrototypeTemplate (T prototype, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare) {
            this (prototype, policy, factory);
            this.compare = compare;
        }

        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forAllMatchingPrototype(prototype, policy, factory, compare);
        }
    }
    
    static final class QueryTemplate<T, P> extends Template<T> {
        private final Comparator<T> compare;
        private final Class <P> fieldType;
        private final P fieldValue;
        private final String fieldName;
        @SuppressWarnings("unchecked")
        QueryTemplate (Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            this(type, fieldName, fieldType, fieldValue, policy, factory, null);
        }
        
        QueryTemplate (Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare) {
            super (type, policy, factory);
            this.fieldName = fieldName;
            this.fieldType = fieldType;
            this.fieldValue = fieldValue;
            this.compare = compare;
        }

        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forAllMatchingQuery(type, fieldName, fieldType, fieldValue, policy, factory, compare);
        }
    }    
    
    static final class CustomQueryTemplate<T, ContainerType, P> extends Template<T> {
        private final DbJob<ContainerType, Collection<T>, P> query;
        private final P arg;
        CustomQueryTemplate (DbJob<ContainerType, Collection<T>, P> query, P arg, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (null, policy, factory);
            this.query = query;
            this.arg = arg;
        }
        
        protected Collection<PersistenceFacade<T>> gFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().runCustomQuery(query, arg);
        }

        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            Collection <PersistenceFacade<T>> c;
            DbJob q = query;
            c = db.getFacadeFactory().<T,P>runCustomQuery(q, arg);
            return c;
        }
    }
    
    static final class NewObjectsTemplate<T> extends Template<T> {
        private final int count;
        NewObjectsTemplate (Class<T> type, int count, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, policy, factory);
            this.count = count;
        }
        
        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            List <PersistenceFacade<T>> result = 
                    new ArrayList<PersistenceFacade<T>>(count);
            for (int i=0; i < count; i++) {
                result.add (db.getFacadeFactory().forNew(type));
            }
            return result;
        }
    }    
    
    static final class ExistingObjectsTemplate<T> extends Template<T> {
        private final Collection<T> objects;
        ExistingObjectsTemplate (Class<T> type, Collection<T> objects, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, policy, factory);
            this.objects = objects;
        }
        
        protected <ContainerType> Collection<PersistenceFacade<T>> getFacades(Db<ContainerType> db) {
            return db.getFacadeFactory().forExistingObjects(objects, policy, factory);
        }
    }
}
