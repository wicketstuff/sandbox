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
package org.wicketstuff.quickmodels;

/**
 * Field definitions for queries.  Each item corresponds to a setter 
 * method on ModelBuilder.
 * 
 * @see Queries.getIllegalFields()
 * @see Queries.getRequiredFields()
 * @see Queries.getOptionalFields()
 * @author Tim Boudreau
 */
public enum QueryFields {
        UID, 
        UUID, 
        TYPE, 
        POLICY, 
        FACTORY, 
        OBJECT, 
        FIELD_TYPE, 
        FIELD_VALUE,
        CUSTOM_QUERY, 
        NEW_OBJECT_COUNT, 
        FIELD_NAME, 
        CUSTOM_QUERY_ARG,
}
