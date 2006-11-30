/*
 * $Id$
 * $Revision$
 * $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.markup.html.navmenu;

import java.util.List;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.markup.html.WebComponent;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.markup.html.resources.StyleSheetReference;
import wicket.model.Model;

/**
 * One row of a menu. Starts by 0 (zero).
 * 
 * @author Eelco Hillenius
 */
public class MenuRow extends Panel<List<MenuItem>>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Listview for a menu row.
	 */
	private final class RowListView extends ListView<MenuItem>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param id
		 *            The id
		 * @param model
		 *            The model
		 */
		public RowListView(MarkupContainer parent, String id, MenuRowModel model)
		{
			super(parent, id, model);
			setReuseItems(true);
		}

		/**
		 * @see wicket.markup.html.list.ListView#populateItem(wicket.markup.html.list.ListItem)
		 */
		@Override
		protected void populateItem(ListItem<MenuItem> item)
		{
			final MenuItem menuItem = item.getModelObject();

			final Panel itemPanel = menuItem.newItemPanel(item, "itemPanel", MenuRow.this);
			if (itemPanel == null)
			{
				throw new WicketRuntimeException("item panel must be not-null");
			}
			if (!"itemPanel".equals(itemPanel.getId()))
			{
				throw new WicketRuntimeException("item panel must have id 'itemPanel' assigned");
			}
		}
	}

	/** this row's style. */
	private final MenuRowStyle style;

	/**
	 * Construct using a default style.
	 * 
	 * @param parent
	 *            The parent
	 * @param id
	 *            component id
	 * @param model
	 *            row model
	 * @see MenuRowStyle
	 */
	public MenuRow(MarkupContainer parent, final String id, final MenuRowModel model)
	{
		this(parent, id, model, new MenuRowStyle());
	}

	/**
	 * Construct using the provided row style.
	 * 
	 * @param parent
	 *            The parent
	 * @param id
	 *            component id
	 * @param model
	 *            row model
	 * @param style
	 *            row style
	 */
	public MenuRow(MarkupContainer parent, final String id, final MenuRowModel model,
			final MenuRowStyle style)
	{
		super(parent, id, model);

		if (model == null)
		{
			throw new NullPointerException("argument model may not be null");
		}
		if (style == null)
		{
			throw new NullPointerException("argument style may not be null");
		}

		this.style = style;
		WebMarkupContainer div = new WebMarkupContainer(this, "div");
		div.add(new AttributeModifier("class", true, new Model<String>())
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String getObject(Component component)
			{
				return style.getContainerCSSClass();
			}
		});

		WebMarkupContainer ul = new WebMarkupContainer(div, "ul");
		ul.add(new AttributeModifier("class", true, new Model<String>()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return style.getRowCSSClass();
			}
		}));
		new RowListView(ul, "columns", model);

		ResourceReference styleSheetResource = style.getStyleSheetResource();
		if (styleSheetResource != null)
		{
			new StyleSheetReference(this, "cssStyleResource", styleSheetResource);
		}
		else
		{
			new WebComponent(this, "cssStyleResource").setVisible(false);
		}
	}

	/**
	 * @see wicket.Component#isVersioned()
	 */
	@Override
	public final boolean isVersioned()
	{
		return false;
	}

	/**
	 * Gets the row style.
	 * 
	 * @return row style
	 */
	public final MenuRowStyle getRowStyle()
	{
		return style;
	}
}
