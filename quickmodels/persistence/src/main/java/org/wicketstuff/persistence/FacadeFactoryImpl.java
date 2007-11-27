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

import java.io.Serializable;
import java.util.*;
import org.wicketstuff.persistence.queries.QueryElement;
/**
 *
 * @author Tim Boudreau
 */
class FacadeFactoryImpl<ContainerType> implements FacadeFactory<ContainerType>, Serializable {
    private LookupStrategyFactory lkp;
    FacadeFactoryImpl(LookupStrategyFactory lkp) {
        this.lkp = lkp;
    }

    /**
     * Convenience method for fetching a collection of facades
     * <p/>
     * @param job The job to run
     * @param arg Optional argument to the job's run() method
     * @return A collection of PersistenceFacades
     */
    public final <T, P> Collection<PersistenceFacade<T>> runCustomQuery (
            DbJob<ContainerType, Collection<T>, P> job, P arg) {
        //XXX butt ugly, but better than having this method live on the DB class
        Collection <T> objects = ((Db<ContainerType>)lkp.db).run(job, arg);
        //XXX make this lazy
        Collection <PersistenceFacade<T>> result = new
                ArrayList<PersistenceFacade<T>>();
        for (T obj : objects) {
            result.add (forExisting(obj));
        }
        return result;
    }

    
    private <T> PersistenceFacade<T> createFacade(Class<T> type, FetchStrategy<T> lkp) {
        PersistenceFacadeImpl<T> result = new PersistenceFacadeImpl<T>(type, lkp);
        return result;
    }

