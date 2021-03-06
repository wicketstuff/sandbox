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
package org.apache.wicket.cluster.session.message;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;
import org.apache.wicket.cluster.session.SessionAttributeHolder;
import org.apache.wicket.cluster.session.SessionComponent;

public class SetAttributeMessage implements SessionMessage {
	
	private static final long serialVersionUID = 1L;
	
	private final String contextPath;
	private final String sessionId;
	private final String attributeName;
	private final Object attributeValue;
		
	public SetAttributeMessage(String contextPath, String sessionId, String attributeName, Object attributeValue) {
		this.sessionId = sessionId;
		this.attributeName = attributeName;
		this.contextPath = contextPath;

		if (attributeValue instanceof SessionAttributeHolder)
			this.attributeValue = attributeValue;
		else
			this.attributeValue = new SessionAttributeHolder(attributeValue);
	}
	
	public String getContextPath() {
		return contextPath;
	};
	
	public void execute(SessionProvider sessionProvider) {
		HttpSession session = sessionProvider.getSession(sessionId, true);
		
		Object attributeValue = this.attributeValue;
		
		boolean keepSerialized = SessionComponent.getInstance().isKeepSessionAttributesSerialized();
		
		if (!keepSerialized && attributeValue instanceof SessionAttributeHolder) {
			attributeValue = ((SessionAttributeHolder)attributeValue).toOriginalObject();
		}
		session.setAttribute(attributeName, attributeValue);
	}
}
