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
package org.wicketstuff.dojo.markup.html.list;

import org.apache.wicket.Component;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;

@SuppressWarnings("serial")
public class DojoOrderableListScrollerBehavior extends AbstractBehavior implements IHeaderContributor{

	private Component scrollComponent;
	private String scrollId;
	
	public DojoOrderableListScrollerBehavior(Component scrollComponent) {
		super();
		this.scrollComponent = scrollComponent;
	}
	
	public DojoOrderableListScrollerBehavior(String scrollId) {
		super();
		this.scrollId = scrollId;
	}

	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(new ResourceReference(DojoOrderableListScrollerBehavior.class, "DojoOrderableListScrollerBehaviorTemplate.js"));
		String js;
		if (scrollComponent != null){
			js= "dojo.event.connect(dojo, \"loaded\", function(){ lookupScrolling(\"" + scrollComponent.getMarkupId() + "\")});";
		}else{
			js= "dojo.event.connect(dojo, \"loaded\", function(){ lookupScrolling(\"" + scrollId + "\")});";
		}
		response.renderJavascript(js, js);
	}
	
	public void updateScrollComponent(Component scrollComponent){
		this.scrollComponent = scrollComponent;
	}

	public void onRendered(Component component) {
		super.onRendered(component);
		
		IRequestTarget target = RequestCycle.get().getRequestTarget();
		if (target instanceof AjaxRequestTarget){
			AjaxRequestTarget current = (AjaxRequestTarget) target;
			if (scrollComponent != null){
				current.appendJavascript("put = false; lookupScrolling(\"" + scrollComponent.getMarkupId() + "\");");
			}else{
				current.appendJavascript("put = false; lookupScrolling(\"" + scrollId + "\");");
			}
			
		}
	}
}
