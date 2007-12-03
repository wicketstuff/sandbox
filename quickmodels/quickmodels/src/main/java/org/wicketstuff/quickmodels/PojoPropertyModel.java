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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;
import org.apache.wicket.model.IPropertyReflectionAwareModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.lang.PropertyResolverConverter;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.persistence.PersistenceFacade;

/**
 * A PropertyModel which looks up a property on an object held by a 
 * PersistenceFacade.  Automatically markes the facade as modified if the
 * value is changed.
 * <p/>
 * PENDING: why is this class public?
 *
 * @author Tim Boudreau
 */
final class PojoPropertyModel<P> implements IObjectClassAwareModel, IPropertyReflectionAwareModel, IWrapModel, IDetachable {
    private transient Object value;
    private String propName;
    private final PersistenceFacade<P> facade;

    PojoPropertyModel(PersistenceFacade<P> facade, String propName) {
        this.facade = facade;
        this.propName = propName;
    }

    public Object getObject() {
        if (value == null) {
            value = load();
        }
        return value;
    }

    public void setObject(Object val) {
        System.err.println("SetObject on " + this + " with " + val);
        @SuppressWarnings(value = "unchecked")
        boolean written = write(val);
        if (written) {
            facade.modified();
        }
    }

    public void detach() {
        value = null;
        facade.detached();
    }
    
    /**
     * @see org.apache.wicket.model.IPropertyReflectionAwareModel#getPropertyField()
     */
    public Field getPropertyField() {
        String expression = propertyExpression();
        if (!Strings.isEmpty(expression)) {
            Object tgt = getTarget();
            if (tgt != null) {
                try {
                    return PropertyResolver.getPropertyField(expression, tgt);
                } catch (Exception ignore) {
                }
            }
        }
        return null;
    }

    private String propertyExpression() {
        return propName;
    }

    private Object getTarget() {
        return facade.get();
    }

    @SuppressWarnings(value = "unchecked")
    private Object load() {
        Object result = null;
        facade.attached();
        Object val = facade.get();
        if (val != null) {
            final String expression = propertyExpression();
            if (Strings.isEmpty(expression)) {
                // Return a meaningful value for an empty property expression
                return val;
            }

            return PropertyResolver.getValue(expression, val);
        }
        return result;
    }

    private boolean write(Object value) {
        boolean result = false;
        Object val = facade.get();
        if (val != null) {
            Object old = getObject();
            if (old == null && value == null) {
                return false;
            } else if (old != null && value != null) {
                if (value.equals(old)) {
                    return false;
                }
            }
            doWrite (value);
            this.value = value;
            result = true;
        }
        return result;
    }
    
    private void doWrite (Object val) {
        PropertyResolverConverter prc
           = new PropertyResolverConverter(
                Application.get().getConverterLocator(),
                Session.get().getLocale());
        //XXX this will try to set a null on primitive type fields and
        //fail - handle conversion
        PropertyResolver.setValue(propName, getTarget(), val, prc);
    }

    /**
     * @return model object class
     */
    public Class getObjectClass() {
        final String expression = propertyExpression();
        if (Strings.isEmpty(expression)) {
            // Return a meaningful value for an empty property expression
            Object tgt = getTarget();
            return tgt != null ? tgt.getClass() : null;
        }

        final Object tgt = getTarget();
        if (tgt != null) {
            return PropertyResolver.getPropertyClass(expression, tgt);
        }
        return null;
    }
    
    /**
     * @see org.apache.wicket.model.IPropertyReflectionAwareModel#getPropertySetter()
     */
    public Method getPropertySetter() {
        if (!Strings.isEmpty(propName)) {
            Object tgt = getTarget();
            if (tgt != null) {
                try {
                    return PropertyResolver.getPropertySetter(propName, tgt);
                } catch (Exception ignore) {
                }
            }
        }
        return null;
    }

    /**
     * @see org.apache.wicket.model.IPropertyReflectionAwareModel#getPropertyGetter()
     */
    public Method getPropertyGetter() {
        if (!Strings.isEmpty(propName)) {
            Object tgt = getTarget();
            if (tgt != null) {
                try {
                    return PropertyResolver.getPropertyGetter(propName, tgt);
                } catch (Exception ignore) {
                }
            }
        }
        return null;
    }
    
    void setWrappedModel (IModel mdl) {
        wrapped = mdl;
    }

    IModel wrapped;
    public IModel getWrappedModel() {
        return wrapped;
    }
    
    @Override
    public String toString() {
        return super.toString() + " for " + facade;
    }
}
