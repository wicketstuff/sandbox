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
package org.apache.wicket.cluster.jetty;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.wicket.cluster.CommunicationModule;
import org.apache.wicket.cluster.initializer.NodeInitializerComponent;
import org.apache.wicket.cluster.pagestore.PageStoreComponent;
import org.apache.wicket.cluster.session.SessionComponent;
import org.apache.wicket.cluster.tribes.TribesCommunicationModule;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.servlet.SessionHandler;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JettyBootstrap {
	
	private ForceSessionIdManager sessionIdManager;	
	
	public JettyBootstrap() {
		if (instance != null) {
			throw new IllegalStateException("Only one instance of JettyBootstrap is allowed.");
		}
		instance = this;
				
		beforeServerStarted();
	}
	
	private static JettyBootstrap instance;
	
	private CommunicationModule communicationModule;
	private SessionComponent sessionComponent;
	private PageStoreComponent pageStoreComponent;
	private NodeInitializerComponent nodeInitializerComponent;
	
	private Set<String> contextsToInitialize = Collections.synchronizedSet(new HashSet<String>());
	
	public void configureServer(Server server) {
		server.setSessionIdManager(getSessionIdManager());
	}
	
	public void configureContext(WebAppContext context) {
		HashSessionManager manager = new HashSessionManager(context.getContextPath());
		SessionHandler handler = new SessionHandler(manager);
		context.setSessionHandler(handler);

		sessionComponent.registerSessionProvider(manager);
		nodeInitializerComponent.registerSessionProvider(manager);
		
		context.addFilter(SessionComponent.getFilterClass(), "/*", Handler.ALL);
		
		// we need to store contextpaths of all context, so that the last initialized
		// StartServlet instance would start the communication module
		contextsToInitialize.add(manager.getContextPath());
		
		ServletHolder holder = new ServletHolder(StartServlet.class);
		holder.setInitOrder(Integer.MAX_VALUE);
		context.addServlet(holder, "");			
	}
	
	public CommunicationModule getCommunicationModule() {
		return communicationModule;
	}
	
	public SessionComponent getSessionComponent() {
		return sessionComponent;
	}
	
	public PageStoreComponent getPageStoreComponent() {
		return pageStoreComponent;
	}
	
	public NodeInitializerComponent getNodeInitializerComponent() {
		return nodeInitializerComponent;
	}
	
	private void beforeServerStarted() {
		log.info("Initializing components");

		sessionIdManager = new ForceSessionIdManager();
		
		communicationModule = new TribesCommunicationModule();
		
		sessionComponent = new SessionComponent(communicationModule);
		communicationModule.addMessageListener(sessionComponent);

	
		pageStoreComponent = new PageStoreComponent(communicationModule);
		nodeInitializerComponent = new NodeInitializerComponent(communicationModule, pageStoreComponent);
		
		communicationModule.addMessageListener(pageStoreComponent);
		communicationModule.addMessageListener(nodeInitializerComponent);
		communicationModule.addMemberListener(nodeInitializerComponent);
	}
	
	private void afterServerStarted() {
		
		log.info("Starting communication module");
		
		communicationModule.run();
	}
	
	public static class StartServlet extends HttpServlet {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void init(ServletConfig config) throws ServletException {
			JettyBootstrap bootstrap = JettyBootstrap.instance;
			bootstrap.contextsToInitialize.remove(config.getServletContext().getContextPath());
			if (bootstrap.contextsToInitialize.isEmpty()) {
				instance.afterServerStarted();
			}
		}			
	}

	
	public ForceSessionIdManager getSessionIdManager() {
		return sessionIdManager;
	}
	
	private static final Logger log = LoggerFactory.getLogger(JettyBootstrap.class);
}
