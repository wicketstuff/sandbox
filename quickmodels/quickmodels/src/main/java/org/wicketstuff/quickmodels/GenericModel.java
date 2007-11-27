/*
 * GenModel.java
 * 
 * Created on Aug 2, 2007, 12:10:39 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wicketstuff.quickmodels;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

/**
 * A generified version of IModel
 *
 * @author Tim Boudreau
 */
public interface GenericModel<T> extends IModel, IDetachable {
    /**
     * Equivalent of IModel.getModelObject() but typed.
     * @return
     */
    T get();
}
