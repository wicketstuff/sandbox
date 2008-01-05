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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.MessageSender;

public class ClusteredHttpServletRequest extends HttpServletRequestWrapper {
	
	private final MessageSender sender;
	private final String contextPath;
	
	public ClusteredHttpServletRequest(HttpServletRequest delegate, ServletContext context, MessageSender sender) {
		super(delegate);
		
		if (sender == null) {
			throw new IllegalArgumentException("Message sender may not be null");
		}
		
		this.contextPath = context.getContextPath();
		this.sender = sender;		
	}
	
	private ClusteredHttpSession cachedSession = null;
	
	@Override
	public HttpSession getSession() {
		if (cachedSession == null) {
			HttpSession session = super.getSession();
			if (session != null) {				
				cachedSession = new ClusteredHttpSession(contextPath, session, sender);
			}
		}
		return cachedSession;
	}
	
	@Override
	public HttpSession getSession(boolean create) {
		if (cachedSession == null) {
			HttpSession session = super.getSession(create);
			if (session != null) {
				cachedSession = new ClusteredHttpSession(contextPath, session, sender);
			}
		}
		return cachedSession;
	}
	
	public void flush() {
		if (cachedSession != null) {
			cachedSession.flush();
		}
	}
}
