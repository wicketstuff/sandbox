package org.apache.wicket.cluster.session;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class HttpSessionWrapper implements HttpSession {

	private final HttpSession delegate;
	
	public HttpSessionWrapper(HttpSession delegate) {
		this.delegate = delegate;
	}
	
	public Object getAttribute(String name) {
		return delegate.getAttribute(name);
	}

	public Enumeration<?> getAttributeNames() {
		return delegate.getAttributeNames();
	}

	public long getCreationTime() {
		return delegate.getCreationTime();
	}

	public String getId() {
		return delegate.getId();
	}

	public long getLastAccessedTime() {
		return delegate.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return delegate.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return delegate.getServletContext();
	}

	public HttpSessionContext getSessionContext() {
		return delegate.getSessionContext();
	}

	public Object getValue(String name) {
		return delegate.getValue(name);
	}

	public String[] getValueNames() {
		return delegate.getValueNames();
	}

	public void invalidate() {
		delegate.invalidate();
	}

	public boolean isNew() {
		return delegate.isNew();
	}

	public void putValue(String name, Object value) {
		delegate.putValue(name, value);
	}

	public void removeAttribute(String name) {
		delegate.removeAttribute(name);
	}

	public void removeValue(String name) {
		delegate.removeValue(name);
	}

	public void setAttribute(String name, Object value) {
		delegate.setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int interval) {
		delegate.setMaxInactiveInterval(interval);
	}

}
