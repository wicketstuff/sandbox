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
package wicket.contrib.dojo.html.list;

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.markup.html.list.ListItem;

public class DojoOrderableListRemover extends AjaxLink{

	// item to remove
	private ListItem item;
	
	public DojoOrderableListRemover(MarkupContainer parent, String id, ListItem item)
	{
		super(parent, id);
		this.item = item; 
	}
	
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		check();
	}

	@Override
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

	protected void onRemove(AjaxRequestTarget target){
		
	}
	
	private void onNotRemove(AjaxRequestTarget target)
	{
		
	}
	
	protected boolean beforeRemoving(){
		return true;
	}
	


	
	
}