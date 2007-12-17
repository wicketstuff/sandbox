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

import org.wicketstuff.persistence.spi.CapabilityProvider;
import org.wicketstuff.persistence.spi.DbImplementation;
import org.wicketstuff.persistence.spi.PersistenceUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstraction for a database.  Note:  To log all database access,
 * set the system property <code>Db.log</code> to true before creating
 * an instance of this class.
 * 
 * @author Tim Boudreau
 */
public final class Db<ContainerType> implements Serializable {
    private final PersistenceUtils utils;
    private final FacadeFactory facade;
    private final DbImplementation <ContainerType> impl;
    private final Map<Class, Integer> activations = new HashMap<Class, Integer>();
    private static final boolean logAccess = Boolean.getBoolean ("Db.log");
    
    /**
     * Create a new database using the passed DbImplementation.
     * @param impl A database implementation
     */
    public Db(DbImplementation <ContainerType> impl) {
        this.impl = impl;
        utils = impl.createPersistenceUtils(this);
        facade = createFacadeFactory();
    }
    
    /**
     * Get this database's factory for PersistenceFacades for persistent
     * objects
     * @return the Facade Factory for this database
     */
    public FacadeFactory<ContainerType> getFacadeFactory() {
        return facade;
    }
    
    private FacadeFactory<ContainerType> createFacadeFactory() {
        return new FacadeFactoryImpl<ContainerType>(getLookupStrategyFactory());
    }
    
    LookupStrategyFactory getLookupStrategyFactory() {
        return new LookupStrategyFactory (this);
    }
    
    /**
     * Get the database's persistence utilities which enable loading and
     * saving and deleting objects from the database
     * @return
     */
    public final PersistenceUtils getPersistenceUtils() {
        return utils;
    }
    
    /**
     * Shut this database down, writing any caches to disk in preparation
     * for VM shutdown
     */
    public void shutdown() {
        impl.shutdown();
    }
    
    /**
     * Run a job against this database
     * @param job The job to run
     * @param arg An optional argument to be passed to the job's run() method
     * @return The result of the job's run() method
     */
    public  <T, P> T run (DbJob<ContainerType, T, P> job, P arg) {
        if (logAccess) {
            System.out.println(job + " on " + Thread.currentThread());
        }
        return impl.run(job, arg);
    }
    
    final ActivationStrategy getActivationStrategy(Class type) {
        Integer val = activations.get(type);
        if (val == null) {
            return ActivationStrategy.createDefault();
        } else {
            return ActivationStrategy.create(val.intValue());
        }
    }

    /**
     * Set how deeply the object graph of a de-persisted object should be
     * resolved, by type
     * @param persistedType
     * @param val
     */
    public final void setActivationStrategy (Class persistedType, int val) {
        activations.put(persistedType, val);
    }
    
    /**
     * Fetch a custom capability of the underlying database implementation.
     * For example, a database might provide an interface for SQL queries,
     * and make an implementation of that interface available from this method
     * @param clazz
     * @return null if the database implementation passed to the constructor does
     * not implement CapabilityProvider;  null if no such capability is present;
     * or an object of the type requested if possible
     */
    public final <T> T getCapability (Class<T> clazz) {
        if (impl instanceof CapabilityProvider) {
            return ((CapabilityProvider) impl).getCapability(clazz);
        } else {
            return null;
        }
    }
}
