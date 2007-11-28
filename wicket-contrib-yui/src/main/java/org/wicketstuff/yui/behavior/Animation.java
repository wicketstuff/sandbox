package org.wicketstuff.yui.behavior;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.yui.YuiHeaderContributor;
import org.wicketstuff.yui.helper.JSArray;

/**
 * YUI Animation container. This Animation acts as a container of a sequence of
 * effects. which will animate in sequence upon an event. Animation needs to be
 * attached to a Component. The list of triggers allow this animation on the
 * component to be triggered by multiple points. (multiple triggers)
 * 
 * If more Animation objects are added to a component, they are kept in an array
 * in the Javascript Animator and are cycled through. (chaining) e.g.
 * Component1.add(new Animation1(OnClick)); Component1.add(new
 * Animation2(OnClick));
 * 
 * where Animation1 has a "Blind Down" effect and Animation2 is a "Blind Up"
 * effect. upon the first click Component1 will "Blind Down" and the second
 * click will "Blind Up" etc...
 * 
 * YAHOO.util.Anim / Motion / Scroll. Also include Effects from <a
 * href="http://blog.davglass.com/files/yui/effects/">http://blog.davglass.com/files/yui/effects/</a>
 * 
 * @author josh
 */
public class Animation extends AbstractBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * a sequence of effects.
	 */
	private List<AnimEffect> effects = new ArrayList<AnimEffect>();

	/**
	 * a list of triggers for this Animation. Each An
	 */
	private List<String> triggers = new ArrayList<String>();

	/**
	 * a list of triggers which are components that may not have their markup
	 * ids ready.
	 */
	private List<Component> componentTriggers = new ArrayList<Component>();

	/**
	 * defines if the attached component should trigger the Animation
	 */
	private boolean isTriggeredByAttachedComponent = true;

	/**
	 * the component that this behaviour is bound to
	 */
	private Component component;

	/**
	 * the Event to trigger the animation
	 */
	private OnEvent onEvent;

	/**
	 * Constructor for an Animation that will be triggered when the 'event'
	 * occurs on the attached Component.
	 * 
	 * @param onEvent
	 */
	public Animation(OnEvent onEvent)
	{
		this.onEvent = onEvent;
	}

	/**
	 * Constructor for an Animation that will be triggered by the triggerId.
	 * 
	 * @param onEvent
	 * @param triggerId
	 */
	public Animation(OnEvent onEvent, String triggerId)
	{
		this.onEvent = onEvent;
		addTrigger(triggerId);
		isTriggeredByAttachedComponent = false;
	}

	/**
	 * Constructor for an Anmation that will be triggered by the component
	 * @param onEvent
	 * @param component
	 */
	public Animation(OnEvent onEvent, Component component)
	{
		this.onEvent = onEvent;
		addTrigger(component);
		isTriggeredByAttachedComponent = false;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(Component component)
	{
		this.component = component;
		this.component.setOutputMarkupId(true);
		component.add(YuiHeaderContributor.forModule("animation"));
		component.add(HeaderContributor.forJavaScript(AnimEffect.class, "effects/effects.js"));
		component.add(HeaderContributor.forJavaScript(AnimEffect.class, "effects/tools.js"));
		component.add(HeaderContributor.forJavaScript(AnimEffect.class, "effects/animator.js"));

		if (isTriggeredByAttachedComponent)
		{
			addTrigger(this.component);
		}
	}

	/*
	 * 
	 * Renders the javascript for this animation. basically 2 lines of
	 * javascript. 1/ var a_anim_object = new new YAHOO.util.Anim('yim-6-pic',
	 * hide_attributes, 1, YAHOO.util.Easing.easeIn); 2/
	 * Wicketstuff.yui.Animator.add(trigger_ids, trigger_event, animation_id,
	 * a_anim_object);
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		StringBuffer buffer = new StringBuffer().append("var ").append(getAnimVar()).append(" = ")
				.append(buildEffectsJS()).append("Wicket.yui.Animator.add(")
				.append(getTriggerIds()).append(",") 							// trigger_ids : the id of the group
				.append("'").append(getOnEvent()).append("'").append(",") 		// trigger_event: the event 'click'
				.append("'").append(getComponentId()).append("'").append(",")	// animation_id : the id of the animation obj
				.append(getAnimVar()) 											// a_anim_object : the animation object
				.append(")");
		response.renderOnDomReadyJavascript(buffer.toString());
	}

	/**
	 * return a list of triggers as an Array
	 * 
	 * @return
	 */
	private String getTriggerIds()
	{
		JSArray triggers = new JSArray();
		
		for (String aTrigger : getTriggers())
		{
			triggers.add("'" + aTrigger + "'");
		}
		
		for (Component aComponent : getComponentTriggers())
		{
			triggers.add("'" + aComponent.getMarkupId() + "'");
		}
		return triggers.toString();
	}

	/**
	 * 
	 * @return
	 */
	private OnEvent getOnEvent()
	{
		return this.onEvent;
	}

	/**
	 * 
	 * @param effect
	 * @return
	 */
	public Animation add(AnimEffect effect)
	{
		getEffects().add(effect);
		return this;
	}

	/**
	 * need this to 1/ strip out the initial "eff =" 2/ end "animate()"
	 * 
	 * var anim_singapore0 = anim_singapore01=new
	 * YAHOO.widget.Effects.BlindDown(singapore0,{delay:true},{delay:true});.animate();;
	 * 
	 * @return
	 */
	private String buildEffectsJS()
	{
		String js = buildEffectsJS(getAnimVar(), effects);
		return js.substring(js.indexOf("=") + 1, js.lastIndexOf(getAnimVar() + ".animate();"));
	}

	/**
	 * builds a Effects from the list
	 * 
	 * eff = new YAHOO.widget.Effects.BlindUp('demo13', { delay: true });
	 * eff.onEffectComplete.subscribe(function() { eff2 = new
	 * YAHOO.widget.Effects.BlindRight('demo13', { delay: true });
	 * eff2.onEffectComplete.subscribe(function() { eff3 = new
	 * YAHOO.widget.Effects.BlindDown('demo13', { delay: true });
	 * eff3.animate(); }); eff2.animate(); });
	 * 
	 * @return
	 */
	private String buildEffectsJS(String jsVar, List<AnimEffect> effectslist)
	{
		int listsize = effectslist.size();
		if (listsize == 0)
		{
			return "";
		}
		else
		{
			AnimEffect effect = effectslist.get(0);
			StringBuffer buffer = new StringBuffer();
			buffer.append(jsVar).append("=").append("new ").append(effect.newEffectJS()).append(
					"('").append(getComponentId()).append("',").append(effect.getAttributes())
					.append(",").append(effect.getOpts()).append(");");

			if (listsize > 1) // means at least one more child to go
			{
				buffer.append(jsVar).append(".").append(effect.onCompleteJS()).append(
						".subscribe(function() {");
				buffer.append(buildEffectsJS(jsVar + listsize, effectslist.subList(1, listsize)));
				buffer.append("});");
			}
			buffer.append(jsVar).append(".").append("animate();");
			return buffer.toString();
		}
	}

	/**
	 * the Javascript variable
	 * 
	 * @return
	 */
	private String getAnimVar()
	{
		return getOnEvent() + "_" + getComponentId();
	}

	/**
	 * the componet's markup id
	 * 
	 * @return
	 */
	public String getComponentId()
	{
		return this.component.getMarkupId();
	}

	/**
	 * the list of Effects
	 * 
	 * @return
	 */
	public List<AnimEffect> getEffects()
	{
		return effects;
	}

	public void setEffects(List<AnimEffect> effects)
	{
		this.effects = effects;
	}

	public List<String> getTriggers()
	{
		return triggers;
	}

	public void setTriggers(List<String> triggers)
	{
		this.triggers = triggers;
	}

	public boolean isTriggeredByAttachedComponent()
	{
		return isTriggeredByAttachedComponent;
	}

	public void setTriggeredByAttachedComponent(boolean isTriggeredByAttachedComponent)
	{
		this.isTriggeredByAttachedComponent = isTriggeredByAttachedComponent;
	}

	public Component getComponent()
	{
		return component;
	}

	public void setComponent(Component component)
	{
		this.component = component;
	}

	public void setOnEvent(OnEvent onEvent)
	{
		this.onEvent = onEvent;
	}

	public List<Component> getComponentTriggers()
	{
		return componentTriggers;
	}

	public void setComponentTriggers(List<Component> componentTriggers)
	{
		this.componentTriggers = componentTriggers;
	}
	
	/**
	 * adds a trigger for this Animation
	 * @param triggerId
	 */
	private Animation addTrigger(String triggerId)
	{
		getTriggers().add(triggerId);
		return this;
	}
	
	/**
	 * adds a component which will be a trigger
	 * @param component
	 * @return
	 */
	public Animation addTrigger(Component component)
	{
		component.setOutputMarkupPlaceholderTag(true);
		getComponentTriggers().add(component);
		return this;
	}
}
