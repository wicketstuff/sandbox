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

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.dojodnd.DojoDragContainer;
import org.wicketstuff.dojo.dojodnd.DojoDropContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;


/**
 * DojoOrderableListViewContainer should contains a {@link DojoOrderableListView} and nothing else.
 * This container and it child allow top make a DragAndDropableList. Model associated with the {@link DojoOrderableListView}
 * will be automaticaly updated during dnd
 * <p>
 * <p>
 * 	This componant should be associated to a <b>div</b> element in the markup
 * </p>
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
@SuppressWarnings("serial")
public class DojoOrderableListContainer extends DojoDropContainer
{

	/**
	 * Construct
	 * @param id id
	 */
	public DojoOrderableListContainer(String id)
	{
		super(id);
		add(new DojoOrderableListContainerHandler());
	}


	/**
	 * 
	 * @param container
	 * @param oldPosition
	 * @param newPosition
	 * @param target
	 */
	public void onDrop(WebMarkupContainer container,  int oldPosition, int newPosition, AjaxRequestTarget target)
	{
		if (oldPosition != newPosition){
			if (container instanceof DojoOrderableListView){
				DojoOrderableListView current = (DojoOrderableListView) container;
				List list = current.getList();
				Object drag = list.remove(oldPosition);
				list.add(newPosition, drag);
				current.setList(list);
			}
			else if (container instanceof DojoOrderableRepeatingView){
				DojoOrderableRepeatingView current = (DojoOrderableRepeatingView) container;
				current.moveItem(oldPosition, newPosition, target);
			}
			target.appendJavascript(getChangeIDScript());
		}
	}
	
	public String getChangeIDScript(){
		String containerId = getMarkupId();
		String changeId = "";
		changeId += "var children = document.getElementById('" + containerId + "').getElementsByTagName('div');\n";
		changeId += "for(var i=0; children.length > i ; i++){\n";
		changeId += "	children[i].setAttribute('pos',i);\n";
		changeId += "}";
		return changeId;
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "div");
		super.onComponentTag(tag);
	}
	
	/**
	 * Called after the model is updated. Use this method to e.g. update the
	 * persistent model. Does nothing by default.
	 */
	protected void onAjaxModelUpdated(AjaxRequestTarget target)
	{
		int newPosition = Integer.parseInt(getRequest().getParameter("position"));
		int oldPosition = Integer.parseInt(getRequest().getParameter("oldPosition"));
		ChildFinder visitor = new ChildFinder();
		visitChildren(visitor);
		WebMarkupContainer container = visitor.getChild();
		onDrop(	container, oldPosition, newPosition, target);
	}
	
	/**
	 * THIS METHOD CAN NOT BE USED IN THIS CONTEXT
	 */
	public final void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position){}
	
	
/***************************************************************************/
	
	private class ChildFinder implements IVisitor{
		private WebMarkupContainer child = null;
		private int listViewCount = 0;
		private int repeatingViewCount = 0;
		
		public Object component(Component component)
		{
			if (component instanceof DojoOrderableListView){
				child = (DojoOrderableListView)component;
				listViewCount ++;
			}
			if (component instanceof DojoOrderableRepeatingView){
				child = (DojoOrderableRepeatingView)component;
				repeatingViewCount ++;
			}
			return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
		}
		
		public WebMarkupContainer getChild(){
			if (listViewCount != 1 && repeatingViewCount != 1){
				throw new WicketRuntimeException("A DojoOrderableListContainer should contain exactly one DojoOrderableList or DojoOrderableRepeatingView as direct child : found " + listViewCount);
			}
			return child;
		}
	}

}
