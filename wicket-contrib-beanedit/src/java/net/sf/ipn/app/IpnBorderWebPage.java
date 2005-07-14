/*
 * Created on Mar 21, 2005
 */
package net.sf.ipn.app;

import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;

/**
 * Ensures that user is authenticated in session. If no user is signed in, a sign in is
 * forced by redirecting the browser to the SignIn page.
 * <p>
 * This base class also creates a border for each page subclass, automatically adding
 * children of the page to the border. This accomplishes two important things: (1)
 * subclasses do not have to repeat the code to create the border navigation and (2) since
 * subclasses do not repeat this code, they are not hardwired to page navigation structure
 * details
 * @author Jonathan Locke (original author)
 * @author Jonathan Carlson
 */
public abstract class IpnBorderWebPage extends IpnWebPage
{

	// Do these belong elsewhere?
	public static final String HOME_TAB = "Home";
	public static final String MY_PROFILE_TAB = "My Profile";
	public static final String PRAYER_ITEMS_TAB = "Prayer Items";

	private IpnBorder border;

	public IpnBorderWebPage()
	{
		super();
		super.add(getMainBorder());
	}

	/**
	 * Adding children to instances of this class causes those children to be added to the
	 * border child instead.
	 * @see wicket.MarkupContainer#add(wicket.Component)
	 */
	public MarkupContainer add(final Component child)
	{
		getMainBorder().add(child);
		return this;
	}


	/**
	 * Removing children from instances of this class causes those children to be removed
	 * from the border child instead.
	 * @see wicket.MarkupContainer#removeAll()
	 */
	public void removeAll()
	{
		getMainBorder().removeAll();
	}

	/**
	 * Replacing children on instances of this class causes those children to be replaced
	 * on the border child instead.
	 * @see wicket.MarkupContainer#replace(wicket.Component)
	 */
	public MarkupContainer replace(Component child)
	{
		return getMainBorder().replace(child);
	}

	/**
	 * Lazily initialize the border field if needed
	 */
	protected IpnBorder getMainBorder()
	{
		if (this.border == null)
		{
			this.border = new IpnBorder("mainBorder");
		}
		return this.border;
	}

	public abstract String getTabLabel();

	protected List getMainTabs()
	{
		return getIpnSession().getTabs();
	}

	/**
	 * @param tab
	 * @return true if this page belongs to the given main tab
	 */
	public boolean belongsToTab(IpnTab tab)
	{
		return tab.getLabel().equals(getTabLabel());
	}


}