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
package org.apache.wicket.quickstart;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.CrudPanel;
import wicketstuff.crud.ReflectionCreateBeanModelFactory;
import wicketstuff.crud.ReflectionPropertySource;


/**
 * Basic bookmarkable index page.
 * 
 * NOTE: You can get session properties from QuickStartSession via
 * getQuickStartSession()
 */
public class Index extends QuickStartPage
{
	private Date date;

	private Contact criteria = new Contact();

	private class SDP extends SortableDataProvider
	{
		public SDP()
		{
			setSort("firstName", true);
		}

		public Iterator iterator(int first, int count)
		{
			QueryParam qp = new QueryParam(first, count);
			SortParam sp = getSort();
			if (sp != null)
			{
				qp.setSort(sp.getProperty());
				qp.setSortAsc(sp.isAscending());
			}

			return new MemoryDatabase().find(qp, criteria);
		}

		public IModel model(Object object)
		{
			return new Model((Serializable)object);
		}

		public int size()
		{
			return new MemoryDatabase().count(criteria);
		}

	}

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public Index(final PageParameters parameters)
	{
		SDP dataProvider = new SDP();

		CrudPanel panel = new CrudPanel("crud", dataProvider)
		{

			@Override
			protected void onDelete(IModel model)
			{
				new MemoryDatabase().delete(((Contact)model.getObject()).getId());
			}

			@Override
			protected void onSave(IModel model)
			{
				new MemoryDatabase().save((Contact)model.getObject());
			}

		};
		panel.setFilterModel(new PropertyModel(this, "criteria"));
		panel.add(new ReflectionPropertySource(Contact.class));
		panel.setCreateBeanModelFactory(new ReflectionCreateBeanModelFactory(Contact.class));
		add(panel);


	}


}
