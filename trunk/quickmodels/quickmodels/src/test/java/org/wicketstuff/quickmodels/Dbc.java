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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.wicketstuff.quickmodels.FakeDatabase.*;

/**
 *
 * @author tim
 */
class Dbc {

    final Map<Class, List<Obj>> classToObjs = new HashMap<Class, List<Obj>>();
    long nextUid = 2000;
    final Map<Obj, Long> uids = new HashMap<Obj, Long>();
    final Map<Obj, Uuid> uuids = new HashMap<Obj, Uuid>();
    final Map<Uuid, Obj> iuuids = new HashMap<Uuid, Obj>();
    final Map<Long, Obj> iuids = new HashMap<Long, Obj>();

    public void add(Obj o, Class c) {
        assert o != null;
        if (c == null) {
            c = o.getClass();
        }
        System.out.println("try to add " + o + " of type " + c);
        List<Obj> cc = classToObjs.get(c);
        if (cc == null) {
            cc = new ArrayList<Obj>();
            classToObjs.put(c, cc);
        } else if (cc.contains(o)) {
            return;
        }
        cc.add(o);
        long uid = nextUid++;
        uids.put(o, uid);
        iuids.put(uid, o);
        Uuid uuid = new Uuid("uuid for " + o.name + " uid " + uid);
        uuids.put(o, uuid);
        iuuids.put(uuid, o);
    }

    public void remove(Obj obj) {
        assert obj != null;
        Long uid = uids.get(obj);
        if (uid != null) {
            Uuid uuid = uuids.get(obj);
            uuids.remove(obj);
            iuuids.remove(uuid);
            uids.remove(obj);
            iuids.remove(uid);
            Class c = obj.getClass();
            List<Obj> objs = classToObjs.get(c);
            if (objs != null) {
                objs.remove(obj);
            }
        }
    }
    
    public Obj getByUid(long uid) {
        return iuids.get(uid);
    }
    
    public Obj getByUuid(Uuid u) {
        assert u != null;
        return iuuids.get(u);
    }
    
    public Uuid getUuid(Obj obj) {
        assert uuids != null;
        assert obj != null;
        return uuids.get(obj);
    }
    
    public long getUid(Obj obj) {
        Long result = uids.get(obj);
        return result == null ? -1L : result.longValue();
    }
}
