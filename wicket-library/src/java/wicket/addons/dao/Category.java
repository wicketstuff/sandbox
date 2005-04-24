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
public class Category implements Serializable, ILastModified, IDeleted, IIdentifiable
{
    private int id;

    private String name;

    private String description;

    private Timestamp lastModified;

    private Timestamp deleted;

    private int createdBy;

    private int count = -1;
    
    public Category()
    {
        ; // empty
    }

    /**
     * @return Returns the createdBy.
     */
    public int getCreatedBy()
    {
        return createdBy;
    }

    /**
     * @param createdBy
     *            The createdBy to set.
     */
    public void setCreatedBy(int createdBy)
    {
        this.createdBy = createdBy;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
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
     *            The id to set.
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
     * @param lastModified
     *            The lastModified to set.
     */
    public void setLastModified(Timestamp lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * @return Returns the count.
     */
    public int getCount()
    {
        return count;
    }
    /**
     * @param count The count to set.
     */
    public void setCount(int count)
    {
        this.count = count;
    }
    /**
     * @return Returns the deleted.
     */
    public Timestamp getDeleted()
    {
        return deleted;
    }
    /**
     * @param deleted The deleted to set.
     */
    public void setDeleted(Timestamp deleted)
    {
        this.deleted = deleted;
    }
    
    public String toString()
    {
        return name + " (" + count + ")";
    }
}
