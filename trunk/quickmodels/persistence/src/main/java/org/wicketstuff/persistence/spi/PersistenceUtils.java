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
package org.wicketstuff.persistence.spi;

import org.wicketstuff.persistence.ActivationStrategy;
import org.wicketstuff.persistence.queries.QueryElement;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * The class one needs to implement to implement database persistence.
 * Does all reads and writes.  Implementations should wrap all calls to
 * this interface's methods in calls to DbImplementation.run() with
 * appropriate implementations of <code>DbJob</code>.
 *
 * @see org.netbeans.libs.persistencefacade.Db
 * @see org.netbeans.libs.persistencefacade.spi.DbImplementation
 *
 * @author Tim Boudreau
 */
public interface PersistenceUtils extends Serializable {
    /**
     * Fetch all persisted objects of the passed type from the database.
     * @param clazz The object type
     * @return A List of all such objects currently in the database
     */
    <T> List<T> fetchAllOfType(Class<T> clazz);
    /**
     * Fetch all objects of type T whose fields match those of the prototype
     * object passed in.
     * @param prototype A prototype object which has some fields set, in order
     * for the query to return objects that match the prototype's values
     * @return A list of objects of type T matching the query
     */
    <T> java.util.List<T> fetchByPrototype(T prototype);
    /**
     * Fetch a persisted object of type T whose unique ID matches the 
     * passed long.
     * @param uid A long representing a unique id in the database
     * @param type The type of object to be returned
     * @param activation For databases that selectively instantiate their 
     * object graph to a given depth, an activation strategy determines how
     * deeply the object graph should be resolved.
     * @return
     */
    <T> T fetchByUid(long uid, Class<T> type, ActivationStrategy activation);
    /**
     * Fetch an object by universal unique id, a string which uniquely
     * identifies an object in a potentially system-independent way.  The
     * actual text content of the UUID is implementation-dependent.
     * @param uuid A unique ID
     * @param type The type of object to be returned
     * @param activation How deeply to activate the object's graph (may be null)
     * @return
     */
    <T> T fetchByUuid(String uuid, Class<T> type, ActivationStrategy activation);
    /**
     * Get a long unique ID for an object
     * @param o A persisted object
     * @return A uid, or -1L if the object is not persisted
     */
    long getUid(Object o);
    /**
     * Get a version number for the passed object.  Version numbers should
     * increment when writes are done to the backing database.
     * @param o
     * @return
     */
    long getVersion(Object o);
    /**
     * Get a unique, potetntially system-independent ID for an object.
     * @param o An object that has been persisted
     * @return A unique id as a string, or null if the object is not persisted
     */
    String getUuid(Object o);
    /**
     * Write an object to the database.
     * @param object An object to save
     * @return whether or not the write was completed successfully.
     */
    <T> boolean saveObject(T object);
    /**
     * Save an object and return a uid for the object
     * @param object An object to save
     * @return A long uid for the object saved, or -1 on failure
     */
    <T> long saveObjectReturnUid(T object);
    /**
     * Fetch a singleton object of a given type from the database.  In the
     * case that more than one object of the type is persisted, it is unspecified
     * what object will be returned.
     * @param type
     * @return
     */
    <T> T fetchOneOfType(Class<T> type);
    /**
     * Delete an object from the database
     * @param obj A persisted object to delete
     * @return true if successful
     */
    boolean delete(Object obj);
    /**
     * Remove all objects of the passed type from the database
     * @param clazz The type
     * @return true if successful
     */
    <T> boolean deleteAllOfType(Class<T> clazz);
    /**
     * Fetch an object with the field of type P named fieldName which has its
     * value set to fieldValue.
     * @param type The type of obejct to return
     * @param field The type of the field
     * @param fieldName The name of the field
     * @param fieldValue The value of the field
     * @return An object matching the query terms or null
     */
    <T,P> T fetchByFieldQuery(Class<T> type, Class<P> field, String fieldName, P fieldValue);
    /**
     * Fetch a list of all objects which have a field set per the passed 
     * arguments
     * @param type The type of object to return
     * @param fieldName The name of the field
     * @param fieldType The type of the field's value
     * @param fieldValue The value the field should have
     * @param compare A comparator for sorting the resulting list
     * @return A list of objects from the database which match the passed
     * query terms
     */
    <T,P> List<T> fetchAllByQuery(Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, Comparator<T> compare);
    /**
     * Fetch a collection of objects that matches a complex query that may
     * specify multiple object fields and values.
     * @param expectedType The type of objects in the returned collection
     * @param query The query
     * @return A collection of objects of the passed type
     */
    <T> Collection<T> fetchByComplexQuery (Class<T> expectedType, QueryElement<T> query);
}
