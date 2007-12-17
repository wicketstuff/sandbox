/*
 * $Id$ $Revision:
 * 1.43 $ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.database.hibernate;

import java.io.Serializable;

import wicket.contrib.database.IDatabaseObject;
import org.apache.wicket.util.lang.Classes;

/**
 * Base class for persistent entities.
 */
public abstract class HibernateDatabaseObject implements IDatabaseObject, Serializable
{
	/**
	 * The identifier for this database object
	 */
	private Long id;

	/**
	 * Construct.
	 */
	public HibernateDatabaseObject()
	{
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(final Object object)
	{
        if (object instanceof HibernateDatabaseObject)
        {
            final HibernateDatabaseObject that = (HibernateDatabaseObject)object;
            if (isNew())
            {
                return this == that;
            }
            else
            {
                return this.getId().equals(that.getId());
            }
        }
        else
        {
            return false;
        }
	}
    
    /**
	 * Returns the unique identifier.
	 * 
	 * @return the unique identifier.
	 */
	public final Long getId()
	{
		return id;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		if (isNew())
		{
			return super.hashCode();
		}
		else
		{
			return id.hashCode();
		}
	}

	/**
     * @return True if the object has not yet been assigned a valid id
     */
    public final boolean isNew()
    {
        return getId() == null;
    }

	/**
	 * Sets the unique identifier.
	 * 
	 * @param id
	 *            the unique identifier
	 */
	public final void setId(final Long id)
	{
		if (id.longValue() != -1)
		{
			this.id = id;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "[" + Classes.simpleName(getClass()) + " id=" + getId() + "]";
	}
}
