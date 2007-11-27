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

/**
 * Some pojos to put in a database
 *
 * @author Tim Boudreau
 */
public class A {
    final B b;
    final String name;
    final String otherString;
    public A (String name, boolean boolval, int intval, long longVal, String stringVal, String otherString) {
        b = new B(boolval, intval, longVal, stringVal);
        this.name = name;
        this.otherString = otherString;
    }
    
    @Override
    public String toString() {
        return name;// + "[b: " + b + "]";
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() + (17 * b.hashCode());
    }
    
    public boolean equals (Object o) {
        if (o == null) {
            return false;
        }
        boolean result = o.getClass() == getClass();
        if (result) {
            A other = (A) o;
            result = other.name.equals(name) && other.b.equals(b);
        }
        return result;
    }
    
    public static class B {
        C c;
        String stringVal;
        public B(boolean boolval, int intval, long longVal, String stringVal) {
            c = new C (boolval, intval, longVal);
            this.stringVal = stringVal;
        }
        
        @Override
        public boolean equals (Object o) {
            if (o == null) {
                return false;
            }
            boolean result = o.getClass() == getClass();
            if (result) {
                B other = (B) o;
                result = other.stringVal.equals(stringVal) && other.c.equals(c);
            }
            return result;
        }
        
        @Override
        public int hashCode() {
            return stringVal.hashCode() * 31 * c.hashCode();
        }
        
        @Override
        public String toString() {
            return stringVal + " c: [" + c + "]";
        }
    }
    
    public static class C {
        private long longval;
        private boolean boolval;
        private int intval;
        private C(boolean boolval, int intval, long longVal) {
            this.boolval = boolval;
            this.intval = intval;
            this.longval = longVal;
        }
        
        @Override
        public String toString() {
            return "boolval " + boolval + "; intval " + intval + "; " + longval;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final C other = (C) obj;
            if (this.longval != other.longval) {
                return false;
            }
            if (this.boolval != other.boolval) {
                return false;
            }
            if (this.intval != other.intval) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            return hash;
        }
    }
}
