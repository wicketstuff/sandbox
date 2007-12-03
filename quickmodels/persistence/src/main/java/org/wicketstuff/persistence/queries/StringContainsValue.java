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
 * Special-case value that can be passed to the constructor of a FieldQueryElement
 * to indicate the query is for matches where the persisted field contains the text
 * here, rather than an exact match.
 * @see org.netbeans.libs.persistencefacade.queries.FieldQueryElement
 * @author Tim Boudreau
 */
public final class StringContainsValue {
    public final String text;
    public final boolean ignoreCase;
    public StringContainsValue(String text, boolean ignoreCase) {
        //PENDING:  Some of these actually can't support ignore case, at least.
        //With db4o.  Need to handle this.
        this.text = text;
        this.ignoreCase = ignoreCase;
    }
}
