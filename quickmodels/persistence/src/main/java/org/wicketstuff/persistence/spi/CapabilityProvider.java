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
package org.wicketstuff.persistence.spi;

/**
 * An interface that can be implemented by DbImplementation
 * instances that want to return provide extension interfaces.
 * See Db.getCapability().
 *
 * @author Tim Boudreau
 */
public interface CapabilityProvider {
    /**
     * Fetch an instance of some known type
     * @param clazz The type of object being requested
     * @return null or an object of type <code>clazz</code>
     */
    public <T> T getCapability (Class<T> clazz);
}
