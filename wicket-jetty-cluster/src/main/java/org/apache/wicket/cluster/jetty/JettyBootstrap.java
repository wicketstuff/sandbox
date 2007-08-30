package org.apache.wicket.cluster.jetty;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.wicket.cluster.CommunicationModule;
import org.apache.wicket.cluster.SessionProvider;
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
	private SessionProvider sessionManager;
	
	public JettyBootstrap() {
		if (instance != null) {
			throw new IllegalStateException("Only one instance of JettyBootstrap is allowed.");
		}
		instance = this;
		
		sessionManager = new HashSessionManager();
		sessionIdManager = new ForceSessionIdManager();
	}
	

	private static JettyBootstrap instance;
	
	private CommunicationModule communicationModule;
	private SessionComponent sessionComponent;
	private PageStoreComponent pageStoreComponent;
	private NodeInitializerComponent nodeInitializerComponent;
	
	public void configureServer(Server server, WebAppContext context) {
		server.setSessionIdManager(getSessionIdManager());

		SessionHandler handler = new SessionHandler(getSessionManager());
		context.setSessionHandler(handler);
		
		context.addFilter(StartFilter.class, "", Handler.ERROR);

		context.addFilter(SessionComponent.getFilterClass(), "/*", Handler.ALL);
		
		
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
		log.info("Initializing components - phase 1 - before context initialized");

		communicationModule = new TribesCommunicationModule();
		
		sessionComponent = new SessionComponent(communicationModule, sessionManager);
		communicationModule.addMessageListener(sessionComponent);
	}
	
	private void afterServerStarted() {
		
		log.info("Initializing components - phase 2 - after context initialized");
		
		pageStoreComponent = new PageStoreComponent(communicationModule);
		nodeInitializerComponent = new NodeInitializerComponent(communicationModule, sessionManager, pageStoreComponent);
		
		communicationModule.addMessageListener(pageStoreComponent);
		communicationModule.addMessageListener(nodeInitializerComponent);
		communicationModule.addMemberListener(nodeInitializerComponent);
		
		communicationModule.run();
	}
	
	public static class StartFilter implements Filter {
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
				ServletException {
			chain.doFilter(request, response);
			
		}
		public void init(FilterConfig filterConfig) throws ServletException {
			instance.beforeServerStarted();
		}
		public void destroy() {
			
		}
	}
	
	public static class StartServlet extends HttpServlet {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void init(ServletConfig config) throws ServletException {
			instance.afterServerStarted();
		}			
	}

	
	public ForceSessionIdManager getSessionIdManager() {
		return sessionIdManager;
	}
	
	public SessionProvider getSessionManager() {
		return sessionManager;
	}
	
	private static final Logger log = LoggerFactory.getLogger(JettyBootstrap.class);
}
