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
package wicket.contrib.dojo.markup.html.list;

import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.markup.html.list.ListItem;

/**
 * Add this widget to a {@link DojoOrderableListView} to make it removable by clicking on this widget
 * <p>
 * <b>Sample</b>
 * <pre>
 * public class OrderableList extends WebPage {
 * 
 * 	static final List objList  = new  ArrayList();
 * 
 * 	
 * 	public OrderableList() {
 * 		super();
 * 		if (objList.size() == 0){
 * 			objList.add("foo1");
 * 			objList.add("bar1");
 * 			objList.add("foo2");
 * 			objList.add("bar2");
 * 			objList.add("foo3");
 * 			objList.add("bar3");
 * 			objList.add("foo4");
 * 			objList.add("bar4");
 * 			objList.add("foo5");
 * 			objList.add("bar5");
 * 			objList.add("foo6");
 * 			objList.add("bar6");
 * 		}
 * 		DojoOrderableListViewContainer container = new DojoOrderableListViewContainer("container");
 * 		add(container);
 * 		DojoOrderableListView list = new DojoOrderableListView(container, "list", objList){
 * 
 * 			protected void populateItem(ListItem item) {
 * 				item.add(new Label("label",(String)item.getModelObject()));
 * 				item.add(new DojoOrderableListRemover("remover", item));
 * 				
 * 			}
 * 			
 * 		};
 * 		container.add(list);
 * 	}
 * 
 * }
 * </pre>
 * </p>
 * @author Vincent Demay
 */
public class DojoOrderableListRemover extends AjaxLink{

	// item to remove
	private ListItem item;
	
	/**
	 * Construct
	 * @param id id
	 * @param item ListItem to be remove if clicking
	 */
	public DojoOrderableListRemover(String id, ListItem item)
	{
		super(id);
		this.item = item; 
	}
	
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		check();
	}

	public final void onClick(AjaxRequestTarget target)
	{
		if (beforeRemoving()){
			DojoOrderableListView parent = ((DojoOrderableListView)this.item.getParent());
			DojoOrderableListViewContainer granParent = (DojoOrderableListViewContainer)parent.getParent();
			parent.getList().remove(parent.getList().indexOf(this.item.getModelObject()));
			parent.modelChanged();
			
			target.addComponent(granParent);
			onRemove(target);
		}
		else {
			onNotRemove(target);
		}
	}
	
	private void check(){
		if (! (item.getParent() instanceof DojoOrderableListView)){
			throw new WicketRuntimeException("Parent of item should be a DojoOrderableListView");
		}
		if (! (item.getParent().getParent() instanceof DojoOrderableListViewContainer)){
			throw new WicketRuntimeException("GranParent of item should be a DojoOrderableListViewContainer");
		}
	}

	/**
	 * Triggered if remove succes
	 * @param target target
	 */
	protected void onRemove(AjaxRequestTarget target){
		
	}
	
	/**
	 * Triggered if remove failed
	 * @param target target 
	 */
	private void onNotRemove(AjaxRequestTarget target)
	{
		
	}
	
	/**
	 * This method is called before remove the element for the list
	 * if returned value is true remove will be done and onRemove will be execute, otherwise
	 * remove will not be done and onNotRemove will be called
	 * @return true to remove the item, false otherwise
	 */
	protected boolean beforeRemoving(){
		return true;
	}
	


	
	
}