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
package org.wicketstuff.dojo.markup.html.floatingpane;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;

/**
 * Lets a DojoModalFloatingPane handle the Wicket Ajax errors by defining the
 * special wicketGlobalFailureHandler() function and calling modalDialog.show()
 */
@SuppressWarnings("serial")
public class AjaxErrorBehavior extends AbstractRequireDojoBehavior implements IHeaderContributor {
	public void renderHead(IHeaderResponse response) {
        String js = "window.wicketGlobalFailureHandler = function() {";
        js += "var errorWindow = dojo.widget.byId('" + getComponent().getMarkupId() + "');";
        js += "errorWindow.show();}";
        response.renderJavascript(js, AjaxErrorBehavior.class + "." + getComponent().getMarkupId());
	}
	protected void respond(AjaxRequestTarget target) {
	}
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.*");
	}
}
