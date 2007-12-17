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
package org.wicketstuff.dojo.indicator;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.wicketstuff.dojo.indicator.behavior.DojoIndicatorBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;

/**
 * A Dojo Request indicator showing an Image indicator when a request is in the flight
 * This Component has to be used in a {@link DojoIndicatorBehavior}
 * and it should be added on the page such as an {@link Image} component
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoImageIndicator extends Image implements IDojoIndicator
{

	/**
	 * Construct a Image indicator
	 * @param parent parent where the image will be added
	 * @param id wicket id of the Image
	 */
	public DojoImageIndicator(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
	}

	/**
	 * Decorator used to execute js on Dojo Request
	 * @return an {@link IAjaxCallDecorator} or null if nothing to do
	 */
	public IAjaxCallDecorator getDojoCallDecorator()
	{
		return null;
	}

	/**
	 * return the markupId of the image when a {@link DojoImageIndicator} is used
	 * @return the markup id of the image if a {@link DojoImageIndicator} is used
	 */
	public String getDojoIndicatorMarkupId()
	{
		return this.getMarkupId();
	}

	protected ResourceReference getImageResourceReference()
	{
		return new ResourceReference(this.getClass(), "dojoIndicator.gif");
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("style", "display:none");
	}

}
