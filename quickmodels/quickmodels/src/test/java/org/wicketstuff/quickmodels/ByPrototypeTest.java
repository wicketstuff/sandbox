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

import com.db4o.ObjectContainer;
import java.io.File;
import java.util.Collection;
import junit.framework.TestCase;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.db4o.Db4oDbImpl;
import org.wicketstuff.persistence.spi.PersistenceUtils;
/**
 *
 * @author Tim Boudreau
 */
public class ByPrototypeTest extends TestCase {
    
    public ByPrototypeTest(String id) {
        super (id);
    }
    
    static Db<ObjectContainer> db;
    static PersistenceUtils utils;
    static A oneA;
    static A oneB;
    static A oneA1;
    static A oneA2;
    @Override
    public void setUp() {
        long time = System.currentTimeMillis();
        File f = new File (System.getProperty("java.io.tmpdir"));
        File dbFile = null;
        int ct = 0;
        while (dbFile == null) {
            dbFile = new File (f, time + ct + ".db");
            if (dbFile.exists()) {
                dbFile = null;
                ct++;
            }
        }
        String path = dbFile.getAbsolutePath();
        db = Db4oDbImpl.create(path);
        
        utils = db.getPersistenceUtils();
        oneA = new A ("oneA", true, 1, 1, "one", "A");
        oneB = new A ("oneB", true, 1, 2, "one", "A");
        oneA1 = new A ("oneA1", false, 1, 3, "foo", "A");
        oneA2 = new A ("oneA2", false, 2, 4, "one", "B");
        utils.saveObject(oneA);
        utils.saveObject(oneB);
        utils.saveObject(oneA1);
        utils.saveObject(oneA2);
    }
    
    @Override
    public void tearDown() {
        db.shutdown();
    }    
    
    public void testFetchByPrototype() {
        System.out.println("testFetchByPrototypes");
        A proto = new A ("oneA", false, 0, 0, null, null);
        Collection<A> other = (Collection<A>) utils.<A>fetchByPrototype(proto);
        assertNotNull (other);
        assertEquals (1, other.size());
        
        ModelBuilder<A, ?> b = Queries.PROTOTYPE.builder(A.class);
        b.setObject(proto);
        PojoModel<A> oneMdl = b.single(db);
        assertNotNull (oneMdl);
        assertNotNull (oneMdl.get());
        assertNotNull (oneMdl.get().get());
        
        PojoCollectionModel<A> cmdls = b.multi(db);
        assertNotNull (cmdls);
        assertEquals (1, cmdls.get().size());
        assertEquals (oneA, cmdls.iterator().next());
    }
}
