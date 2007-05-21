/*
 * $Id: HomePage.java,v 1.1 2007/05/15 14:44:25 marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2007/05/15 14:44:25 $
 *
 * ====================================================================
 * Copyright (c) 2007, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages.secure;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.components.markup.html.form.SecureTextField;
import org.apache.wicket.security.pages.SecureTestPage;


/**
 * @author marrink
 *
 */
public class PageA extends SecureTestPage
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
		add(new Label("welcome","Welcome Only logged in users can see this page"));
		add(new SecureTextField("secure",new Model("secure textfield")));
	}

}
