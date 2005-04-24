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
public class Comment implements Serializable, IIdentifiable
{
    private int id;

    private User user;
    
    private Addon addon;

    private Timestamp lastModified = new Timestamp(System.currentTimeMillis()); 

    private String comment;

	/**
	 * @return Returns the addon.
	 */
	public Addon getAddon()
	{
		return addon;
	}
	/**
	 * @param addon The addon to set.
	 */
	public void setAddon(Addon addon)
	{
		this.addon = addon;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment()
	{
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * @return Returns the lastModified.
	 */
	public Timestamp getLastModified()
	{
		return lastModified;
	}
	/**
	 * @param lastModified The lastModified to set.
	 */
	public void setLastModified(Timestamp lastModified)
	{
		this.lastModified = lastModified;
	}
	/**
	 * @return Returns the user.
	 */
	public User getUser()
	{
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(User user)
	{
		this.user = user;
	}
}
