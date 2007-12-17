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
import org.wicketstuff.persistence.spi.PersistenceUtils;
/**
 *
 * @author Tim Boudreau
 */
class LookupStrategyFactory implements Serializable {
    final Db<?> db;
    LookupStrategyFactory(Db<?> db) {
        this.db = db;
    }
    
    PersistenceUtils utils() {
        //XXX inelegant
        return db.getPersistenceUtils();
    }

    public <T> FetchStrategy<T> prototype(Class<T> type, T prototype) {
        return new FetchStrategy.PrototypeFetchStrategy<T>(
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                prototype, 
                null,
                null);
    }
    
    public <T> FetchStrategy<T> prototype(Class<T> type, T prototype, LoadFailurePolicy policy) {
        return new FetchStrategy.PrototypeFetchStrategy<T>(
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                prototype, 
                policy,
                null);
    }
    
    public <T> FetchStrategy<T> prototype(Class<T> type, T prototype, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        return new FetchStrategy.PrototypeFetchStrategy<T>(
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                prototype, 
                policy,
                factory);
    }
    
    public <T> FetchStrategy<T> newObject(Class<T> type) {
        return new FetchStrategy.CreateNewFetchStrategy<T>(
                null, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils());
    }
    
    public <T> FetchStrategy<T> newObject(Class<T> type, NewObjectFactory<T> factory) {
        return new FetchStrategy.CreateNewFetchStrategy<T>(
                factory, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils());
    }
    
    public <T> FetchStrategy<T> uuid(String uuid, Class<T> type) throws InvalidUuidException {
        return new FetchStrategy.UuidFetchStrategy<T>(
                uuid, 
                type,
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                LoadFailurePolicy.THROW_EXCEPTION_ON_FAILURE);
    }
    
    public <T> FetchStrategy<T> uuid(String uuid, Class<T> type, LoadFailurePolicy policy) throws InvalidUuidException {
        return new FetchStrategy.UuidFetchStrategy<T>(
                uuid, 
                type,
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(),
                policy);
    }
    
    public <T> FetchStrategy<T> uuid(String uuid, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) throws InvalidUuidException {
        return new FetchStrategy.UuidFetchStrategy<T>(
                uuid, 
                type,
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                policy, 
                factory);
    }
    
/*    public <T> FetchStrategy<T> uuid(String uuid, Class<T> type, LoadFailurePolicy policy, PersistenceFacade.NewObjectFactory<T> factory) throws InvalidUuidException {
        return new FetchStrategy.UuidFetchStrategy<T>(
                uuid, 
                type,
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                policy, 
                factory);
    }
 */ 
    
    public <T> FetchStrategy<T> uuid(Class<T> type, long uid) {
        return new FetchStrategy.UidFetchStrategy<T>(
                uid, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils());
    }
    
    public <T> FetchStrategy<T> uid(Class<T> type, long uid, LoadFailurePolicy policy) {
        return new FetchStrategy.UidFetchStrategy<T>(
                uid, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(),
                policy);
    }
    
    public <T> FetchStrategy<T> uid(Class<T> type, long uid, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        return new FetchStrategy.UidFetchStrategy<T>(
                uid, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(),
                policy,
                factory);
    }
/*
    public <T> FetchStrategy<T> uid(Class<T> type,
            long uid, LoadFailurePolicy policy) {
        return new FetchStrategy.UidFetchStrategy<T>(
                uid, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                policy);
    }
    
    public <T> FetchStrategy<T> uid(Class<T> type,
            long uid, LoadFailurePolicy policy, 
            PersistenceFacade.NewObjectFactory<T> factory) {
        return new FetchStrategy.UidFetchStrategy<T>(
                uid, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                policy, 
                factory);
    }
 */ 
    
    public <P, T> FetchStrategy<T> query(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy) {
        return new FetchStrategy.SinglePropertyQueryLookupStrategy<T, P>(
                type, 
                field, 
                fieldType, 
                value, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                policy, 
                null);
    }
    public <P, T> FetchStrategy<T> query(Class<T> type, String field, Class<P> fieldType, P value) {
        return new FetchStrategy.SinglePropertyQueryLookupStrategy<T, P>(
                type, 
                field, 
                fieldType, 
                value, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                null, 
                null);
    }
    public <P, T> FetchStrategy<T> query(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        return new FetchStrategy.SinglePropertyQueryLookupStrategy<T, P>(
                type, 
                field, 
                fieldType, 
                value, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(), 
                policy, 
                factory);
    }
    
    public <T> FetchStrategy<T> preexisting(T object, LoadFailurePolicy policy, NewObjectFactory factory) {
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) object.getClass();
        return new FetchStrategy.HoldAndSaveFetchStrategy<T>(
                object, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils(),
                policy,
                factory);
    }
    
    
    public <T> FetchStrategy<T> preexisting(T object) {
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) object.getClass();
        return new FetchStrategy.HoldAndSaveFetchStrategy<T>(
                object, 
                type, 
                db.getActivationStrategy(type), 
                db.getPersistenceUtils());
    }
    
    public <T> FetchStrategy<T> singleton(Class<T> clazz, LoadFailurePolicy policy, NewObjectFactory<T> factory) {
        return new FetchStrategy.SingletonOrFirstFoundLookupStrategy<T>(
                factory,
                clazz, 
                db.getActivationStrategy(clazz), 
                db.getPersistenceUtils(),
                policy);
    }
    
    public <T> FetchStrategy<T> singleton(Class<T> clazz, LoadFailurePolicy policy) {
        return new FetchStrategy.SingletonOrFirstFoundLookupStrategy<T>(
                null,
                clazz, 
                db.getActivationStrategy(clazz), 
                db.getPersistenceUtils(),
                policy);
    }
    
    public <T> FetchStrategy<T> singleton(Class<T> clazz) {
        return new FetchStrategy.SingletonOrFirstFoundLookupStrategy<T>(
                null,
                clazz, 
                db.getActivationStrategy(clazz), 
                db.getPersistenceUtils(),
                null);
    }
    
}
