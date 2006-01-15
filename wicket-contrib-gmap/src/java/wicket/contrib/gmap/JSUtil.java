/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.gmap;

/**
 * @author Iulian-Corneliu Costan
 */
class JSUtil {

    public static String createFunction(String name, String body) {
        return "function " + name + "() {\n" + body + "\n}";
    }

    public static String longitudeAsString(GPoint point) {
        return Float.toString(point.getLongitude()).replace(".", "").replace("-", "1");
    }

    public static String latitudeAsString(GPoint point) {
        return Float.toString(point.getLatitude()).replace(".", "").replace("-", "1");
    }

    public static String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
