package org.apache.wicket.security.components;

import org.apache.wicket.IPageMap;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.strategies.ClassAuthorizationStrategy;


/**
 * Basic implementation of a SecurePage. Note that any check added to this page using
 * {@link #setSecurityCheck(ISecurityCheck)} is too late to be considered for
 * {@link IAuthorizationStrategy#isInstantiationAuthorized(Class)} so please check your
 * wasp implementation for details on how to do that. Or see
 * {@link ClassAuthorizationStrategy} for one way of doing it.
 * @author marrink
 */
public class SecurePage extends Page implements ISecurePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param pageMap
	 * @param model
	 */
	public SecurePage(IPageMap pageMap, IModel model)
	{
		super(pageMap, model);
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class, we are to
		// late for that, please check your implementation for how that is handled.
	}

	/**
	 * @param pageMap
	 */
	public SecurePage(IPageMap pageMap)
	{
		super(pageMap);
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class, we are to
		// late for that, please check your implementation for how that is handled.
	}

	/**
	 * 
	 */
	public SecurePage()
	{
		super();
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class, we are to
		// late for that, please check your implementation for how that is handled.
	}

	/**
	 * @param model
	 */
	public SecurePage(IModel model)
	{
		super(model);
		setSecurityCheck(new ComponentSecurityCheck(this));
		// Note this check does not handle the right to instantiate this class, we are to
		// late for that, please check your implementation for how that is handled.
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#setSecurityCheck(org.apache.wicket.security.checks.ISecurityCheck)
	 */
	public final void setSecurityCheck(ISecurityCheck check)
	{
		SecureComponentHelper.setSecurityCheck(this, check);

	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#getSecurityCheck()
	 */
	public final ISecurityCheck getSecurityCheck()
	{
		return SecureComponentHelper.getSecurityCheck(this);
	}

	/**
	 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String action)
	{
		return SecureComponentHelper.isActionAuthorized(this, action);
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
}
