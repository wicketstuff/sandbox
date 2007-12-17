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
import org.wicketstuff.persistence.db4o.Db4oDbImpl;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.queries.*;
import org.wicketstuff.persistence.spi.PersistenceUtils;


/**
 *
 * @author Tim Boudreau
 */
public class ComplexQueryValuesTest extends TestCase {
//    
//    @BeforeClass
//    public static void setUpClass() {
////        System.setProperty ("Db.log", "true");
//    }
    
    public ComplexQueryValuesTest (String id) {
        super (id);
    }
    
    static Db<ObjectContainer> db;
    static PersistenceUtils utils;
    static A oneA;
    static A oneB;
    static A oneA1;
    static A oneA2;
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
        oneA = new A ("oneA", true, 1, 1, "comb", "A");
        oneB = new A ("oneB", true, 10, 2, "tomb", "A");
        oneA1 = new A ("twoA1", false, 15, 3, "bomb", "A");
        oneA2 = new A ("twoA2", false, 21, 4, "sludge", "B");
        utils.saveObject(oneA);
        utils.saveObject(oneB);
        utils.saveObject(oneA1);
        utils.saveObject(oneA2);
    }
    
    public void tearDown() {
        db.shutdown();
    }
    
    public void testRanges() throws Exception {
        System.out.println("testRanges");
        FieldQueryElement fd = new FieldQueryElement("b.c.intval", Integer.TYPE,
                new RangeValue<Integer>(10, 25, Integer.class), false);
        
        Collection <A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 3, c.size());
        assertTrue (c.contains(oneB));
        assertTrue (c.contains(oneA1));
        assertTrue (c.contains(oneA2));
        assertFalse (c.contains(oneA));
        System.out.println("testRanges exit");
    }
    
    
    public void testStartsWith() throws Exception {
        System.out.println("testStartsWith");
        FieldQueryElement fd = new FieldQueryElement("name", String.class,
                new StringStartsWithValue("one", true), false);
        Collection <A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 2, c.size());
        assertTrue (c.contains(oneA));
        assertTrue (c.contains(oneB));
        System.out.println("testStartsWith exit");
    }
    
    
    public void testContains() throws Exception {
        System.out.println("testContains");
        FieldQueryElement fd = new FieldQueryElement("name", String.class,
                new StringContainsValue("wo", false), false);
        Collection <A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 2, c.size());
        assertTrue (c.contains(oneA1));
        assertTrue (c.contains(oneA2));
        
        fd = new FieldQueryElement("name", String.class,
                new StringContainsValue("thipt", false), false);
        c = utils.fetchByComplexQuery(A.class, fd);
        assertTrue ("Bad result: " + c, c.isEmpty());
        
        System.out.println("testContains exit");
    }
    
    
    public void testEndsWith() throws Exception {
        System.out.println("testEndsWith");
        FieldQueryElement fd = new FieldQueryElement("b.stringVal", String.class,
                new StringEndsWithValue("omb", false), false);
        Collection <A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 3, c.size());
        assertTrue (c.contains(oneA));
        assertTrue (c.contains(oneA1));
        assertTrue (c.contains(oneB));
        System.out.println("testEndsWith exit");
    }
    
}
