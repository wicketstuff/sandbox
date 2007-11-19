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

package org.apache.wicket.seam.contexts;

import org.apache.wicket.seam.el.SeamELSupport;
import org.jboss.seam.ScopeType;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;

/**
 * Provides easy access to all seam contexts.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public class SeamContexts {

    /**
     * @see org.jboss.seam.contexts.Contexts#getEventContext()
     * @return the Seam Event Context.
     */
    public static Context getEventContext() {
        return Contexts.getEventContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#getMethodContext()
     * @return the Seam Method Context.
     */
    public static Context getMethodContext() {
        return Contexts.getMethodContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#getPageContext()
     * @return the Seam Page Context.
     */
    public static Context getPageContext() {
        return Contexts.getPageContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#getSessionContext()
     * @return the Seam Session Context.
     */
    public static Context getSessionContext() {
        return Contexts.getSessionContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#getApplicationContext()
     * @return the Seam Application Context.
     */
    public static Context getApplicationContext() {
        return Contexts.getApplicationContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#getConversationContext()
     * @return the Seam Conversation Context.
     */
    public static Context getConversationContext() {
        return Contexts.getConversationContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#getBusinessProcessContext()
     * @return the Seam Business Process Context.
     */
    public static Context getBusinessProcessContext() {
        return Contexts.getBusinessProcessContext();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isConversationContextActive()
     * @return true if the Conversation context is active.
     */
    public static boolean isConversationContextActive() {
        return Contexts.isConversationContextActive();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isEventContextActive()
     * @return true if the Event context is active.
     */
    public static boolean isEventContextActive() {
        return Contexts.isEventContextActive();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isMethodContextActive()
     * @return true if the Method context is active.
     */
    public static boolean isMethodContextActive() {
        return Contexts.isMethodContextActive();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isPageContextActive()
     * @return true if the Page context is active.
     */
    public static boolean isPageContextActive() {
        return Contexts.isPageContextActive();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isSessionContextActive()
     * @return true if the Session context is active.
     */
    public static boolean isSessionContextActive() {
        return Contexts.isSessionContextActive();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isApplicationContextActive()
     * @return true if the Application context is active.
     */
    public static boolean isApplicationContextActive() {
        return Contexts.isApplicationContextActive();
    }

    /**
     * @see org.jboss.seam.contexts.Contexts#isBusinessProcessContextActive()
     * @return true if the Business Process context is active.
     */
    public static boolean isBusinessProcessContextActive() {
        return Contexts.isBusinessProcessContextActive();
    }

    /**
     * Remove the named component from all contexts.
     */
    public static void removeFromAllContexts(String name) {
        Contexts.removeFromAllContexts(name);
    }

    /**
    * Search for a named attribute in all contexts, in the
    * following order: method, event, page, conversation,
    * session, business process, application.
    * 
    * @return the first component found, or null
    */
    public static Object lookupInStatefulContexts(String name) {
        return Contexts.lookupInStatefulContexts(name);
    }
    
    /**
     * Set a reference into a scoped context.
     * @param name the name
     * @param value the value
     * @param scope the scope
     */
    public static void set(String name, Object value, ScopeType scope) {
        switch(scope) {
            case METHOD:
                if (isMethodContextActive()) {
                    getMethodContext().set(name, value);
                }
                break;
            case EVENT:
                if (isEventContextActive()) {
                    getEventContext().set(name, value);
                }
                break;
            case PAGE:
                if (isPageContextActive()) {
                    getPageContext().set(name, value);
                }
                break;
            case CONVERSATION:
                if (isConversationContextActive()) {
                    getConversationContext().set(name, value);
                }
                break;
            case SESSION:
                if (isSessionContextActive()) {
                    getSessionContext().set(name, value);
                }
                break;
            case BUSINESS_PROCESS:
                if (isBusinessProcessContextActive()) {
                    getBusinessProcessContext().set(name, value);
                }
                break;
            case APPLICATION:
                if (isApplicationContextActive()) {
                    getApplicationContext().set(name, value);
                }
                break;
            case UNSPECIFIED:
            case STATELESS:
        }
    }
    
    /**
     * Get a reference looking in all stateful contexts.
     * @param name the name
     * @return the reference
     */
    public static Object get(String name) {
        return lookupInStatefulContexts(name);
    }
    
    /**
     * Get a scoped reference from the respective context.
     * @param name the name
     * @param scope the scope
     * @return the reference
     */
    public static Object get(String name, ScopeType scope) {
        switch(scope) {
            case METHOD:
                if (isMethodContextActive()) {
                    return getMethodContext().get(name);
                }
                break;
            case EVENT:
                if (isEventContextActive()) {
                    return getEventContext().get(name);
                }
                break;
            case PAGE:
                if (isPageContextActive()) {
                    return getPageContext().get(name);
                }
                break;
            case CONVERSATION:
                if (isConversationContextActive()) {
                    return getConversationContext().get(name);
                }
                break;
            case SESSION:
                if (isSessionContextActive()) {
                    return getSessionContext().get(name);
                }
                break;
            case BUSINESS_PROCESS:
                if (isBusinessProcessContextActive()) {
                    return getBusinessProcessContext().get(name);
                }
                break;
            case APPLICATION:
                if (isApplicationContextActive()) {
                    return getApplicationContext().get(name);
                }
                break;
            case UNSPECIFIED:
            case STATELESS:
        }
        return null;
    }
    
    /**
     * Eval the passed expression through Seam contexts.
     * @param expression jboss-el expression.
     * @param returnType the expected result type.
     * @return the result of the expression evaluation.
     */
    public static <T> T eval(String expression, Class<T> returnType) {
        return SeamELSupport.evaluate(expression, returnType);
    }
    
}
