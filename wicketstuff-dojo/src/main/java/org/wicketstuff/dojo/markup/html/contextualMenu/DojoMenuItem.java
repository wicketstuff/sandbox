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
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * A dojoMenuItem is an item on a contextual menu. It also accept other {@link DojoMenuItem} as child
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
public class DojoMenuItem implements Serializable{
	
	private String caption;
	private String accelKey;
	private ResourceReference icon;
	protected HashMap<String, DojoMenuItem> children;
	protected DojoMenu menu;
	protected String id;

	/**
	 * Constructs a DojoMenuItem
	 * @param id DojoMenuItem id
	 * @param caption item caption
	 * @param accelKey item key associated to the action
	 * @param icon Icon to show before the caption
	 */
	public DojoMenuItem(String id, String caption, String accelKey, ResourceReference icon) {
		super();
		children = new HashMap<String, DojoMenuItem>();
		this.caption = caption;
		this.id = id;
		this.accelKey = accelKey;
		this.icon = icon;
	}
	
	/**
	 * Constructs a DojoMenuItem
	 * @param id DojoMenuItem id
	 * @param caption item caption
	 * @param accelKey item key associated to the action
	 */
	public DojoMenuItem(String id, String caption, String accelKey) {
		this(id, caption, accelKey, null);
	}
	
	/**
	 * Constructs a DojoMenuItem
	 * @param id DojoMenuItem id
	 * @param caption item caption
	 * @param icon Icon to show before the caption
	 */
	public DojoMenuItem(String id, String caption, ResourceReference icon) {
		this(id, caption, null, icon);
	}
	
	/**
	 * Constructs a DojoMenuItem
	 * @param id DojoMenuItem id
	 * @param caption item caption
	 */
	public DojoMenuItem(String id, String caption) {
		this(id, caption, null, null);
	}
	
	/**
	 * Set the menu containing this item
	 * @param menu menu containing this item
	 */
	protected void setMenu(DojoMenu menu) {
		this.menu = menu;
		Iterator it = children.entrySet().iterator();
		while (it.hasNext()){
			DojoMenuItem element = (DojoMenuItem) ((Entry) it.next()).getValue();
			element.setMenu(menu);
		}
	}

	/**
	 * return the item id
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Add a child to this item
	 * @param child to add to this item
	 * @return this item : allows addChild chaining
	 */
	public DojoMenuItem addChild(DojoMenuItem child){
		children.put(child.getId(), child);
		return this;
	}
	
	/**
	 * Return the callback script when click on the item
	 * @return the callback script when click on the item
	 */
	protected CharSequence getCallBackScript(){
		return menu.getMenuBehavior().getCallBackScriptForMenu(id);
	}
	
	/**
	 * Return Js source code to add this item
	 * @return Js source code to add this item
	 */
	protected String generateAddJS(){
		return ".addChild(" + id + ")\n";
	}
	
	/**
	 * Return Js source code to create this item and its children
	 * @return Js source code to create this item and its children
	 */
	protected String generateConstructJS(){
		String toReturn = "";
		if(!children.isEmpty()){
			toReturn += "var " + id + "Sub  = dojo.widget.createWidget('PopupMenu2', {id:'" + id + "'})\n";
		}
		
		toReturn += "var " + id + " = dojo.widget.createWidget('MenuItem2',{";
		toReturn += "caption: '" + caption + "'";
		if(!children.isEmpty()){
			toReturn += ", submenuId: '" + id + "'";
		}
		if (accelKey != null){
			toReturn += ", accelKey : '" + accelKey + "'";
		}
		if (icon != null){
			toReturn += ", iconSrc : '" + menu.getIconUrl(icon) + "'";
		}
		toReturn += ", onClick : function(){" + getCallBackScript() + "}";
		toReturn += "});\n";
		
		Iterator it = children.entrySet().iterator();
		while (it.hasNext()){
			DojoMenuItem element = (DojoMenuItem) ((Entry) it.next()).getValue();
			toReturn += element.generateConstructJS();
			toReturn += id + "Sub" + element.generateAddJS();
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
					DojoMenuItem item = ((DojoMenuItem) element).getMenuItem(action);
					if(item !=null){
						return item;
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * Triggered when user click on this item in the ContextualMenu
	 * @param target {@link AjaxRequestTarget}
	 */
	public void onClick(AjaxRequestTarget target){
	}

}
