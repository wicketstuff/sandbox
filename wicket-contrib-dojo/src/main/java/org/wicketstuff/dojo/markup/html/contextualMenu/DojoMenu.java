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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.wicket.ResourceReference;

/**
 * A dojoMenu should be added to a {@link DojoContextualMenuBehavior} to display a contextual menu
 * <br/>
 * <u>INFO : </u>This is not a Component : no Html needs to be added in the template<br/>
 * <br/>
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
public class DojoMenu implements Serializable{
	private HashMap<String, DojoMenuItem> children;
	private DojoContextualMenuBehavior menuBehavior;
	private String id;

	/**
	 * Constructs
	 * @param id DojoMenu id
	 */
	public DojoMenu(String id) {
		super();
		children = new HashMap<String, DojoMenuItem>();
		this.id = id;
	}
	
	/**
	 * Add an item to the menu
	 * @param child menu Item
	 */
	public void addChild(DojoMenuItem child){
		children.put(child.getId(), child);
		child.setMenu(this);
	}
	
	/**
	 * Returnthe icon url for a resource
	 * @param icon {@link ResourceReference} of an icon
	 * @return icon url
	 */
	protected String getIconUrl(ResourceReference icon){
		return menuBehavior.getIconUrl(icon);
	}

	/**
	 * return the bahvior where the menu has been added
	 * @return  the bahvior where the menu has been added
	 */
	protected DojoContextualMenuBehavior getMenuBehavior() {
		return menuBehavior;
	}

	/**
	 * set the bahvior where the menu has been added
	 * @param menuBehavior the bahvior where the menu has been added
	 */
	protected void setMenuBehavior(DojoContextualMenuBehavior menuBehavior) {
		this.menuBehavior = menuBehavior;
	}
	
	/**
	 * Genrate the js to create the menu
	 * @return the js to create the menu
	 */
	protected String generateJS(){
		String toReturn = "var " + id + " = dojo.widget.createWidget('PopupMenu2', {targetNodeIds: ['" + menuBehavior.getAssociatedComponent().getMarkupId() + "']});\n";
		
		Iterator it = children.entrySet().iterator();
		while (it.hasNext()){
			DojoMenuItem element = (DojoMenuItem) ((Entry) it.next()).getValue();
			toReturn += element.generateConstructJS();
			toReturn += id + element.generateAddJS();
		}
		return toReturn;
	}
	
	/**
	 * Return the {@link DojoMenuItem} according to its action
	 * @param action action of a {@link DojoMenuItem}
	 * @return the {@link DojoMenuItem} according to its action
	 */
	public DojoMenuItem getMenuItem(String action){
		if (children.containsKey(action)){
			return (DojoMenuItem) children.get(action);
		}else{
			Iterator it = children.entrySet().iterator();
			while (it.hasNext()){
				DojoMenuItem element = (DojoMenuItem) ((Entry) it.next()).getValue();
				if (element instanceof DojoMenuItem){
					DojoMenuItem item = ((DojoMenuItem)element).getMenuItem(action);
					if(item !=null){
						return item;
					}
				}
			}
			return null;
		}
	}
}
