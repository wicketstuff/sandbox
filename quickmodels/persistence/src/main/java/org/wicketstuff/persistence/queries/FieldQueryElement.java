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
    
    public FieldQueryElement(String name, Class<T> type, Object value, boolean negated) {
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
