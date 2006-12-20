/*
 * Created on Mar 21, 2005
 */
package net.sf.ipn.app;

import net.sf.ipn.data.User;

import org.objectstyle.cayenne.access.DataContext;

import wicket.markup.html.WebPage;

/**
 * Ensures that user is authenticated in session. If no user is signed in, a sign in is
 * forced by redirecting the browser to the SignIn page.
 * <p>
 * @author Jonathan Carlson
 */
public abstract class IpnWebPage extends WebPage
{

	public IpnWebPage()
	{
		super();
	}

	/**
	 * Get downcast session object
	 * @return The session
	 */
	public IpnSession getIpnSession()
	{
		return (IpnSession)getSession();
	}

	/**
	 * Overrides the superclass implementation
	 * @see wicket.Page#checkAccess()
	 */
	protected boolean checkAccess()
	{
		// Is user signed in?
		if (getIpnSession().isSignedIn())
			return ACCESS_ALLOWED;
		else
		{ // Force sign in
			redirectToInterceptPage(new SignIn());
			return ACCESS_DENIED;
		}
	}

	/**
	 * Returns the access-point for Cayenne-managed persistent data.
	 * @return the access-point for Cayenne-managed persistent data.
	 */
	protected DataContext getDataContext()
	{
		return getIpnSession().getDataContext();
	}

	/**
	 * Returns the current user for this session.
	 * @return
	 */
	protected User getUser()
	{
		return this.getIpnSession().getUser();
	}

}