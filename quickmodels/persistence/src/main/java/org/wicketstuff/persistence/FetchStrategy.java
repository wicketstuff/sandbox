/* Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.persistence;

import java.util.List;
import java.io.Serializable;
import org.wicketstuff.persistence.spi.PersistenceUtils;
/**
 * Strategies for caching and fetching persisted objects.  A PersistenceFacade may
 * use different strategies at different points in its lifecycle - if created
 * against an existing object, it will use the hold-and-save strategy to cache
 * the object;  once it has been saved, it will probably switch to only 
 * caching the UID of the object and discarding the actual object instance;
 * if the object is subsequently modified (someone calls modified() on its
 * facade), it should go back to hold-and-save;  and so forth.
 * <p/>
 * When a FetchStrategy changes state (for example, because it was saved or
 * an object was fetched from the database, or a live object was discarded),
 * the operation returns a new FetchStrategy that replaces the current one
 * in the persistence facade.  So, for example, once an object is saved, the
 * object instance it holds no longer needs to be held onto, because the 
 * equivalent object may be re-fetched from the database.  So the load method
 * returns a FetchStrategy that will just look the object by id.
 *
 * @author Tim Boudreau
 */
abstract class FetchStrategy<T> implements Serializable {
    protected final Class<T> type;
    protected final ActivationStrategy activation;
    protected final PersistenceUtils utils;
    private FetchStrategy(Class<T> type, ActivationStrategy activation, PersistenceUtils utils) {
        this.type = type;
        this.activation = activation;
        this.utils = utils;
        if (type == null) throw new NullPointerException("Type null");
        if (activation == null) throw new NullPointerException("Activation null");
        if (utils == null) throw new NullPointerException("Utils null");
    }
    
    abstract FetchStrategy<T> load(ObjectReceiver<T> holder) throws LookupFailedException;
    abstract FetchStrategy<T> save(T t);
    abstract boolean isPersisted(T t);
    FetchStrategy<T> delete(T t) {
        HoldAndSaveFetchStrategy<T>  result = new HoldAndSaveFetchStrategy<T> (t, type, activation, utils);
        result.setDeleted(true);
        utils.delete(t);
        return result;
    }
    
    public boolean isDeleted() {
        return false;
    }
    
    FetchStrategy<T> dirty(T obj) {
        return new HoldAndSaveFetchStrategy(obj, type, activation, utils);
    }
    
    String getUUID(T obj) {
        if (isPersisted(obj)) {
            return utils.getUuid(obj);
        } else {
            return null;
        }
    }
        
    long getUid(T t) {
        return utils.getUid(t);
    }
    
    public static final class ObjectReceiver<T> implements Serializable {
        T val;
        void set(T t) {
            val = t;
        }
        
        T get() {
            return val;
        }
    }
    
    static final class CreateNewFetchStrategy<T> extends FetchStrategy<T> {
        private final NewObjectFactory<T> factory;
        CreateNewFetchStrategy(NewObjectFactory<T> factory, Class<T> type, ActivationStrategy activation, PersistenceUtils utils) {
            super (type, activation, utils);
            this.factory = factory == null ? new NewObjectFactory.Reflection<T>(type) : factory;
        }
        
        CreateNewFetchStrategy(Class<T> type, ActivationStrategy activation, PersistenceUtils utils) {
            this (null, type, activation, utils);
        }

        FetchStrategy<T> load(ObjectReceiver<T> holder) {
            T obj = factory.create();
            holder.set(obj);
            return new HoldAndSaveFetchStrategy<T>(obj, type, activation, utils);
        }

        FetchStrategy<T> save(T t) {
            utils.saveObject(t);
            long uid = utils.getUid(t);
            //XXX there is no constructor for another load failure policy - there should be
            return new FetchStrategy.UidFetchStrategy<T>(uid, type, activation, utils, LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE, factory);
        }

        boolean isPersisted(T t) {
            return false;
        }

        @Override
        FetchStrategy<T> delete(T t) {
            return this;
        }
    }
    
