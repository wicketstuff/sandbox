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
 * Field definitions for queries.  Each item corresponds to one or two setter 
 * methods on ModelBuilder.
 * 
 * @see Queries.getIllegalFields()
 * @see Queries.getRequiredFields()
 * @see Queries.getOptionalFields()
 * 
 * @author Tim Boudreau
 */
public enum QueryFields {
        /**
         * Denotes the setUid() or setUids() method on ModelBuilder
         */
        UID, 
        /**
         * Denotes the setUuid() or setUuids() methods on ModelBuilder
         */
        UUID, 
        /**
         * Denotes the setType() method on ModelBuilder
         */
        TYPE, 
        /**
         * Denotes the setLoadFailurePolicy() method on ModelBuilder
         */
        POLICY, 
        /**
         * Denotes the setNewObjectFactory() method on ModelBuilder
         */
        FACTORY, 
        /**
         * Denotes the setObject() or setObjects() method on ModelBuilder
         */
        OBJECT, 
        /**
         * Denotes the setFieldType() method on ModelBuilder
         */
        FIELD_TYPE, 
        /**
         * Denotes the setFieldValue() method on ModelBuilder
         */
        FIELD_VALUE,
        /**
         * Denotes the setCustomQuery() method on ModelBuilder
         */
        CUSTOM_QUERY, 
        /**
         * Denotes the setNewObjectCount() method on ModelBuilder
         */
        NEW_OBJECT_COUNT, 
        /**
         * Denotes the setFieldName method on ModelBuilder
         */
        FIELD_NAME, 
        /**
         * Denotes the setCustomQueryArgument() method on ModelBuilder
         */
        CUSTOM_QUERY_ARG,
}
