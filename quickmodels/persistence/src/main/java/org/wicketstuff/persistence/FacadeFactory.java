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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.wicketstuff.persistence.queries.QueryElement;

/**
 * Interface that creates PersistenceFacades for various types of queries.
 * Persistence facades are used to achieve lazy loading of objects from
 * the database (i.e. you get a facade for a UID, it does not necessarily
 * fetch the object from the database until the facade is actually used).
 * <p/>
 * The implementation of this interface is provided by the library - if you
 * are implementing it, you are probably doing something wrong.
 * <p/>
 * PENDING:  Get rid of some of the silly overloads in this interface.<br/>
 * PENDING:  Would be nicer to wrap this and make it a final class - makes more
 * sense than interfaces in API.
 *
 * @author Tim Boudreau
 */
public interface FacadeFactory <ContainerType> {
    /**
     * Create an instance of <code>type</code> using the default constructor, 
     * and return PersistenceFacade for that object.
     * @param type The type of object to create.  Must have a default no-arg
     * constructor
     * @return A facade that can manage the lifecycle of the new object
     */
    <T> PersistenceFacade<T> forNew(Class<T> type);
    /**
     * Create an instance of <code>type</code> using the passed factory,
     * and return PersistenceFacade for that object.
     * @param type The object type
     * @param factory A factory for objects of <code>type</code>
     * @return A facade that can manage the lifecycle of that object
     */
    <T> PersistenceFacade<T> forNew(Class<T> type,
            NewObjectFactory<T> factory);
    
    /**
     * Create a facade for an existing object (which may or may not be
     * persisted)
     * @param obj An object a facade should be created for
     * @return A facade that can manage the lifecycle of that object
     */
    <T> PersistenceFacade<T> forExisting(T obj);
    /**
     * Create a facade that will, on demand, fetch an object from the
     * database which matches the prototype object.
     * @param obj A prototype object with some fields set
     * @return A facade that can manage the lifecycle of that object
     */
     <T> PersistenceFacade<T> forPrototype(T obj);


    /**
     * Create a facade for a singleton object of the passed type - for the 
     * case that the database should only contain one of this type.
     * @param type The object type
     * @return A facade that can manage the lifecycle of that object
     */
     <T> PersistenceFacade<T> forSingleton(
            Class<T> type);
    /**
     * Create a facade for a singleton object of the passed type - for the 
     * case that the database should only contain one of this type.  If no
     * object of that type is found, the LoadFailurePolicy will determine
     * the mode of failure.
     * @param type The type of the persisted singleton object
     * @param policy What to do on failure - create a new object, return null or
     * throw an exception
     * @return A facade that can manage the lifecycle of that object
     * @throws LookupFailedException if the policy is set as such
     */
     <T> PersistenceFacade<T> forSingleton(
            Class<T> type, LoadFailurePolicy policy);
    /**
     * Create a facade for a singleton object of the passed type - for the 
     * case that the database should only contain one of this type.  If no
     * object of that type is found, the LoadFailurePolicy will determine
     * the mode of failure.
     * @param type The type of the singleton
     * @param policy What to do if no object of that type can be found
     * @param factory A factory for new objects (only meaningful when used
     * in conjunction with LoadFailurePolicy.CREATE_NEW_OBJECT)
     * @return A facade that can manage the lifecycle of that object
     */
     <T> PersistenceFacade<T> forSingleton(
            Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory);

    
    /**
     * Fetch a facade for an object by its universal unique id
     * @param uuid The universal unique id
     * @param type The type of object expected
     * @return A facade that can manage the lifecycle of that object
     * @throws org.netbeans.libs.persistencefacade.InvalidUuidException
     */
     <T> PersistenceFacade<T> forUuid(String uuid, Class<T> type) throws InvalidUuidException;
    /**
     * Fetch a facade for an object by its universal unique id
     * @param uuid The universal unique id
     * @param type The type of object expected
     * @param policy What to do if the object cannot be created
     * @return A facade that can manage the lifecycle of that object
     * @throws org.netbeans.libs.persistencefacade.InvalidUuidException if the
     * passed UUID is corrupted and cannot be parsed
     */
     <T> PersistenceFacade<T> forUuid(String uuid, Class<T> type, LoadFailurePolicy policy) throws InvalidUuidException;
    /**
     * Fetch a facade for an object by its universal unique id
     * @param uuid The universal unique id
     * @param type The type of object expected
     * @param policy What to do if the object cannot be created
     * @param factory A factory for new objects (only meaningful when used
     * in conjunction with LoadFailurePolicy.CREATE_NEW_OBJECT)
     * @return A facade that can manage the lifecycle of that object
     * @throws org.netbeans.libs.persistencefacade.InvalidUuidException if the
     * passed UUID is corrupted and cannot be parsed
     */
     <T> PersistenceFacade<T> forUuid(String uuid, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory) throws InvalidUuidException;
    
    /**
     * Fetch an object by its long unique id
     * @param type The type of object expected
     * @param uid The unique id
     * @return A facade which can manage the lifecycle of this object
     */
     <T> PersistenceFacade<T> forUid(Class<T> type,
            long uid);
    /**
     * Fetch an object by its long unique id
     * @param type The type of object expected
     * @param uid The unique id
     * @param policy What to do in the case that no object with this uid is found
     * @return A facade which can manage the lifecycle of this object
     */
     <T> PersistenceFacade<T> forUid(Class<T> type,
            long uid, LoadFailurePolicy policy);
    /**
     * Fetch an object by its long unique id
     * @param type The type of object expected
     * @param uid The unique id
     * @param policy What to do in the case that no object with this uid is found
     * @param factory A factory for new objects (only meaningful when used
     * in conjunction with LoadFailurePolicy.CREATE_NEW_OBJECT)
     * @return A facade which can manage the lifecycle of this object
     */
     <T> PersistenceFacade<T> forUid(Class<T> type,
            long uid, LoadFailurePolicy policy, 
            NewObjectFactory<T> factory);
    
