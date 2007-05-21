/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.components.markup.html.form;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.models.ISecureModel;

/**
 * Textfield which automaticly switches between read and write mode based on the user
 * rights. By default it does not consider {@link ISecureModel}, unless told to do so by
 * either removing the securitycheck or by using the specialized constructor.
 * @author marrink
 */
public class SecureTextField extends TextField implements ISecureComponent
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 */
	public SecureTextField(String id)
	{
		super(id);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param type
	 */
	public SecureTextField(String id, Class type)
	{
		super(id, type);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param object
	 */
	public SecureTextField(String id, IModel object)
	{
		super(id, object);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param model
	 * @param checkModel tells the {@link ComponentSecurityCheck} to also check this
	 *            model.
	 */
	public SecureTextField(String id, ISecureModel model, boolean checkModel)
	{
		super(id, model);
		setSecurityCheck(new ComponentSecurityCheck(this, checkModel));
	}

	/**
	 * @param id
	 * @param model
	 * @param type
	 */
	public SecureTextField(String id, IModel model, Class type)
	{
		super(id, model, type);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param id
	 * @param model
	 * @param checkModel tells the {@link ComponentSecurityCheck} to also check this
	 *            model.
	 * @param type
	 */
	public SecureTextField(String id, ISecureModel model, boolean checkModel, Class type)
	{
		super(id, model, type);
		setSecurityCheck(new ComponentSecurityCheck(this, checkModel));
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#getSecurityCheck()
	 */
	public ISecurityCheck getSecurityCheck()
	{
		return SecureComponentHelper.getSecurityCheck(this);
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String waspAction)
	{
		return SecureComponentHelper.isActionAuthorized(this, waspAction);
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(org.apache.wicket.security.actions.AbstractWaspAction)
	 */
	public boolean isActionAuthorized(AbstractWaspAction action)
	{
		return SecureComponentHelper.isActionAuthorized(this, action);
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return SecureComponentHelper.isAuthenticated(this);
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#setSecurityCheck(org.apache.wicket.security.checks.ISecurityCheck)
	 */
	public void setSecurityCheck(ISecurityCheck check)
	{
		SecureComponentHelper.setSecurityCheck(this, check);
	}
}
