package org.wicketstuff.yui.markup.html.thumbnail;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.InlineStyle;
import org.wicketstuff.yui.behavior.Anim;
import org.wicketstuff.yui.behavior.AnimEffect;
import org.wicketstuff.yui.behavior.Animation;
import org.wicketstuff.yui.behavior.Attributes;
import org.wicketstuff.yui.behavior.Easing;
import org.wicketstuff.yui.behavior.OnEvent;

/**
 * This component shows a thumbnail image which will 
 * a) animate when the mouseover it with some caption
 * b) displays an actual picture when it is clicked.
 * 
 * @author josh
 *
 */
@SuppressWarnings("serial")
public class AnimatedThumbnail extends Panel 
{
	private static final long serialVersionUID = 1L;
		
	/**
	 * the settings that control this AnimatedThumbnail object.
	 */
	private AnimatedThumbnailSettings settings;

	private Component caption;
	
	private Component thumbnail;

	private Component pictureImage;

	/**
	 * 
	 * @param id
	 * @param settings
	 */
	public AnimatedThumbnail(String id, AnimatedThumbnailSettings settings)
	{
		super(id);
		add(HeaderContributor.forCss(AnimatedThumbnail.class, "AnimatedThumbnail.css"));
		this.settings = settings;
		
		// Thumbnail
		add(thumbnail = new URIImage("thumbnail", new Model(settings.getThumbnailURI())));
		thumbnail.add(new StyleAttributeModifier());		
		
		// Caption
		add(caption = new Label("caption", settings.getCaptionText()));
		caption.add(animationOnMouseOverThumbnail());
		caption.add(animationOnMouseOut());
		caption.add(new StyleAttributeModifier());
		
		// picture
		WebMarkupContainer picture = new WebMarkupContainer("picture");
		picture.add(pictureImage = new URIImage("picture_image", new Model(settings.getPictureURI())));
		picture.add(animationOnThumbnailClicked());
		picture.add(animationOnPictureClicked());
		add(picture);
	}

	/**
	 * the Animation when mouse over the thumbnail
	 * 
	 * @return
	 */
	private Animation animationOnMouseOverThumbnail()
	{
		Attributes attributes = new Attributes();
		attributes
		.add("zIndex", 			new Attributes("from", "1","to", "2"))
		.add("backgroundColor", new Attributes("from", "#FFFFFF", "to", "#CCCCCC"))
		.add("opacity", 		new Attributes("from", 0f, "to", getSettings().getOpacity()))
		.add("height",			new Attributes("to", getSettings().getThumbnailHeight()))
		.add("width", 			new Attributes("to", getSettings().getThumbnailWidth()));
		
		if (getSettings().getFontSize() > 0)
		{
			attributes.add("fontSize", new Attributes("from", 0, "to", getSettings().getFontSize()));
		}
		
		if (getSettings().getFontFamily() != null)
		{
			attributes.add("fontFamily", new Attributes("to", getSettings().getFontFamily()));
		}
		
		if (getSettings().getColor() != null)
		{
			attributes.add("color",  new Attributes("to", getSettings().getColor()));
		}
		
		return new Animation(OnEvent.mouseover)
		{
			@Override
			public String getTriggerId()
			{
				AnimatedThumbnail.this.thumbnail.setOutputMarkupId(true);
				return AnimatedThumbnail.this.thumbnail.getMarkupId();
			}
		}
		.add(new Anim(Anim.Type.ColorAnim, attributes, 0, Easing.bounceBoth));
	}

	/**
	 * the Animation when the mouse out of the thumbnail
	 * 
	 * @return
	 */
	private Animation animationOnMouseOut()
	{
		Attributes attributes =   new Attributes();
		attributes.add("opacity", new Attributes("from", getSettings().getOpacity(), "to", 0f));
		attributes.add("zIndex",  new Attributes("from", "2", "to", "1"));
	
		return new Animation(OnEvent.mouseout)
		{
			@Override
			public String getTriggerId()
			{
				AnimatedThumbnail.this.caption.setOutputMarkupId(true);
				return AnimatedThumbnail.this.caption.getMarkupId();
			}
		}
		.add(new Anim(Anim.Type.Anim, attributes, 1, Easing.easeIn));
	}
	
	/**
	 * the Animation when this thumbnail is clicked.
	 * 
	 * @return
	 */
	private Animation animationOnThumbnailClicked()
	{
		Attributes display = new Attributes();
		display
		.add("width", 		new Attributes("to", getSettings().getPictureWidth()).add("from", "0"))
		.add("height", 		new Attributes("to", getSettings().getPictureHeight()).add("from", "0"))
		.add("top", 		new Attributes("from", "0", "to", "0"))
		.add("left",		new Attributes("from", "0", "to", "0"))
		.add("opacity", 	new Attributes("from",  0, "to", 1))
		.add("borderWidth", new Attributes("from", getSettings().getOpacity(), "to", 2))
		.add("zIndex", 		new Attributes("from", "9",    "to", "9"));
		
		AnimEffect grow = new Anim(AnimEffect.Type.Anim, display, 1, Easing.easeNone);
		return new Animation(OnEvent.click)
		{
			@Override
			public String getTriggerId()
			{
				return AnimatedThumbnail.this.caption.getMarkupId();
			}
		}.add(grow);
	}

	/**
	 * the Animation when the picture is clicked
	 * @return
	 */
	public Animation animationOnPictureClicked()
	{
		Attributes hide = new Attributes();
		hide
		.add("width",	new Attributes("from", getSettings().getPictureWidth(), "to", 0))
		.add("height", 	new Attributes("from", getSettings().getPictureHeight(), "to", 0))
		.add("opacity", new Attributes("from", 1,"to", 0))
		.add("top", 	new Attributes("to",  getSettings().getPictureHeight()))
		.add("left",	new Attributes("to",  getSettings().getPictureWidth()))
		.add("zIndex", 	new Attributes("from", 99, "to", 1));
				
		return new Animation(OnEvent.click)
		{
			@Override
			public String getTriggerId()
			{
				AnimatedThumbnail.this.pictureImage.setOutputMarkupId(true);
				return AnimatedThumbnail.this.pictureImage.getMarkupId();
			}
		}
		.add(new Anim(Anim.Type.Anim, hide, 1, Easing.easeNone));
	}

	/**
	 * private class to add the styles for thumbnail and caption
	 * @author josh
	 *
	 */
	private class StyleAttributeModifier extends AttributeModifier
	{
		public StyleAttributeModifier()
		{
			super("style", true, new AbstractReadOnlyModel()
			{
				@Override
				public Object getObject()
				{
					InlineStyle style = new InlineStyle();
					style.add("width", getSettings().getThumbnailWidth());
					style.add("height", getSettings().getThumbnailHeight());
					return style.getStyle();
				}
			});
		}
	}

	public AnimatedThumbnailSettings getSettings()
	{
		return settings;
	}

	public void setSettings(AnimatedThumbnailSettings settings)
	{
		this.settings = settings;
	}
}
