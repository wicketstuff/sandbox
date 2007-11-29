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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import org.wicketstuff.persistence.ActivationStrategy;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.DbJob;
import org.wicketstuff.persistence.InvalidUuidException;
import org.wicketstuff.persistence.LoadFailurePolicy;
import org.wicketstuff.persistence.NewObjectFactory;
import org.wicketstuff.persistence.queries.FieldQueryElement;
import org.wicketstuff.persistence.queries.QueryElement;
import org.wicketstuff.persistence.spi.DbImplementation;
import org.wicketstuff.persistence.spi.PersistenceUtils;
/**
 *
 * @author Tim Boudreau
 */
public class QueriesTest extends TestCase {
    
    public QueriesTest(String id) {
        super (id);
    }

    
    public void testGetFields() {
        System.out.println("testGetFields");
        for (Queries q : Queries.values()) {
            Set <QueryFields> r = q.getRequiredFields();
            Set <QueryFields> i = q.getIllegalFields();
            Set <QueryFields> o = q.getOptionalFields();
            
            Set <QueryFields> test = new HashSet<QueryFields>(r);
            test.removeAll(i);
            assertEquals ("Required fields overlap with illegal fields " + test + "\n"+ q,
                    r.size(), test.size());
            test = new HashSet<QueryFields>(r);
            test.removeAll(o);
            assertEquals ("Required fields overlap with optional fields: " + test + "\n" + q,
                    r.size(), test.size());
            
            test = new HashSet<QueryFields>(i);
            test.removeAll(o);
            assertEquals ("Illegal fields overlap with optional fields " + test + "\n"+ q,
                    i.size(), test.size());
        }
    }

/*    
    public void testCreateBuilder() {
        System.out.println("testCreateBuilder");
        List<String> failures = new ArrayList<String>();
        for (Queries q : Queries.values()) {
            if (q == Queries.OF_TYPE || q == Queries.CUSTOM_QUERY) {
                //only thing that can be set already is
                continue;
            }
            ModelBuilder<String,String> b = q.<String,String>builder(String.class);
            assertNotNull (b);
            for (QueryFields f : q.getIllegalFields()) {
                Exception e = null;
                try {
                    setAField(f, b);
                } catch (Exception ee) {
                    e = ee;
                }
                assertNotNull ("No exception thrown for setting illegal field " + f + " on " + q.name(),
                        e);
            }
            b = q.<String,String>builder(String.class);
            for (QueryFields f : q.getOptionalFields()) {
                setAField(f, b);
            }
            
            b = q.<String,String>builder(String.class);
            for (QueryFields f : q.getRequiredFields()) {
                setAField(f, b);
            }
            
            Db<String> db = new Db<String> (new FakeDb());
            //Now make sure an exception is thrown when any required field is
            //missing
            b = q.<String,String>builder(String.class);
            for (QueryFields f : q.getRequiredFields()) {
                if (f == QueryFields.TYPE) {
                    continue;
                }
                for (QueryFields ff : q.getRequiredFields()) {
                    if (ff != f) {
                        setAField(ff, b);
                    }
                    if (ff == QueryFields.TYPE) {
                        continue;
                    }
                }
                Exception e = null;
                try {
                    b.<String>single(db);
                } catch (Exception ee) {
                    e = ee;
                }
                //PENDING:  Fixme
//                assertNotNull("Exception not thrown when invoking single() on a builder of type " + q.name() + 
//                        " with the " + f + " field not set",e);
                if (e == null) {
                    failures.add ("Exception not thrown when invoking single() on a builder of type " + q.name() + 
                        " with the " + f + " field not set\n");
                }
                try {
                    b.<String>multi(db);
                } catch (Exception ee) {
                    e = ee;
                }
                //PENDING:  Fixme
//                assertNotNull("Exception not thrown when invoking multi() on a builder of type " + q.name() + 
//                        " with the " + f + " field not set",e);
                failures.add("Exception not thrown when invoking multi() on a builder of type " + q.name() + 
                        " with the " + f + " field not set\n");
                
            }
        }
        if (!failures.isEmpty()) {
            fail (failures.toString());
        }
    }
    
    public void testSingleBuilders() {
        System.out.println("testSingleBuilders");
        PojoModel single;
        for (Queries q : Queries.values()) {
            if (q == Queries.CUSTOM_QUERY) continue;
            ModelBuilder<String,String> b = q.<String,String>builder(String.class);
            for (QueryFields f : q.getOptionalFields()) {
                setAField(f, b);
            }
            
            for (QueryFields f : q.getRequiredFields()) {
                setAField(f, b);
            }
            Db<String> db = new Db<String> (new FakeDb());
            //PENDING: Fixme
            single = b.<String>single(db);
        }
    }
    */
 
    
    
