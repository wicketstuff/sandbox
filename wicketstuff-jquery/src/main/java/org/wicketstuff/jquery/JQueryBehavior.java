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
package org.wicketstuff.jquery;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class JQueryBehavior extends AbstractDefaultAjaxBehavior {

    // create a reference to the base javascript file.
    // we use JavascriptResourceReference so that the included file will have
    // its comments stripped and gzipped.
    private static final JavascriptResourceReference JQUERY_JS = new JavascriptResourceReference(JQueryBehavior.class, "jquery.pack.js");
    private static final JavascriptResourceReference JQUERY_DEBUG_JS = new JavascriptResourceReference(JQueryBehavior.class, "jquery.debug.js");
    private transient Logger logger_;

    @Override
    public void renderHead(IHeaderResponse response) {
        try {
            super.renderHead(response);
            response.renderJavascriptReference(JQUERY_JS);
            if (Application.DEVELOPMENT.equals(Application.get().getConfigurationType())) {
                response.renderJavascriptReference(JQUERY_DEBUG_JS);
            }
            CharSequence script = getOnReadyScript();
            if ((script != null) && (script.length() > 0)) {
                StringBuilder builder = new StringBuilder(script.length() + 61);
                builder.append("<script>$(document).ready(function(){\n");
                builder.append(script);
                builder.append("\n});</script>");
                response.renderString(builder.toString());
            }
        } catch (RuntimeException exc) {
            throw exc;
        } catch (Exception exc) {
            throw new RuntimeException("wrap: " + exc.getMessage(), exc);
        }
    }

    /**
     * to be override by subclass if need to run script when dom is ready.
     * The returned script is wrapped by caller into &lt;script&gt; tag and the "$(document).ready(function(){...}"
     *
     * @return the script to execute when the dom is ready, or null (default)
     */
    protected CharSequence getOnReadyScript() {
        return null;
    }

    @Override
    protected void respond(AjaxRequestTarget arg0) {
        throw new UnsupportedOperationException("nothing to do");
    }

    protected Logger logger() {
        if (logger_ == null) {
            logger_ = LoggerFactory.getLogger(this.getClass());
        }
        return logger_;
    }
}
