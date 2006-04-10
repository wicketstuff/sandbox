/*
 * $Id$ $Revision:
 * 408 $ $Date$
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import wicket.Response;
import wicket.addons.db.User;
import wicket.addons.utils.WicketHibernateUtils;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebRequestCycle;
import wicket.protocol.http.WebSession;

/**
 * Special request cycle for this application that opens and closes a hibernate
 * session for each request.
 */
public final class AddonRequestCycle extends WebRequestCycle
{
	private static Log log = LogFactory.getLog(AddonRequestCycle.class);

	private WicketHibernateUtils hibernateUtils = new WicketHibernateUtils();

	private User user;

	/**
	 * Construct.
	 * 
	 * @param session
	 *            session object
	 * @param request
	 *            request object
	 * @param response
	 *            response object
	 * @param sessionFactory
	 *            hibernate session factory
	 */
	public AddonRequestCycle(WebSession session, WebRequest request, Response response)
	{
		super(session, request, response);
	}

	/**
	 * @see wicket.RequestCycle#onBeginRequest()
	 */
	protected void onBeginRequest()
	{
		hibernateUtils.onBeginRequest();
	}

	/**
	 * @see wicket.RequestCycle#onEndRequest()
	 */
	protected void onEndRequest()
	{
		hibernateUtils.onEndRequest();
	}

	/**
	 * 
	 * @return
	 */
	public Session getCurrentHibernateSession()
	{
		return hibernateUtils.getCurrentHibernateSession();
	}

	/**
	 * 
	 * @return
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(final User user)
	{
		this.user = user;
	}
}