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
package org.apache.wicket.security.examples.customactions.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.examples.customactions.entities.Department;
import org.apache.wicket.security.examples.customactions.entities.Organisation;
import org.apache.wicket.security.examples.multilogin.components.navigation.ButtonContainer;
import org.apache.wicket.security.examples.pages.MySecurePage;

/**
 * Page for showing the departments in our organisation.
 * 
 * @author marrink
 */
public class DepartmentsPage extends MySecurePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public DepartmentsPage()
	{
		add(new ButtonContainer("buttoncontainer", ButtonContainer.BUTTON_OVERVIEW));
		add(new ListView("transactions", generateData())
		{
			private static final long serialVersionUID = 1L;
			protected void populateItem(ListItem item)
			{
				item.add(new Label("name"));
				item.add(new Label("description"));
				if(item.getIndex()%2==0)
					item.add(new SimpleAttributeModifier("class","outside halfhour"));
			}
			/**
			 * 
			 * @see org.apache.wicket.markup.html.list.ListView#getListItemModel(org.apache.wicket.model.IModel, int)
			 */
			protected IModel getListItemModel(IModel listViewModel, int index)
			{
				return new CompoundPropertyModel(super.getListItemModel(listViewModel, index));
			}
		});
	}
	/**
	 * Generate some random data
	 * @return
	 */
	private List generateData()
	{
		Organisation organisation=new Organisation();
		organisation.name="Bee Hive: Honey Production (inc)";
		String[] departments=new String[]{"Tracking","Tracks swarm movements","false",
				"H.I.E","Honey Industrial Espionage","true",
				"C.B.I.A","Counter Bee Interrogation Agency","true",
				"Honey Gathering","Gathers honey from all the swarms","false",
				"Storage","Stores all the honey","false"};
		int size=5;
		List data=new ArrayList(size);
		for(int i=0;i<size;i++)
		{
			data.add(new Department(organisation,departments[i*3],departments[(i*3)+1], Boolean.valueOf(departments[(i*3)+2]).booleanValue()));
		}
		return data;
	}
}
