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
package org.wicketstuff.push.timer;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IChannelTarget;
import org.wicketstuff.push.ChannelEvent;

/**
 * Behavior to listen to a {@link ChannelEvent} actually using polling
 * to perform the client side refresh on server side events.
 * <p>
 * This Behavior will triggered the onEvent method when a push event will 
 * be triggered on the same channel as the channel on the constructor.
 * This kind of event can be triggered by {@link TimerChannelPublisher},
 * but you usually use {@link IChannelService} implementation
 * instead of depending directly on these implementations.
 * </p>
 * <p>
 * The polling interval is configured in the constructor. The more frequent
 * is the polling, the more quickly your client will be updated, but also the
 * more you will charge your server and your network.
 * <p>
 * 
 * @author Xavier Hanin
 * 
 * @see IChannelService
 * @see TimerChannelService
 */
public class TimerChannelBehavior extends AbstractAjaxTimerBehavior 
	implements IChannelTarget, Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static Method[] methods;

	private static final int ADD_COMPONENT_METHOD = 0;
	
	private static final int ADD_COMPONENT_WITH_MARKUP_ID_METHOD = 1;

	private static final int APPEND_JAVASCRIPT_METHOD = 2;

	private static final int PREPEND_JAVASCRIPT_METHOD = 3;

	private static final int FOCUS_COMPONENT_METHOD = 4;
	
	static {
		try
		{
			methods = new Method[] {
				AjaxRequestTarget.class.getMethod("addComponent", new Class[] {Component.class}),
				AjaxRequestTarget.class.getMethod("addComponent", new Class[] {Component.class, String.class}),
				AjaxRequestTarget.class.getMethod("appendJavascript", new Class[] {String.class}),
				AjaxRequestTarget.class.getMethod("prependJavascript", new Class[] {String.class}),
				AjaxRequestTarget.class.getMethod("focusComponent", new Class[] {Component.class}),
			};
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException("Unable to initialize DefaultAjaxPushBehavior", e);
		}
	}
	
	/**
	 * This class is used to store a list of delayed method calls.
	 * 
	 * The method calls are actually calls to methods on {@link AjaxRequestTarget},
	 * which are invoked when the client polls the server.
	 * 
	 * @author Xavier Hanin
	 */
	private static class DelayedMethodCallList implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * Used to store a method and its parameters to be later invoked on an object.
		 * 
		 * @author Xavier Hanin
		 */
		private static class DelayedMethodCall implements Serializable {
			private static final long serialVersionUID = 1L;
			
			/**
			 * The index of the method to invoke
			 * We store only an index to avoid serialization issues
			 */
			private int m;
			/**
			 * the parameters to use when the method is called
			 */
			private Object[] parameters;

			/**
			 * Construct.
			 * @param m the index of the method to be called
			 * @param parameters the parameters to use when the method is called
			 */
			public DelayedMethodCall(int m, Object[] parameters)
			{
				this.m = m;
				this.parameters = parameters;
			}

			/**
			 * Invokes the method with the parameters on the given object.
			 * 
			 * @see java.lang.reflect.Method#invoke(Object, Object[])
			 * @param o the object on which the method should be called
			 * @throws IllegalArgumentException
			 * @throws IllegalAccessException
			 * @throws InvocationTargetException
			 */
			public void invoke(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
			{
				methods[m].invoke(o, parameters);
			}
		}
		/**
		 * stores the list of {@link DelayedMethodCall} to invoke
		 */
		private List<DelayedMethodCall> calls = new ArrayList<DelayedMethodCall>();
		
		/**
		 * Construct.
		 */
		public DelayedMethodCallList()
		{
		}

		/**
		 * Construct a copy of the given {@link DelayedMethodCallList}.
		 * @param dmcl
		 */
		public DelayedMethodCallList(DelayedMethodCallList dmcl)
		{
			calls = new ArrayList<DelayedMethodCall>(dmcl.calls);
		}
		
		/**
		 * Add a {@link DelayedMethodCall} to the list
		 * @param m the index of the method to be later invoked
		 * @param parameters the parameters to use when the method will be invoked
		 */
		public void addCall(int m, Object[] parameters) {
			calls.add(new DelayedMethodCall(m, parameters));
		}
		
		/**
		 * Invokes all the {@link DelayedMethodCall} in the list on the given Object
		 * 
		 * @see java.lang.reflect.Method#invoke(Object, Object[])
		 * @param o the object on which delayed methods should be called
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 */
		public void invoke(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			for (Iterator iter = calls.iterator(); iter.hasNext();)
			{
				DelayedMethodCall dmc = (DelayedMethodCall)iter.next();
				dmc.invoke(o);
			}
		}

		/**
		 * Indicates if this list is empty or not
		 * @return true if this lsit is empty, false otherwise
		 */
		public boolean isEmpty()
		{
			return calls.isEmpty();
		}

		/**
		 * Used to remove all the delayed methods from this list
		 */
		public void clear()
		{
			calls.clear();
		}
	}
	
	/**
	 * A trigger currenty being constructed, waiting for a call to trigger to go
	 * to the triggers list.
	 */
	private DelayedMethodCallList currentTrigger = new DelayedMethodCallList();
	
	/**
	 * a list of triggers to send to the client 
	 */
	private List<DelayedMethodCallList> triggers = new ArrayList<DelayedMethodCallList>(); 

	/**
	 * channel to listen to
	 */
	private String channel;

	/**
	 * Listener to notify of events
	 */
	private IChannelListener listener;
	
	/**
	 * Construct a TimerChannelBehavior which actually refreshes the clients
	 * by polling the server for changes at the given duration.
	 * 
	 * @param updateInterval the interval at which the server should be polled for changes
	 * @channel String representing the channel to listen to
	 * @listener the {@link IChannelListener} to notify of events
	 */
	public TimerChannelBehavior(Duration updateInterval, String channel, IChannelListener listener)
	{
		super(updateInterval);
		this.channel = channel;
		this.listener = listener;
		EventStore.get().addEventStoreListener(new EventStoreListener(){
			public void EventTriggered(String channel, Map data)
			{
				if (channel.equals(TimerChannelBehavior.this.channel)){
					TimerChannelBehavior.this.listener.onEvent(channel, data, TimerChannelBehavior.this);
					TimerChannelBehavior.this.trigger();
				}
			}
			
		});
	}
	
	/**
	 * @see IAjaxPushBehavior#addComponent(Component)
	 */
	public void addComponent(Component component)
	{
		synchronized(currentTrigger) { 
			currentTrigger.addCall(ADD_COMPONENT_METHOD, new Object[] {component});
		}
	}

	/**
	 * @see IAjaxPushBehavior#addComponent(Component, String)
	 */
	public void addComponent(Component component, String markupId)
	{
		synchronized(currentTrigger) { 
			currentTrigger.addCall(ADD_COMPONENT_WITH_MARKUP_ID_METHOD, new Object[] {component, markupId});
		}
	}

	/**
	 * @see IAjaxPushBehavior#appendJavascript(String)
	 */
	public void appendJavascript(String javascript)
	{
		synchronized(currentTrigger) { 
			currentTrigger.addCall(APPEND_JAVASCRIPT_METHOD, new Object[] {javascript});
		}
	}

	/**
	 * @see IAjaxPushBehavior#focusComponent(Component)
	 */
	public void focusComponent(Component component)
	{
		synchronized(currentTrigger) { 
			currentTrigger.addCall(FOCUS_COMPONENT_METHOD, new Object[] {component});
		}
	}

	/**
	 * @see IAjaxPushBehavior#prependJavascript(String)
	 */
	public void prependJavascript(String javascript)
	{
		synchronized(currentTrigger) { 
			currentTrigger.addCall(PREPEND_JAVASCRIPT_METHOD, new Object[] {javascript});
		}
	}

	/**
	 * @see IAjaxPushBehavior#trigger()
	 */
	public void trigger()
	{
		DelayedMethodCallList trigger = null;
		synchronized(currentTrigger) {
			if (currentTrigger.isEmpty()) {
				return;
			}
			trigger = new DelayedMethodCallList(currentTrigger);
			currentTrigger.clear();
		}
		synchronized (triggers)
		{
			triggers.add(trigger);
		}
	}

	/**
	 * @see AbstractAjaxTimerBehavior#onTimer(AjaxRequestTarget)
	 */
	protected void onTimer(AjaxRequestTarget target)
	{
		List<DelayedMethodCallList> triggers;
		synchronized (this.triggers)
		{
			triggers = new ArrayList<DelayedMethodCallList>(this.triggers);
			this.triggers.clear();
		}
		for (Iterator iter = triggers.iterator(); iter.hasNext();)
		{
			DelayedMethodCallList dmcl = (DelayedMethodCallList)iter.next();
			try
			{
				dmcl.invoke(target);
			}
			catch (Exception e)
			{
				throw new WicketRuntimeException("a problem occured while adding events to AjaxRequestTarget", e);
			}
		}
	}

}