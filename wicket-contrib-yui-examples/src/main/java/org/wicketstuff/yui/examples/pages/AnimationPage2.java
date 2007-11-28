package org.wicketstuff.yui.examples.pages;
import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnail;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailGallery;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailSettings;

/**
 * Example on using Animation...
 * 
 * @author josh
 *
 */
public class AnimationPage2 extends WicketExamplePage 
{
	int cols = 3;
	int rows = 3;
	int tn_width = 120;
	int tn_height = 80;
	
	public AnimationPage2()
	{
		// a single AnimatedThumbanil 
		AnimatedThumbnailSettings settings = new AnimatedThumbnailSettings("images/neuschwanstein_tn.jpg", "images/neuschwanstein.jpg" , "Neuschwanstein Castle" );
		
		settings.setOpacity(0.7f);
		settings.setPictureDimension(800, 583);
		settings.setThumbnailDimension(100, 74);
		add(new AnimatedThumbnail("thumbnail", settings));
		
		// a gallery of AnimatedThumbnails
		List<AnimatedThumbnailSettings> listOfThumbnails = new ArrayList<AnimatedThumbnailSettings>();
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/singapore.png", 	"images/sgp.jpg", 		"Singapore"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/malaysia.png", 	"images/kl.jpg", 		"Malaysia"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/indonesia.png", 	"images/jakarta.jpg", 	"Indonesia"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/philippines.png","images/makati.jpg", 	"Philippines"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/thailand.png", 	"images/bangkok.jpg", 	"Thailand"));
		listOfThumbnails.add(new AnimatedThumbnailSettings("images/hongkong.png", 	"images/hk.jpg", 		"Hong Kong"));
		
		AnimatedThumbnailGallery gallery;
		add(gallery = new AnimatedThumbnailGallery("gallery", listOfThumbnails, cols, rows));
		gallery.setThumbnailSize(tn_width, tn_height);
		gallery.setPictureSize(tn_width * cols, tn_height * rows);
	}
}
