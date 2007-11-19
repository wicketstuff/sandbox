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

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * WebApplication specialization for Jboss Seam Integration.
 * Your application must extend this class too get Seam bijection, context, el, etc...
 * <ul>
 *   <li>
 *     The newSession method is marked as final, but there is a new
 *     seamApp_newSession method that you can override.
 *   </li>
 *   <li>
 *     The init method is marked as final, but there is a new
 *     seamApp_init method that you can override.
 *   </li>
 * </ul>
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public abstract class SeamWebApplication extends WebApplication {

    /**
     * @see org.apache.wicket.protocol.http.WebApplication#newSession(...)
     * @see #seamApp_newSession(...)
     */
    @Override
    public final Session newSession(Request request, Response response) {
        return seamApp_newSession(request, response);
    }

    /**
     * @see org.apache.wicket.protocol.http.WebApplication#init()
     * @see #seamApp_init()
     */
    @Override
    protected final void init() {
        super.init();
        SeamComponentLifecycleManager.setManaged(this);
        seamApp_init();
    }
    
    /**
     * Delegated implementation of newSession.
     * @see #newSession(...)
     * @param request the request
     * @param response the response
     * @return a new Session
     */
    protected SeamWebSession seamApp_newSession(Request request, Response response) {
        return new SeamWebSession(request);
    }

    /**
     * Delegated implementation of init.
     * @see #init()
     */
    protected void seamApp_init() { }
    
}
