/*
 * $Id: HomePage.java,v 1.1 2007/05/15 14:44:25 marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2007/05/15 14:44:25 $
 *
 * ====================================================================
 * Copyright (c) 2007, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages.insecure;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.pages.BasePage;


/**
 * @author marrink
 *
 */
public class HomePage extends BasePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HomePage()
	{
		add(new Label("welcome","Welcome Anyone can see this page"));
	}

}
