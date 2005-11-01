/*
 * $Id$ $Revision:
 * 1.6 $ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.addons;

import java.util.Locale;

import wicket.Application;
import wicket.IRequestCycleFactory;
import wicket.Request;
import wicket.RequestCycle;
import wicket.Response;
import wicket.Session;
import wicket.addons.db.User;
import wicket.addons.utils.UserCount;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebSession;

/**
 * @author Jonathan Locke
 */
public final class AddonSession extends WebSession
{
    private User user;

    // If true, the sidebar "top rated" will be shown, else "top clicks"
    private boolean topRated = true;
    
    // we are using a modified RequestCycleFactory
	private static IRequestCycleFactory requestCycleFactory = new IRequestCycleFactory()
	{
		public RequestCycle newRequestCycle(Session session, Request request, Response response)
		{
			return new AddonRequestCycle((WebSession)session, (WebRequest)request, response);
		}
	};
    
	/**
	 * Constructor
	 * 
	 * @param application
	 *            The application
	 */
	protected AddonSession(Application application)
	{
		super(application);
	}

	/**
	 * Checks the given username and password, returning a User object if if the
	 * username and password identify a valid user.
	 * 
	 * @param username
	 *            The username
	 * @param password
	 *            The password
	 * @return True if the user was authenticated
	 */
	public final boolean authenticate(final String username, final String password)
	{
        if (user == null)
        {
            user = ((AddonApplication)getApplication()).authenticate(username, password);
            
            if (user != null)
            {
    		    UserCount.addUser(user);

                if (user.getDefaultLocale() != null)
                {
                    setLocale(new Locale(user.getDefaultLocale()));
                }
            }
        }

        return isSignedIn();
	}

	/**
	 * @return True if user is signed in
	 */
	public boolean isSignedIn()
	{
		return user != null;
	}

	/**
	 * @return User
	 */
	public User getUser()
	{
		return this.user;
	}
	
	public boolean isTopRated()
	{
	    return this.topRated;
	}
	
	public void toggleTopRated()
	{
	    this.topRated = !this.topRated;
	}

	/**
	 * @see wicket.protocol.http.WebSession#getRequestCycleFactory()
	 */
	// TODO shouldn't this factory be in Application ?!?!
	protected IRequestCycleFactory getRequestCycleFactory()
	{
		return requestCycleFactory;
	}
}
