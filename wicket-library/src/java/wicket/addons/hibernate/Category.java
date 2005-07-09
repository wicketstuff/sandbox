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
package wicket.addons.hibernate;

import java.io.Serializable;
import java.util.Date;

import wicket.addons.hibernate.base.BaseCategory;

/**
 * @author Juergen Donnerstag
 */
public class Category extends BaseCategory implements Serializable, ILastModified, IDeleted, IIdentifiable
{
    private int count = -1;
    
    public Category()
    {
        ; // empty
    }

    /**
     * @return Returns the count.
     */
    // TODO I guess it is an error that 'count' is not table attribute
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
    
    public String toString()
    {
        return getName() + " (" + getCount() + ")";
    }
    
	protected void initialize () 
	{
	    if (getLastModified() == null)
	    {
	        setLastModified(new Date(System.currentTimeMillis()));
	    }
	}
}
