/*
 * ModelBuilder.java
 * 
 * Created on Aug 3, 2007, 6:59:19 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wicketstuff.quickmodels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.wicketstuff.persistence.*;
import org.wicketstuff.persistence.NewObjectFactory;
import org.wicketstuff.persistence.queries.QueryElement;
import static org.wicketstuff.quickmodels.QueryFields.*;
/**
 * Factory for wicket models based on a database query.  Typical usage is
 * to have some data for fetching objects from a database - for example,
 * a UUID passed in a URL.  e.g, 
 * <pre>
 * ModelBuilder<MyType> builder = Queries.UUID.builder(MyType.class);
 * builder.setUuid (theUuid);
 * PojoModel <MyType> wicketModel = builder.single();
 * </pre>
 * 
 * This class contains methods for setting all parameters of all supported
 * query types;  different types of queries have different sets of required,
 * optional and unused (illegal) parameters.
 * <p/>
 * A ModelBuilder is meant to be used once and then discarded.
 * 
 * @see Queries.builder()
 * @author Tim Boudreau
 */
public final class ModelBuilder<T, P> {
    private final Queries kind;
    private String uuid;
    private Collection<Long> uids;
    private Collection<String> uuids;
    private Comparator<T> comparator;
    private LoadFailurePolicy policy;
    private NewObjectFactory<T> factory;
    private Object object;
    private Class<P> fieldType;
    private P fieldValue;
    private String fieldName;
    private int newObjectCount = 1;
    private DbJob<?, Collection<T>, P> customQuery;
    private P customQueryArg;
    private long uid = -1L;
    private Class<T> type;
    private Collection<T> objects;
    

