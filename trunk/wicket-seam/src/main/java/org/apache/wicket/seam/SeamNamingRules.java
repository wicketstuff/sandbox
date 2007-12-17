/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wicket.seam;

import java.lang.reflect.Method;

/**
 * Utility class for name calculation using Seam rules.
 * @author Frank D. Martinez M. fmartinez@asimovt.com
 */
public class SeamNamingRules {

    /**
     * Returns the component or variable name based on method name.
     * @param method the method.
     * @return the component or variable name based on method name.
     */
    public static final String getNameFromMethod(Method method) {
        String name = method.getName();
        if (name.startsWith("get") || name.startsWith("set")) {
            char[] ca = name.toCharArray();
            ca[3] = Character.toLowerCase(ca[3]);
            name = new String(ca, 3, ca.length - 3);
        }
        return name;
    }
    
}
