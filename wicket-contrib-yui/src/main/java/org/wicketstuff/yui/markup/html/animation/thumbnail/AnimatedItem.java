package org.wicketstuff.yui.markup.html.animation.thumbnail;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.yui.behavior.Anim;
import org.wicketstuff.yui.behavior.AnimEffect;
import org.wicketstuff.yui.behavior.Animation;
import org.wicketstuff.yui.behavior.Attributes;
import org.wicketstuff.yui.behavior.Easing;
import org.wicketstuff.yui.behavior.OnEvent;

/**
 * AnimatedItem contains 3 component which have in built animations that will
 * overlay on the mouseover/out/click events. This serves as the base class for 
 * other Animated interested in this behaviour.
 * 
 *  nb: if the child component is a complex panel with lots of nested divs it may not
 *  work. I only use a Panel of IMG and Labels
 * 
 * @author josh
 *
 */
@SuppressWarnings("serial")
public abstract class AnimatedItem extends Panel 
{
	private static final long serialVersionUID = 1L;
		
	private Component onloadItem ;
	
	private Component mouseoverItem;

	private Component onclickItem;

	public static Attributes SHOW_ATTRIBUTE = new Attributes("zIndex", 0, 1);
	
	public static Attributes HIDE_ATTRIBUTE = new Attributes("zIndex", 1, 0);
	
	/**
	 * 
	 * @param id
	 * @param settings
	 */
	public AnimatedItem(String id)
	{
		super(id);
		add(HeaderContributor.forCss(AnimatedItem.class, "AnimatedItem.css"));
	}

	/**
	 * CHILD CLASS MUST CALL THIS !!
	 */
	public void init()
	{
		add(onloadItem = newOnloadItem("onload_item"));
		add(mouseoverItem = newMouseoverItem("mouseover_item"));
		add(onclickItem = newOnclickItem("onclick_item"));
		
		// animation
		mouseoverItem.add(new Animation(OnEvent.mouseover, onloadItem).add(mouseoverEffect()));
		mouseoverItem.add(new Animation(OnEvent.mouseout, mouseoverItem).add(mouseoutEffect()));
		onclickItem.add(new Animation(OnEvent.click, mouseoverItem).add(onselectEffect()));
		onclickItem.add(new Animation(OnEvent.click, onclickItem).add(onunselectEffect()));
	}
	
	public AnimEffect onunselectEffect()
	{
		return new Anim(Anim.Type.Anim, HIDE_ATTRIBUTE, 1, Easing.easeNone);
	}

	public AnimEffect onselectEffect()
	{
		return new Anim(Anim.Type.Anim, SHOW_ATTRIBUTE, 1, Easing.easeNone);
	}

	public AnimEffect mouseoutEffect()
	{
		return new Anim(Anim.Type.Anim, HIDE_ATTRIBUTE, 1, Easing.easeNone);
	}

	public AnimEffect mouseoverEffect()
	{
		return new Anim(Anim.Type.Anim, SHOW_ATTRIBUTE, 1, Easing.easeNone);
	}

	public abstract Component newOnloadItem(String id);
	
	public abstract Component newMouseoverItem(String id);
	
	public abstract Component newOnclickItem(String id);

}
