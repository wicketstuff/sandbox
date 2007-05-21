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

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.markup.html.form.SecureTextField;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.pages.SecureTestPage;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


/**
 * @author marrink
 *
 */
public class PageD extends SecureTestPage
{

	private final class SecureModel implements ISecureModel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String value = "foo";

		public boolean isAuthenticated(Component component)
		{
			return getStrategy().isModelAuthenticated(this, component);
		}

		/**
		 * @return
		 */
		private WaspAuthorizationStrategy getStrategy()
		{
			return ((WaspAuthorizationStrategy)getSecureSession().getAuthorizationStrategy());
		}

		public boolean isAuthorized(Component component, WaspAction action)
		{
			return getStrategy().isModelAuthorized(this, component, action);
		}

		public Object getObject()
		{
			return value;
		}

		public void setObject(Object object)
		{
			value=String.valueOf(object);
		}

		public void detach()
		{
			//noop
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PageD()
	{
		add(new Label("welcome","Welcome Only logged in users can see this page"));
		
		add(new SecureTextField("componentcheck",new Model("secure textfield")));
		add(new TextField("modelcheck",new SecureModel()));
		add(new SecureTextField("both",new SecureModel()));
		add(new SecureTextField("bothcheck",new SecureModel(),true));
	}

}
