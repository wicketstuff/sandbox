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
import java.io.Serializable;
/**
 * Describes how objects of a given type should be "activated".
 * In some OODBs, fields of child objects are uninitialized in
 * newly retrieved objects.  This class allows hints to be
 * provided as to the depth to which an object should be activated.
 *
 * @author Tim Boudreau
 */
public class ActivationStrategy implements Serializable {
    private final int depth;
    private ActivationStrategy(int depth) {
        this.depth = depth;
    }
    
    public static ActivationStrategy create(int depth) {
        return new ActivationStrategy(depth);
    }
    
    public static ActivationStrategy createDefault() {
        return new ActivationStrategy(5);
    }
    
    public int getActivationDepth() {
        return depth;
    }
}
