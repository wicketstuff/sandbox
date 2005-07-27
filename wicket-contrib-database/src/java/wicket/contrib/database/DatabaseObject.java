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
package wicket.contrib.database;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;

/**
 * Base class for persistent entities.
 */
@Entity
public abstract class DatabaseObject implements Cloneable, Serializable
{
	private Long id;

	/**
	 * Construct.
	 */
	public DatabaseObject()
	{
	}

	/**
	 * Returns the unique identifier.
	 * 
	 * @return the unique identifier.
	 */
	@Id(generate = GeneratorType.AUTO)
	public final Long getId()
	{
		return id;
	}

	/**
	 * Sets the unique identifier.
	 * 
	 * @param id
	 *            the unique identifier
	 */
	public final void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String clsName = getClass().getName();
		String simpleClsName = clsName.substring(clsName.lastIndexOf('.') + 1);
		return simpleClsName + "{id=" + getId() + "}";
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof DatabaseObject)
		{
			if (id != null)
			{
				return id.equals(((DatabaseObject)obj).id);
			}
			else
			{
				return super.equals(obj);
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		if (id != null)
		{
			return getClass().hashCode() + id.hashCode();
		}
		return super.hashCode();
	}
}
