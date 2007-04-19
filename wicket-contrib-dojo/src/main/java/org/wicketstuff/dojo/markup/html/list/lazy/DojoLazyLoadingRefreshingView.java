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
package org.wicketstuff.dojo.markup.html.list.lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 * <b>UNDER DEVELOPMENT</b>
 * @author Vincent Demay
 *
 */
public abstract class DojoLazyLoadingRefreshingView extends RefreshingView
{
	private int first = 0;
	private int count = 30;
	
	private DojoLazyLoadingListContainer parent;
	IDataProvider dataProvider;

	public DojoLazyLoadingRefreshingView(String id, IDataProvider dataProvider) {
		super(id);
		this.dataProvider = dataProvider;
		setOutputMarkupId(true);
	}


	public DojoLazyLoadingRefreshingView(String id) {
		super(id);
		setOutputMarkupId(true);
	}

/*

	public abstract Iterator iterator(int first, int count);
	
	
	public IModel model(Object object){
		if (!(object instanceof Serializable)){
			throw new IllegalArgumentException("The model Object should be Serializable in a DojoLazyLoadingListContainer");
		}
		return new Model((Serializable)object);
	}

	public void detach(){}
*/

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

	protected final Iterator getItemModels(){
		
		final List toReturn = new ArrayList();
		
		Iterator it = dataProvider.iterator(first, count);
		while (it.hasNext())
		{
			toReturn.add(dataProvider.model(it.next()));
		}

		return toReturn.iterator();
	}
}
