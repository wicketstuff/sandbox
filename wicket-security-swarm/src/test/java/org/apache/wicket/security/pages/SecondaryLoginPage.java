/*
 * $Id: MockLoginPage.java,v 1.1 2006/09/18 08:00:12 Marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2006/09/18 08:00:12 $
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.SecondaryLoginContext;
import org.apache.wicket.security.strategies.LoginException;



/**
 * @author marrink
 *
 */
public class SecondaryLoginPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log=LogFactory.getLog(SecondaryLoginPage.class);
	
	private Form form;
	private TextField textField;
	/**
	 * 
	 */
	public SecondaryLoginPage()
	{
		super();
		add(new Label("label","welcome please login to continue to the secret"));
		add(form=new Form("form"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void onSubmit()
			{
				login(get("username").getModelObjectAsString());
			}
			});
		form.add(textField=new TextField("username",new Model()));
	}
	public boolean login(String username)
	{
		try
		{
			LoginContext context=new SecondaryLoginContext();
			((WaspSession)Session.get()).login(context);
			if (!continueToOriginalDestination())
				setResponsePage(Application.get().getHomePage());
			return true;
		}
		catch (LoginException e)
		{
			log.error(e,e);
		}
		return false;
	}
	
	public final Form getForm()
	{
		return form;
	}
	
	public final TextField getTextField()
	{
		return textField;
	}

}
