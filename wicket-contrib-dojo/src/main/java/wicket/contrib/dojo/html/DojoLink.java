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
package wicket.contrib.dojo.html;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.markup.ComponentTag;
import wicket.model.IModel;

/**
 * A component that allows a trigger request to be triggered via html anchor tag
 * 
 * @param <T>
 *            The type
 * 
 * @author Vincent Demay
 * 
 */
public abstract class DojoLink<T> extends AjaxLink<T>
{

	/**
	 * Construct.
	 * 
	 * @param parent
	 *            The parent of this component
	 * 
	 * @param id
	 */
	public DojoLink(MarkupContainer parent, final String id)
	{
		this(parent, id, null);
	}

	/**
	 * Construct.
	 * 
	 * @param parent
	 *            The parent of this component
	 * 
	 * @param id
	 * @param model
	 */
	public DojoLink(MarkupContainer parent, final String id, final IModel<T> model)
	{
		super(parent, id, model);

		add(new AbstractDefaultDojoBehavior()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				// add the onclick handler only if link is enabled 
				if (isLinkEnabled())
				{
					tag.put("onclick", getCallbackScript());
				}
			}

			@Override
			protected void respond(AjaxRequestTarget target)
			{
				((DojoLink)getComponent()).onClick(target);
			}

		});
	}

}
