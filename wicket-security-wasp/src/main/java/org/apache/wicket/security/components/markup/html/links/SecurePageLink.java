/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.components.markup.html.links;

import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.checks.ClassSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;


/**
 * Pagelink with visibility / clickability based on user rights. Requires read rights to
 * be visible, and execute rights to be clickable. Note that the target of the link is
 * checked not the link itself. So you probably also want to grant access rights to the
 * target or you might risk not being able to instantiate the page. Instantiation rights
 * for the link are offcourse checked on the link itself, this is usually handled by
 * inherited rights on the page containing the link.
 * @author marrink
 * @see ClassSecurityCheck
 */
public class SecurePageLink extends PageLink implements ISecureComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param c
	 */
	public SecurePageLink(String id, Class c)
	{
		super(id, c);
		setSecurityCheck(new ClassSecurityCheck(c));
	}

	/**
	 * @param id
	 * @param pageLink
	 */
	public SecurePageLink(String id, IPageLink pageLink)
	{
		super(id, pageLink);
		setSecurityCheck(new ClassSecurityCheck(pageLink.getPageIdentity()));
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
