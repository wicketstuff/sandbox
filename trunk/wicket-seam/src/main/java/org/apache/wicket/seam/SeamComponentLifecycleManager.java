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

package org.apache.wicket.seam;

import org.apache.wicket.seam.annotations.OutProcessor;
import org.apache.wicket.seam.annotations.InProcessor;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.application.IComponentOnAfterRenderListener;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.seam.annotations.CleanupProcessor;
import org.apache.wicket.seam.annotations.CreateProcessor;

/**
 * Manage component intantiation and lifecycle events.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public class SeamComponentLifecycleManager implements 
        IComponentOnBeforeRenderListener, 
        IComponentOnAfterRenderListener,
        IComponentInstantiationListener {

    /** &amp;#064;In annotation processor. */
    static final InProcessor inAnnotationProcessor = new InProcessor();
    
    /** &amp;#064;Out annotation processor. */
    static final OutProcessor outAnnotationProcessor = new OutProcessor();
    
    /** &amp;#064;Create annotation processor. */
    static final CreateProcessor createAnnotationProcessor = new CreateProcessor();
    
    /** Cleanup annotation processor. */
    static final CleanupProcessor cleanupProcessor = new CleanupProcessor();
    
    /** 
     * Activate Seam support.
     * @param application the application to be managed.
     */
    public static void setManaged(Application application) {
        SeamComponentLifecycleManager manager = new SeamComponentLifecycleManager();
        application.addComponentInstantiationListener(manager);
        application.addComponentOnBeforeRenderListener(manager);
        application.addComponentOnAfterRenderListener(manager);
    }
    
    /** 
     * @see org.apache.wicket.application.IComponentOnBeforeRenderListener#onBeforeRender() 
     */
    public void onBeforeRender(Component component) {
    }

    /** 
     * @see org.apache.wicket.application.IComponentOnAfterRenderListener#onAfterRender()
     */
    public void onAfterRender(Component component) {
    }

    /** 
     * @see org.apache.wicket.application.IComponentInstantiationListener#onInstantiation()
     */
    public void onInstantiation(Component component) {
        if (component instanceof SeamWebPage) {
            inAnnotationProcessor.process(component);
            createAnnotationProcessor.process(component);
        }
    }

}
