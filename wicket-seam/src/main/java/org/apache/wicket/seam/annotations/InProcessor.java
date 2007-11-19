/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wicket.seam.annotations;

import org.apache.wicket.seam.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.seam.contexts.SeamContexts;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.util.string.Strings;
import org.jboss.seam.ScopeType;
import org.jboss.seam.Seam;
import org.jboss.seam.annotations.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Inject Seam references into SeamWebPage subclasses searching for
 * &amp;#064;In annotations in fields and methods.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public class InProcessor extends AbstractAnnotationProcessor<Component> {

    private static final Logger logger = 
            LoggerFactory.getLogger(InProcessor.class);
    
    @Override
    public void process(Component subject) {
        processFieldsAndMethods(subject, In.class);
    }

    @Override
    protected void onFieldMatch(Component subject, Field field, List<Annotation> annotations) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object proxy = findValue(field.getType(), field.getName(), (In)annotations.get(0));
            field.set(subject, proxy);
            logger.debug("Inject field: " + subject.getClass().getName() + "." + field.getName() + " = " + proxy);
        } 
        catch (IllegalAccessException e) {
            throw new WicketRuntimeException("No access to field " 
                    + field.getName() + " in " + subject 
                    + " when trying to setup Seam injection", e);
        }
    }

    @Override
    protected void onMethodMatch(Component subject, Method method, List<Annotation> annotations) {
        if (method.getReturnType() == Void.class && method.getParameterTypes().length == 1) {
            Class<?> type = method.getParameterTypes()[0];
            try {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                Object proxy = findValue(type, SeamNamingRules.getNameFromMethod(method), (In)annotations.get(0));
                method.invoke(subject, proxy);
                logger.debug("Inject method: " + subject.getClass().getName() + "." + method.getName() + "(" + proxy + ")");
            } 
            catch (IllegalAccessException e) {
                throw new WicketRuntimeException("No access to method " 
                        + method.getName() + " in " + subject 
                        + " when trying to setup Seam injection", e);
            }
            catch (InvocationTargetException e) {
                throw new WicketRuntimeException(e);
            }
        }
    }
    
    private Object findValue(Class<?> type, String defaultName, In in) {

        String    name       = Strings.isEmpty(in.value()) ? defaultName : in.value();
        ScopeType scope      = in.scope() == null ? ScopeType.UNSPECIFIED : in.scope();
        boolean   create     = in.create();
        boolean   required   = in.required();
        String    targetType = type.getName();

        Object component = null;
        if (name.contains("#")) {
            component = SeamContexts.eval(name, Object.class);
        }
        else {
            if (scope == ScopeType.UNSPECIFIED) {
                // 1. Search as var
                component = SeamContexts.get(name);
                // 2. Search as component by name
                if (component == null) {
                    component = 
                        org.jboss.seam.Component.getInstance(name, create);
                }
                // 3. Search as component by type
                if (component == null) {
                    Class<?> target = Classes.resolveClass(targetType);
                    String impliedName = Seam.getComponentName(target);
                    if (impliedName != null) {
                        component = 
                            org.jboss.seam.Component.getInstance(target, create);
                    }
                }
            }
            else {
                // 1. Search as var
                component = SeamContexts.get(name, scope);
                // 2. Search as component by name
                if (component == null) {
                    component = 
                        org.jboss.seam.Component.getInstance(name, scope, create);
                }
                // 3. Search as component by type
                if (component == null) {
                    Class<?> target = Classes.resolveClass(targetType);
                    String impliedName = Seam.getComponentName(target);
                    if (impliedName != null) {
                        component = 
                            org.jboss.seam.Component.getInstance(impliedName, scope, create);
                    }
                }
            }
        }

        if (required && component == null) {
            throw new 
                WicketSeamException(
                    "required @In dependency " + in + " not found.");
        }

        return component;
    }
    
}
