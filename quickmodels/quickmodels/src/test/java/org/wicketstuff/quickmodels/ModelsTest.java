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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.wicketstuff.quickmodels.FakeDatabase.*;
import org.wicketstuff.persistence.ActivationStrategy;
import org.wicketstuff.persistence.Db;


/**
 *
 * @author Tim Boudreau
 */
public class ModelsTest extends TestCase {
    public ModelsTest (String id) {
        super (id);
    }
    
    
    public void testSanityCheck() throws Exception {
        Db<Dbc> db = FakeDatabase.create();
        FakeDatabase i = FakeDatabase.instance;
        Obj obj = new Obj("one");
        assertEquals(-1L, i.getUid(obj));
        i.dbc.add(obj, Obj.class);
        assertFalse(-1L == i.getUid(obj));
        long l = i.getUid(obj);
        assertSame(obj, i.dbc.getByUid(l));
        obj.setVal("hello");
        long l2  = i.saveObjectReturnUid(obj);
        assertEquals(l, l2);
        Obj proto = new Obj("one");
        List<Obj> pf = i.fetchByPrototype(proto);
        assertEquals(1, pf.size());
        assertTrue(pf.contains(obj));
        
        i.saveObjectReturnUid(proto);

        Obj proto2 = new Obj("one");
        pf = i.fetchByPrototype(proto2);
        assertEquals(2, pf.size());
        assertTrue(pf.contains(obj));
        assertTrue(pf.contains(proto));
        
        i.saveObject(proto2);
        
        Obj[] objs = new Obj[] { obj, proto, proto2 };
        for (Obj o : objs) {
            String s = i.getUUID(o);
            assertNotNull(s);
            Obj retrieved2 = i.fetchByUuid(s, Obj.class, ActivationStrategy.createDefault());
            assertNotNull (retrieved2);
            assertSame (o, retrieved2);
        }
    }
    
    
    public void testPojoCollectionModel() {
        System.out.println("testPojoCollectionModel");
        PojoModel single;
        Db<Dbc> db = FakeDatabase.create();
        FakeDatabase i = FakeDatabase.instance;
        List<Obj> orig = prep (i);
        Dbc d = i.dbc;
        ModelBuilder<Obj,String> b = Queries.OF_TYPE.<Obj,String>builder(Obj.class);
        PojoCollectionModel<Obj> m = b.<Dbc>multi(db);
        assertEquals(5, m.get().size());
        List <Obj> l = new ArrayList<Obj>();
        for (Obj o : m) {
            l.add(o);
        }
        assertTrue(l.containsAll(orig));
        assertTrue(orig.containsAll(l));
        m.detach();
        //Now all of the objects should be uids.  Get them again and test;
        l = new ArrayList<Obj>();
        for (Obj o : m) {
            l.add(o);
        }
        assertTrue(l.containsAll(orig));
        assertTrue(orig.containsAll(l));
        
        ModelBuilder<Obj,String> b2 = Queries.EXISTING_OBJECTS.<Obj,String>builder(Obj.class);
        b2.setObjects(orig);
        PojoCollectionModel<Obj> m2 = b2.<Dbc>multi(db);
        l = new ArrayList<Obj>();
        for (Obj o : m2) {
            l.add(o);
        }
        assertTrue(l.containsAll(orig));
        assertTrue(orig.containsAll(l));
        
        ModelBuilder<Obj,String> b3 = Queries.PROTOTYPE.<Obj,String>builder(Obj.class);
        Obj proto = new Obj("one");
        proto.setVal("I am one");
        b3.setObject(proto);
        PojoCollectionModel<Obj> m3 = b3.<Dbc>multi(db);
        assertEquals(1, m3.get().size());
        assertSame(orig.get(0), m3.iterator().next());
        
        ModelBuilder<Obj,String> b4 = Queries.QUERY.<Obj,String>builder(Obj.class);
        b4.setFieldName("val");
        b4.setFieldType(String.class);
        b4.setFieldValue("I am one");
        PojoCollectionModel<Obj> m4 = b4.<Dbc>multi(db);
        assertEquals(2, m4.get().size());

        List<Long> uids = new ArrayList<Long>();
        for (Obj o : orig) {
            uids.add(i.getUid(o));
        }
        
        ModelBuilder<Obj,String> b5 = Queries.UID.<Obj,String>builder(Obj.class);
        b5.setUids(uids);
        PojoCollectionModel<Obj> m5 = b5.<Dbc>multi(db);
        assertEquals(5, m5.get().size());
        l = new ArrayList<Obj>();
        for (Obj o : m5) {
            l.add(o);
        }
        assertTrue(l.containsAll(orig));
        assertTrue(orig.containsAll(l));
        
        ModelBuilder<Obj,String> b6 = Queries.UID.<Obj,String>builder(Obj.class);
        long theUid = uids.get(0);
        System.err.println("Will search for " + theUid);
        b6.setUid(theUid);
        PojoCollectionModel<Obj> m6 = b6.<Dbc>multi(db);
        assertEquals(1, m6.get().size());
        l = new ArrayList<Obj>();
        for (Obj o : m6) {
            l.add(o);
        }
        assertEquals(1, l.size());
        assertSame(orig.get(0), l.get(0));
        
        List<String> uuids = new ArrayList<String>();
        for (Obj o : orig) {
            uuids.add(i.getUUID(o));
        }
        
        ModelBuilder<Obj,String> b7 = Queries.UUID.<Obj,String>builder(Obj.class);
        b7.setUUids(uuids);
        PojoCollectionModel<Obj> m7 = b7.<Dbc>multi(db);
        assertEquals(5, m7.get().size());
        l = new ArrayList<Obj>();
        for (Obj o : m7) {
            l.add(o);
        }
        assertTrue(l.containsAll(orig));
        assertTrue(orig.containsAll(l));
        
        ModelBuilder<Obj,String> b8 = Queries.UUID.<Obj,String>builder(Obj.class);
        b8.setUuid(uuids.get(0));
        PojoCollectionModel<Obj> m8 = b8.<Dbc>multi(db);
        assertEquals(1, m8.get().size());
        assertSame(orig.get(0), m8.iterator().next());
        
        ModelBuilder<Obj,String> b9 = Queries.NEW_OBJECT.<Obj,String>builder(Obj.class);
        PojoCollectionModel<Obj> m9 = b9.<Dbc>multi(db);
        Obj nue = m9.iterator().next();
        assertEquals(-1L, i.getUid(nue));
        m9.get().iterator().next().dirty();
        m9.get().iterator().next().get().save();
        
        ModelBuilder<Obj,String> b10 = Queries.OF_TYPE.<Obj,String>builder(Obj.class);
        PojoCollectionModel<Obj> m10= b10.<Dbc>multi(db);
        assertEquals(6, m10.get().size());
        l = new ArrayList<Obj>();
        for (Obj o : m10) {
            l.add(o);
        }
        assertTrue(l.contains(nue));
    }   
    
    
    public void testSingleModel() {
        System.out.println("testSingleModel");
        PojoModel single;
        Db<Dbc> db = FakeDatabase.create();
        FakeDatabase i = FakeDatabase.instance;
        List<Obj> orig = prep (i);
        Dbc d = i.dbc;
        ModelBuilder<Obj,String> b = Queries.OF_TYPE.<Obj,String>builder(Obj.class);
        
    }    
    
