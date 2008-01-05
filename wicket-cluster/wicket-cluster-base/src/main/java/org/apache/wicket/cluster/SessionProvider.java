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
package org.apache.wicket.cluster;

import java.util.Set;

import javax.servlet.http.HttpSession;

/**
 * Interface that the session manager must implement.
 * 
 * @author Matej Knopp
 */
public interface SessionProvider {

	/**
	 * Returns the http session with given Id. If the session doesn't exist and
	 * the createIfDoesNotExist parameter is <code>true</code>, new session
	 * is created (honoring the specified sessionId).
	 * 
	 * @param sessionId
	 * @param createIfDoesNotExist
	 * @return
	 */
	public HttpSession getSession(String sessionId, boolean createIfDoesNotExist);

	/**
	 * Returns set of active session ids. The returned set should not be
	 * affected by new sessions created after it has been created.
	 * 
	 * @return set of active session ids
	 */
	public Set<String> getActiveSessions();

	/**
	 * Returns the count of active http sessions.
	 * 
	 * @return
	 */
	public int getActiveSessionsCount();
	
	/**
	 * Returns context path of context to which this session provider belongs
	 * @return
	 */
	public String getContextPath();

}