    public <T> PersistenceFacade<T> forNew(Class<T> type) {
        FetchStrategy<T> s = lkp.newObject(type);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forNew(Class<T> type, NewObjectFactory<T> factory) {
        FetchStrategy<T> s = lkp.newObject(type, factory);
        return createFacade(type, s);
    }

    @SuppressWarnings("unchecked")
    public <T> PersistenceFacade<T> forExisting(T obj) {
        Class<T> clazz = (Class<T>) obj.getClass();
        FetchStrategy<T> s = lkp.preexisting(obj);
        return createFacade(clazz, s);
    }
    
    @SuppressWarnings("unchecked")
    public <T> PersistenceFacade<T> createFacadeForExistingObject(T obj, LoadFailurePolicy policy, NewObjectFactory factory) {
        Class<T> clazz = (Class<T>) obj.getClass();
        FetchStrategy<T> s = lkp.preexisting(obj, policy, factory);
        return createFacade(clazz, s);
    }
    

    @SuppressWarnings("unchecked")
    public <T> PersistenceFacade<T> forPrototype(T obj) {
        FetchStrategy<T> s = lkp.prototype((Class<T>)obj.getClass(), obj);
        return createFacade((Class<T>)obj.getClass(), s);
    }

    public <T> PersistenceFacade<T> forSingleton(
            Class<T> type) {
        FetchStrategy<T> s = lkp.singleton(type);
        return createFacade(type, s);
    }
    
    public <T> PersistenceFacade<T> forSingleton(Class<T> type, LoadFailurePolicy policy) {
        FetchStrategy<T> s = lkp.singleton(type, policy);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forSingleton(Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        FetchStrategy<T> s = lkp.singleton(type, policy, factory);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forUuid(String uuid, Class<T> type) throws InvalidUuidException {
        FetchStrategy<T> s = lkp.uuid(uuid, type);
        return createFacade(type, s);
    }
    
    public <T> PersistenceFacade<T> forUuid(String uuid, Class<T> type, LoadFailurePolicy policy) throws InvalidUuidException {
        FetchStrategy<T> s = lkp.uuid(uuid, type, policy);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forUuid(String uuid, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) throws InvalidUuidException {
        FetchStrategy<T> s = lkp.uuid(uuid, type, policy, factory);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forUid(Class<T> type, long uid) {
        FetchStrategy<T> s = lkp.uuid(type, uid);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forUid(Class<T> type, long uid, LoadFailurePolicy policy) {
        FetchStrategy<T> s = lkp.uid(type, uid, policy);
        return createFacade(type, s);
    }

    public <T> PersistenceFacade<T> forUid(Class<T> type, long uid, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        FetchStrategy<T> s = lkp.uid(type, uid, policy, factory);
        return createFacade(type, s);
    }

    public <T, P> PersistenceFacade<T> forQuery(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy) {
        FetchStrategy<T> s = lkp.query(type, field, fieldType, value, policy);
        return createFacade(type, s);
    }

    public <T, P> PersistenceFacade<T> forQuery(Class<T> type, String field, Class<P> fieldType, P value) {
        FetchStrategy<T> s = lkp.query(type, field, fieldType, value);
        return createFacade(type, s);
    }

    public <T, P> PersistenceFacade<T> forQuery(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        FetchStrategy<T> s = lkp.query(type, field, fieldType, value, policy, factory);
        return createFacade(type, s);
    }
    
    private <T>Collection<PersistenceFacade<T>> createCollectionOfFacades(Collection<T> objs, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        List <PersistenceFacade<T>> result = new ArrayList<PersistenceFacade<T>>(objs == null ? 0 : objs.size());
        if (objs != null) {
            for (T t : objs) {
                //Don't want existing object because it will be retained in the session
                long uid = lkp.utils().getUid(t);
                if (uid != -1L) {
                    @SuppressWarnings("unchecked")
                    //XXX possible that the new object factory is for the wrong
                    //type if the collection is heterogenous
                    Class<T> clazz = (Class<T>) t.getClass();
                    result.add (this.<T>forUid(clazz, uid, policy, factory));
                } else { //new objects
                    result.add (this.<T>createFacadeForExistingObject(t, policy, factory));
                }
            }
        }
        return result;
    }

    public <T> Collection<PersistenceFacade<T>> forAllOfType(Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare) {
        Collection <T> l = new ArrayList<T>(lkp.utils().fetchAllOfType(type));
        if (compare != null) {
            if (!(l instanceof List)) {
                l = new ArrayList<T>(l);
            }
            Collections.sort ((List<T>)l, compare);
        }
        return this.createCollectionOfFacades(l, policy, factory);
    }

    public <T> Collection<PersistenceFacade<T>> forAllMatchingPrototype(T prototype, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare) {
        Collection <T> l = new ArrayList<T>(lkp.utils().fetchByPrototype(prototype));
        if (compare != null) {
            if (!(l instanceof List)) {
                l = new ArrayList<T>(l);
            }
            Collections.sort ((List<T>)l, compare);
        }
        return this.createCollectionOfFacades(l, policy, factory);
    }

    public <T, P>Collection<PersistenceFacade<T>> forAllMatchingQuery(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare) {
        List <T> l = lkp.utils().fetchAllByQuery(type, field, fieldType, value, compare);
        return createCollectionOfFacades(l, policy, factory);
    }

    public <T> Collection<PersistenceFacade<T>> forExistingObjects(Collection<T> objs, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        return createCollectionOfFacades(objs, policy, factory);
    }
    
    public <T> Collection<PersistenceFacade<T>> forComplexQuery(Class<T> type, QueryElement element, Comparator<T> comparator, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        //PENDING:  Would be nice to create a PojoCollectionModel without 
        //actually preemptively running the query here and resolving the list
        //of objects.  Probably would just need a lazy list impl for the list
        //of underlying PojoModels to do it
        Collection<T> objs = lkp.utils().fetchByComplexQuery(type, element);
        if (comparator != null) {
            objs = new ArrayList<T> (objs);
            Collections.sort((List<T>)objs, comparator);
        }
        return createCollectionOfFacades(objs, policy, factory);
    }

    public <T> List<PersistenceFacade<T>> forUids(Collection<Long> uids, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        List <PersistenceFacade<T>> result = new ArrayList<PersistenceFacade<T>>(uids.size());
        for (long uid : uids) {
            result.add (forUid(type, uid, policy, factory));
        }
        return result;
    }
    
    public <T> List<PersistenceFacade<T>> forUuids(Collection<String> uids, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        List <PersistenceFacade<T>> result = new ArrayList<PersistenceFacade<T>>(uids.size());
        for (String uuid : uids) {
            result.add (forUuid(uuid, type, policy, factory));
        }
        return result;
    }

}
