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

import junit.framework.TestCase;

/**
 *
 * @author Tim Boudreau
 */
public class CompoundQueryElementTest extends TestCase {
    
    //PENDING:  Move tests that belong here out of the wicket models project
    
    public CompoundQueryElementTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAddOr() {
        FieldQueryElement<String> a = new FieldQueryElement<String>("name", String.class, "Joe", false);
        FieldQueryElement<Boolean> b = new FieldQueryElement<Boolean>("alive", Boolean.class, Boolean.TRUE, false);
        FieldQueryElement<Integer> c = new FieldQueryElement<Integer>("age", Integer.class, 30, false);
        QueryElement qe = a.or(b).or(c);
        System.out.println(qe.toString());
        
        QueryElement qe2 = CompoundQueryElement.create(a.or(b), c, Logic.AND);
        System.out.println(qe2);
    }
}
