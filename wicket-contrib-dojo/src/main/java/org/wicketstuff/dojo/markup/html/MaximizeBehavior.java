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
package org.wicketstuff.dojo.markup.html;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Dojo behaviour that maximizes an HTML element to the whole window's viewport
 * size
 * @deprecated use {@link DojoLayoutContainer} instead
 */
@SuppressWarnings("serial")
public class MaximizeBehavior extends AbstractRequireDojoBehavior {
	/** container handler is attached to. */
	private Component container;

	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind() {
		this.container = getComponent();
	}

	protected int getVerticalMargin() {
		return 0;
	}
	protected int getHorizontalMargin() {
		return 0;
	}
	protected float getHorizontalRatio() {
		return 1f;
	}
	protected float getVerticalRatio() {
		return 1f;
	}
	/**
	 * @see wicket.contrib.dojo.DojoAjaxHandler#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(MaximizeBehavior.class, "MaximizeBehavior.js"));
		response.renderJavascript(generateDefinition(), MaximizeBehavior.class.getName() + container.getMarkupId());
	}

	private String generateDefinition() {
		StringBuffer toReturn = new StringBuffer();
		toReturn.append("dojo.addOnLoad( function() { maximize('" + container.getMarkupId() + "', " + getVerticalMargin() + ", " + getHorizontalMargin() + ", " + getVerticalRatio() + ", " + getHorizontalRatio() + "); var widget = dojo.widget.byId('"
				+ container.getMarkupId() + "'); if (widget.onResized) widget.onResized()});\n");
		return toReturn.toString();
	}

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.html");
	}

	protected void respond(AjaxRequestTarget target) {
		// Not used
	}
}
