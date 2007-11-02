package org.wicketstuff.yui.markup.html.thumbnail;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * this is a Group of Animated Thumbnails. 
 * 
 * @author josh
 *
 */
public class AnimatedThumbnailGallery extends Panel
{
	private static final long serialVersionUID = 1L;
	
	private IDataProvider dataProvider;

	private int thumbnailWidth;

	private int thumbnailHeight;

	private int pictureWidth;

	private int pictureHeight;

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param listOfThumbnails
	 * @param cols 
	 * @param rows 
	 */
	@SuppressWarnings("serial")
	public AnimatedThumbnailGallery(String id, List<AnimatedThumbnailSettings> listOfThumbnails, int cols, int rows)
	{
		super(id);
		dataProvider = new ThumbnailProvider(listOfThumbnails);
		init(dataProvider, cols, rows);
	}

	/**
	 * 
	 * @param id
	 * @param provider
	 */
	public AnimatedThumbnailGallery(String id, IDataProvider provider, int cols, int rows)
	{
		super(id);
		dataProvider = provider;
		init(provider, cols, rows);
	}
	
	/**
	 * 
	 * @param provider
	 * @param rows
	 * @param cols
	 */
	@SuppressWarnings("serial")
	private void init(IDataProvider provider, int cols, int rows)
	{
		add(HeaderContributor.forCss(AnimatedThumbnailGallery.class, "AnimatedThumbnailGallery.css"));
		GridView view;
		add(view = new GridView("rows", dataProvider)
		{
			@Override
			protected void populateEmptyItem(Item item)
			{
				item.add(new Label("animatedThumbnail", "--"));
			}

			@Override
			protected void populateItem(Item item)
			{
				AnimatedThumbnailSettings settings = (AnimatedThumbnailSettings)item.getModelObject();
				settings.setThumbnailWidth(thumbnailWidth);
				settings.setThumbnailHeight(thumbnailHeight);
				settings.setPictureWidth(pictureWidth);
				settings.setPictureHeight(pictureHeight);
				item.add(new AnimatedThumbnail("animatedThumbnail", settings));
			}
		});
		view.setColumns(cols);
		view.setRows(rows);
	}

	public void setThumbnailSize(int tn_width, int tn_height)
	{
		this.thumbnailWidth = tn_width;
		this.thumbnailHeight = tn_height;
	}

	public void setPictureSize(int width, int height)
	{
		this.pictureWidth = width;
		this.pictureHeight = height;
	}

	/**
	 * @author josh
	 *
	 */
	@SuppressWarnings("serial")
	private class ThumbnailProvider implements IDataProvider
	{
		List<AnimatedThumbnailSettings> listOfThumbnails;
		
		public ThumbnailProvider(List<AnimatedThumbnailSettings> listOfThumbnails)
		{
			this.listOfThumbnails = listOfThumbnails;
		}

		@SuppressWarnings("unchecked")
		public Iterator iterator(int first, int count)
		{
			return this.listOfThumbnails.iterator();
		}

		public IModel model(Object object)
		{
			return new Model((AnimatedThumbnailSettings) object);
		}

		public int size()
		{
			return this.listOfThumbnails.size();
		}

		public void detach()
		{
		}
	}
}
