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

/**
 * Factory which can create new instances of type T.
 * 
 * @author Tim Boudreau
 * @param T The type of object to create
 */
public interface NewObjectFactory<T> {
    /**
     * Creates a new instance of type T
     * @return A newly created object
     */
    T create();
    
    /**
     * Implementation of NewObjectFactory which uses Class.newInstance()
     * to instantiate the object.
     * 
     * @param T The type of object to instantiate
     */
    public static final class Reflection<T> implements NewObjectFactory<T> {
        private final Class<T> clazz;
        public Reflection(Class<T> clazz) {
            this.clazz = clazz;
        }
        
        /**
         * Create the object
         * @return a new instance of the type passed to the constructor
         * @throws java.lang.IllegalStateException if an exception occurs
         * during instantiation
         */
        public T create() {
            try {
                return (T) clazz.newInstance();
            } catch (InstantiationException ex) {
                throw new IllegalStateException (ex);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException (ex);
            }
        }
        
    }
}