    /**
     * Fetch a facade for a single object which matches the passed query
     * @param type The type of object expected
     * @param field The name of a field on that object
     * @param fieldType The type of the field on that object
     * @param value The value that should be matched
     * @return A facade which can manage the lifecycle of the persisted object
     */
     <T,P> PersistenceFacade<T> forQuery(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy);
    /**
     * Fetch a facade for a single object which matches the passed query
     * @param type The type of object expected
     * @param field The name of a field on that object
     * @param fieldType The type of the field on that object
     * @param value The value that should be matched
     * @param policy What to do if no object matches - may be null
     * @return A facade which can manage the lifecycle of the persisted object
     */
     <T,P> PersistenceFacade<T> forQuery(Class<T> type, String field, Class<P> fieldType, P value);
    /**
     * Fetch a facade for a single object which matches the passed query
     * @param type The type of object expected
     * @param field The name of a field on that object
     * @param fieldType The type of the field on that object
     * @param value The value that should be matched
     * @param policy What to do if no object matches - may be null
     * @param factory A factory that can create new objects - may be null
     * @return A facade which can manage the lifecycle of the persisted object
     */
     <T,P> PersistenceFacade<T> forQuery(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy policy, NewObjectFactory<T> factory);

    /**
     * Fetch a collection facades for all persisted objects of the passed type
     * in the database.
     * @param type The expected type
     * @param policy What to do if no match
     * @param factory A factory to create new objects, or null
     * @param compare An optional comparator to sort the returned collection
     * @return A collection of facades
     */
     <T> Collection<PersistenceFacade<T>> forAllOfType(Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare);
    /**
     * Fetch a collection of facades for all persisted objects matching the
     * passed prototype object
     * @param prototype A prototype object with some fields set
     * @param policy What to do if no match
     * @param factory A factory to create new objects, or null
     * @param compare An optional comparator to sort the results
     * @return A collection of facades
     */
     <T> Collection<PersistenceFacade<T>> forAllMatchingPrototype(T prototype, LoadFailurePolicy policy, NewObjectFactory<T> factory, Comparator<T> compare);
    /**
     * Fetch a collection of facades in which a field matches the values 
     * passed
     * @param type The type of object expected
     * @param field Name of a field on that object
     * @param fieldType Type of the field on that object
     * @param value Value that should match by equality on returned objects
     * @param policy What to do if not matched
     * @param factory A factory for new objects, or null
     * @param compare An optional comparator for sorting results
     * @return A collection of facades for objects matching the query
     */
     <T, P> Collection<PersistenceFacade<T>> forAllMatchingQuery(Class<T> type, String field, Class<P> fieldType, P value, LoadFailurePolicy Policy, NewObjectFactory<T> factory, Comparator<T> compare);

    /**
     * Fetch a collection of facades for a collection of existing objects.
     * The order of returned facades will match the order of objects in the
     * passed collection.
     * 
     * @param objs A list of objects that may be persisted
     * @param policy What to do on load failure - this can happen if, for example,
     * an object is deleted after a facade has saved it (saving should revert
     * the facade to only holding the ID of the object, not the object instance
     * itself)
     * @param factory Optional factory for new instances for use if the 
     * cached object is deleted from the database
     * @return A collection of facades that can manage the lifecycle of the 
     * passed objects
     */
     <T> Collection<PersistenceFacade<T>> forExistingObjects(Collection<T> objs, LoadFailurePolicy policy, NewObjectFactory<T> factory);
    /**
     * Fetch a collection of facades for a collection of unique ids.
     * @param uids A collection of unique ids.
     * @param type The type of the objects expected
     * @param policy What to do if no object with a given uid exists
     * @param factory Optional factory for new instances for use if the 
     * cached object is deleted from the database
     * @return A collection of facades that can manage the lifecycle of the 
     * persisted objects
     */
     <T> List<PersistenceFacade<T>> forUids(Collection<Long> uids, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory);
    /**
     * Fetch a collection of facades for a collection of unique ids.
     * @param uuids A collection of universal unique ids.
     * @param type The type of the objects expected
     * @param policy What to do if no object with a given uid exists
     * @param factory Optional factory for new instances for use if the 
     * cached object is deleted from the database
     * @return A collection of facades that can manage the lifecycle of the 
     * persisted objects
     */
     <T> List<PersistenceFacade<T>> forUuids(Collection<String> uuids, Class<T> type, LoadFailurePolicy policy, NewObjectFactory<T> factory);
    
    /**
     * 
     * @param type The type of objects expected
     * @param element A FieldQueryElement or CompoundQueryElement
     * @param comparator Optional comparator to sort the result of the query
     * @param policy What to do if nothing matches the query
     * @param factory A factory for objects if the policy is LoadFailurePolicy.CREATE...
     * @return A collection of facades that can manage the lifecycle of the
     * persisted objects
     */
     <T> Collection<PersistenceFacade<T>> forComplexQuery(Class<T> type, QueryElement element, Comparator<T> comparator, LoadFailurePolicy policy, NewObjectFactory<T> factory);

    /**
     *
     * @param job
     * @param arg
     * @return
     */
    <T, P> Collection<PersistenceFacade<T>> runCustomQuery (
            DbJob<ContainerType, Collection<T>, P> job, P arg);

}
