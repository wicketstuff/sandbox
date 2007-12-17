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
import org.wicketstuff.persistence.queries.CompoundQueryElement.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a group of query elements related by logical operations.
 * Think of this class as a pair of parentheses.  Other QueryElements or
 * QueryElementGroups can be added to nest logical operations.
 * @see org.netbeans.libs.persistencefacade.queries.QueryElement
 * @see org.netbeans.libs.persistencefacade.queries.FieldQueryElement
 *
 * @author Tim Boudreau
 */
public final class CompoundQueryElement<T> implements QueryElement<T>, Iterable <Item> {
    private final QueryElement root;
    private final List<Item> elements = new ArrayList<Item>();
    CompoundQueryElement(QueryElement root) {
        this.root = root;
    }

    /**
     * Logically OR another query element into this one. <i>Important:</i>
     * this method adds another query term <i>inside</i> the set of 
     * parentheses that is this CompoundQueryElement.  To logically or
     * two <i>CompoundQueryElements</i>, use the static <code>create()</code>
     * method.
     * @param element A QueryElement to or into this one.
     * @return A compound query element.  Always returns <code>this</code>.
     */
    public CompoundQueryElement<T> or(QueryElement element) {
        if (element == this) throw new IllegalArgumentException();
        elements.add(new Item (element, Logic.OR));
        return this;
    }

    /**
     * Logically AND another query element into this one. <i>Important:</i>
     * this method adds another query term <i>inside</i> the set of 
     * parentheses that is this CompoundQueryElement.  To logically and
     * two <i>CompoundQueryElements</i>, use the static <code>create()</code>
     * method.
     * @param element A QueryElement to and into this one.
     * @return A compound query element.  Always returns <code>this</code>.
     */
    public CompoundQueryElement<T> and(QueryElement element) {
        if (element == this) throw new IllegalArgumentException();
        elements.add(new Item (element, Logic.AND));
        return this;
    }

    /**
     * Create a compound query element representing a logical operation
     * between two existing query elements.
     * @param first The first term
     * @param second The second term
     * @param operation Whether to AND or OR
     * @return A compound query element
     */
    public static CompoundQueryElement create (QueryElement first, QueryElement second, Logic operation) {
        CompoundQueryElement result = new CompoundQueryElement(first);
        result.elements.add(new Item (second, operation));
        return result;
    }
    
    /**
     * Class which holds the elements of this query mapped to their
     * logical operations
     */
    public static final class Item {

        public final QueryElement element;
        public final Logic op;

        private Item(QueryElement element, Logic op) {
            this.op = op;
            this.element = element;
        }
        
        @Override
        public String toString() {
            return " " + op + ' ' + element + ' ';
        }
    }

    /**
     * Get an iterator of the contents of this query.
     * @return An iterator
     */
    public Iterator<Item> iterator() {
        List<Item> l = new ArrayList<Item>(elements);
        l.add(0, new Item (root, Logic.AND));
        return l.iterator();
    }
    
    /**
     * Provides a reasonable string representation of this query
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append ('(');
        sb.append (root);
        for (Item item : this) {
            sb.append (item.toString());
        }
        sb.append (')');
        return sb.toString();
    }
}
