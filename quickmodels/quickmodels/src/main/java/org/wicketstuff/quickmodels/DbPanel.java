/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
