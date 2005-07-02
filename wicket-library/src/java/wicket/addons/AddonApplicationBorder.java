/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package wicket.addons;

import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.addons.sidebars.SidebarAdminMenu;
import wicket.addons.sidebars.SidebarHostedBy;
import wicket.addons.sidebars.SidebarMainMenu;
import wicket.addons.sidebars.SidebarNews;
import wicket.addons.sidebars.SidebarRSS;
import wicket.addons.sidebars.SidebarRegisteredUsers;
import wicket.addons.sidebars.SidebarTopClicks;
import wicket.addons.sidebars.SidebarTopRated;
import wicket.addons.sidebars.SidebarUserMenu;
import wicket.markup.html.basic.Label;
import wicket.markup.html.border.Border;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public class AddonApplicationBorder extends Border
{
    public static final String PAGE_SPECIFIC_HEADER = "pageSpecificHeader";

    private final ListView sidebarLeft;

    /**
     * Constructor
     * @param componentName The name of this component
     */
    public AddonApplicationBorder(final String componentName, final String pageTitle)
    {
        super(componentName);

        add(new Label("username", new PropertyModel(this, "nickname")));
        
        // Sidebar Left
        List sidebarLeftData = new ArrayList();
        sidebarLeftData.add(new SidebarMainMenu("sidebarLeftContent"));
        
        if (((AddonApplication)getApplication()).getAdminEnabled() == true)
        {
            sidebarLeftData.add(new SidebarAdminMenu("sidebarLeftContent"));
        }

        if (((AddonSession)getSession()).getUserId() > 0)
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
 
        // <html><head><title>: set page specific title
/*
        add(new HtmlComponent("headerTitle")
        {
            protected void handleBody(final RequestCycle cycle, final MarkupStream markupStream, final ComponentTag openTag)
            {
                replaceBody(cycle, markupStream, openTag, "Appfuse ~ " + pageTitle);
            }
        });

        // Page error message
        final Label errorMsg = new Label("errorMsg", "");
        errorMsg.setVisible(false);
        add(errorMsg);

        // Visibility and model objects may get changed during onRender 
        this.userStatus = new Label("userStatus", "Logged in as: ");
        add(this.userStatus);
        this.userName = new Label("userName", "<username>");
        add(this.userName);
*/
        // Some simple links. May be disabled during onRender, which
        // is why they are no [autolinks]. I need the id.
/*        
        this.logout = new BookmarkablePageLink("logout", Logout.class);
        add(this.logout);
        this.gotoHome = new BookmarkablePageLink("gotoHome", Home.class);
        add(this.gotoHome);
        
        this.activeUsers = new BookmarkablePageLink("activeUsers", ActiveUsers.class);
        this.activeUsers.setAutoEnable(false);
        this.activeUsers.setBeforeDisabledLink("");
        this.activeUsers.setAfterDisabledLink("");
        this.activeUsers.setEnabled(false); // default; determine at render time
        add(this.activeUsers);
        
        // Dummy model to be filled during onRender
        this.userCounter = new Label("userCounter", new UserCount(), "currentUserCount");

        add(this.userCounter);

        // Small menu at the right. Will be disabled on login screen
        TreeModel menuModel = buildMenuTreeModel();
        add(new HtmlListMenu("menuOnTheRight", menuModel));
*/
//        add(new Label("copyright", "Version @APPVERSION@ &middot; Copyright &copy; @COPYRIGHT-YEAR@ &middot;"));
    }

    public String getNickname()
    {
        final String name = ((BaseHtmlPage)getPage()).getAddonSession().getUserLogonName();

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
        sidebarRightData.add(new SidebarTopRated("sidebarRightContent", (BaseHtmlPage)getPage()));
        sidebarRightData.add(new SidebarTopClicks("sidebarRightContent", (BaseHtmlPage)getPage()));
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
    
    /**
     * Each Page may add its own <html><head> specific tags.
     */
    public final void addHeader(final Component header)
    {
        if (!header.getId().equals(PAGE_SPECIFIC_HEADER))
        {
            throw new IllegalArgumentException("Header Component must have name 'pageSpecificHeader'");
        }

        add(header);
    }
    
    /**
     * 
     * @see com.voicetribe.wicket.Component#onRender(com.voicetribe.wicket.RequestCycle)
     */
    protected void onRender()
    {
        
        // Some components depend on the user logged in
/*        
        final User user = getUser();
        if (user != null)
        {
            // Link will be enabled for administrators
            this.activeUsers.setEnabled(user.hasRole(User.ROLE_ADMIN));
            
            // Show user name (not log-in id)
            this.userName.setModel(new PropertyModel(new Model(UserDetails.get(user.getName())), "username"));
        }
        else
        {
            // User not yet logged in. Disable some of the components
            this.userStatus.setVisible(false);
            this.userName.setVisible(false);
            this.logout.setVisible(false);
        }
*/
        // If no page specific component, add an invisible default to avoid
        // error msg.
/*        
        if (get(PAGE_SPECIFIC_HEADER) == null)
        {
            // invisible Default 
            final Label pageSpecificHeader = new Label(PAGE_SPECIFIC_HEADER, "");
            pageSpecificHeader.setVisible(false);
            add(pageSpecificHeader);
        }
*/
        // update logged-in-user-counter
        //this.userCounter.setModelObject(UserCount.getCurrentUserCount());
        //this.userCounter.invalidateModel();

        // now continue render
        super.onRender();
    }

    /**
     * get the user
     * @return
     */
/*    
    private final User getUser()
    {
        Authenticator authenticator = Authenticator.forSession(getSession());
        if (authenticator == null)
        {
            return null;
        }
        
        return authenticator.getUser();
    }
*/        
}
