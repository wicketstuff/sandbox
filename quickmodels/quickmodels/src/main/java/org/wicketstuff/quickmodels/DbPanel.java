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

import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.persistence.Db;

/**
 * Panel base-class which uses a generified model and is database-aware.
 * 
 * @author Tim Boudreau
 */
public abstract class DbPanel<T> extends Panel {
    protected DbPanel(String id) {
        super (id);
    }
    
    protected DbPanel(String id, GenericModel<T> model) {
        super (id, model);
    }
    
    public Db getDatabase() {
        return DbApplication.getDb();
    }
    
    public GenericModel<T> mdl() {
        return (GenericModel<T>) super.getModel();
    }
}
