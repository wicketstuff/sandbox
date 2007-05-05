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

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.Serializable;

/**
 * Stores list of renderers.
 */
class RenderersList implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final List<IComponentRenderer<?>> DEFAULT_RENDERERS = new ArrayList<IComponentRenderer<?>>();

	static
	{
		DEFAULT_RENDERERS.add(new LabelRenderer());
		DEFAULT_RENDERERS.add(new LinkRenderer());
		DEFAULT_RENDERERS.add(new ListViewRenderer());
		DEFAULT_RENDERERS.add(new FormRenderer());
		DEFAULT_RENDERERS.add(new ButtonRenderer());
		DEFAULT_RENDERERS.add(new DefaultWebMarkupContainerRenderer());
		DEFAULT_RENDERERS.add(new DefaultRenderer());
	}

	public static List<IComponentRenderer<?>> getDefaultRenderers()
	{
		return Collections.unmodifiableList(DEFAULT_RENDERERS);
	}

	private final List<IComponentRenderer<?>> renderers;

	public RenderersList(final List<IComponentRenderer<?>> renderers)
	{
		this.renderers = renderers;
	}

	@SuppressWarnings({"unchecked"})
			<T extends Component> IComponentRenderer<T> findRendererForClass(final Class<? extends T> aClass)
	{
		for (IComponentRenderer componentRenderer : renderers)
		{
			if (componentRenderer.getComponentClass().isAssignableFrom(aClass))
			{
				return componentRenderer;
			}
		}
		throw new WicketRuntimeException("Can't find renderer for class " + aClass);
	}

	// -------------- renderes --------------

	public static abstract class BaseRenderer<T extends Component> implements IComponentRenderer<T>
	{
		protected String getIdAttribute(final Component component)
		{
			return "wicket:id=\"" + component.getId() + "\"";
		}
	}

	public static final class DefaultRenderer extends BaseRenderer<Component>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final Component component)
		{
			return String.format("<span %s></span>", getIdAttribute(component));
		}

		public Class<Component> getComponentClass()
		{
			return Component.class;
		}
	}

	public static final class LabelRenderer extends BaseRenderer<Label>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final Label component)
		{
			return String.format("<span %s></span>", getIdAttribute(component));
		}

		public Class<Label> getComponentClass()
		{
			return Label.class;
		}
	}

	public static abstract class BaseWebMarkupContainerRenderer<T extends WebMarkupContainer> extends BaseRenderer<T>
	{
		private final ILayout layout;

		protected BaseWebMarkupContainerRenderer()
		{
			this.layout = new FlowLayout();
		}

		protected BaseWebMarkupContainerRenderer(final ILayout layout)
		{
			this.layout = layout;
		}

		public CharSequence getBodyMarkup(final WebMarkupContainer container)
		{
			final List<Component> componentsToRender = new ArrayList<Component>();

			container.visitChildren(new Component.IVisitor()
			{
				public Object component(final Component component)
				{
					componentsToRender.add(component);
					return Component.IVisitor.CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
				}
			});

			return layout.renderComponents(componentsToRender);
		}
	}

	public static final class DefaultWebMarkupContainerRenderer extends BaseWebMarkupContainerRenderer<WebMarkupContainer>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final WebMarkupContainer component)
		{
			return String.format(
					"<span>%s</span>",
					getBodyMarkup(component)
			);
		}

		public Class<? extends WebMarkupContainer> getComponentClass()
		{
			return WebMarkupContainer.class;
		}
	}

	public static final class ListViewRenderer extends BaseWebMarkupContainerRenderer<ListView>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final ListView component)
		{
			return String.format(
					"<span %s>%s</span>",
					getIdAttribute(component),
					getListViewBodyMarkup(component)
			);
		}

		private CharSequence getListViewBodyMarkup(final ListView listView)
		{
			if (listView.getList().isEmpty()) return "";
			final ListItem listItem = (ListItem) listView.get("0");
			return getBodyMarkup(listItem);
		}

		public Class<? extends ListView> getComponentClass()
		{
			return ListView.class;
		}
	}

	public static final class LinkRenderer extends BaseWebMarkupContainerRenderer<Link>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final Link component)
		{
			return String.format(
					"<a %s>%s</a>",
					getIdAttribute(component),
					getBodyMarkup(component)
			);
		}

		public Class<? extends Link> getComponentClass()
		{
			return Link.class;
		}
	}

	public static final class FormRenderer extends BaseWebMarkupContainerRenderer<Form>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final Form component)
		{
			return String.format(
					"<form %s>%s</form>",
					getIdAttribute(component),
					getBodyMarkup(component)
			);
		}

		public Class<? extends Form> getComponentClass()
		{
			return Form.class;
		}
	}

	public static final class ButtonRenderer extends BaseRenderer<Button>
	{
		private static final long serialVersionUID = 1L;

		public CharSequence getMarkup(final Button component)
		{
			return String.format(
					"<input type=\"submit\" %s/>",
					getIdAttribute(component)
			);
		}

		public Class<? extends Button> getComponentClass()
		{
			return Button.class;
		}
	}
}
