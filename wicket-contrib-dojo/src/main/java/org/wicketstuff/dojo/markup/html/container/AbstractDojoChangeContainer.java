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
package org.wicketstuff.dojo.markup.html.container;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * All component extending this class are component having several children which some are hidden 
 * and one is visible. <br/>
 * So it is possible to select the child component you want to show using {@link #setSelected(IDojoContainer)} or 
 * {@link #setSelected(int)}. The component selected will be displayed in the container. If nothing selected the first will
 * be displayed
 * 
 * @author Vincent Demay
 * @version SVN: $Id$
 *
 */
public abstract class AbstractDojoChangeContainer extends AbstractDojoContainer{

	public AbstractDojoChangeContainer(String id) {
		super(id);
	} 
	
	public AbstractDojoChangeContainer(String id, String title) {
		super(id, title);
	}

	/**
	 * Current selected 
	 */
	private IDojoContainer selected;

	/**
	 * child select by default
	 * This setSelected need a refresh of the component to be visible on client side
	 * @param toSelect child select by default
	 */
	public void setSelected(IDojoContainer toSelect) {
		selected = toSelect;
	}
	
	/**
	 * Set a selected child by its position on the ChageContainer. Index start on 0 .
	 * This setSelected need a refresh of the component to be visible on client side .
	 * @param position of the child in the javaDeclaration. <b>BE CARREFULL</b> the order in the markup (UI) can be different  
	 * than the order in the java Declaration.
	 */
	public void setSelected(int position){
		ChildByPositionFinder visitor = new ChildByPositionFinder(position);
		this.visitChildren(visitor);
		setSelected(visitor.getIDojoContainer());
	}
	
	/**
	 * return the current selected tab id
	 * @return the current selected tab id
	 */
	public String getSelectedChildId(){
		if (selected != null){
			return selected.getMarkupId();
		}
		else return "";
	}
	
	/**
	 * return the current container selected in the tab container
	 * @return the selected container in the tab container
	 */
	public IDojoContainer getSelectedTab(){
		return selected;
	}

	/**
	 * Ovewrite this methos to handle clicks on tab
	 * @param tab new tab selected
	 */
	public void onSelectionChange(IDojoContainer selected, AjaxRequestTarget target)
	{
				
	}
	
	//*********************************************/
	//**   A visitor to find a child by position **/
	//*********************************************/
	
	/**
	 * Find a IDojoContainer by a position in the widget
	 * hierarchie
	 */
	public class ChildByPositionFinder implements IVisitor{

		private int position;
		private int currentPos;
		
		private IDojoContainer found;
		
		public ChildByPositionFinder(int position) {
			this.position = position;
			this.currentPos = 0;
		}

		/**
		 * Visit direct children to find the IDojoContainer at position position
		 */
		public Object component(Component component) {
			if (component instanceof IDojoContainer){
				if (position == currentPos){
					found = (IDojoContainer) component;
					return IVisitor.STOP_TRAVERSAL;
				}
				currentPos++;
			}
			return IVisitor.CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
		}
		
		/**
		 * return the IDojoContainer or null if not found
		 * @return the IDojoContainer or null if not found
		 */
		public IDojoContainer getIDojoContainer(){
			return found;
		}
		
	}


}
