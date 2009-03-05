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
package org.wicketstuff.dojo11.push.cometd;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author stf
 */
public class CometdJavascriptBehavior extends CometdAbstractBehavior
{

	/**
	 * name of the default callback, that evals the evalscript property of a message
	 */
	public static final String DEFAULT_CALLBACK = "wicketDojoCometdEval";
	
	private final String _callbackMethod;

	/**
	 * Construct.
	 * @param channelId
	 */
	public CometdJavascriptBehavior(String channelId)
	{
		this(channelId, null);
	}
	
	/**
	 * Construct.
	 * @param channelId
	 * @param callbackMethod 
	 */
	public CometdJavascriptBehavior(String channelId, String callbackMethod)
	{
		super(channelId);
		_callbackMethod = (callbackMethod == null || "".equals(callbackMethod)) ? DEFAULT_CALLBACK : callbackMethod;
	}

	/**
	 * @see org.wicketstuff.dojo11.push.cometd.CometdAbstractBehavior#getCometdInterceptorScript()
	 */
	@Override
	public String getCometdInterceptorScript()
	{
		return null;
	}

	/**
	 * @see org.wicketstuff.dojo11.push.cometd.CometdAbstractBehavior#getPartialSubscriber()
	 */
	@Override
	public CharSequence getPartialSubscriber()
	{
		return _callbackMethod;
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		// never called
	}

	protected String getJavascriptId()
	{
		// don't add component
		return getChannelId();
	}
}
