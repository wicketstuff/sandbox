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
 * Value that can be passed to the constructor of FieldQueryElement to indicate
 * the query is for anything that matches a range of values.
 * @see org.netbeans.libs.persistencefacade.queries.FieldQueryElement
 * @author Tim Boudreau
 */
public final class RangeValue<T extends Number> {
    public final T start;
    public final T finish;
    public final Class<T> numberType;
    /**
     * Create a range from a starting to an ending number.
     * @param start The starting value
     * @param finish The ending value
     * @param numberType The type of the number, such as Integer
     */
    public RangeValue(T start, T finish, Class<T> numberType) {
        this.start = start;
        this.finish = finish;
        this.numberType = numberType;
    }
}
