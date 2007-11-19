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
import java.lang.reflect.Method;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.seam.WicketSeamException;
import org.jboss.seam.annotations.Create;

/**
 * Call any &amp;#064;Create annotated method.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public class CreateProcessor extends AbstractAnnotationProcessor<Component> {

    @Override
    public void process(Component subject) {
        processMethods(subject, Create.class);
    }

    @Override
    protected void onMethodMatch(Component subject, Method method, 
            List<Annotation> annotations) {
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke(subject, new Object[0]);
        } 
        catch (Exception ex) {
            throw new WicketSeamException(ex.getMessage(), ex);
        } 
    }
    
}
