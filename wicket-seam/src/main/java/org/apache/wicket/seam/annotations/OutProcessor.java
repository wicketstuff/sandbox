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
import java.lang.reflect.Method;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.proxy.ILazyInitProxy;
import org.apache.wicket.seam.contexts.SeamContexts;
import org.apache.wicket.util.string.Strings;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Outjects any &amp;#064;Out annotated field or method into a Seam context.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public class OutProcessor extends AbstractAnnotationProcessor<Component> {

    private static final Logger logger = 
            LoggerFactory.getLogger(OutProcessor.class);
    
    @Override
    public void process(Component subject) {
        processFieldsAndMethods(subject, Out.class);
    }

    @Override
    protected void onFieldMatch(Component subject, Field field, List<Annotation> annotations) {
        Out out = (Out)annotations.get(0);
        boolean required = out.required();
        ScopeType scope = out.scope();
        String name = Strings.isEmpty(out.value()) ? field.getName() : out.value();
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object value = field.get(subject);
            logger.debug("Outject field: " + subject.getClass().getName() + "." + field.getName() + " = " + value);
            if (required && value == null) {
                throw new WicketSeamException("Required @Out dependency " + out + " is null");
            }
            outject(name, scope, value);
        } 
        catch (Exception ex) {
            throw new WicketSeamException(ex.getMessage(), ex);
        } 
    }

    @Override
    protected void onMethodMatch(Component subject, Method method, List<Annotation> annotations) {
        if (!method.getReturnType().equals(Void.class) && method.getParameterTypes().length == 0) {
            Out out = (Out)annotations.get(0);
            boolean required = out.required();
            ScopeType scope = out.scope();
            String name = Strings.isEmpty(out.value()) ? SeamNamingRules.getNameFromMethod(method) : out.value();
            try {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                Object value = method.invoke(subject);
                logger.debug("Outject method: " + subject.getClass().getName() + "." + method.getName() + "() = " + value);
                if (required && value == null) {
                    throw new WicketSeamException("Required @Out dependency " + out + " is null");
                }
                outject(name, scope, value);
            } 
            catch (Exception ex) {
                if (ex instanceof WicketSeamException) throw (WicketSeamException)ex;
                throw new WicketSeamException(ex.getMessage(), ex);
            } 
        }
    }
    
    private void outject(String name, ScopeType scope, Object value) {
        if (value instanceof ILazyInitProxy) return;
        switch (scope) {
            case UNSPECIFIED:
                SeamContexts.set(name, value, ScopeType.SESSION);
                break;
            case STATELESS:
                logger.warn("Outjected to a transient context: " + name + ": " + value);
                break;
            default:
                SeamContexts.set(name, value, scope);
        }
    }
    
}
