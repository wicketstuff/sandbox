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
package org.apache.wicket.protocol.http.portlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.protocol.http.WicketFilter;

/**
 * Wraps the HttpServletRequest and HttpServletResponse objects for convenience during
 * {@link WicketFilterPortletContext} and {@link WicketFilter}'s processing.
 * 
 * @author Ate Douma
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public final class FilterRequestContext
{
	private HttpServletRequest request;
	private HttpServletResponse response;

	public FilterRequestContext(final HttpServletRequest request, final HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public HttpServletResponse getResponse()
	{
		return response;
	}

	public void setRequest(final HttpServletRequest request)
	{
		this.request = request;
	}

	public void setResponse(final HttpServletResponse response)
	{
		this.response = response;
	}
}