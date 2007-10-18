package org.wicketstuff.yui.behavior;

/**
 * a Dave Galss Effect
 * 
 * @author josh
 *
 */
public class Effect extends AnimEffect
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @author josh
	 *
	 */
	public enum Type 
	{
		Shadow, Fade, Appear, Fold, UnFold,
		BlindDown, BlindUp, BlindRight, BlindLeft, TV,
		ShakeLR, ShakeTB, Drop, Pulse, Shrink, Grow;
		
		public String functionFor() 
		{
			return "YAHOO.widget.Effects." + this;
		}
	}

	private Type widgetEffetct;
	
	public Effect(Type widgetEffect, Attributes attributes)
	{
		super();
		this.widgetEffetct = widgetEffect;
		setAttributes(attributes);
	}

	public Effect(Type widgetEffect)
	{
		this(widgetEffect, new Attributes());
	}

	/**
	 *  YAHOO.widget.Effects.BlindDown(this, {bind: 'bottom'});
	 */
	@Override
	public String newEffectJS()
	{
		return getWidgetEffetct().functionFor();
	}

	@Override
	public String onCompleteJS()
	{
		return "onEffectComplete";
	}
	
	@Override
	public String getOpts()
	{
		return super.getOpts();
	}

	public Type getWidgetEffetct()
	{
		return widgetEffetct;
	}

	public void setWidgetEffetct(Type widgetEffetct)
	{
		this.widgetEffetct = widgetEffetct;
	}

}
