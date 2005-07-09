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

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.addons.hibernate.AddonDaoSupport;
import wicket.addons.hibernate.User;
import wicket.addons.hibernate.UserDaoSupport;
import wicket.addons.utils.UserCount;
import wicket.protocol.http.WebApplication;
import wicket.util.time.Duration;

/**
 * @author Juergen Donnerstag
 */
public class AddonApplication extends WebApplication
{
    final private XmlBeanFactory beanFactory;
    
    final private UserCount userCount;
    
    private boolean adminEnabled;
    
    private static Timer timer;
    
    /**
     * Constructor.
     */
    public AddonApplication()
    {
        getPages().setHomePage(Home.class);
        getSettings().setResourcePollFrequency(Duration.ONE_SECOND);
        
        beanFactory = new XmlBeanFactory(new ClassPathResource("spring-dao.xml"));
        PropertyPlaceholderConfigurer cfg = (PropertyPlaceholderConfigurer) beanFactory.getBean("propertyConfigurer");
        if (beanFactory != null)
        {
            cfg.postProcessBeanFactory(beanFactory);
        }
        
        userCount = new UserCount();
        
/*        
        // Just to test automatic reloading of apps properties
        IStringResourceLoader loader = new ApplicationStringResourceLoader(this);
        loader.get(null, "test", Locale.getDefault(), null);
*/        

/* Temporarily disabled. but it works fine         
        if (timer == null)
        {
	        timer = new Timer(true);
	        timer.schedule(new UpdateStatisticsTask(), 60 * 1000, 60 * 1000);
        }
*/        
    }
    
    /**
     * Only subclasses shall be able to replace it.
     * 
     * @param enable
     */
    protected void setAdminEnabled(final boolean enable)
    {
        this.adminEnabled = enable;
    }

    public boolean getAdminEnabled()
    {
        return this.adminEnabled;
    }
    
    /**
     * @return Returns the beanFactory.
     */
    public XmlBeanFactory getBeanFactory()
    {
        return beanFactory;
    }
    
    public AddonDaoSupport getAddonDao()
    {
        return (AddonDaoSupport) getBeanFactory().getBean("AddonDao");
    }
    
    /**
     * @see wicket.protocol.http.WebApplication#getSessionFactory()
     */
    public ISessionFactory getSessionFactory()
    {
        return new ISessionFactory()
        {
            public Session newSession()
            {
                return new AddonSession(AddonApplication.this);
            }
        };
    }
    
	public final User authenticate(final String username, final String password)
	{
        final UserDaoSupport dao = (UserDaoSupport) getBeanFactory().getBean("UserDaoTarget");
        return dao.login(username, null, password);
	}
	
	private class UpdateStatisticsTask extends TimerTask
	{
	    private boolean active = false;
	    public void run()
	    {
	        if (active == false)
	        {
	            active = true;
	            try
	            {
		            getAddonDao().updateAddonClicksStatistics();
		            getAddonDao().updateAddonAvgRating();
	            }
	            finally
	            {
	                active = false;
	            }
	        }
	    }
	}
}
