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

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.PrimaryLoginContext;
import org.apache.wicket.security.strategies.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author marrink
 *
 */
public class MockLoginPage extends WebPage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MockLoginPage.class);
	
	private Form form;
	private TextField textField;
	/**
	 * 
	 */
	public MockLoginPage()
	{
		super();
		add(new Label("label","welcome please login"));
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
			LoginContext context=new PrimaryLoginContext();
			((WaspSession)Session.get()).login(context);
			if (!continueToOriginalDestination())
				setResponsePage(Application.get().getHomePage());
			return true;
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(),e);
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
