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
package org.wicketstuff.table.examples;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

/**
 * Homepage
 */
public class HomePage extends WebPage
{
	private Component testPanel;
	private static final String TEST_PANEL_ID = "testPanel";

	public HomePage()
	{
		add(CSSPackageResource.getHeaderContribution(HomePage.class, "style.css"));
		add(new TestLink("tableWithAjaxColumn", AjaxColumnPanel.class));
		add(new TestLink("numberTablePanel", NumberTablePanel.class));
		add(testPanel = new AjaxColumnPanel(TEST_PANEL_ID));
	}

	private class TestLink extends Link
	{
		private static final long serialVersionUID = 1L;
		private Class panelClass;

		public TestLink(String id, Class panelClass)
		{
			super(id);
			this.panelClass = panelClass;
		}


		@Override
		public void onClick()
		{
			try
			{
				Component newPanel = (Component)panelClass.getConstructor(String.class)
						.newInstance(TEST_PANEL_ID);
				testPanel.replaceWith(newPanel);
				testPanel = newPanel;
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean isEnabled()
		{
			return !testPanel.getClass().equals(panelClass);
		}
	}
}