    public void testDeletion() {
        System.out.println("testDeletion");
        Db<Dbc> db = FakeDatabase.create();
        FakeDatabase i = FakeDatabase.instance;
        List<Obj> orig = prep (i);
        ModelBuilder<Obj,String> b = Queries.OF_TYPE.<Obj,String>builder(Obj.class);
        PojoModel<Obj> single = b.single(db);
        assertFalse (single.isDeleted());
        single.delete();
        assertTrue(single.isDeleted());
        assertEquals(-1, single.get().getUid());
        assertNull (single.get().getUuid());
        single.save();
//        assertFalse(single.isDeleted());
//        assertNotNull (single.get().getUuid());
        
        PojoCollectionModel<Obj> m = b.multi(db);
        int val = m.get().size();
        m.get().get(2).delete();
        m.refresh();
        assertEquals(val -1, m.get().size());
    }
    
    private List<Obj> prep(FakeDatabase i) {
        Dbc d = i.dbc;
        
        Obj one = new Obj ("one");
        one.setVal("I am one");
        Obj two = new Obj ("two");
        two.setVal("I am two");
        Obj three = new Obj ("three");
        three.setVal("I am three");
        Obj four = new Obj ("four");
        four.setVal("I am four");
        Obj oneOne = new Obj ("I am also one");
        oneOne.setVal("I am one");
        d.add(one, null);
        d.add(two, null);
        d.add(three, null);
        d.add(four, null);
        d.add(oneOne, null);
        return new ArrayList<Obj>(Arrays.<Obj>asList(one, two, three,four,oneOne));
    }
}
