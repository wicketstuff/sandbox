/**
 * 
 */
package org.wicketstuff.jamon.web;

import org.apache.wicket.model.LoadableDetachableModel;
/**
 * Model that will discard the model as soon it is rendered.
 * 
 * @author lars
 *
 */
@SuppressWarnings("serial")
final class ThrowAwayModel extends LoadableDetachableModel {
    ThrowAwayModel(Object object) {
        super(object);
    }

    @Override
    protected Object load() {
        return null;
    }
}