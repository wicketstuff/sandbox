package org.wicketstuff.yui.markup.html.ellipse;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.helper.CSSInlineStyle;

/**
 * a Panel that can layout it's child in an Ellipse. 
 * 
 * @author josh
 *
 */
public abstract class EllipsePanel extends Panel
{
	private static final long serialVersionUID = 1L;

	private int width;
	
	private int height;
	
	private int itemWidth;
	
	private int itemHeight;
	
	private EllipseHelper ellipseHelper;
	/**
	 * Constructor
	 * @param id
	 */
	@SuppressWarnings("serial")
	public EllipsePanel(String id, IDataProvider provider)
	{
		super(id);
		add(HeaderContributor.forCss(EllipsePanel.class, "EllipsePanel.css"));
		
		this.ellipseHelper = new EllipseHelper(provider.size());
	
		WebMarkupContainer panel;
		add(panel = new WebMarkupContainer("ellipse_panel"));
		panel.add(new AttributeAppender("style", true, new AbstractReadOnlyModel()
		{
			@Override
			public Object getObject()
			{
				CSSInlineStyle size = new CSSInlineStyle();
				size.add("width", getWidth());
				size.add("height", getHeight());
				return size;
			}
		}, ""));
		
		panel.add(new DataView("items", provider)
		{
			@Override
			protected void populateItem(Item item)
			{
				Component child;
				item.add(child = newEllipseItem("item", item.getModelObject()));
				item.setRenderBodyOnly(true);
				child.add(new AttributeAppender("style", true, new Model(getPosition(item.getIndex())), ""));
				child.add(new AttributeAppender("style", true, new Model(getDimention()), ""));
			}
		});
	}
	
	/**
	 * 
	 * @return
	 */
	private String getDimention()
	{
		CSSInlineStyle dimension = new CSSInlineStyle();
		dimension.add("width", getWidth());
		dimension.add("height", getHeight());
		return dimension.toString();
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getPosition(int index)
	{
		this.ellipseHelper.setWidth(getWidth() - getItemWidth());
		this.ellipseHelper.setHeight(getHeight() - getItemHeight());
		
		CSSInlineStyle position = new CSSInlineStyle();
		position.add("top", "0");
		position.add("left", "0");
		position.add("padding-top", this.ellipseHelper.getTop(index));
		position.add("padding-left", this.ellipseHelper.getLeft(index));
		return position.toString();
	}
	
	public abstract Component newEllipseItem(String id, Object modelObject);

	/**
	 * the dimension of this Panel
	 * @param width
	 * @param height
	 */
	public void setDimension(int width, int height)
	{
		setWidth(width);
		setHeight(height);
	}

	/**
	 * the dimension of the item within the Ellipse
	 * @param width
	 * @param height
	 */
	public void setItemDimension(int width, int height)
	{
		setItemWidth(width);
		setItemHeight(height);
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getItemWidth()
	{
		return itemWidth;
	}

	public void setItemWidth(int itemWidth)
	{
		this.itemWidth = itemWidth;
	}

	public int getItemHeight()
	{
		return itemHeight;
	}

	public void setItemHeight(int itemHeight)
	{
		this.itemHeight = itemHeight;
	}
}
