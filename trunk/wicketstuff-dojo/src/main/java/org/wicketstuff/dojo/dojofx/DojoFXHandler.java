/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.dojo.dojofx;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.apache.wicket.model.IModel;


/**
 * Abstract Handler class for Dojo.fx.html components currently only handles som
 * instance fields which are currently mandatory for every dojo.fx.html
 * animation and has an AppendAttributeModifier private class, mostly for style
 * and onclick attributes. Also declares some method signature for FX
 * subclasses.
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 * 
 */
public abstract class DojoFXHandler extends AbstractRequireDojoBehavior
{
	private final String eventName;
	protected Component component;
	private final int duration;
	private final Component trigger;


	/**
	 * @param eventName
	 *            name of the function to be bound to the effect (i.e. ONCLICK)
	 * @param duration
	 *            duration of the animation.
	 * @param trigger
	 *            trigger object for the animation.
	 */
	public DojoFXHandler(String eventName, int duration, Component trigger)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.duration = duration;
		this.trigger = trigger;
	}


	/**
	 * @return animation duration.
	 */
	public int getDuration()
	{
		return duration;
	}

	/**
	 * @return event name
	 */
	public String getEventName()
	{
		return eventName;
	}

	/**
	 * @return trigger component
	 */
	public Component getTrigger()
	{
		return trigger;
	}

	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}
	

	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.lfx.*");
	}

	/**
	 * @author Ruud Booltink
	 * @author Marco van de Haar
	 * 
	 * AttributeModifier that appends the new value to the current value if an
	 * old value exists. If it does not exist, it sets the new value.
	 */
	final static class AppendAttributeModifier extends AttributeModifier
	{
		/**
		 * Construct.
		 * 
		 * @param attribute
		 * @param addAttributeIfNotPresent
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, boolean addAttributeIfNotPresent,
				IModel replaceModel)
		{
			super(attribute, addAttributeIfNotPresent, replaceModel);
		}

		/**
		 * Construct.
		 * 
		 * @param attribute
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, IModel replaceModel)
		{
			super(attribute, replaceModel);
		}

		/**
		 * Construct.
		 * 
		 * @param attribute
		 * @param pattern
		 * @param addAttributeIfNotPresent
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, String pattern,
				boolean addAttributeIfNotPresent, IModel replaceModel)
		{
			super(attribute, pattern, addAttributeIfNotPresent, replaceModel);
		}

		/**
		 * Construct.
		 * 
		 * @param attribute
		 * @param pattern
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, String pattern, IModel replaceModel)
		{
			super(attribute, pattern, replaceModel);
		}
		
		/**
		 * @see wicket.AttributeModifier#newValue(java.lang.String, java.lang.String)
		 */
		protected String newValue(String currentValue, String replacementValue)
		{
			return (currentValue == null ? "" : currentValue + "; ") + replacementValue;
		}
	}


}
