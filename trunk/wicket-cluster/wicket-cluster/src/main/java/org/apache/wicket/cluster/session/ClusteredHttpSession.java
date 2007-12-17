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
package org.apache.wicket.cluster.session;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.MessageSender;
import org.apache.wicket.cluster.session.message.InvalidateSessionMessage;
import org.apache.wicket.cluster.session.message.RemoveAttributeMessage;
import org.apache.wicket.cluster.session.message.SetAttributeMessage;
import org.apache.wicket.cluster.session.message.SetMaxInactiveIntervalMessage;


/**
 * Wrapper for HTTP session. The lifecycle of an ClustedHttpSession shouldn't
 * span over multiple requests 
 * @author Matej Knopp
 */
public class ClusteredHttpSession extends HttpSessionWrapper {
	
	private final String contextPath;
	private final MessageSender messageSender;
	
	// set of modified attributes
	private Set<String> modified = new HashSet<String>();
	
	public ClusteredHttpSession(String contextPath, HttpSession delegate, MessageSender messageSender) {
		super(delegate);
		this.contextPath = contextPath;
		
		if (delegate == null) {
			throw new IllegalArgumentException("Session delegate may not be null");
		}
		if (messageSender == null) {
			throw new IllegalArgumentException("Message sender may not be null");
		}
		this.messageSender = messageSender;
	}
	
	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}
	
	@Override
	public void setAttribute(String name, Object value) {
		super.setAttribute(name, value);
		modified.add(name);
	}
	
	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}
	
	@Override
	public void removeAttribute(String name) {
		super.removeAttribute(name);
		messageSender.sendMessage(new RemoveAttributeMessage(contextPath, getId(), name));
	}
	
	@Override
	public void setMaxInactiveInterval(int interval) {
		super.setMaxInactiveInterval(interval);
		messageSender.sendMessage(new SetMaxInactiveIntervalMessage(contextPath, getId(), interval));
	}
	
	@Override
	public void invalidate() {
		String id = getId();
		super.invalidate();
		messageSender.sendMessage(new InvalidateSessionMessage(contextPath, id));
	}
	
	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}
	
	@Override
	public Object getAttribute(String name) {
		Object attribute = super.getAttribute(name);
		if (attribute instanceof SessionAttributeHolder) {
			attribute = ((SessionAttributeHolder)attribute).toOriginalObject();
			super.setAttribute(name, attribute);
		}
		return attribute;
	}
	
	public void flush() {
		for (String name : modified) {
			Object value = super.getAttribute(name);
			messageSender.sendMessage(new SetAttributeMessage(contextPath, getId(), name, value));
		}
	}
}
