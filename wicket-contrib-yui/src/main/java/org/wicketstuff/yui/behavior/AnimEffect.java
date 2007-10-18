package org.wicketstuff.yui.behavior;

import java.io.Serializable;

/**
 * Effects class. this is the base class for
 * YAHOO.util.Anim/Motion/Scroll and 
 * YAHOO.widgets.Effects
 * @author josh
 *
 */
public abstract class AnimEffect implements Serializable
{
	/**
	 * the attributes of this Effect
	 */
	private Attributes attributes;
	
	/**
	 * the construct 
	 */
	public AnimEffect()
	{
		super();
	}

	public abstract String newEffectJS();

	public abstract String onCompleteJS();

	public Attributes getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}

	public String getOpts()
	{
		return "";
	}
}
