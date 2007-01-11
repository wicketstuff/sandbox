/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.dojo.markup.html.list;

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.markup.html.list.ListItem;

/**
 * Add this widget to a {@link DojoOrderableListView} to make it removable by
 * clicking on this widget
 * <p>
 * <b>Sample</b>
 * 
 * <pre>
 * public class OrderableList extends WebPage
 * {
 * 
 * 	static final List objList = new ArrayList();
 * 
 * 
 * 	public OrderableList()
 * 	{
 * 		super();
 * 		if (objList.size() == 0)
 * 		{
 * 			objList.add(&quot;foo1&quot;);
 * 			objList.add(&quot;bar1&quot;);
 * 			objList.add(&quot;foo2&quot;);
 * 			objList.add(&quot;bar2&quot;);
 * 			objList.add(&quot;foo3&quot;);
 * 			objList.add(&quot;bar3&quot;);
 * 			objList.add(&quot;foo4&quot;);
 * 			objList.add(&quot;bar4&quot;);
 * 			objList.add(&quot;foo5&quot;);
 * 			objList.add(&quot;bar5&quot;);
 * 			objList.add(&quot;foo6&quot;);
 * 			objList.add(&quot;bar6&quot;);
 * 		}
 * 		DojoOrderableListViewContainer container = new DojoOrderableListViewContainer(this,
 * 				&quot;container&quot;);
 * 		DojoOrderableListView list = new DojoOrderableListView(container, &quot;list&quot;, objList)
 * 		{
 * 
 * 			protected void populateItem(ListItem item)
 * 			{
 * 				item.add(new Label(&quot;label&quot;, (String)item.getModelObject()));
 * 				item.add(new DojoOrderableListRemover(&quot;remover&quot;, item));
 * 
 * 			}
 * 
 * 		};
 * 	}
 * 
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author Vincent Demay
 */
public class DojoOrderableListRemover extends AjaxLink
{

	// item to remove
	private ListItem item;

	/**
	 * Construct
	 * 
	 * @param parent
	 * @param id
	 *            id
	 * @param item
	 *            ListItem to be remove if clicking
	 */
	public DojoOrderableListRemover(MarkupContainer parent, String id, ListItem item)
	{
		super(parent, id);
		this.item = item;
	}

	protected void onBeforeRender()
	{
		super.onBeforeRender();
		check();
	}

	public final void onClick(AjaxRequestTarget target)
	{
		if (beforeRemoving())
		{
			DojoOrderableListView parent = ((DojoOrderableListView)this.item.getParent());
			DojoOrderableListViewContainer granParent = (DojoOrderableListViewContainer)parent
					.getParent();
			parent.getList().remove(parent.getList().indexOf(this.item.getModelObject()));
			parent.modelChanged();

			target.addComponent(granParent);
			onRemove(target);
		}
		else
		{
			onNotRemove(target);
		}
	}

	private void check()
	{
		if (!(item.getParent() instanceof DojoOrderableListView))
		{
			throw new WicketRuntimeException("Parent of item should be a DojoOrderableListView");
		}
		if (!(item.getParent().getParent() instanceof DojoOrderableListViewContainer))
		{
			throw new WicketRuntimeException(
					"GranParent of item should be a DojoOrderableListViewContainer");
		}
	}

	/**
	 * Triggered if remove succes
	 * 
	 * @param target
	 *            target
	 */
	protected void onRemove(AjaxRequestTarget target)
	{

	}

	/**
	 * Triggered if remove failed
	 * 
	 * @param target
	 *            target
	 */
	private void onNotRemove(AjaxRequestTarget target)
	{

	}

	/**
	 * This method is called before remove the element for the list if returned
	 * value is true remove will be done and onRemove will be execute, otherwise
	 * remove will not be done and onNotRemove will be called
	 * 
	 * @return true to remove the item, false otherwise
	 */
	protected boolean beforeRemoving()
	{
		return true;
	}


}