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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines types of queries.  Usage:<br>
 * <code>ModelBuilder builder = Queries.UID.builder(Foo.class);</code>
 * <br><code>builder.setUid(2502L);</code><br>
 * <code>PojoModel&lt;Foo&gt; myModel = builder.single();</code><br>
 * or
 * <br>
 * <code>builder.setUids(someListOfLongs);</code><br>
 * <code>builder.multi()</code>
 * <p>
 * If the required fields are not set for a particular query, or
 * ignored fields for a particular query type are set, an exception
 * will be thrown.
 * <p>
 * Various query types are available.
 * 
 * @author Tim Boudreau
 */
public enum Queries {
    /**
     * Query for objects of a particular type from the database
     */
    OF_TYPE(
        QueryFields.TYPE), 
    /**
     * Query that runs a custom DbJob against the database, which
     * returns a collection of objects
     */
    CUSTOM_QUERY(
        QueryFields.CUSTOM_QUERY), 
    /**
     * Query that returns objects which match a prototype object that has
     * some fields set
     */
    PROTOTYPE (
        QueryFields.OBJECT),
    /**
     * Creates models for one or more objects which already exist and
     * may or may not be persisted but could be.
     */
    EXISTING_OBJECTS (
        QueryFields.OBJECT), 
    /**
     * Creates a query which will instantiate a new instance of an object
     * on demand and persist that if necessary
     */
    NEW_OBJECT(
        QueryFields.TYPE),
    /**
     * Creates queries which match fields on persisted objects of a given type.
     */
    QUERY(QueryFields.TYPE, 
        QueryFields.FIELD_TYPE, 
        QueryFields.FIELD_VALUE, 
        QueryFields.FIELD_NAME), 
    /**
     * Creates queries which look up objects by universal unique id. You must
     * call setUuid() or setUuids() on the resulting builder before trying to
     * get a model from its single() or multi() methods.
     */
    UUID(
        QueryFields.TYPE, QueryFields.UUID), 
    /**
     * Creates queries which look up objects by long unique id.  You must
     * call setUid() or setUids() on the resulting builder before trying to
     * get a model from its single() or multi() methods.
     */
    UID(
        QueryFields.UID),
        
    /**
     * Creates queries which look up objects using QueryElements.  You must
     * call setObject() and pass in a QueryElement (such as a FieldQueryElement
     * or CompoundQueryElement) before invoking single() or multi() on the
     * ModelBuilder you get from the builder() method
     */
    COMPLEX(QueryFields.OBJECT);
    
    private Set<QueryFields> required = new HashSet<QueryFields>();
    private Set<QueryFields> illegal;
    private Set<QueryFields> optional;
    Queries (QueryFields... fields) {
        required.addAll(Arrays.<QueryFields>asList(fields));
    }
    
