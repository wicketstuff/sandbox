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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.components.markup.html.form.SecureTextField;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;


/**
 * @author marrink
 */
public class PageA extends SecureWebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PageA()
	{
		super();
		add(new Label("label", "this page shows security inheritance"));
		add(new SecurePageLink("link",MockHomePage.class));
		add(new SecureTextField("invisible"));
		add(new SecureTextField("readonly"));
		add(new TextField("unchecked"));
	}

	public boolean logoff(Object context)
	{
		return ((WaspSession) Session.get()).logoff(context);
	}
}
