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
package wicket.addons.dao;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Juergen Donnerstag
 */
public class Click implements Serializable, IIdentifiable
{
	private int id;

	private Addon addon;

	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	private String sessionId;
	private String remoteIPAddress;

	/**
	 * @return Returns the addon.
	 */
	public Addon getAddon()
	{
		return addon;
	}

	/**
	 * @param addon
	 *           The addon to set.
	 */
	public void setAddon(Addon addon)
	{
		this.addon = addon;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id
	 *           The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return Returns the lastModified.
	 */
	public Timestamp getTimestamp()
	{
		return timestamp;
	}

	/**
	 * @param lastModified
	 *           The lastModified to set.
	 */
	public void setTimestamp(final Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * @return Returns the remoteIPAddress.
	 */
	public String getRemoteIPAddress()
	{
		return remoteIPAddress;
	}

	/**
	 * @param remoteIPAddress
	 *           The remoteIPAddress to set.
	 */
	public void setRemoteIPAddress(String remoteIPAddress)
	{
		this.remoteIPAddress = remoteIPAddress;
	}

	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * @param sessionId
	 *           The sessionId to set.
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}
}