    static final class HoldAndSaveFetchStrategy<T> extends FetchStrategy<T> {
        private T value;
        private final LoadFailurePolicy policy;
        private final NewObjectFactory<T> factory;
        HoldAndSaveFetchStrategy(T t, Class<T> type, ActivationStrategy activation, PersistenceUtils utils) {
            super (type, activation, utils);
            this.value = t;
            this.factory = null;
            this.policy = LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE;
        }
        
        private boolean deleted = false;
        void setDeleted(boolean val) {
            deleted = true;
        }
        
        @Override
        public boolean isDeleted() {
            return deleted;
        }
        
        HoldAndSaveFetchStrategy(T t, Class<T> type, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, activation, utils);
            this.factory = factory;
            this.policy = policy;
            this.value = t;
        }
        
        FetchStrategy<T> load(ObjectReceiver<T> holder) {
            holder.set(value);
            //XXX should we turn into a uid here?
            //Otherwise an existing object will get serialized into the
            //session.  We need to hold if the object is modified, but
            //we can probably determine this by the policy
            return this;
        }

        FetchStrategy<T> save(T t) {
            long uid = utils.saveObjectReturnUid(t);
            setDeleted(false);
            return new UidFetchStrategy<T>(uid, type, activation, utils, policy, factory);
        }

        boolean isPersisted(T t) {
            return false;
        }

        @Override
        FetchStrategy<T> delete(T t) {
            return this;
        }
        
