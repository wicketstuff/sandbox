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
package org.apache.wicket.seam;

import static org.jboss.seam.ScopeType.APPLICATION;
import static org.jboss.seam.annotations.Install.FRAMEWORK;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.Filter;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.ServletLifecycle;
import org.jboss.seam.web.AbstractFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Seam component that delegates requests to the {@link WicketFilter} and sets
 * up and pulls down Seam {@link Context}s for the request.
 * <p>
 * Users do not have to install this filter, but instead just install
 * {@link WicketFilter} like they would normally do. This Seam component
 * automatically attaches to it.
 * </p>
 * 
 * @author eelcohillenius
 */
@Startup
@Scope(APPLICATION)
@Name("org.apache.wicket.seam.WicketSeamFilter")
@Install(classDependencies = { "org.apache.wicket.protocol.http.WebApplication" }, value = true, precedence = FRAMEWORK)
@BypassInterceptors
@Filter()
public class WicketSeamFilter extends AbstractFilter {

	private static Logger log = LoggerFactory.getLogger(WicketSeamFilter.class);

	private WicketFilter delegate = null;

	/** See javax.servlet.FilterConfig */
	private FilterConfig filterConfig;

	/**
	 * Construct.
	 */
	public WicketSeamFilter() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		// Check for the Wicket filter (which might be initialized after this
		// filter, hence the lazy loading). Synchronization is not important.
		if (delegate == null) {
			WebApplication webApplication = (WebApplication) filterConfig
					.getServletContext().getAttribute(
							WebApplication.SERVLET_CONTEXT_APPLICATION_KEY);
			if (webApplication == null) {
				log
						.warn("ignoring request: no Wicket web application instance found");
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}

			delegate = webApplication.getWicketFilter();
		}

		ServletLifecycle.beginRequest((HttpServletRequest) servletRequest);
		delegate.doFilter(servletRequest, servletResponse, filterChain);
		ServletLifecycle.endRequest((HttpServletRequest) servletRequest);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.filterConfig = filterConfig;
	}
}
