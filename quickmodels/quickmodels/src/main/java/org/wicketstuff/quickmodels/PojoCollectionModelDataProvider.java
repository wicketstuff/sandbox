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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

/**
 * Takes a PojoCollectionModel and provides individual models from it.
 * @author Tim Boudreau
 */
class PojoCollectionModelDataProvider<T> implements IDataProvider {
    private PojoCollectionModel<T> mdl;
    public PojoCollectionModelDataProvider(PojoCollectionModel<T> mdl) {
        this.mdl = mdl;
        if (mdl == null) {
            throw new NullPointerException();
        }
    }
    
    public void setModel(PojoCollectionModel<T> m) {
        if (mdl != null) {
            mdl.detach();
        }
        detach();
        this.mdl = m;
        if (m == null) {
            throw new NullPointerException();
        }
    }

    public Iterator iterator(int first, int count) {
        List <T> result = list();
        if (count != result.size()) {
            result = result.subList(first, count);
        }
        return result.iterator();
    }

    private List <T> list;
    Map <T, PojoModel<T>> objs2mdls = new HashMap<T, PojoModel<T>>();
    private List <T> list() {
        if (list == null) {
            List <PojoModel<T>> sbs = mdl.get();
            list = new ArrayList<T>();
            for (PojoModel<T> m : sbs) {
                T t = m.get().get();
                list.add(t);
                objs2mdls.put(t, m);
            }
        }
        return list;
    }

    public int size() {
        return list().size();
    }

    public IModel model(Object object) {
        if (objs2mdls.isEmpty()) list();
        return objs2mdls.get(object);
    }

    public void detach() {
        list = null;
        objs2mdls.clear();
    }
}
