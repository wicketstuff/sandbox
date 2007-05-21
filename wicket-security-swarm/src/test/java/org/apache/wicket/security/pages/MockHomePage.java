/*
 * $Id: MockHomePage.java,v 1.1 2006/09/18 08:00:12 Marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2006/09/18 08:00:12 $
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;


/**
 * @author marrink
 */
public class MockHomePage extends SecureWebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public MockHomePage()
	{
		super();
		add(new Label("label", "this page is secured"));
		// In this test setup it is not possible to use a SecurePageLink, if you do want
		// one you need to replace the securitycheck on the link with one that does use
		// the target page.
		add(new PageLink("secret", VerySecurePage.class));
		
		add(new SecurePageLink("link",PageA.class));
	}

	public boolean logoff(Object context)
	{
		return ((WaspSession) Session.get()).logoff(context);
	}
}
