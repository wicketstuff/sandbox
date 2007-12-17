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
package org.apache.wicket.cluster.initializer.message;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.initializer.NodeInitializer;
import org.apache.wicket.cluster.initializer.InitializerMessage;
import org.apache.wicket.cluster.session.SessionAttributeHolder;

public class ReplicateSessionMessage implements InitializerMessage {

	private static final long serialVersionUID = 1L;

	private final String contextPath;
	private final String id;
	private final int maxInactiveInterval;
	private final Map<String, SessionAttributeHolder> attributes;
	
	public ReplicateSessionMessage(HttpSession session) {
		this.contextPath = session.getServletContext().getContextPath();
		this.id = session.getId();
		this.maxInactiveInterval = session.getMaxInactiveInterval();
		
		attributes = new HashMap<String, SessionAttributeHolder>();
		for (Enumeration<?> e = session.getAttributeNames(); e.hasMoreElements(); ) {
			String name = (String) e.nextElement();
			Object value = session.getAttribute(name);
			
			if (value instanceof SessionAttributeHolder == false) {
				value = new SessionAttributeHolder(value);
			}
			attributes.put(name, (SessionAttributeHolder)value);
		}
	}
	
	public void execute(NodeInitializer initializer, Member member) {
		initializer.replicateSession(contextPath, id, maxInactiveInterval, attributes);
	}

}
