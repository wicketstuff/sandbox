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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.wicketstuff.quickmodels.FakeDatabase.Obj;
import org.wicketstuff.quickmodels.FakeDatabase.Uuid;
import org.wicketstuff.persistence.ActivationStrategy;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.DbJob;
import org.wicketstuff.persistence.InvalidUuidException;
import org.wicketstuff.persistence.queries.QueryElement;
import org.wicketstuff.persistence.spi.DbImplementation;
import org.wicketstuff.persistence.spi.PersistenceUtils;

/**
 *
 * @author Tim Boudreau
 */
class FakeDatabase implements DbImplementation<Dbc>, PersistenceUtils {
    static FakeDatabase instance;
    static Db<Dbc> create() {
        return new Db<Dbc> (instance = new FakeDatabase());
    }
    
    Dbc dbc = new Dbc();
    public static final class Obj {
        public String name;
        int ver = 0;
        public Obj (String name) {
            assert name != null;
            this.name = name;
        }
        
        public Obj() {
            this("created by reflection");
        }
        
        String val = "val";
        public void setVal(String val) {
            this.val = val;
            modified = true;
        }
        
        public String getVal() {
            return val;
        }
        
        boolean modified = true;
        
        @Override
        public String toString() {
            return "(" + name + ")";
        }
    }
    
    static final class Uuid {
        public final String uuid;
        public Uuid (String uuid) {
            this.uuid = uuid;
        }
        @Override
        public String toString() {
            return uuid;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof Uuid) {
                return ((Uuid) o).uuid.equals(uuid);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return uuid.hashCode();
        }
    }
    

    public <T, P> T run(DbJob<Dbc, T, P> access, P param) {
        return access.run (dbc, param);
    }

    public PersistenceUtils createPersistenceUtils(Db<Dbc> db) {
        return this;
    }

    public <T> List<T> fetchByPrototype(T prototype) {
        List<T> result = new ArrayList<T>();
        List x = dbc.classToObjs.get(prototype.getClass());
        System.err.println("fetch by prototype " + prototype + " avail objs " + x);
        for (Object o : x) {
            System.err.println("  check " + o + " and " + prototype);
            if (prototype.toString().equals(o.toString())) {
                System.err.println("  match, add to result");
                result.add((T)o);
            }
        }
        return result;
    }

    public Uuid stringToUUid(String s) throws InvalidUuidException {
        if ("bad".equals(s)) throw new InvalidUuidException("bad");
        return new Uuid(s);
    }

    public String uuidToString(Uuid uuid) throws InvalidUuidException {
        if ("bad".equals(uuid.toString())) throw new InvalidUuidException("bad");
        return uuid.toString();
    }

    public String getUUID(Object o) {
        return dbc.getUuid((Obj)o).toString();
    }

    public boolean delete(Object obj) {
        boolean result = dbc.getUid((Obj)obj) != -1L;
        dbc.remove((Obj)obj);
        return result;
    }

    public <T> boolean deleteAllOfType(Class<T> clazz) {
        boolean result = dbc.classToObjs.get(clazz) != null;
        dbc.classToObjs.remove(clazz);
        return result;
    }


    public <T> List<T> fetchAllOfType(Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        List x = dbc.classToObjs.get(clazz);
        result.addAll(x);
        return result;
    }

    public <T> T fetchByUid(long uid, Class<T> type, ActivationStrategy activation) {
        return (T) dbc.getByUid(uid);
    }

    public <T> T fetchByUuid(String uuid, Class<T> type, ActivationStrategy activation) {
        return (T) dbc.getByUuid(new Uuid(uuid));
    }

    public long getUid(Object o) {
        return dbc.getUid((Obj)o);
    }

    public long getVersion(Object o) {
        return ((Obj)o).ver;
    }

    public String getUuid(Object o) {
        return dbc.getUuid((Obj)o).toString();
    }

    public <T> boolean saveObject(T object) {
        Obj obj = (Obj) object;
        if (obj.modified) {
            obj.modified = false;
            dbc.add(obj, Obj.class);
            return true;
        }
        return false;
    }

    public <T> long saveObjectReturnUid(T object) {
        saveObject(object);
        return dbc.getUid((Obj)object);
    }

    public <T> T fetchOneOfType(Class<T> type) {
        List<T> x = (List<T>) dbc.classToObjs.get(type);
        return x.isEmpty() ? null : x.iterator().next();
    }

    public <T, P> T fetchByFieldQuery(Class<T> type, Class<P> field, String fieldName, P fieldValue) {
        List<T> l = (List<T>) dbc.classToObjs.get(type);
        if (l != null) {
            for (T x : l) {
                Obj o = (Obj) x;
                if (fieldValue.equals(o.val)) {
                    return x;
                }
            }
        }
        return null;
    }

    public <T, P> List<T> fetchAllByQuery(Class<T> type, String fieldName, Class<P> fieldType, P fieldValue, Comparator<T> compare) {
        List<T> l = (List<T>) dbc.classToObjs.get(type);
        List<T> result = new ArrayList<T>();
        if (l != null) {
            for (T x : l) {
                Obj o = (Obj) x;
                if (fieldValue.equals(o.val)) {
                    result.add (x);
                }
            }
        }
        return result;
    }

    public void shutdown() {
        //do nothing
    }

    public <T> Collection<T> fetchByComplexQuery(Class<T> expectedType, QueryElement<T> query) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
