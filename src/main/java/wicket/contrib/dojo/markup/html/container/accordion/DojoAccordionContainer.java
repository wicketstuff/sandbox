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

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_ACCORDIONCONTAINER;
import wicket.MarkupContainer;
import wicket.contrib.dojo.markup.html.container.AbstractDojoContainer;
import wicket.markup.ComponentTag;

/**
 * <p>
 * 	An accordion container
 * </p>
 * <p>
 * <b>Sample</b>
 * <pre>
 * public class AccordionContainerSample extends WebPage {
 *
 *	public AccordionContainerSample() {
 *		super();
 *		DojoAccordionContainer container = new DojoAccordionContainer(this,"tabContainer");
 *		container.setHeight("500px");
 *		new DojoSimpleContainer(container, "tab1", "title1");
 *		new DojoSimpleContainer(container, "tab2", "title2");
 *		new DojoPageContainer(container, "tab3", DatePickerShower.class).setTitle("title3");
 *		
 *	}
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
	public DojoAccordionContainer(MarkupContainer parent, String id, String title)
	{
		super(parent, id, title);
		add(new DojoAccordionHandler());
	}

	/**
	 * Construct a DojoAccordionContainer
	 * @param parent parent where the container will be added
	 * @param id container id
	 */
	public DojoAccordionContainer(MarkupContainer parent, String id)
	{
		this(parent, id, null);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_ACCORDIONCONTAINER);
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
