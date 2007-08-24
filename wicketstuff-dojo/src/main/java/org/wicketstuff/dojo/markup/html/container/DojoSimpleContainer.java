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
package org.wicketstuff.dojo.markup.html.container;

import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.widgetloadingpolicy.IDojoWidgetLoadingPolicy;
import org.apache.wicket.markup.ComponentTag;

/**
 * A simple Dojo container. Used to take place in an other container
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoSimpleContainer extends AbstractDojoContainer
{
	/**
	 * Construct a Dojo container
	 * @param id container id
	 * @param title container title
	 */
	public DojoSimpleContainer(String id, String title)
	{
		super(id, title);
		add(new DojoSimpleContainerHandler());
	}

	/**
	 * Construct a Dojo container
	 * @param id container id
	 */
	public DojoSimpleContainer(String id)
	{
		this(id, null);
	}

	/**
	 * Construct a Dojo container.
	 * @param id container id
	 * @param title container title
	 * @param policy loading policy
	 */
	public DojoSimpleContainer(String id, String title, IDojoWidgetLoadingPolicy policy)
	{
		super(id, title);
		add(new DojoSimpleContainerHandler(policy));
	}
	

	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_CONTENTPANE;
	}
	
	/**
	 * add attributes on component tag
	 * @param tag 
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("label", getTitle());
	}


}
