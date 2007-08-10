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
package org.wicketstuff.dojo.markup.html.floatingpane;

import org.apache.wicket.markup.ComponentTag;
import org.wicketstuff.dojo.DojoIdConstants;

/**
 * <p>
 * A floatingPane is not modal. You can add a lot of them ina page
 * </p>
 * <p>
 * <b>Sample</b>
 * 	<pre>
 * package org.wicketstuff.dojo.examples;
 * 
 * import org.apache.wicket.PageParameters;
 * import org.wicketstuff.dojo.markup.html.floatingpane.DojoFloatingPane;
 * import org.apache.wicket.markup.html.WebPage;
 * 
 * public class FloatingPaneShower extends WebPage {
 * 	
 * 	public FloatingPaneShower(PageParameters parameters){
 * 		DojoFloatingPane pane1 = new DojoFloatingPane( "pane1");
 * 		add(pane1);
 * 		DojoFloatingPane pane2 = new DojoFloatingPane("pane2");
 * 		pane1.add(pane2);
 * 		DojoFloatingPane pane3 = new DojoFloatingPane("pane3");
 * 		add(pane3);
 * 		
 * 		
 * 		pane3.setHasShadow(true);
 * 		pane3.setHeight("300px");
 * 		pane3.setWidth("300px");
 * 		
 * 		pane1.setDisplayCloseAction(false);
 * 		pane1.setHeight("300px");
 * 		pane1.setWidth("300px");
 * 		pane2.setTitle("a title here");
 * 	}
 * }
 * </pre>
 * </p>
 * @author vdemay
 *
 */
@SuppressWarnings("serial")
public class DojoFloatingPane extends DojoAbstractFloatingPane
{

	
	/**
	 * @param id
	 */
	public DojoFloatingPane(String id)
	{
		super(id);
		add(new DojoFloatingPaneHandler());
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_FLOATINGPANE;
	}
}
