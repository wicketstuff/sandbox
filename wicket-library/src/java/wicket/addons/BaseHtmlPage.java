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

import org.springframework.beans.factory.BeanFactory;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.PageParameters;
import wicket.addons.dao.AddonDaoImpl;
import wicket.addons.dao.UserDaoImpl;
import wicket.markup.html.WebPage;

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
	final protected PageParameters parameters;
    final protected AddonApplicationBorder border;

    final private static int[] pagesPerHour = new int[7 * 24];

    /**
     * Constructor
     */
    public BaseHtmlPage(final PageParameters parameters, final String pageTitle, final AddonApplicationBorder border)
    {
    	this.parameters = parameters;
    	
        this.border = border;
        super.add(border);
    }

    public BaseHtmlPage(final PageParameters parameters, final String pageTitle)
    {
        this(parameters, pageTitle, new AddonApplicationBorder("border", pageTitle));
        border.addRightSidebar();
    }
    
    public AddonDaoImpl getAddonDao()
    {
        BeanFactory fac = ((AddonApplication)this.getApplication()).getBeanFactory();
        return (AddonDaoImpl) fac.getBean("AddonDao");
    }
    
    public UserDaoImpl getUserDao()
    {
        BeanFactory fac = ((AddonApplication)this.getApplication()).getBeanFactory();
        return (UserDaoImpl) fac.getBean("UserDao");
    }
    
    public AddonSession getAddonSession()
    {
        return (AddonSession)this.getSession();
    }
   
    public boolean isUserSignedIn()
    {
        return getAddonSession().isSignedIn();
    }
    
    /**
     * Adding children to instances of this class causes those children to
     * be added to the border child instead.  
     * @see com.voicetribe.wicket.Container#add(com.voicetribe.wicket.Component)
     */
    public MarkupContainer add(final Component child)
    {
        // Add children of the page to the page's border component
        border.add(child);
        return this;
    }
    
    public final int getPageDownloadsPerHour(final int hoursBack)
    {
        int hour = (int)(((System.currentTimeMillis() / 1000 / 60 / 60) - hoursBack) % pagesPerHour.length);
        return pagesPerHour[hour];
    }
    
    /**
     * @see wicket.Component#onRender(wicket.RequestCycle)
     */
    protected void onBeginRender()
    {
        // increase number per page downloads per hour
        int hour = (int)((System.currentTimeMillis() / 1000 / 60 / 60) % pagesPerHour.length);
        pagesPerHour[hour]++;
    }
}
