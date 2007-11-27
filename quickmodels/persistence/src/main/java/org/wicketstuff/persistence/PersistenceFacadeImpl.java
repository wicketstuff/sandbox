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
package org.wicketstuff.persistence;
import org.wicketstuff.persistence.FetchStrategy.ObjectReceiver;
/**
 *
 * @author Tim Boudreau
 */
final class PersistenceFacadeImpl<T> implements PersistenceFacade<T> {
    private final Class<T> type;
    private transient T value;
    private FetchStrategy<T> strategy;
    private boolean saveOnDetach = false;
    private int attachCount = 0;
    private boolean dirty;
    
    PersistenceFacadeImpl (Class<T> type, FetchStrategy<T> strategy) {
        this.type = type;
        this.strategy = strategy;
    }
    
    @Override
    public String toString() {
        return super.toString() + " value " + value + " strategy " + 
                strategy + " saveOnDetach " + saveOnDetach + " modified "
                + dirty + " attachCount " + attachCount;
    }

    public Class<T> getType() {
        return type;
    }

    public T get() {
        if (value == null) {
            value = load();
        }
        return value;
    }
    
    public boolean isAttached() {
        return value != null;
    }

    boolean detachOnLastModelDetach = false;
    public void detach() {
        if (attachCount == 0) {
            if (isModified()) {
                new IllegalStateException ("Discarding modified object " + value ).printStackTrace();
            }
            doDetach();
        } else {
            detachOnLastModelDetach = true;
        }
    }

    public void save() {
        if (dirty) {
            strategy = strategy.save(get());
            dirty = false;
        }
    }

    public boolean isModified() {
        return dirty;
    }

    public boolean isPersisted() {
        return strategy.isPersisted(value);
    }
    
    private T load() {
        ObjectReceiver<T> receiver = new ObjectReceiver<T>();
        strategy = strategy.load(receiver);
        detachOnLastModelDetach = false;
        T result = receiver.get();
        return result;
    }

    private void doDetach() {
        if (saveOnDetach) {
            save();
        }
        if (isAttached()) {
            value = null;
        }
    }
    
    public void attached () {
        attachCount++;
    }
    
    public void detached () {
        attachCount = Math.max (0, attachCount-1);
        if (attachCount == 0 && detachOnLastModelDetach) {
            doDetach();
        }
    }
    
    public void setSaveOnDetach(boolean val) {
        saveOnDetach = val;
    }
    
    public void modified() {
        dirty = true;
        if (value != null) {
            //We want the value serialized into the session if
            //it is changed, so it survives a detach even if
            //not written to the database
            strategy = strategy.dirty(value);
        }
    }

    public String getUuid() {
        T t = get();
        return t == null ? null : strategy.getUUID(t);
    }

    public long getUid() {
        T t = get();
        return t == null ? -1 : strategy.getUid(t);
    }
    
    public void delete() {
        T t = get();
        if (t != null) {
            strategy = strategy.delete(t);
        }
    }
    
    public boolean isDeleted() {
        return strategy.isDeleted();
    }
}
