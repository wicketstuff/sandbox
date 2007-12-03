/* Licensed under the Apache License, Version 2.0 (the "License"); you may not
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
package org.wicketstuff.persistence.queries;

import java.io.Serializable;

/**
 * An element of a complex query.
 * Do not implement this interface.
 *
 * @see org.netbeans.libs.persistencefacade.queries.FieldQueryElement
 * @see org.netbeans.libs.persistencefacade.queries.CompoundQueryElement
 * @see org.netbeans.lib.persistence.wicketmodels.Queries.COMPLEX
 *
 * @author Tim Boudreau
 */
public interface QueryElement<T> extends Serializable {
    /**
     * Create a compound query element representing a logical OR 
     * of this query element and the passed one..
     * @param element The element to or with this one
     * @return A compound query element
     */
    public CompoundQueryElement<T> or(QueryElement element);
    /**
     * Create a compound query element representing a logical AND
     * of this query element and the passed one..
     * @param element The element to or with this one
     * @return A compound query element
     */
    public CompoundQueryElement<T> and(QueryElement element);
}
