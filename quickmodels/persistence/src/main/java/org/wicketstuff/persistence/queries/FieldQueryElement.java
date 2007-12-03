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

/**
 * Describes the desired value of a field on an object in a query.
 *
 * @author Tim Boudreau
 */
public final class FieldQueryElement<T> implements QueryElement {
    public final String name;
    public final Class<T> type;
    public final Object value;
    public final boolean negated;
    /**
     * Create a field query element, representing something you wish to match
     * against a persisted object.
     * @param name The name of the field (<i>not</i> getter method) on the
     * class you are querying.  Note that this name may specify a field on
     * a child object - i.e. <code>&quot;user.address.city&quot;</code> 
     * specifies to find a field named user on the initial object, then
     * an address field on that object, and a city field on the address
     * object.
     * @param type The type of the <i>field</i>, i.e. if the field is of
     * type String, pass String.class here
     * @param value The desired value you want to match.  This may be an 
     * instance of the type passed in the type parameter, or it may be an 
     * instance of one of the special value classes in this package, 
     * RangeValue, StringContainsValue, StringStartsWithValue or 
     * StringEndsWithValue
     * @param negated Whether the query should be a logical NOT, finding all
     * non-matches
     */
    public FieldQueryElement(String name, Class<T> type, Object value, boolean negated) {
        if (name == null) throw new NullPointerException("Name is null");
        if (type == null) throw new NullPointerException("Type is null");
        if (value == null) throw new NullPointerException("Value is null");
        this.name = name;
        this.type = type;
        this.value = value;
        this.negated = negated;
    }

    public CompoundQueryElement or(QueryElement element) {
        return new CompoundQueryElement(this).or(element);
    }

    public CompoundQueryElement and(QueryElement element) {
        return new CompoundQueryElement(this).and(element);
    }

    @Override
    public String toString() {
        return "obj." + name + (negated ? " != " :  " == ") + value;
    }

    public boolean isNegated() {
        return negated;
    }
}
