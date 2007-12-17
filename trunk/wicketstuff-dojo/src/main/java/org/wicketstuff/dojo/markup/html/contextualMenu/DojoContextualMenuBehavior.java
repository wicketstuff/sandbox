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
package org.wicketstuff.dojo.markup.html.contextualMenu;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * This behavior allows user to add a contextual menu on a Component:
 * <b>exemple</b>
 * <pre>
 * DojoSimpleContainer container = new DojoSimpleContainer("container");
 * container.setHeight("500px");
 * 
 * DojoMenu menu = new DojoMenu("menu");
 * menu.addChild(new DojoMenuItem("about", "About"));
 * menu.addChild(new DojoMenuItem("edit", "Edit")
 * 	.addChild(new DojoMenuItem("copy", "Copy", new ResourceReference(MenuSample.class, "copy.jpg")))
 * 	.addChild(new DojoMenuItem("move", "Move", new ResourceReference(MenuSample.class, "move.jpg"))));
 * container.add(new DojoContextualMenuBehavior(menu));
 * 
 * add(container);
 * </pre>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoContextualMenuBehavior extends AbstractRequireDojoBehavior{
	
	private DojoMenu menu;
	
	/**
	 * Construct the behavior
	 * @param menu menu to used as contextualMenu
	 */
	public DojoContextualMenuBehavior(DojoMenu menu) {
		super();
		this.menu = menu;
		this.menu.setMenuBehavior(this);
	}
	
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.Menu2");
	}

	protected void respond(AjaxRequestTarget target) {
		//FIXME : WTF?
		String action = RequestCycle.get().getRequest().getParameter("amp;action");
		DojoMenuItem item = menu.getMenuItem(action);
		item.onClick(target);
	}

	protected String getIconUrl(ResourceReference icon) {
		return getComponent().urlFor(icon).toString();
	}
	
	protected CharSequence getCallBackScriptForMenu(String id){
		return generateCallbackScript("wicketAjaxGet('"
				+ getCallbackUrl() + "&amp;action=" + id + "'");
	}

	/**
	 * Return the component where this bahavior has been added
	 * @return the component where this bahavior has been added
	 */
	public Component getAssociatedComponent() {
		return getComponent();
	}
	
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascript("dojo.addOnLoad(function(){\n"
				+ menu.generateJS()
				+ "\n});", getComponent().getMarkupId() + "MenuScript");
	}

	

}
