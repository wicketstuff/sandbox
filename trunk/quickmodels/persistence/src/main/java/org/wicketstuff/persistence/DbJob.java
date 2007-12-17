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
package org.wicketstuff.persistence;

/**
 * Interface which wraps all database access.  For logging purposes,
 * it is best if all implementations override <code>toString()</code> to
 * describe what is being done in the database.
 *
 * @see org.wicketstuff.persistence.FacadeFactory
 * 
 * @author Tim Boudreau
 */
public interface DbJob<ContainerType, ResultType, ArgType> {
    /**
     * Perform some activity with respect to the database.
     * @param container The container type of the database - this arguement
     * will be database-specific
     * @param argument Optional argument
     * @return Whatever the result of performing this job is
     */
    public ResultType run (ContainerType container, ArgType argument);
}