    public void testMultiBuilders() {
        System.out.println("testMultiBuilders");
        PojoCollectionModel<String> multi;
        for (Queries q : Queries.values()) {
            ModelBuilder<String,String> b = q.<String,String>builder(String.class);
            for (QueryFields f : q.getOptionalFields()) {
                setAField(f, b);
            }
            
            for (QueryFields f : q.getRequiredFields()) {
                setAField(f, b);
            }
            Db<String> db = new Db<String> (new FakeDb());
            multi = b.<String>multi(db);
        }
    }
    
    
    private void setAField (QueryFields f, ModelBuilder<String,String> b) {
//        System.err.println("set a field " + f + " on " + b);
        switch (f) {
        case CUSTOM_QUERY:
            DbJob<String, Collection<String>, String> db = new DbJob<String, Collection<String>, String>() {
                public Collection<String> run(String container, String argtype) {
                    return Arrays.asList("this", "is", "a", "collection", " for " + container + " with " + argtype);
                }
            };
            b.setCustomQuery(db);
            break;
        case CUSTOM_QUERY_ARG :
            String in = "cq arg";
            b.setCustomQueryArg(in);
            break;
        case FACTORY :
            NewObjectFactory<String> x = 
                    new NewObjectFactory<String>() {
                int ct = 0;
                public String create() {
                    return "Created by factory, call " + (ct++);
                }
            };
            b.setFactory(x);
            break;
        case FIELD_NAME :
            b.setFieldName("A field name");
            break;
        case FIELD_TYPE :
            b.setFieldType(String.class);
            break;
        case FIELD_VALUE :
            b.setFieldValue("A field value");
            break;
        case NEW_OBJECT_COUNT :
            b.setNewObjectCount(23);
            break;
        case OBJECT :
            if (b.kind() == Queries.COMPLEX) {
                b.setObject(new FieldQueryElement("foo", String.class, "bar", false));
            } else {
                b.setObject("An object");
            }
            break;
        case POLICY :
            b.setPolicy(LoadFailurePolicy.CREATE_NEW_OBJECT_ON_FAILURE);
            break;
        case TYPE :
            b.setType(String.class);
            break;
        case UID :
            b.setUid(42);
            break;
        case UUID :
            b.setUuid("A uuid");
            break;
        }
    }
    
    private static final class FakeDb implements DbImplementation<String>, PersistenceUtils {

        public <T, P> T run(DbJob<String, T, P> access, P param) {
            return access.run("container", param);
        }

        public PersistenceUtils createPersistenceUtils(Db<String> db) {
            return this;
        }

        public <T> List<T> fetchAllOfType(Class<T> clazz) {
            return (List<T>) Collections.singletonList("fetchAllObjectsOfType " +
                    "" + clazz);
        }

        public <T> List<T> fetchByPrototype(T prototype) {
            return (List<T>) Collections.singletonList("fetchByPrototype " + prototype);
        }

        public <T> T fetchByUid(long uid, Class<T> type, ActivationStrategy activation) {
            return (T)("fetchByUid " + uid);
        }

        public <T> T fetchByUuid(String uuid, Class<T> type, ActivationStrategy activation) {
            return (T) ("fetchByUuid " + uuid);
        }

        public long getUid(Object o) {
            return o.hashCode();
        }

        public long getVersion(Object o) {
            return o.hashCode();
        }

        public <T> boolean saveObject(T object) {
            return true;
        }

        public <T> long saveObjectReturnUid(T object) {
            return object.hashCode();
        }

        public String stringToUUid(String s) throws InvalidUuidException {
            return s;
        }

        public String uuidToString(String uuid) throws InvalidUuidException {
            return uuid;
        }

        public String getUUID(Object o) {
            return o.toString();
        }

        public <T> T fetchOneOfType(Class<T> type) {
            return (T) ("Any of type " + type);
        }

        public boolean delete(Object obj) {
            //do nothing;
            return true;
        }

        public <T> boolean deleteAllOfType(Class<T> clazz) {
            return true;
        }

        public <T, P> T fetchByFieldQuery(Class<T> type, Class<P> field, String fieldName, P fieldValue) {
            return (T) ("Find by query " + type + field + fieldName + fieldValue);
        }

        public <T, P> List<T> fetchAllObjectsOfType(Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, Comparator<T> compare) {
            return (List<T>) Collections.singletonList("FindAllByQuery " + type+ fieldType + fieldName + fieldValue);
        }

        public String getUuid(Object o) {
            return o.toString();
        }

        public <T, P> List<T> fetchAllByQuery(Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, Comparator<T> compare) {
            return (List<T>) Collections.<String>singletonList("Hello");
        }

        public void shutdown() {
            //do nothing
        }

        public <T> Collection<T> fetchByComplexQuery(Class<T> expectedType, QueryElement<T> query) {
            return null;
        }
    }
}
