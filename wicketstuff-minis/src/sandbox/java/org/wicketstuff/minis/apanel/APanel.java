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
package org.wicketstuff.minis.apanel;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringBufferResourceStream;

/**
 * Panel that generates markup for added components, so that it doesn't need to
 * have a markup file.
 */
public class APanel extends Panel implements IMarkupResourceStreamProvider
{
	private static final long serialVersionUID = 1L;
	private final APanelRenderer thisPanelRenderer;

	/**
	 * Constructor.
	 *
	 * @param id id
	 */
	public APanel(final String id)
	{
		this(id, new FlowLayout());
	}

	/**
	 * Constructor.
	 *
	 * @param id	 id
	 * @param layout layout to use for components in this panel.
	 */
	public APanel(final String id, final ILayout layout)
	{
		super(id);
		thisPanelRenderer = new APanelRenderer(layout);
	}


	/**
	 * @see org.apache.wicket.markup.IMarkupResourceStreamProvider#getMarkupResourceStream(org.apache.wicket.MarkupContainer,
	 *java.lang.Class)
	 */
	public IResourceStream getMarkupResourceStream(final MarkupContainer container,
												   final Class containerClass)
	{
		if (container != this)
		{
			throw new IllegalArgumentException("Conatainer " + container + " must be instance "
					+ this);
		}

		final StringBufferResourceStream resourceStream = new StringBufferResourceStream();
		resourceStream.append(thisPanelRenderer.getMarkup((APanel) container));
		return resourceStream;
	}

	private static final class APanelRenderer
			extends
			RenderersList.BaseWebMarkupContainerRenderer<APanel>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * @param layout
		 */
		public APanelRenderer(final ILayout layout)
		{
			super(layout);
		}

		/**
		 * @param component
		 * @return markup
		 * @see org.wicketstuff.minis.apanel.IComponentRenderer#getMarkup(org.apache.wicket.Component)
		 */
		public CharSequence getMarkup(final APanel component)
		{
			return String.format("<wicket:panel>%s</wicket:panel>", getBodyMarkup(component));
		}

		/**
		 * @see org.wicketstuff.minis.apanel.IComponentRenderer#getComponentClass()
		 */
		public Class<? extends APanel> getComponentClass()
		{
			return APanel.class;
		}
	}
}
