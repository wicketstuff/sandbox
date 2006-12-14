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
package wicket.contrib.dojo.html.list.lazy;


import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_LAZYTABLE;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;

/**
 * A lazy loading Repeating view
 * @author vincent demay
 *
 */
public class DojoLazyLoadingListContainer extends  WebMarkupContainer
{
	
	private int max;

	public DojoLazyLoadingListContainer(MarkupContainer parent, String id, IModel model, int maxItem)
	{
		super(parent, id, model);
		add(new DojoLazyLoadingListContainerHandler());
		max = maxItem;
	}

	public DojoLazyLoadingListContainer(MarkupContainer parent, String id, int maxItem)
	{
		super(parent, id);
		add(new DojoLazyLoadingListContainerHandler());
		max = maxItem;
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_LAZYTABLE);
		tag.put("templatePath", urlFor(new ResourceReference(DojoLazyLoadingListContainerHandler.class, "LazyTable.htm")));
		tag.put("rowCount","" + max);
	}
	
	/**
	 * Find the list view in children
	 * if none or more than one throw an exception!
	 * 
	 * @return the child ListView of this container
	 */
	public Component getChild()
	{
		ChildFinder visitor = new ChildFinder();
		visitChildren(visitor);
		return visitor.getChild();
	}
	
	
	/**
	 * Check for child - retreive a 
	 *   * DojoLazyLoadingRefreshingView
	 * @author Vincent Demay
	 *
	 */
	private class ChildFinder implements IVisitor{
		private Component component = null;
		private int componentNumber = 0;
		
		public Object component(Component component)
		{
			if (component instanceof DojoLazyLoadingRefreshingView){
				this.component = component;
				componentNumber ++;
			}
			return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
		}
		
		public Component getChild(){
			if (componentNumber != 1 ){
				throw new WicketRuntimeException("A DojoLazyLoadingListContainer should contain exactly one DojoLazyLoadingRefreshingView as directly child");
			}
			return component;
		}
	}
	
	



	
}