    private void init() {
        //Seems switch(this) in an enum's constructor triggers an npe,
        //so lazy init'ing
        if (illegal != null) return;
        switch (this) {
        case CUSTOM_QUERY :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.OBJECT, 
                    QueryFields.FIELD_TYPE, 
                    QueryFields.FIELD_VALUE,
                    QueryFields.NEW_OBJECT_COUNT, 
                    QueryFields.FIELD_NAME,
                    QueryFields.POLICY, 
                    QueryFields.FACTORY);
            break;
        case EXISTING_OBJECTS :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.FIELD_TYPE, 
                    QueryFields.FIELD_VALUE,
                    QueryFields.FIELD_NAME,
                    QueryFields.NEW_OBJECT_COUNT, 
                    QueryFields.CUSTOM_QUERY);
            break;
        case COMPLEX :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.FIELD_TYPE, 
                    QueryFields.FIELD_VALUE,
                    QueryFields.FIELD_NAME,
                    QueryFields.NEW_OBJECT_COUNT, 
                    QueryFields.CUSTOM_QUERY);
            break;
        case NEW_OBJECT :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.FIELD_TYPE,
                    QueryFields.FIELD_VALUE,
                    QueryFields.FIELD_NAME,
                    QueryFields.CUSTOM_QUERY
                    
                    );
            break;
        case OF_TYPE :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.OBJECT, 
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.FIELD_TYPE,
                    QueryFields.FIELD_VALUE,
                    QueryFields.FIELD_NAME,
                    QueryFields.CUSTOM_QUERY
                    
                    );
            break;
        case PROTOTYPE :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.FIELD_VALUE,
                    QueryFields.FIELD_NAME,
                    QueryFields.FIELD_TYPE,
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.NEW_OBJECT_COUNT,
                    QueryFields.CUSTOM_QUERY
                    
                    );
            break;
        case QUERY :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.UID, 
                    QueryFields.OBJECT,
                    QueryFields.NEW_OBJECT_COUNT,
                    QueryFields.CUSTOM_QUERY,
                    QueryFields.CUSTOM_QUERY_ARG
                    
                    );
            break;
        case UID :
            illegal = fieldSet(
                    QueryFields.UUID, 
                    QueryFields.FIELD_VALUE,
                    QueryFields.OBJECT,
                    QueryFields.FIELD_NAME,
                    QueryFields.FIELD_TYPE,
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.NEW_OBJECT_COUNT,
                    QueryFields.CUSTOM_QUERY
                    
                    );
            break;
        case UUID :
            illegal = fieldSet(
                    QueryFields.UID, 
                    QueryFields.FIELD_VALUE,
                    QueryFields.OBJECT,
                    QueryFields.FIELD_NAME,
                    QueryFields.FIELD_TYPE,
                    QueryFields.NEW_OBJECT_COUNT,
                    QueryFields.CUSTOM_QUERY_ARG,
                    QueryFields.CUSTOM_QUERY
                    
                    );
            break;
        default :
            throw new AssertionError ("" + this);
        }
        Set <QueryFields> s = new HashSet<QueryFields>();
        s.addAll(Arrays.<QueryFields>asList(QueryFields.values()));
        s.removeAll (required);
        s.removeAll (illegal);
        optional = Collections.unmodifiableSet(s);
    }
    
    
    /**
     * Get the set of fields that may be set on a ModelBuilder
     * to complete this type of query;  mutually exclusive with 
     * required and illegal fields
     * @return A set of QueryFields
     */
    public Set<QueryFields> getOptionalFields() {
        init();
        return optional;
    }
    
    /**
     * Get the set of fields that must be set on a ModelBuilder
     * to complete this type of query
     * @return A set of QueryFields
     */
    public Set<QueryFields> getRequiredFields() {
        init();
        return Collections.unmodifiableSet(required);
    }
    
    /**
     * Get the set of parameters that are meaningless for this type of
     * query
     * @return A set of fields as defined by QueryFields
     */
    public Set<QueryFields> getIllegalFields() {
        init();
        return illegal;
    }
        
    /**
     * Create a ModelBuilder for this kind of query.  Additional
     * parameters on the ModelBuilder may be set, and then its
     * <code>single()</code> or <code>multi()</code> method may be
     * called to get actual objects.
     * @param type The type of objects the query should find in the database
     * @return A ModelBuilder with the passed type and fieldType (for non-field
     * queries, use Void for P).
     */
    public <T, P> ModelBuilder<T,P> builder(Class<T> type) {
        init();
        return new ModelBuilder<T,P> (type, this);
    }
    
    private Set<QueryFields> fieldSet (QueryFields... fields) {
        Set <QueryFields> result = new HashSet<QueryFields>();
        result.addAll(Arrays.<QueryFields>asList(fields));
        return Collections.unmodifiableSet(result);
    }
    
    @Override
    public String toString() {
        return "Query " + name() + " required fields \n" + 
                required + " optional fields \n" + 
                optional + " illegal fields \n" + illegal;
    }
}
