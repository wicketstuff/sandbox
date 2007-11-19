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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

/**
 * WebPage specialization for Seam integration.
 * Your pages must extend this class to get Seam bijection, contexts, el, etc...
 * <ul>
 *   <li>
 *     The onPageAttached method is marked as final, but there is a new
 *     seamPage_attached method that you can override.
 *   </li>
 *   <li>
 *     The onDetach method is marked as final, but there is a new
 *     seamPage_detached method that you can override.
 *   </li>
 *   <li>
 *     The onBeforeRender method is marked as final, but there is a new
 *     seamPage_beforeRender method that you can override.
 *   </li>
 *   <li>
 *     The onAfterRender method is marked as final, but there is a new
 *     seamPage_afterRender method that you can override.
 *   </li>
 *     
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public abstract class SeamWebPage extends WebPage {

    /**
     * @see org.apache.wicket.markup.html.WebPage#onPageAttached
     */
    @Override
    public final void onPageAttached() {
        SeamComponentLifecycleManager.inAnnotationProcessor.process(this);
        seamPage_attached();
        super.onPageAttached();
    }

    /**
     * @see org.apache.wicket.markup.html.WebPage#onDetach
     */
    @Override
    protected final void onDetach() {
        SeamComponentLifecycleManager.cleanupProcessor.process(this);
        seamPage_detached();
        super.onDetach();
    }

    /**
     * @see org.apache.wicket.markup.html.WebPage#onBeforeRender
     */
    @Override
    protected final void onBeforeRender() {
        SeamComponentLifecycleManager.outAnnotationProcessor.process(this);
        seamPage_beforeRender();
        super.onBeforeRender();
    }

    /**
     * @see org.apache.wicket.markup.html.WebPage#onAfterRender
     */
    @Override
    protected final void onAfterRender() {
        seamPage_afterRender();
        super.onAfterRender();
    }

    /** 
     * Default constructor. 
     */
    public SeamWebPage() {
        this(null);
    }
    
    /** 
     * Model based constructor. 
     */
    public SeamWebPage(IModel model) {
        super(model);
    }
    
    /**
     * Lifecycle listener method fired when the page is attached.
     * @see #onPageAttached()
     */
    protected void seamPage_attached() { }
    
    /**
     * Lifecycle listener method fired when the page is detached.
     * @see #onDetach()
     */
    protected void seamPage_detached() { }
    
    /**
     * Lifecycle listener method fired before the page is rendered.
     * @see #onBeforeRender()
     */
    protected void seamPage_beforeRender() { }
    
    /**
     * Lifecycle listener method fired after the page is rendered.
     * @see #onAfterRender()
     */
    protected void seamPage_afterRender() { }

}
