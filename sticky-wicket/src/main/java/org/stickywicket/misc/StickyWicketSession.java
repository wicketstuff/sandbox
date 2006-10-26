// $Id: $
package org.stickywicket.misc;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.stickywicket.jcr.JcrUtil;
import org.stickywicket.renderer.NodeType;

import wicket.protocol.http.WebSession;

public class StickyWicketSession extends WebSession {

	private static final long serialVersionUID = 1L;

	private transient Session jcrSession;

	public StickyWicketSession(StickyWicketApplication application) {
		super(application);
	}

	public Session getJcrSession() {
		if (jcrSession != null) {
			return jcrSession;
		}
		try {
			jcrSession = StickyWicketApplication.get().getRepository().login(
					new SimpleCredentials("userid", "".toCharArray()));
			JcrUtil.getOrCreateNode(jcrSession.getRootNode(), "content");

			Node root = jcrSession.getRootNode();

			// TODO need node/prop name prefixes
			// TODO need real jcr references

			Node components = root.addNode("components");
			Node component1 = components.addNode("component1");
			component1.setProperty("type", NodeType.CONTENT.toString());
			component1.setProperty("content", "I AM COMPONENT 1 and this is: ["
					+ "<sticky:container sticky:id=\"container\"/>" + "]");
			
			Node c1container = component1.addNode("container");
			c1container.setProperty("type", NodeType.CONTAINER.toString());

			Node c1refc2 = c1container.addNode("c1refc2");
			c1refc2.setProperty("type", NodeType.CONTENT_REFERENCE.toString());
			c1refc2.setProperty("ref", "/components/component2");

			Node component2 = components.addNode("component2");
			component2.setProperty("type", NodeType.CONTENT.toString());
			component2.setProperty("content", "I AM COMPONENT 2");

			Node pages = root.addNode("pages");
			Node page = pages.addNode("testpage");
			page.setProperty("type", NodeType.CONTENT.toString());
			page
					.setProperty(
							"content",
							"<html><body>this is a test page. this is where container 1 goes "
									+ "<div style='border:1px solid black;'>"
									+ "<sticky:container sticky:id=\"container1\"/>"
									+ "</div>"
									+ "and this is after container1</body></html>");

			Node container1 = page.addNode("container1");
			container1.setProperty("type", NodeType.CONTAINER.toString());

			Node component1ref = container1.addNode("component1ref");
			component1ref.setProperty("type", NodeType.CONTENT_REFERENCE
					.toString());
			component1ref.setProperty("ref", "/components/component1");

			Node component2ref = container1.addNode("component1ref2");
			component2ref.setProperty("type", NodeType.CONTENT_REFERENCE
					.toString());
			component2ref.setProperty("ref", "/components/component2");

		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
		return jcrSession;
	}

	public static StickyWicketSession get() {
		return (StickyWicketSession) WebSession.get();
	}
}
