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
/**
 * Wrapper for a persisted object, which manages its lifecycle.
 * Depending on the state (modified, saved, deleted, etc.) an instance of
 * PersistenceFacade may cache the object it wraps;  or it may simply
 * hold a unique id to the object and fetch it on demand.
 * Persistence facades are used to achieve lazy loading of objects from
 * the database (i.e. you get a facade for a UID, it does not necessarily
 * fetch the object from the database until the facade is actually used).
 * <p/>
 * The implementation of this interface is provided by the library - if you
 * are implementing it, you are probably doing something wrong.
 * <p/>
 * Instances are obtained from a Db's FacadeFactory instance - i.e. call
 * db.getFacadeFactory().forExisting(myObject) to create a facade that
 * can save myObject to the database, etc.  To query for existing, already
 * persisted objects,
 * other methods on FacadeFactory are useful.
 *
 * @author Tim Boudreau
 */
public interface PersistenceFacade<T> extends Serializable {
    /**
     * Get the type of the persisted object
     * @return The type of the object being wrapped
     */
    Class<T> getType();
    /**
     * Fetches the actual object wrapped.
     * @return
     */
    T get();
    /**
     * Detach this facade - this method hints to the facade that, if possible,
     * it should revert to a state where it does not cache the object - i.e.
     * it should revert to its least memory-consuming state.  Wrappers for
     * modified but unsaved objects may ignore this call.
     */
    void detach();
    /**
     * Save the wrapped object to the database.
     */
    void save();
    /**
     * Get a unique, system independent id for an object in the database.
     * @return A unique id
     */
    String getUuid();
    /**
     * Get a long representing the id of an object in the database.
     * @return
     */
    long getUid();
    /**
     * Delete the object from the database.
     */
    void delete();
    /**
     * Hint to the implementation that some fields on the object have been
     * changed and that it should be saved.
     */
    void modified();
    /**
     * Determine if the object has a local copy of the object, or if a database
     * read is required to fetch it
     * @return Whether the object has already been fetched
     */
    boolean isAttached();
    /**
     * Hint that an object has become interested in the contents of
     * this PersistenceFacade.  The implementation uses a counting mechanism
     * to automatically discard the live object in some cases, if attached
     * and detached have been called an equal number of times.
     */
    void attached();
    /**
     * Called when a cached object has been discarded.
     * PENDING:  This probably shouldn't be in the API...
     */
    void detached();
    /**
     * Determine if the delete method has been called.
     * @return true if the object was deleted in the past
     */
    boolean isDeleted();
    /**
     * Determine if the object wrapped is stored in the database.  Deleted 
     * objects and new objects that are not yet saved will return false.
     * @return whether or not the object exists in the database.
     */
    boolean isPersisted();
    /**
     * Determine if the modified() method has been called since the last
     * call to save().
     * @return True if the object has been modified
     */
    boolean isModified();
    /**
     * If set to true, the facade should automatically invoke save() on itself
     * when detach() is called, if modified.
     * @param val Whether or not to auto-save on detach
     */
    void setSaveOnDetach(boolean val);
}
