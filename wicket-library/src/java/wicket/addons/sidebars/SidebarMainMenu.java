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
package wicket.addons.sidebars;

import wicket.addons.BaseHtmlPage;
import wicket.addons.Categories;
import wicket.addons.Contact;
import wicket.addons.Home;
import wicket.addons.Login;
import wicket.addons.NewAndUpdatedAddons;
import wicket.addons.Register;
import wicket.addons.Search;
import wicket.addons.WicketLinks;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.protocol.http.WebRequestCycle;

/**
 * @author Juergen Donnerstag
 */
public final class SidebarMainMenu extends Panel
{
    final WebMarkupContainer login;
    final WebMarkupContainer logout;
    
    /**
     * Constructor
     * @param parameters
      */
    public SidebarMainMenu(final String componentName)
    {
        super(componentName);

        // I couldn't use [autolink] because the admin-pages
        // are in a subdirectory. And autolink allows only
        // to access subpackages but not parent packages.
        add(new BookmarkablePageLink("Home", Home.class));
        add(new BookmarkablePageLink("NewAndUpdatedAddons", NewAndUpdatedAddons.class));
        add(new BookmarkablePageLink("Categories", Categories.class));
        add(new BookmarkablePageLink("Search", Search.class));
        add(new BookmarkablePageLink("Contact", Contact.class));
        add(new BookmarkablePageLink("WicketLinks", WicketLinks.class));
        
        login = new WebMarkupContainer("login");
        add(login);
        
        login.add(new BookmarkablePageLink("Login", Login.class));
        login.add(new BookmarkablePageLink("Register", Register.class));
        
        logout = new WebMarkupContainer("logout");
        logout.setVisible(false);
        add(logout);
        
        final Link logoutLink = new Link("Logout")
        {
            public void onClick()
            {
                // Log-out the user and return to Homepage
                getSession().invalidate();
                WebRequestCycle cycle = (WebRequestCycle)getRequestCycle();
                cycle.getResponse().redirect(((WebPage)getPage()).urlFor("0", getApplication().getPages().getHomePage(), null));
            }
        };
        
        logout.add(logoutLink);
    }
    
    /**
     * @see wicket.Component#render()
     */
    public void onBeginRender()
    {
        final boolean loggedIn = ((BaseHtmlPage)getPage()).isUserSignedIn();
        login.setVisible(!loggedIn);
        logout.setVisible(loggedIn);
    }
}
