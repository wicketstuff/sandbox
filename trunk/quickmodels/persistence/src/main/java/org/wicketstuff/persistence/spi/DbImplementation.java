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

import java.io.Serializable;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.DbJob;

/**
 * SPI for supporting a database.  Applications should instantiate an
 * instance of Db over a concrete DbImplemention;  they should not directly
 * call methods on DbImplementation.
 * 
 * @author Tim Boudreau
 * @param ContainerType Implementation-specific type representing the database,
 * which can be used in the case of custom queries
 */
public interface DbImplementation <ContainerType> extends Serializable {
    /**
     * Run a job that interacts with the database.  All database operations
     * should be performed through this method in order to enable consistent
     * logging and debugging.  Implementations may or may not synchronize
     * access.
     * @param job A job to run
     * @param param An optional parameter to pass to the job
     * @return The result of running the job
     */
    public <T, P> T run (DbJob<ContainerType, T, P> job, P param);
    /**
     * Creates an instance of PersistenceUtils which enable reading and
     * writing of objects to this database.
     * @param db This database
     * @return An instance of PersistenceUtils which can be used over the
     * lifecycle of this database
     */
    public PersistenceUtils createPersistenceUtils(Db<ContainerType> db);
    /**
     * Shut the database down, indicating that any caches should be written
     * to disk and that the JVM is probably about to be shut down.
     */
    public void shutdown();
}
