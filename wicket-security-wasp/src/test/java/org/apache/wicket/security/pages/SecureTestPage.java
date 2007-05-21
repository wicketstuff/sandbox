package org.apache.wicket.security.pages;


import org.apache.wicket.PageParameters;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecurePage;
import org.apache.wicket.security.components.SecureComponentHelper;


/**
 * Secure version of {@link BasePage}. This class only allows logged in users with
 * sufficient rights to access the page.
 * @author marrink
 * @see KryptonStrategy
 */
public class SecureTestPage extends BasePage implements ISecurePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SecureTestPage()
	{
		super();
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param model
	 */
	public SecureTestPage(IModel model)
	{
		super(model);
		setSecurityCheck(new ComponentSecurityCheck(this));
	}

	/**
	 * @param parameters
	 */
	public SecureTestPage(PageParameters parameters)
	{
		super(parameters);
		setSecurityCheck(new ComponentSecurityCheck(this));
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
