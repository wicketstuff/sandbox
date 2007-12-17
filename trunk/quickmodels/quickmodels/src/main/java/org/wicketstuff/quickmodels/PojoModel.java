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

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.model.IComponentInheritedModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.persistence.PersistenceFacade;
import org.wicketstuff.persistence.LookupFailedException;
import org.wicketstuff.persistence.DbProvider;

import java.io.Serializable;

/**
 * A model which represents a single object in a database.  Typically
 * created by an instance of ModelBuilder.  A PojoModel is a wrapper for
 * a PersistenceFacade that manages the lifecycle of an object stored in
 * a database, such that when the model is automatically detached, the only
 * thing stored in the session is a unique id to retrieve the object later - 
 * thus making applications scale better.
 * <p/>
 * @see org.netbeans.lib.persistence.wicketmodels.Queries
 * @see org.netbeans.lib.persistence.wicketmodels.ModelBuilder
 * 
 * @author Tim Boudreau
 */
public final class PojoModel<T> extends LoadableDetachableModel implements GenericModel<PersistenceFacade<T>>, Serializable, IComponentInheritedModel {
    private final PersistenceFacade<T> facade;
    /**
     * Create a model over the object wrapped by the passed persistence facade
     * @param facade A persistence facade
     */
    public PojoModel(PersistenceFacade<T> facade) {
        super (facade);
        this.facade = facade;
    }

    /**
     * Get a PojoModel for an object with a UUID stored in the passed 
     * PageParameters under the key <code>key</code>
     * @param key A key to look for a UUID in the PageParameters
     * @param pp The page parameters
     * @param clazz The type of object expected
     * @param dp An object which is the database provider
     * @return A model or null
     */
    public static <T> PojoModel <T> findFromParams(String key, PageParameters pp, Class<T> clazz, DbProvider dp) {
        String uuid = pp.getString( key );
        if (uuid != null) {
            ModelBuilder qb = Queries.UUID.builder( clazz );
            qb.setUuid( uuid );
            PojoModel <T> result = null;
            try {
                result = qb.single( dp.getDatabase() );
                result.get().get(); //will throw an exception if doesn't exist
            } catch (LookupFailedException e) {
                //do nothing - bad url or someone trying to hack in
            }
        }
        return null;
    }
    
    @Override
    public void detach() {
        super.detach();
        facade.detach();
    }
    
    protected Object load() {
        return facade.get();
    }
    
    /**
     * Components should call this method if they make a change in the object
     * which should be written back to the database.
     */
    public void modified() {
        facade.modified();
    }
    
    void dirty() {
        facade.modified();
    }
    
    void attached() {
        facade.attached();
    }
    
    void detached() {
        facade.detached();
    }
    
    /**
     * Delete the object in the database, if it is persisted, immediately
     */
    public void delete() {
        get().delete();
    }
    
    /**
     * Save the object in the database.
     */
    public void save() {
        get().save();
    }
    
    /**
     * Get the object represented by this model, as the type T
     * @return The object represented
     */
    public T getPojo() {
        return get().get();
    }
    
    /**
     * Determine if the persisted object has been deleted
     * @return Whether or not it has been deleted
     */
    public boolean isDeleted() {
        return get().isDeleted();
    }
    
    public IWrapModel wrapOnInheritance(Component component) {
        String expression = component.getId();
        PojoPropertyModel <T> mdl =
            new PojoPropertyModel<T>(
                facade, 
                expression);
        mdl.setWrappedModel(this);
        return mdl;
    }

    /**
     * Get the persistence facade that is managing object lifecycle and 
     * persistence for this model
     * @return The facade
     */
    public PersistenceFacade<T> get() {
        return facade;
    }
    
    @Override
    public String toString() {
        return super.toString() + " for " + facade;
    }
}
