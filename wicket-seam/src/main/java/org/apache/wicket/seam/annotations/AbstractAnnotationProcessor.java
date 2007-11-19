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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.seam.SeamWebPage;

/**
 * Class visitor, fire events on annotations present on class, methods or fields.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public abstract class AbstractAnnotationProcessor<T> {

    /**
     * Check the subject and its superclasses for the specified annotations,
     * if any of the specified annotations are present, fires the onInstanceMatch event.
     * This method only check the class level annotations.
     * @param subject the object instance to be inspected.
     * @param annotations a list of annotations to be checked.
     */
    protected final void processInstance(T subject, Class<? extends Annotation>... annotations) {
        Class<?> clazz = subject.getClass();
        List<Annotation> found = new LinkedList<Annotation>();
        while (isManagedClass(clazz)) {
            for (final Class<? extends Annotation> a : annotations) {
                if (clazz.isAnnotationPresent(a)) {
                    found.add(clazz.getAnnotation(a));
                }
            }
            clazz = clazz.getSuperclass();
        }
        if (found.size() > 0) {
            onInstanceMatch(subject, found);
        }
    }

    /**
     * Check the subject and its superclasses for the specified annotations,
     * if any of the specified annotations are present, fires the onFieldMatch event.
     * This method only check the field level annotations.
     * @param subject the object instance to be inspected.
     * @param annotations a list of annotations to be checked.
     */
    protected final void processFields(T subject, Class<? extends Annotation>... annotations) {
        Class<?> clazz = subject.getClass();
        List<Annotation> found = new LinkedList<Annotation>();
        while (isManagedClass(clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                found.clear();
                for (final Class<? extends Annotation> a : annotations) {
                    if (field.isAnnotationPresent(a)) {
                        found.add(field.getAnnotation(a));
                    }
                }
                if (found.size() > 0) {
                    onFieldMatch(subject, field, found);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
    
    /**
     * Check the subject and its superclasses for the specified annotations,
     * if any of the specified annotations are present, fires the onMethodMatch event.
     * This method only check the method level annotations.
     * @param subject the object instance to be inspected.
     * @param annotations a list of annotations to be checked.
     */
    protected final void processMethods(T subject, Class<? extends Annotation>... annotations) {
        Class<?> clazz = subject.getClass();
        List<Annotation> found = new LinkedList<Annotation>();
        while (isManagedClass(clazz)) {
            Method[] methods = clazz.getDeclaredMethods();
            for (final Method method : methods) {
                found.clear();
                for (final Class<? extends Annotation> a : annotations) {
                    if (method.isAnnotationPresent(a)) {
                        found.add(method.getAnnotation(a));
                    }
                }
                if (found.size() > 0) {
                    onMethodMatch(subject, method, found);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
    
    /**
     * Check the subject and its superclasses for the specified annotations,
     * if any of the specified annotations are present, fires 
     * onFieldMatch, onMethodMatch or onInstanceMatch events when correspond.
     * @param subject the object instance to be inspected.
     * @param annotations a list of annotations to be checked.
     */
    protected final void processAll(T subject, Class<? extends Annotation>... annotations) {
        Class<?> clazz = subject.getClass();
        List<Annotation> found = new LinkedList<Annotation>();
        List<Annotation> foundi = new LinkedList<Annotation>();
        while (isManagedClass(clazz)) {
            // Process instance
            for (final Class<? extends Annotation> a : annotations) {
                if (clazz.isAnnotationPresent(a)) {
                    foundi.add(clazz.getAnnotation(a));
                }
            }
            // Process fields
            Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                found.clear();
                for (final Class<? extends Annotation> a : annotations) {
                    if (field.isAnnotationPresent(a)) {
                        found.add(field.getAnnotation(a));
                    }
                }
                if (found.size() > 0) {
                    onFieldMatch(subject, field, found);
                }
            }
            // Process methods
            Method[] methods = clazz.getDeclaredMethods();
            for (final Method method : methods) {
                found.clear();
                for (final Class<? extends Annotation> a : annotations) {
                    if (method.isAnnotationPresent(a)) {
                        found.add(method.getAnnotation(a));
                    }
                }
                if (found.size() > 0) {
                    onMethodMatch(subject, method, found);
                }
            }
            clazz = clazz.getSuperclass();
        }
        if (foundi.size() > 0) {
            onInstanceMatch(subject, foundi);
        }
    }

    /**
     * Check the subject and its superclasses for the specified annotations,
     * if any of the specified annotations are present, fires 
     * onMethodMatch or onFieldMatch event when correspond.
     * This method only check the method and field level annotations.
     * @param subject the object instance to be inspected.
     * @param annotations a list of annotations to be checked.
     */
    protected final void processFieldsAndMethods(T subject, Class<? extends Annotation>... annotations) {
        Class<?> clazz = subject.getClass();
        List<Annotation> found = new LinkedList<Annotation>();
        while (isManagedClass(clazz)) {
            // Process fields
            Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                found.clear();
                for (final Class<? extends Annotation> a : annotations) {
                    if (field.isAnnotationPresent(a)) {
                        found.add(field.getAnnotation(a));
                    }
                }
                if (found.size() > 0) {
                    onFieldMatch(subject, field, found);
                }
            }
            // Process methods
            Method[] methods = clazz.getDeclaredMethods();
            for (final Method method : methods) {
                found.clear();
                for (final Class<? extends Annotation> a : annotations) {
                    if (method.isAnnotationPresent(a)) {
                        found.add(method.getAnnotation(a));
                    }
                }
                if (found.size() > 0) {
                    onMethodMatch(subject, method, found);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
    
    /**
     * Limits the hierarchic search. When this method returns false, the
     * hierarchic search stops.
     * @param clazz the subject class
     * @return true if this class must be checked, false if this class must
     * not be checked and the search must stop now.
     */
    protected boolean isManagedClass(Class<?> clazz) {
        return 
            clazz != null 
            && !SeamWebPage.class.equals(clazz)
            && !Panel.class.equals(clazz)
            && !WebPage.class.equals(clazz)
            && !Page.class.equals(clazz)
            && !MarkupContainer.class.equals(clazz)
            && !Component.class.equals(clazz)
            && !Object.class.equals(clazz);
    }
    
    /**
     * Event listener method called when there is a field that match any of the
     * specified annotatios.
     * @param subject the source object.
     * @param field the matching field.
     * @param annotations the matching annotations on this field.
     */
    protected void onFieldMatch(T subject, Field field, List<Annotation> annotations) {
        // Implemented in subclasses
    }
    
    /**
     * Event listener method called when there is a method that match any of the
     * specified annotatios.
     * @param subject the source object.
     * @param method the matching method.
     * @param annotations the matching annotations on this method.
     */
    protected void onMethodMatch(T subject, Method method, List<Annotation> annotations) {
        // Implemented in subclasses
    }
    
    /**
     * Event listener method called when there are any of then specified annotations
     * on the subject.
     * @param subject the source object.
     * @param annotations the matching annotations on this subject.
     */
    protected void onInstanceMatch(T subject, List<Annotation> annotations) {
        // Implemented in subclasses
    }
    
    /**
     * Subclasses must call at least one of the process* methods from here.  
     * @param subject
     */
    public abstract void process(T subject);
    
}
