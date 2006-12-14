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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.extensions.markup.html.repeater.data.IDataProvider;
import wicket.extensions.markup.html.repeater.refreshing.RefreshingView;
import wicket.extensions.markup.html.repeater.refreshing.ReuseIfModelsEqualStrategy;
import wicket.model.IModel;
import wicket.model.Model;

public abstract class DojoLazyLoadingRefreshingView extends RefreshingView implements IDataProvider
{
	private int first = 0;
	private int count = 30;
	
	private DojoLazyLoadingListContainer parent;
	
	public DojoLazyLoadingRefreshingView(DojoLazyLoadingListContainer parent, String id, IModel model) {
		super(parent, id, model);
		this.parent = parent;
	}


	public DojoLazyLoadingRefreshingView(DojoLazyLoadingListContainer parent, String id) {
		super(parent, id);
		this.parent = parent;
	}


	/**
	 * @param first 
	 * @param count 
	 * @return 
	 * 
	 */
	public abstract Iterator iterator(int first, int count);
	
	
	public IModel model(Object object){
		return new Model(object);
	}

	public void detach(){}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getFirst()
	{
		return first;
	}

	public void setFirst(int first)
	{
		this.first = first;
	}

	@Override
	protected final Iterator getItemModels(){
		
		final List toReturn = new ArrayList();
		
		Iterator it = iterator(first, count);
		while (it.hasNext())
		{
			toReturn.add(model(it.next()));
		}

		return toReturn.iterator();
	}
}