        @Override
        long getUid(T t) {
            return -1;
        }
        
    }
    
    static final class UidFetchStrategy<T> extends FetchStrategy<T>  {
        private final long uid;
        private final LoadFailurePolicy policy;
        private final NewObjectFactory<T> factory;
        UidFetchStrategy(long uid, Class<T> type, ActivationStrategy activation, PersistenceUtils utils) {
            this(uid, type, activation, utils, LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE, null);
        }

        UidFetchStrategy(long uid, Class<T> type, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy) {
            this (uid, type, activation, utils, policy, policy == LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE ? null :
                new NewObjectFactory.Reflection<T>(type));
        }
        
        UidFetchStrategy(long uid, Class<T> type, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, activation, utils);
            this.uid = uid;
            this.policy = policy;
            this.factory = factory;
        }

        FetchStrategy<T> load(ObjectReceiver<T> holder) throws LookupFailedException {
            T result = utils.fetchByUid(uid, type, activation);
            if (result == null) {
                switch (policy) {
                case CREATE_NEW_OBJECT_ON_FAILURE :
                    result = factory.create();
                    break;
                case RETURN_NULL_ON_FAILURE :
                    break;
                case THROW_EXCEPTION_ON_FAILURE :
                    throw new LookupFailedException("Lookup of object with uid " + uid + " failed");
                }
            }
            holder.set(result);
            return this;
        }

        FetchStrategy<T> save(T t) {
            //boolean success = utils.saveObject(t);
            //XXX maybe return a different strategy on failure
            return this;
        }

        boolean isPersisted(T t) {
            return true;
        }

        @Override
        long getUid(T t) {
            try {
                ObjectReceiver<T> r = new ObjectReceiver<T>();
                load(r);
                T other = r.get();
                if (other != null && other.equals(t)) {
                    return uid;
                }
            } catch (LookupFailedException e) {
                
            }
            return utils.getUid(t);
        }
    }
    
    static final class UuidFetchStrategy<T> extends FetchStrategy<T> {
        private final String uuid;
        private final LoadFailurePolicy policy;
        private final NewObjectFactory<T> factory;

        UuidFetchStrategy(String uuid, Class<T> type, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy) throws InvalidUuidException {
            this (uuid, type, activation, utils, policy, policy == LoadFailurePolicy.CREATE_NEW_OBJECT_ON_FAILURE ? 
                new NewObjectFactory.Reflection<T>(type) : null);
        }
        
        UuidFetchStrategy(String uuid, Class<T> type, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy, NewObjectFactory<T> factory) throws InvalidUuidException {
            super (type, activation, utils);
            this.uuid = uuid;
            this.policy = policy == null ? LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE : policy;
            this.factory = factory == null ? policy == LoadFailurePolicy.CREATE_NEW_OBJECT_ON_FAILURE ?
                new NewObjectFactory.Reflection<T>(type) : null : factory;
            ObjectReceiver<T> r = new ObjectReceiver<T>();
            load (r);
        }
        
        FetchStrategy<T> load(ObjectReceiver<T> holder) {
            T result = utils.fetchByUuid(uuid, type, activation);
            holder.set(result);
            long uid = result == null ? -1L : utils.getUid(result);
            if (uid == -1L) {
                switch (policy) {
                case THROW_EXCEPTION_ON_FAILURE :
                    throw new LookupFailedException ("Lookup of uuid " + 
                            uuid + 
                            "failed");
                case CREATE_NEW_OBJECT_ON_FAILURE :
                    T t = factory.create();
                    return new HoldAndSaveFetchStrategy<T>(t, type,
                            activation, utils);
                default :
                    return this;
                }
            } else {
                return new UidFetchStrategy<T>(uid, type, activation, utils);
            }
        }

        FetchStrategy<T> save(T t) {
            long uid = utils.saveObjectReturnUid(t);
            return new UidFetchStrategy<T>(uid, type, activation, utils);
        }
        
        long getUid(T t) {
            if (isPersisted(t)) {
                return utils.getUid(t);
            } else {
                return -1L;
            }
        }

        boolean isPersisted(T t) {
            return true;
        }
    }
    
    static final class NoOpLookupStrategy<T> extends FetchStrategy<T> {
        NoOpLookupStrategy(Class<T> clazz, ActivationStrategy activation) {
            super (clazz, activation, null);
        }

        FetchStrategy<T> load(ObjectReceiver<T> holder) {
            return this;
        }

        FetchStrategy<T> save(T t) {
            return this;
        }

        boolean isPersisted(T t) {
            return false;
        }

        @Override
        FetchStrategy<T> delete(T t) {
            return this;
        }
    }
    
    static final class PrototypeFetchStrategy<T> extends FetchStrategy<T> {
        private final T prototype;
        private final NewObjectFactory<T> factory;
        private final LoadFailurePolicy policy;
        PrototypeFetchStrategy(Class<T> type, ActivationStrategy strategy, PersistenceUtils utils, T prototype, LoadFailurePolicy policy) {
            this (type, strategy, utils, prototype, null, null);
        }
        
        PrototypeFetchStrategy(Class<T> type, ActivationStrategy strategy, PersistenceUtils utils, T prototype, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, strategy, utils);
            this.prototype = prototype;
            this.policy = policy == null ? LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE : policy;
            this.factory = policy == LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE ? null : factory;
        }
        
        PrototypeFetchStrategy(Class<T> type, ActivationStrategy strategy, PersistenceUtils utils, T prototype) {
            this (type, strategy, utils, prototype, null, null);
        }
        
        FetchStrategy<T> load(ObjectReceiver<T> holder) throws LookupFailedException  {
            List<T> l = utils.fetchByPrototype(prototype);
            T result = l.isEmpty() ? null : l.iterator().next();
            if (result == null) {
                switch(policy) {
                case CREATE_NEW_OBJECT_ON_FAILURE :
                    result = factory.create();
                    holder.set(result);
                    return new HoldAndSaveFetchStrategy<T>(result, type,
                            activation, utils);
                case THROW_EXCEPTION_ON_FAILURE :
                    throw new LookupFailedException("Failed to fetch object matching " + prototype);
                default :
                    return this;
                }
            } else {
                holder.set(result);
                long uid = utils.getUid(result);
                return new UidFetchStrategy<T>(uid, type, activation, utils);
            }
        }

        FetchStrategy<T> save(T t) throws LookupFailedException {
            if (t == null) throw new NullPointerException("Null object to save");
            long uid = utils.saveObjectReturnUid(t);
            if (uid != -1) {
                return new UidFetchStrategy<T>(uid, type, activation, utils);
            } else {
                throw new LookupFailedException("Save of " + t + " failed");
            }
        }

        boolean isPersisted(T t) {
            ObjectReceiver<T> r = new ObjectReceiver<T>();
            load (r);
            return r.get() != null;
        }
    }
    
    static final class SingletonOrFirstFoundLookupStrategy<T> extends FetchStrategy<T> {
        private final NewObjectFactory<T> factory;
        private final LoadFailurePolicy policy;
        SingletonOrFirstFoundLookupStrategy(NewObjectFactory<T> factory, Class<T> type, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy) {
            super (type, activation, utils);
            this.policy = policy == null ? LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE : policy;
            this.factory = factory == null ? 
                policy == LoadFailurePolicy.CREATE_NEW_OBJECT_ON_FAILURE ? 
                    new NewObjectFactory.Reflection<T>(type) : null : factory;
        }

        FetchStrategy<T> load(ObjectReceiver<T> holder) throws LookupFailedException {
            T result = utils.fetchOneOfType(type);
            if (result != null) {
                holder.set(result);
                UidFetchStrategy<T> s =
                        new UidFetchStrategy<T>(
                        utils.getUid(result),
                        type, activation, utils);
                return s;
            } else {
                switch (policy) {
                case THROW_EXCEPTION_ON_FAILURE :
                    throw new LookupFailedException("No objects of type " + type + 
                        " in database");
                case CREATE_NEW_OBJECT_ON_FAILURE :
                    result = factory.create();
                    holder.set(result);
                    HoldAndSaveFetchStrategy<T> s =
                            new HoldAndSaveFetchStrategy(result,
                            type, activation, utils);
                    return s;
                default :
                    return this;
                }
            }
        }

        FetchStrategy<T> save(T t) {
            long uid = utils.saveObjectReturnUid(t);
            UidFetchStrategy<T> s = new
                    UidFetchStrategy<T>(uid,
                    type, activation, utils);
            return s;
        }

        boolean isPersisted(T t) {
            return true;
        }
    }
    
    static final class SinglePropertyQueryLookupStrategy<T, P> extends FetchStrategy<T> {
        private final NewObjectFactory<T> factory;
        private final LoadFailurePolicy policy;
        private final Class<P> fieldType;
        private final P value;
        private final String fieldName;
        SinglePropertyQueryLookupStrategy(Class<T> type, String field, Class<P> fieldType, P value, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
            super (type, activation, utils);
            this.fieldType = fieldType;
            this.value = value;
            this.fieldName = field;
            this.factory = factory != null ? factory : 
                policy == null || policy ==
                LoadFailurePolicy.CREATE_NEW_OBJECT_ON_FAILURE ? 
                new NewObjectFactory.Reflection<T>(type) : null;
            this.policy = policy == null ? LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE : policy;
        }
        
        SinglePropertyQueryLookupStrategy(Class<T> type, String field, Class<P> fieldType, P value, ActivationStrategy activation, PersistenceUtils utils, LoadFailurePolicy policy) {
            this (type, field, fieldType, value, activation, utils, policy, null);
        }

        SinglePropertyQueryLookupStrategy(Class<T> type, String field, Class<P> fieldType, P value, ActivationStrategy activation, PersistenceUtils utils) {
            this (type, field, fieldType, value, activation, utils, null, null);
        }

        FetchStrategy<T> load(ObjectReceiver<T> holder) throws LookupFailedException {
            T result = utils.fetchByFieldQuery(type, fieldType, fieldName, value);
            if (result == null) {
                switch (policy) {
                case CREATE_NEW_OBJECT_ON_FAILURE :
                    result = factory.create();
                    holder.set(result);
                    HoldAndSaveFetchStrategy<T> s =
                            new HoldAndSaveFetchStrategy<T>(result, type,
                            activation, utils);
                    return s;
                case RETURN_NULL_ON_FAILURE :
                    return this;
                default :
                    throw new LookupFailedException("Could not find an object " +
                            "of type " + type.getName() + " with a field called " +
                            fieldName + " of type " + fieldType.getName() + " set " +
                            "to " + value);
                }
            } else {
                holder.set(result);
                UidFetchStrategy<T> s = new UidFetchStrategy<T>(utils.getUid(result),
                        type, activation, utils, policy, factory);
                return s;
            }
        }

        FetchStrategy<T> save(T t) {
            long uid = utils.saveObjectReturnUid(t);
            return new UidFetchStrategy<T>(uid, type, activation, utils, policy, factory);
        }

        boolean isPersisted(T t) {
            return utils.getUid(t) != -1;
        }
    }
}
