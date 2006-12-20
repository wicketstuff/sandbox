/*
 * Created on Mar 21, 2005
 */
package net.sf.ipn.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ipn.app.page.Home;
import net.sf.ipn.app.page.MyProfile;
import net.sf.ipn.app.page.PrayerItems;
import net.sf.ipn.data.User;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.conf.BasicServletConfiguration;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

import wicket.Application;
import wicket.protocol.http.WebSession;

/**
 * @author Jonathan Carlson Manages session-scoped information for this Wicket-based
 *         application
 */
public class IpnSession extends WebSession
{

	private User user = null;
	private DataContext dataContext = null;
	private List tabs = null;

	/**
	 * @param application
	 */
	public IpnSession(Application application)
	{
		super(application);
	}

	public final User authenticate(String username, String password)
	{
		this.user = null;
		SelectQuery query = new SelectQuery(User.class, ExpressionFactory
				.matchExp("name", username)
				.andExp(ExpressionFactory.matchExp("password", password)));
		List results = getDataContext().performQuery(query);
		if (results.size() == 0)
			return null;
		this.user = (User)results.get(0);
		return this.user;
	}

	public boolean isSignedIn()
	{
		return this.user != null;
	}

	public DataContext getDataContext()
	{
		if (this.dataContext == null)
		{
			this.dataContext = BasicServletConfiguration.getDefaultContext(getHttpSession());
		}
		return this.dataContext;
	}

	public List getTabs()
	{
		if (this.tabs == null)
		{
			List tempTabs = new ArrayList();
			tempTabs.add(new IpnTab(this, Home.class, IpnBorderWebPage.HOME_TAB));
			tempTabs.add(new IpnTab(this, MyProfile.class, IpnBorderWebPage.MY_PROFILE_TAB));
			tempTabs.add(new IpnTab(this, PrayerItems.class, IpnBorderWebPage.PRAYER_ITEMS_TAB));

			this.tabs = Collections.unmodifiableList(tempTabs);
		}

		return this.tabs;
	}

	public User getUser()
	{
		return this.user;
	}

}