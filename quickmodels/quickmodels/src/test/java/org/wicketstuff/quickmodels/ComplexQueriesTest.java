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
import org.wicketstuff.persistence.PersistenceFacade;
import org.wicketstuff.persistence.queries.FieldQueryElement;
import org.wicketstuff.persistence.queries.Logic;
import org.wicketstuff.persistence.queries.QueryElement;
import org.wicketstuff.persistence.queries.CompoundQueryElement;
import org.wicketstuff.persistence.spi.PersistenceUtils;


/**
 *
 * @author Tim Boudreau
 */
public class ComplexQueriesTest extends TestCase {
//    
//    @BeforeClass
//    public static void setUpClass() {
////        System.setProperty ("Db.log", "true");
//    }
    
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
        oneA = new A ("oneA", true, 1, 1, "one", "A");
        oneB = new A ("oneB", true, 1, 2, "one", "A");
        oneA1 = new A ("oneA1", false, 1, 3, "foo", "A");
        oneA2 = new A ("oneA2", false, 2, 4, "one", "B");
        utils.saveObject(oneA);
        utils.saveObject(oneB);
        utils.saveObject(oneA1);
        utils.saveObject(oneA2);
    }
    
    public void tearDown() {
        db.shutdown();
    }
    /*
    @Test public void foo() throws Exception {
        System.out.println("foo");
        
        db.run(new DbJob <ObjectContainer, Void, Void>() {
            public Void run(ObjectContainer oc, Void argument) {
//                Query q = oc.query();
//                q.constrain(A.class);
//                Constraint c = q.descend("b").descend("stringVal").constrain("foo");
//                c = c.or(q.descend("name").constrain("oneA2"));
//                
//                System.out.println("RESULT: " + q.execute());
                
                Query q = oc.query();
//        FieldQueryElement nameDesc = new FieldQueryElement ("name", String.class, "oneA", true);
//        FieldQueryElement otherDesc = new FieldQueryElement ("otherString", String.class, "A", false);
                q.descend("name").constrain("oneA").not().and(
                q.descend("otherString").constrain("B"));
//                System.out.println("RESULT: " + q.execute());
                
                return null;
            }
        }, null);
        
    }
     */ 
    
    public void sanityCheck() throws Exception {
        System.out.println("sanityCheck");
        Collection<A> c = db.getPersistenceUtils().fetchAllOfType(A.class);
        assertEquals (4, c.size());
        System.out.println("sanityCheck exit");
    }
     
    public void testSimpleNegation() throws Exception {
        System.out.println("testSimpleNegation");
        FieldQueryElement fd = new FieldQueryElement("name", String.class, "oneA", true);
        Collection<A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 3, c.size());
        assertFalse (c.contains(oneA));
        System.out.println("testSimpleNegation exit");
    }
    
    public void testBasicQueries() throws Exception {
        System.out.println("testBasicQueries");
        FieldQueryElement fd = new FieldQueryElement("name", String.class, "oneA", false);
        Collection<A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 1, c.size());
        assertEquals (oneA, c.iterator().next());
        System.out.println("testBasicQueries exit");
        
        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, fd, null, null, null);
        assertEquals (1, mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(fd);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
    }
    
    public void testComplexQuery() throws Exception {
        System.out.println("testComplexQuery");
        FieldQueryElement fd = new FieldQueryElement("b.stringVal", String.class, "one", false);
        Collection<A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 3, c.size());
        assertFalse (c.contains(oneA1));
        
        fd = new FieldQueryElement("b.stringVal", String.class, "one", true);
        c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 1, c.size());
        assertTrue (c.contains(oneA1));
        System.out.println("testComplexQuery exit");

        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, fd, null, null, null);
        assertEquals (1, mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(fd);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
    }
    
    public void testThreeLevelsDeep() throws Exception {
        System.out.println("testThreeLevelsDeep");
        FieldQueryElement fd = new FieldQueryElement("b.c.intval", Integer.TYPE, new Integer(1), false);
        Collection<A> c = utils.fetchByComplexQuery(A.class, fd);
        assertEquals ("Bad result: " + c, 3, c.size());

        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, fd, null, null, null);
        assertEquals (3, mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(fd);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
        System.out.println("testThreeLevelsDeep exit");
    }

    public void testSimpleCompoundAnd() throws Exception {
        System.out.println("testSimpleCompoundAnd");
        //Anything but oneA
        FieldQueryElement nameDesc = new FieldQueryElement("name", String.class, "oneA", false);
        FieldQueryElement otherDesc = new FieldQueryElement("otherString", String.class, "A", false);
//        QueryElement compound = CompoundQueryElement.create(nameDesc, otherDesc, Logic.AND, false);
        QueryElement compound = nameDesc.and(otherDesc);
        Collection<A> c = utils.fetchByComplexQuery(A.class, compound);
        assertEquals ("Bad result: " + c, 1, c.size());
        assertTrue (c.contains(oneA));

        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, compound, null, null, null);
        assertEquals (1, mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(compound);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
        System.out.println("testSimpleCompoundAnd exit");
    }
    
    public void testSimpleCompoundAndWithNegation() throws Exception {
        System.out.println("testSimpleCompoundAndWithNegation");
        //Anything but oneA
        FieldQueryElement nameDesc = new FieldQueryElement("name", String.class, "oneA", true);
        FieldQueryElement otherDesc = new FieldQueryElement("otherString", String.class, "A", false);
//        QueryElement compound = CompoundQueryElement.create(nameDesc, otherDesc, Logic.AND, false);
        QueryElement compound = nameDesc.and(otherDesc);
        Collection<A> c = utils.fetchByComplexQuery(A.class, compound);
        assertEquals ("Bad result: " + c, 2, c.size());
        assertTrue (c.contains(oneB));
        assertTrue (c.contains(oneA1));
        
        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, compound, null, null, null);
        assertEquals (2, mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(compound);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
        System.out.println("testSimpleCompoundAndWithNegation exit");
    }
      
    public void testSimpleCompoundOr() throws Exception {
        System.out.println("testSimpleCompoundOr");
        FieldQueryElement nameDesc = new FieldQueryElement("name", String.class, "oneA", false);
        FieldQueryElement otherDesc = new FieldQueryElement("otherString", String.class, "B", false);
        QueryElement compound = CompoundQueryElement.create(nameDesc, otherDesc, Logic.OR);
        Collection<A> c = utils.fetchByComplexQuery(A.class, compound);
        assertEquals ("Bad result: " + c, 2, c.size());
        assertTrue (c.contains(oneA));
        assertTrue (c.contains(oneA2));

        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, compound, null, null, null);
        assertEquals (c.size(), mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(compound);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
        System.out.println("testSimpleCompoundAnd exit");
        System.out.println("testSimpleCompoundOr exit");
    }
    public void testSimpleCompoundOrNegated() throws Exception {
        System.out.println("testSimpleCompoundOrNegated");
        FieldQueryElement nameDesc = new FieldQueryElement("name", String.class, "oneA", true);
        FieldQueryElement otherDesc = new FieldQueryElement("otherString", String.class, "B", true);
        QueryElement compound = CompoundQueryElement.create(nameDesc, otherDesc, Logic.AND);
        Collection<A> c = utils.fetchByComplexQuery(A.class, compound);
        assertEquals ("Bad result: " + c, 2, c.size());
        assertFalse (c.contains(oneA));
        assertFalse (c.contains(oneA2));

        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, compound, null, null, null);
        assertEquals (c.size(), mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(compound);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
        System.out.println("testSimpleCompoundAnd exit");
        System.out.println("testSimpleCompoundOrNegated exit");
    }
    /*
    public void testComplexCompoundAnd() throws Exception {
        System.out.println("testComplexCompoundAnd");
//        oneA1 = new A ("oneA1", false, 1, 3, "foo", "A");
        FieldQueryElement nameDesc = new FieldQueryElement("name", String.class, "oneA", false);
        FieldQueryElement intValDesc = new FieldQueryElement("b.c.intval", Integer.TYPE, new Integer(1), false);
        FieldQueryElement longValDesc = new FieldQueryElement("b.c.longval", Long.TYPE, new Long(3L), false);
        FieldQueryElement boolValDesc = new FieldQueryElement("b.c.boolval", Boolean.TYPE, new Boolean(false), false);
        FieldQueryElement otherStringDesc = new FieldQueryElement("otherString", String.class, "A", false);
        
        QueryElement compound = nameDesc.and(intValDesc).and(longValDesc).and(boolValDesc).and(otherStringDesc);
        System.out.println("COMPOUND IS " + compound);
        Collection<A> c = utils.fetchByComplexQuery(A.class, compound);
        assertEquals ("Bad result: " + c, 1, c.size());
        assertTrue (c.contains(oneA1));

        Collection<PersistenceFacade<A>> mdls = db.getFacadeFactory().forComplexQuery(A.class, compound, null, null, null);
        assertEquals (c.size(), mdls.size());
        ModelBuilder b = Queries.COMPLEX.builder(A.class);
        b.setObject(compound);
        PojoCollectionModel <A> mmdl = b.multi(db);
        assertFalse (mmdl.isEmpty());
        assertEquals (c.size(), mmdl.get().size());
        System.out.println("testComplexCompoundAnd exit");
    }
     */ 
    
    public void testComplexCompoundOr() throws Exception {
        System.out.println("testComplexCompoundOr");
        FieldQueryElement stringValDesc = new FieldQueryElement("b.stringVal", String.class, "foo", false);
        FieldQueryElement nameDesc = new FieldQueryElement("name", String.class, "oneA", false);
        CompoundQueryElement compound = nameDesc.or(stringValDesc);
        Collection<A> c = utils.fetchByComplexQuery(A.class, compound);
        assertEquals ("Bad result: " + c, 2, c.size());
        assertTrue (c.contains(oneA));
        assertTrue (c.contains(oneA1));
        System.out.println("testComplexCompoundOr exit");
    }
}
