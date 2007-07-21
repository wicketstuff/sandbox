package org.apache.wicket.cluster.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.cluster.CommunicationModule;


public class ClusteredFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		ClusteredHttpServletRequest clusteredRequest = new ClusteredHttpServletRequest(httpServletRequest, communicationModule);
		
		communicationModule.beginMessagesBatch();
		try {
			chain.doFilter(clusteredRequest, response);
		} finally {
			clusteredRequest.flush();
			communicationModule.endMessagesBatch();
		}
	}

	private CommunicationModule communicationModule;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		communicationModule = SessionComponent.getCommunicationModule();
	}

}
