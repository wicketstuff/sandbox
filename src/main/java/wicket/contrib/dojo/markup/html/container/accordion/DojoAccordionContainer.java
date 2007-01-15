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
package wicket.contrib.dojo.markup.html.container.accordion;

import wicket.MarkupContainer;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.markup.html.container.AbstractDojoContainer;
import wicket.markup.ComponentTag;

/**
 * <p>
 * 	An accordion container
 * </p>
 * <p>
 * <b>Sample</b>
 * <pre>
 * package wicket.contrib.dojo.examples;
 * 
 * import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
 * import wicket.contrib.dojo.markup.html.container.accordion.DojoAccordionContainer;
 * import wicket.contrib.dojo.markup.html.container.page.DojoPageContainer;
 * import wicket.markup.html.WebPage;
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
public class DojoAccordionContainer extends AbstractDojoContainer
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
		add(new DojoAccordionHandler());
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

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_ACCORDIONCONTAINER);
		tag.put("label", getTitle());
	}

	/**
	 * Triggered when change selection
	 *
	 */
	public void onChange()
	{
		
	}
}
