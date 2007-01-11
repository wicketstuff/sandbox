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
package wicket.contrib.dojo.markup.html.floatingpane;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_FLOATINGPANE;
import wicket.MarkupContainer;
import wicket.markup.ComponentTag;

/**
 * <p>
 * A floatingPane is not modal. You can add a lot of them ina page
 * </p>
 * <p>
 * <b>Sample</b>
 * 	<pre>
 * public class FloatingPaneShower extends WebPage {
 *	
 *	public FloatingPaneShower(PageParameters parameters){
 *		DojoFloatingPane pane1 = new DojoFloatingPane(this, "pane1");
 *		DojoFloatingPane pane2 = new DojoFloatingPane(pane1, "pane2");
 *		DojoFloatingPane pane3 = new DojoFloatingPane(this, "pane3");
 *		
 *		
 *		pane3.setHasShadow(true);
 *		pane3.setHeight("300px");
 *		pane3.setWidth("300px");
 *		
 *		pane1.setDisplayCloseAction(false);
 *		pane1.setHeight("300px");
 *		pane1.setWidth("300px");
 *		pane2.setTitle("a title here");
 *	}
 *}
 *
 * </pre>
 * </p>
 * @author vdemay
 *
 */
public class DojoFloatingPane extends DojoAbstractFloatingPane
{

	private boolean constrainToContainer;
	
	/**
	 * @param parent
	 * @param id
	 */
	public DojoFloatingPane(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoFloatingPaneHandler());
		constrainToContainer = true;
	}
	
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_FLOATINGPANE);
	}
}
