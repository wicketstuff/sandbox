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
import java.util.Date;

import wicket.addons.dao.base.BaseUser;

/**
 * @author Juergen Donnerstag
 */
public class User extends BaseUser implements Serializable, ILastModified, IDeleted, IIdentifiable
{
	public String getNameLastNameFirst()
	{
		return getLastname() + ", " + getFirstname();
	}

	public String getNameFirstNameFirst()
	{
		return getFirstname() + getLastname();
	}
    
	protected void initialize () 
	{
	    if (getLastModified() == null)
	    {
	        setLastModified(new Date(System.currentTimeMillis()));
	    }
	}
}
