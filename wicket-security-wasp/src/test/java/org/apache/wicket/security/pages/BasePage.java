/*
 * $Id: org.eclipse.jdt.ui.prefs,v 1.1 2007/05/15 14:44:25 marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2007/05/15 14:44:25 $
 *
 * ====================================================================
 * Copyright (c) 2007, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.WaspWebApplication;


/**
 * Basic page. This page is accessible by everyone.
 * @author marrink
 *
 */
public class BasePage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BasePage()
	{
	}

	/**
	 * @param model
	 */
	public BasePage(IModel model)
	{
		super(model);
	}

	/**
	 * @param parameters
	 */
	public BasePage(PageParameters parameters)
	{
		super(parameters);
	}
	/**
	 * @return
	 */
	protected final WaspSession getSecureSession()
	{
		return(WaspSession)Session.get();
	}
	protected final WaspWebApplication getWaspApplication()
	{
		return (WaspWebApplication)Application.get();
	}
}
