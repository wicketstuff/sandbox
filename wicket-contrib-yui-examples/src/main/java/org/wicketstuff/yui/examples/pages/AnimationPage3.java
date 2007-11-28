package org.wicketstuff.yui.examples.pages;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnail;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailSettings;
import org.wicketstuff.yui.markup.html.ellipse.EllipsePanel;

/**
 * Example on using Animation...
 * 
 * @author josh
 *
 */
public class AnimationPage3 extends WicketExamplePage 
{
	private static int PIC_WIDTH = 300;
	private static int PIC_HEIGHT = 200;
	
	private static int PANEL_WIDTH = 600;
	private static int PANEL_HEIGHT = 400;
	
	private static int ITEM_WIDTH = 100;
	private static int ITEM_HEIGHT = 70;
	
	private static int PIC_LEFT = (PANEL_WIDTH - PIC_WIDTH) / 2;
	private static int PIC_TOP = (PANEL_HEIGHT - PIC_HEIGHT) / 2;
	
	public AnimationPage3()
	{
		EllipsePanel ellipsePanel;
		add(ellipsePanel = new EllipsePanel("ep1", new ThumbnailProvider())
		{
			@Override
			public Component newEllipseItem(String id, Object object)
			{
				AnimatedThumbnailSettings settings = (AnimatedThumbnailSettings)object;
				settings.setThumbnailDimension(getItemWidth(), getItemHeight());
				settings.setPictureDimension(PIC_WIDTH, PIC_HEIGHT);
				settings.setPicturePosition(PIC_LEFT, PIC_TOP);
				return new AnimatedThumbnail(id, settings);
			}
		});
		ellipsePanel.setDimension(PANEL_WIDTH, PANEL_HEIGHT);
		ellipsePanel.setItemDimension(ITEM_WIDTH, ITEM_HEIGHT);
	}
	
	/**
	 * a Provider of thumbnails
	 * @author josh
	 *
	 */
	private class ThumbnailProvider implements IDataProvider
	{
		List<AnimatedThumbnailSettings> listOfThumbnails = new ArrayList<AnimatedThumbnailSettings>();
		
		public ThumbnailProvider()
		{
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/singapore.png", 	"images/sgp.jpg", 		"Singapore"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/malaysia.png", 	"images/kl.jpg", 		"Malaysia"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/indonesia.png", 	"images/jakarta.jpg", 	"Indonesia"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/philippines.png","images/makati.jpg", 	"Philippines"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/thailand.png", 	"images/bangkok.jpg", 	"Thailand"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/hongkong.png", 	"images/hk.jpg", 		"Hong Kong"));			
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
