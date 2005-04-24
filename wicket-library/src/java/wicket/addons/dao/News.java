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
public class News implements Serializable, ILastModified, IIdentifiable
{
    private int id;

    private String headline;

    private String message;

    private Timestamp lastModified = new Timestamp(System.currentTimeMillis());

    public News()
    {
        ; // empty
    }

    /**
     * @return Returns the headline.
     */
    public String getHeadline()
    {
        return headline;
    }

    /**
     * @param headline
     *            The headline to set.
     */
    public void setHeadline(String headline)
    {
        this.headline = headline;
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
     * @return Returns the message.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message
     *            The message to set.
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
