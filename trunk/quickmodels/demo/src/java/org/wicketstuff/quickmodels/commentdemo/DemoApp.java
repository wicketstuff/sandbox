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
package org.wicketstuff.quickmodels.commentdemo;           

import com.db4o.ObjectContainer;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.db4o.Db4oDbImpl;
import org.wicketstuff.quickmodels.DbApplication;
/** 
 * Our application instance, subclassing DbApplication.  Currently uses
 * the db4o implementation of persistence, but could use any implementation
 *
 * @author Tim Boudreau
 */
public class DemoApp extends DbApplication<ObjectContainer> {
    public Class getHomePage() {
        return Home.class;
    }

    @Override
    protected Db<ObjectContainer> createDatabase() {
        //Create database file in the application's working dir
        //In Glassfish w/ defaults this will be under $GF_HOME/domains/domain1/...
        return Db4oDbImpl.create("database");
    }
}
