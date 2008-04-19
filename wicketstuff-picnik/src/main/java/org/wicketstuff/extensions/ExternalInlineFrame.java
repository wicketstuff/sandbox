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
package org.wicketstuff.extensions;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

/**
 * Implementation of an <a href="http://www.w3.org/TR/REC-html40/present/frames.html#h-16.5">inline
 * frame</a> component. Must be used with an iframe (&lt;iframe src...) element. The src attribute
 * will be filled with the model's string represantation. This component can be used to integrate
 * external sites via an inline frame.
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class ExternalInlineFrame extends WebMarkupContainer
{
	/**
	 * @see Component#Component(String)
	 */
	public ExternalInlineFrame(final String id)
	{
		super(id);
	}
	
	/**
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public ExternalInlineFrame(final String id, final IModel model)
	{
		super(id, model);
	}

	/**
	 * Handles this frame's tag.
	 * 
	 * @param tag
	 *            the component tag
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected final void onComponentTag(final ComponentTag tag)
	{
		checkComponentTag(tag, "iframe");

		// Set href to link to this frame's frameRequested method
		CharSequence url = getModelObjectAsString();

		// generate the src attribute
//		tag.put("src", Strings.replaceAll(url, "&", "&amp;"));
		tag.put("src", url);

		super.onComponentTag(tag);
	}
}