    ModelBuilder(Class<T> type, Queries kind) {
        this.type = type;
        this.kind = kind;
        if (!kind.getIllegalFields().contains(TYPE)) {
            usedField(TYPE, type);
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + " for " + kind + ": uid=" + uid + "uuid="
                + uuid + " uids=" + uids + " uuids=" + uuids + " comparator " +
                comparator + " policy " + policy + " factory " + factory + 
                " object " + object + " fieldType " + fieldType + 
                " fieldName " + fieldName + " newObjectCount " + newObjectCount +
                " customQuery " + customQuery + " customQueryArg " + 
                customQueryArg + " type " + type + " objects " + objects;
    }
    
    /**
     * Create a model for a collection of persisted objects matching this
     * query.
     * @param db The database in question
     * @return A wicket model of a collection of objects matching this query
     * @throws IllegalStateException if not all required parameters needed to
     * complete this query have been set.  Note that setting a parameter to null
     * is the equivalent of unsetting it.
     */
    public <ContainerType> PojoCollectionModel<T> multi(Db<ContainerType> db) {
        checkRequiredFields();
        PojoCollectionModel.Template<T> t;
        switch (kind) {
        case COMPLEX :
            t = new PojoCollectionModel.ComplexQueryTemplate(type, (QueryElement) object,
                    comparator, policy, factory);
            break;
        case CUSTOM_QUERY :
            t = new PojoCollectionModel.CustomQueryTemplate(customQuery,
                    customQueryArg, policy, factory);
            break;
        case EXISTING_OBJECTS :
            //create a new list because the one we have may be unmodifiable
            //and if the comparator is used it will need to be sorted
            Collection<T> o;
            if (object == null) {
                o = new ArrayList<T>(objects);
            } else {
                o = new ArrayList<T>(Collections.<T>singletonList((T)object));
            }
            t = new PojoCollectionModel.ExistingObjectsTemplate<T>(type, o,
                    policy, factory);
            break;
        case NEW_OBJECT :
            t = new PojoCollectionModel.NewObjectsTemplate <T> (type,
                    newObjectCount, policy, factory);
            break;
        case OF_TYPE :
            t = new PojoCollectionModel.AllOfTypeTemplate<T> (type,
                    policy, factory, comparator);
            break;
        case PROTOTYPE :
            t = new PojoCollectionModel.PrototypeTemplate<T>((T)object,
                    policy, factory, comparator);
            break;
        case QUERY :
            t = new PojoCollectionModel.QueryTemplate<T, P>(type,
                    fieldName, fieldType,
                    fieldValue, policy, factory, comparator);
            break;
        case UID :
            Collection<Long> c = uids == null ? Collections.singleton(uid) :
                uids;
            t = new PojoCollectionModel.UidTemplate<T>(c, type, policy, factory);
            break;
        case UUID :
            Collection<String> cc = uuids == null ? Collections.singleton(uuid) :
                uuids;
            t = new PojoCollectionModel.UUidTemplate<T>(cc, type, policy, factory);
            break;
        default :
            throw new AssertionError("Kind not handled: " + kind);
        }
        return t.create(db);
    }
    
    /**
     * Create a wicket mdoel for a single object which matches this query
     * @param db The database to look the object up in
     * @return A wicket model for an object matching this query
     */
    public <ContainerType> PojoModel<T> single(Db <ContainerType> db) {
        if (comparator != null) {
            throw new IllegalArgumentException("Comparator was set but is " +
                    "unused unless you are creating a collection model");
        }
        if (objects != null) {
            throw new IllegalArgumentException("Object collection was set but is " +
                    "unused unless you are creating a collection model");
        }
        checkRequiredFields();
        PersistenceFacade<T> facade;
        switch (kind) {
        case COMPLEX :
            Collection<PersistenceFacade<T>> cc = 
                    db.getFacadeFactory().forComplexQuery (type, (QueryElement) object,
                    comparator, policy, factory);
            if (!cc.isEmpty()) {
                facade = cc.iterator().next();
            } else {
                facade = null;
            }
        case CUSTOM_QUERY :
            Collection <PersistenceFacade<T>> c;
            DbJob q = customQuery;
            c = db.getFacadeFactory().<T,P>runCustomQuery(q, customQueryArg);
            if (!c.isEmpty()) {
                facade = c.iterator().next();
            } else {
                facade = null;
            }
            break;
        case EXISTING_OBJECTS :
            Object o = null;
            if (object != null) {
                o = object;
            } else if (objects != null && !objects.isEmpty()) {
                o = objects.iterator().next();
            }
            if (o == null) {
                throw new IllegalStateException("No objects to operate on for " + this);
            }
            facade = db.getFacadeFactory().forExisting((T)o);
            break;
        case NEW_OBJECT :
            facade = db.getFacadeFactory().forNew(type, factory);
            break;
        case OF_TYPE :
            facade = db.getFacadeFactory().forSingleton(type, policy, factory);
            break;
        case PROTOTYPE :
            Object oo = null;
            if (object != null) {
                oo = object;
            } else if (objects != null && !objects.isEmpty()) {
                oo = objects.iterator().next();
            }
            if (oo == null) {
                throw new IllegalStateException("No objects to operate on for " + this);
            }
            facade = db.getFacadeFactory().forPrototype((T)oo);
            break;
        case QUERY :
            facade = db.getFacadeFactory().forQuery(type, fieldName, fieldType, fieldValue, policy, factory);
            break;
        case UID :
            if (uid == -1L && uids == null || (uids != null &&uids.isEmpty())) {
                throw new IllegalStateException ("No UID for " + this);
            }
            long u2 = this.uid == -1L ? uids.iterator().next() : this.uid;
            facade = db.getFacadeFactory().forUid(type, u2, policy, factory);
            break;
        case UUID :
            if (uuid == null && uuids == null || (uuids != null && uuids.isEmpty())) {
                throw new IllegalStateException("No uuid for " + this);
            }
            String uu = uuid == null ? uuids.isEmpty() ? null : uuids.iterator().next() : uuid;
            if (uu == null) {
                throw new IllegalStateException("No uuid for " + this);
            }
            facade = db.getFacadeFactory().forUuid(uu, type, policy, factory);
            break;
        default :
            throw new AssertionError("Kind not handled: " + kind);
        }
        return new PojoModel<T>(facade);
    }
    
    private void checkRequiredFields() {
        Set <QueryFields> s = new HashSet <QueryFields> (kind.getRequiredFields());
        s.removeAll(usedFields);
        if (!s.isEmpty()) {
            throw new IllegalStateException ("The following required fields have " +
                    "not been set or were set to null: " + s);
        }
//        if (kind == Queries.PROTOTYPE && (object == null || objects == null || (objects != null && objects.isEmpty()))) {
//            throw new IllegalStateException(); //XXX find out what's wrong
//        }
    }
    
    private void checkSet(QueryFields f) {
        if (kind.getIllegalFields().contains(f)) {
            throw new IllegalArgumentException ("Field " + 
                    f + " is not allowed in queries of " + kind);
        }
    }
    
    private final Set<QueryFields> usedFields = new HashSet<QueryFields>();
    private void usedField (QueryFields f, Object o) {
        if (o != null) {
            checkSet(f);
            usedFields.add(f);
        } else {
            usedFields.remove(f);
        }
    }

    public void setCustomQuery(DbJob<?, Collection<T>, P> customQuery) {
        usedField(CUSTOM_QUERY, customQuery);
        this.customQuery = customQuery;
    }

    public void setCustomQueryArg(P customQueryArg) {
        usedField(CUSTOM_QUERY_ARG, customQueryArg);
        this.customQueryArg = customQueryArg;
    }

    public void setFactory(NewObjectFactory factory) {
        usedField(FACTORY, factory);
        this.factory = factory;
    }

    public void setFieldName(String fieldName) {
        usedField (FIELD_NAME, fieldName);
        this.fieldName = fieldName;
    }

    public void setFieldType(Class<P> fieldType) {
        usedField (FIELD_TYPE, fieldType);
        this.fieldType = fieldType;
    }

    public void setFieldValue(P fieldValue) {
        usedField (FIELD_VALUE, fieldValue);
        this.fieldValue = fieldValue;
    }

    public void setNewObjectCount(int newObjectCount) {
        usedField(NEW_OBJECT_COUNT, newObjectCount > 1 ?
                newObjectCount : null);
        if (newObjectCount < 1) {
            throw new IllegalArgumentException("Cannot set " +
                    "the new object count to less than one: " + 
                    newObjectCount);
        }
        this.newObjectCount = newObjectCount;
    }
    
    Queries kind() {
        return kind;
    }

    public void setObject(Object object) {
        if (object != null && this.objects != null) {
            throw new IllegalArgumentException ("Use setObject() or " +
                    "setObjects() but not both, and do not call more " +
                    "than once");
        }
        if (this.kind == Queries.COMPLEX) {
            if (object != null && !(object instanceof QueryElement)) {
                throw new IllegalArgumentException ("In the case of " +
                        "Queries.CUSTOM, arguments to setObject must be " +
                        "instances of QueryElement");
            }
        }
        usedField(OBJECT, object);
        this.object = object;
    }

    public void setPolicy(LoadFailurePolicy policy) {
        usedField (POLICY, policy);
        this.policy = policy;
    }

    public void setType(Class<T> type) {
        usedField (TYPE, type);
        this.type = type;
    }

    public void setUid(long uid) {
        if (uid != -1L && uids != null && !uids.isEmpty()) {
            throw new IllegalStateException("Cannot call both setUid() and " +
                    "setUids(), use one or the other");
        }
        usedField (UID, uid == -1L ? null : uid);
        this.uid = uid;
    }

    public void setUuid(String uuid) {
        if (uuid != null && uuids != null && !uuids.isEmpty()) {
            throw new IllegalArgumentException("Cannot call both setUuid() and " +
                    "setUuids(), use one or the other");
        }
        usedField (UUID, uuid);
        this.uuid = uuid;
    }
    
    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
    public void setUids(Collection<Long> uids) {
        if (uid != -1L) {
            throw new IllegalArgumentException("Cannot call both setUid() and " +
                    "setUids(), use one or the other");
        }
        usedField(UID, uids);
        this.uids = uids;
    }
    
    public void setUUids(Collection<String> uuids) {
        usedField(UUID, uuids == null || uuids.isEmpty() ? null : uuids);
        this.uuids = uuids;
    }
    
    public void setObjects(Collection<T> objects) {
        if (object != null && objects != null) {
            throw new IllegalArgumentException ("Use setObject() or " +
                    "setObjects() but not both");
        }
        usedField (OBJECT, objects == null || objects.isEmpty() ? null : objects);
        this.objects = objects;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final ModelBuilder<T, P> other = ( ModelBuilder<T, P>) obj;
        if (this.kind != other.kind && (this.kind == null || !this.kind.equals(other.kind))) {
            return false;
        }
        if (this.usedFields != other.usedFields && (this.usedFields == null || !this.usedFields.equals(other.usedFields))) {
            return false;
        }
        if (this.uuid != other.uuid && (this.uuid == null || !this.uuid.equals(other.uuid))) {
            return false;
        }
        if (this.policy != other.policy && (this.policy == null || !this.policy.equals(other.policy))) {
            return false;
        }
        if (this.factory != other.factory && (this.factory == null || !this.factory.equals(other.factory))) {
            return false;
        }
        if (this.object != other.object && (this.object == null || !this.object.equals(other.object))) {
            return false;
        }
        if (this.fieldType != other.fieldType && (this.fieldType == null || !this.fieldType.equals(other.fieldType))) {
            return false;
        }
        if (this.fieldValue != other.fieldValue && (this.fieldValue == null || !this.fieldValue.equals(other.fieldValue))) {
            return false;
        }
        if (this.fieldName != other.fieldName && (this.fieldName == null || !this.fieldName.equals(other.fieldName))) {
            return false;
        }
        if (this.newObjectCount != other.newObjectCount) {
            return false;
        }
        if (this.customQuery != other.customQuery && (this.customQuery == null || !this.customQuery.equals(other.customQuery))) {
            return false;
        }
        if (this.customQueryArg != other.customQueryArg && (this.customQueryArg == null || !this.customQueryArg.equals(other.customQueryArg))) {
            return false;
        }
        if (this.uid != other.uid) {
            return false;
        }
        return !(this.type != other.type && (this.type == null || !this.type.equals(other.type)));
    }

    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.kind != null ? this.kind.hashCode() : 0);
        hash = 67 * hash + (this.usedFields != null ? this.usedFields.hashCode() : 0);
        hash = 7 * hash + (this.uuid != null ? this.uuid.hashCode() : 0);
        hash = 67 * hash + (this.policy != null ? this.policy.hashCode() : 0);
        hash = 11 * hash + (this.factory != null ? this.factory.hashCode() : 0);
        hash = 31 * hash + (this.object != null ? this.object.hashCode() : 0);
        hash = 67 * hash + (this.fieldType != null ? this.fieldType.hashCode() : 0);
        hash = 67 * hash + (this.fieldValue != null ? this.fieldValue.hashCode() : 0);
        hash = 67 * hash + (this.fieldName != null ? this.fieldName.hashCode() : 0);
        hash = 67 * hash + this.newObjectCount;
        hash = 67 * hash + (this.customQuery != null ? this.customQuery.hashCode() : 0);
        hash = 67 * hash + (this.customQueryArg != null ? this.customQueryArg.hashCode() : 0);
        hash = 67 * hash + (int) (this.uid ^ (this.uid >>> 32));
        hash = 67 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }
    
    
}
