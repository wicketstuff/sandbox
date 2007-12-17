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
package org.wicketstuff.dojo.markup.html.container.accordion;

import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.markup.html.container.AbstractDojoChangeContainer;
import org.apache.wicket.markup.ComponentTag;

/**
 * <p>
 * 	An accordion container
 * </p>
 * <p>
 * <b>Sample</b>
 * <pre>
 * package org.wicketstuff.dojo.examples;
 * 
 * import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
 * import org.wicketstuff.dojo.markup.html.container.accordion.DojoAccordionContainer;
 * import org.wicketstuff.dojo.markup.html.container.page.DojoPageContainer;
 * import org.apache.wicket.markup.html.WebPage;
 * 
 * public class AccordionContainerSample extends WebPage { 
 * 
 * 	public AccordionContainerSample() {
 * 		super();
 * 		DojoAccordionContainer container = new DojoAccordionContainer("tabContainer");
 * 		container.setHeight("500px");
 * 		container.add(new DojoSimpleContainer("tab1", "title1"));
 * 		container.add(new DojoSimpleContainer("tab2", "title2"));
 * 		container.add(new DojoPageContainer("tab3", "an other page", SliderSample.class));
 * 		this.add(container);
 * 	}
 * 
 * }
 * </pre>
 * </p>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoAccordionContainer extends AbstractDojoChangeContainer
{
	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 * @param title container title
	 */
	public DojoAccordionContainer(String id, String title)
	{
		super(id, title);
		add(new DojoAccordionContainerHandler());
	}
	
	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoAccordionContainer(String id)
	{
		this(id, null);
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_ACCORDIONCONTAINER;
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("selectedChild", getSelectedChildId());
		tag.put("label", getTitle());
	} 

}
