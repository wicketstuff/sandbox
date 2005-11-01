///////////////////////////////////////////////////////////////////////////////////
//
// Created Jun 25, 2004
//
// Copyright 2004, Jonathan W. Locke
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package wicket.addons;

import java.util.ArrayList;
import java.util.List;

import wicket.addons.ServiceLocator;

import wicket.PageParameters;
import wicket.addons.db.User;
import wicket.addons.services.AddonService;
import wicket.addons.services.UserService;
import wicket.addons.sidebars.SidebarAdminMenu;
import wicket.addons.sidebars.SidebarHostedBy;
import wicket.addons.sidebars.SidebarMainMenu;
import wicket.addons.sidebars.SidebarNews;
import wicket.addons.sidebars.SidebarRSS;
import wicket.addons.sidebars.SidebarRegisteredUsers;
import wicket.addons.sidebars.SidebarTopClicks;
import wicket.addons.sidebars.SidebarTopRated;
import wicket.addons.sidebars.SidebarUserMenu;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.PropertyModel;

/**
 * Ensures that user is authenticated in session.  If no user is signed in, a sign
 * in is forced by redirecting the browser to the SignIn page.  
 * <p>
 * This base class also creates a border for each page subclass, automatically adding 
 * children of the page to the border.  This accomplishes two important things: 
 * (1) subclasses do not have to repeat the code to create the border navigation and 
 * (2) since subclasses do not repeat this code, they are not hardwired to page 
 * navigation structure details
 *  
 * @author Juergen Donnerstag
 */
public class BaseHtmlPage extends WebPage
{
    public static final String PAGE_SPECIFIC_HEADER = "pageSpecificHeader";

    private final ListView sidebarLeft;
    
	final protected PageParameters parameters;

    final private static int[] pagesPerHour = new int[7 * 24];

    public BaseHtmlPage(final PageParameters parameters, final String pageTitle)
    {
        this.parameters = parameters;
        
        add(new Label("username", new PropertyModel(this, "loginName")));
        
        // Sidebar Left
        List sidebarLeftData = new ArrayList();
        sidebarLeftData.add(new SidebarMainMenu("sidebarLeftContent"));
        
        if (((AddonApplication)getApplication()).getAdminEnabled() == true)
        {
            sidebarLeftData.add(new SidebarAdminMenu("sidebarLeftContent"));
        }

        if (getUser() != null)
        {
            sidebarLeftData.add(new SidebarUserMenu("sidebarLeftContent"));
        }
        
        sidebarLeftData.add(new SidebarRegisteredUsers("sidebarLeftContent"));
        sidebarLeftData.add(new SidebarRSS("sidebarLeftContent"));
        sidebarLeftData.add(new SidebarHostedBy("sidebarLeftContent"));
        
        // Add table of existing comments
        sidebarLeft = new ListView("sidebarsLeft", sidebarLeftData)
        {
            public void populateItem(final ListItem listItem)
            {
                final Panel value = (Panel) listItem.getModelObject();
                listItem.add(value);
            }
            
        	protected void renderItem(final ListItem listItem, final boolean lastItem)
        	{
        	    if (listItem.getModelObject() instanceof SidebarUserMenu)
        	    {
        	        final boolean loggedIn = ((BaseHtmlPage)getPage()).getAddonSession().isSignedIn();
        	        listItem.setVisible(loggedIn);
        	    }
        	    
        		super.renderItem(listItem);
        	}
        };

        add(sidebarLeft);
 
        addRightSidebar();
    }

    public String getLoginName()
    {
        User user = getUser();
        if (user == null)
        {
            return null;
        }
        
        final String name = user.getLoginName();
        if (name == null)
        {
            return null;
        }
        
        return "User: " + name;
    }
    
    protected void addRightSidebar()
    {
        // Sidebar Right
        List sidebarRightData = new ArrayList();
        sidebarRightData.add(new SidebarTopRated("sidebarRightContent", this));
        sidebarRightData.add(new SidebarTopClicks("sidebarRightContent", this));
        sidebarRightData.add(new SidebarNews("sidebarRightContent"));
        
        // Add table of existing comments
        final ListView sidebarRight = new ListView("sidebarsRight", sidebarRightData)
        {
            public void populateItem(final ListItem listItem)
            {
                final Panel value = (Panel) listItem.getModelObject();
                listItem.add(value);
            }
        };

        add(sidebarRight);
    }
    
    public AddonService getAddonService()
    {
		return ServiceLocator.instance().getAddonService();
    }
    
    public UserService getUserService()
    {
		return ServiceLocator.instance().getUserService();
    }
    
    public AddonSession getAddonSession()
    {
        return (AddonSession)this.getSession();
    }

    public User getUser()
    {
        User user = ((AddonRequestCycle)getRequestCycle()).getUser();
        if (user == null)
        {
            user = getAddonSession().getUser();
            if (user != null)
            {
	            getUserService().lock(user, 0);
	            ((AddonRequestCycle)getRequestCycle()).setUser(user);
            }
        }
        
        return user;
    }
    
    public boolean isUserSignedIn()
    {
        return getAddonSession().isSignedIn();
    }

    public final int getPageDownloadsPerHour(final int hoursBack)
    {
        int hour = (int)(((System.currentTimeMillis() / 1000 / 60 / 60) - hoursBack) % pagesPerHour.length);
        return pagesPerHour[hour];
    }
    
    /**
     * @see wicket.Component#onRender(wicket.RequestCycle)
     */
    protected void onBeginRequest()
    {
        // increase number per page downloads per hour
        int hour = (int)((System.currentTimeMillis() / 1000 / 60 / 60) % pagesPerHour.length);
        pagesPerHour[hour]++;
    }
    
    /**
	 * @see wicket.Page#checkAccess()
	 */
	protected boolean checkAccess()
	{
	    if (((AddonApplication)getApplication()).getAdminEnabled() == false)
	    {
	        return super.checkAccess();
	    }
	    
	    if (this instanceof Login)
	    {
	        return super.checkAccess();
	    }
	    
        // Is a user signed into this cycle's session?
        boolean signedIn = getAddonSession().isSignedIn();

        // If nobody is signed in
        if (!signedIn)
        {
            // Redirect request to SignIn page
            redirectToInterceptPage(newPage(Login.class));
        }

        // Return true if someone is signed in and access is okay
        return signedIn;
	}
}
