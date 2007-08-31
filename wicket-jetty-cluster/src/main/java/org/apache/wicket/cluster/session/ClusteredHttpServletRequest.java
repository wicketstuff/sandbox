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
