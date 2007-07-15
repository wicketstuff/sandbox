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
package org.apache.wicket.security.examples.tabs.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.examples.tabs.components.tabs.SecureTab;
import org.apache.wicket.security.examples.tabs.panels.Gifkikker;
import org.apache.wicket.security.examples.tabs.panels.Grolsch;
import org.apache.wicket.security.examples.tabs.panels.Heineken;


/**
 * The home page, this one is secured with a login. Dummy page really
 * 
 * @author marrink
 * 
 */
public class MasterPage extends SecurePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MasterPage()
	{
		// add(new ButtonContainer("buttoncontainer",
		// ButtonContainer.BUTTON_OVERVIEW));
		List tabs = new ArrayList();
		tabs.add(new SecureTab(new ITab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				return new Gifkikker(panelId);
			}

			public IModel getTitle()
			{
				return new Model("Gifkikker");
			}
		}));
		tabs.add(new SecureTab(new ITab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				return new Heineken(panelId);
			}

			public IModel getTitle()
			{
				return new Model("Heineken");
			}
		}));
		tabs.add(new SecureTab(new ITab()
		{
			private static final long serialVersionUID = 1L;

			public Panel getPanel(String panelId)
			{
				return new Grolsch(panelId);
			}

			public IModel getTitle()
			{
				return new Model("Grolsch");
			}
		}));
		TabbedPanel tabPanel = new TabbedPanel("tabs", tabs);
		add(tabPanel);
	}
}
