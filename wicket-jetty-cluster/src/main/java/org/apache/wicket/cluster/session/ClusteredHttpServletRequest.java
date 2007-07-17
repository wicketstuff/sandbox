package org.apache.wicket.cluster.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.MessageSender;


public class ClusteredHttpServletRequest extends HttpServletRequestWrapper {
	
	private final MessageSender sender;
	
	public ClusteredHttpServletRequest(HttpServletRequest delegate, MessageSender sender) {
		super(delegate);
		
		if (sender == null) {
			throw new IllegalArgumentException("Message sender may not be null");
		}
		
		this.sender = sender;
	}
	
	private HttpSession cachedSession = null;
	
	@Override
	public HttpSession getSession() {
		if (cachedSession == null) {
			HttpSession session = super.getSession();
			if (session != null) {
				cachedSession = new ClusteredHttpSession(session, sender);
			}
		}
		return cachedSession;
	}
	
	@Override
	public HttpSession getSession(boolean create) {
		if (cachedSession == null) {
			HttpSession session = super.getSession(create);
			if (session != null) {
				cachedSession = new ClusteredHttpSession(session, sender);
			}
		}
		return cachedSession;
	}
}
