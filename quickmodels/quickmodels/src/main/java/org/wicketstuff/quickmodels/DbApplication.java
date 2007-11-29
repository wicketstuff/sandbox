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
package org.wicketstuff.quickmodels;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.DbProvider;
import org.wicketstuff.persistence.NewObjectFactory;
import org.wicketstuff.persistence.spi.DbImplementation;

/**
 * Base class for applications which use persistence facade models over a
 * database.  Implementations must override either 
 * <code>createDatabase()</code> or <code>createDatabaseImpl()</code> to
 * provide the actual database container.
 * <p>
 * 
 * @author Tim Boudreau
 * @param ContainerType the database implementation-specific type of the
 * database container
 */
public abstract class DbApplication<ContainerType> extends WebApplication implements DbProvider<ContainerType> {
    private final Db<ContainerType> db;
    protected DbApplication () {
        this.db = createDatabase();
    }
    
    /**
     * Create the DatabaseImplementation that will drive the database for this 
     * application.  <strong>Note:  This method is called in the superclass
     * constructor and should not rely on instance fields being set at the time
     * it is called</strong>.  The default implementation simply calls 
     * createDatabaseImpl() and creates an instance of Db from the result.
     * @return
     */
    protected Db createDatabase() {
        return new Db (createDatabaseImpl());
    }
    
    /**
     * Create the database implementation.  The default implementation
     * throws an Error.  Either this method must be overridden, or 
     * <code>createDatabase()</code> must be.
     * @return
     */
    protected DbImplementation<ContainerType> createDatabaseImpl() {
        throw new Error ("Either override this method or createDatabase()");
    }

    @Override
    protected void internalDestroy() {
        super.internalDestroy();
        db.shutdown();
    }

    /**
     * Get this app's database
     * @return the database
     */
    public final Db<ContainerType> getDatabase() {
        return db;
    }
    
    /**
     * Get the database for the current application
     * @return an instance of Db
     */
    public static Db getDb() {
        Application app = Application.get();
        if (app instanceof DbApplication) {
            DbApplication dba = (DbApplication) app;
            return dba.getDatabase();
        }
        return null;
    }
    
    /**
     * Get the running application as an instance of DbApplication.
     * @return A DbApplication, or null if the application cannot be resolved
     * or is not an instance of DbApplication
     */
    public static DbApplication dbApp() {
        Application app = Application.get();
        if (app instanceof DbApplication) {
            DbApplication dba = (DbApplication) app;
            return dba;
        }
        return null;
    }
    
    /**
     * Get a singleton such as application configuration information, from the
     * database.  If no such object exists in the database, one will be created
     * via <code>type.newInstance</code>; so any type passed to this method
     * must have a public, no-argument constructor.
     * 
     * @param type The type of object
     * @return A singleton object from the database
     * @throws IllegalStateException if an exception is thrown instantiating
     * the object
     */
    public final <T> T getSingleton (Class<T> type) {
        return getSingleton (type, null);
    }
    
    /**
     * Get a singleton such as application configuration information, from the
     * database.
     * @param type The type of object
     * @param factory A factory that will create an object of that type if none
     * exists in the database.  If null, <code>type.newInstance()</code> will
     * be used.
     * @return A singleton object from the database
     * @throws IllegalStateException if an exception is thrown instantiating
     * the object
     */
    public final <T> T getSingleton (Class<T> type, NewObjectFactory<T> factory) {
        Object result = getDatabase().getPersistenceUtils().fetchOneOfType(type);
        if (result != null && !type.isInstance(result)) {
            throw new ClassCastException ("Expected " + type + " but got " +
                    result.getClass());
        }
        if (result == null) {
            if (factory == null) {
                factory = new NewObjectFactory.Reflection(type);
            }
            result = factory.create();
        }
        return (T) result;
    }
}
