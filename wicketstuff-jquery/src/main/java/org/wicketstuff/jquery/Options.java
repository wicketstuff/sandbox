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
package org.wicketstuff.jquery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Options implements Serializable {

    protected Map<String, Object> options_ = new HashMap<String,Object>();

    public Object get(String name) throws Exception {
        return options_.get(name);
    }

    public Object get(String name, Object defaultValue) throws Exception {
        Object back = options_.get(name);
        if (back == null) {
            back = defaultValue;
        }
        return back;
    }

    /**
     * shortcut method, call set with overwrite = true.
     * @see #set(String name, Object value, boolean overwrite)
     */
    public Options set(String name, Object value) throws Exception {
        return set(name, value, true);
    }

    /**
     * set an option.
     * @param name name of the option
     * @param value new value of the option (if null, then remove the option)
     * @param overwrite if false and the value is already set, then the option is unchanged
     * @return this
     */
    public Options set(String name, Object value, boolean overwrite) throws Exception {
        if (!overwrite && options_.containsKey(name)) {
            return this;
        }
        if ((value == null) && options_.containsKey(name)) {
            options_.remove(name);
        }
        options_.put(name, value);
        return this;
    }

    public CharSequence toString(boolean asFragment) throws Exception {
        StringBuilder str = new StringBuilder();
        if (!asFragment) {
            str.append("{\n");
        }
        for(Map.Entry<String, Object> entry : options_.entrySet()) {
            str.append('\t')
                .append(entry.getKey())
                .append(':')
                ;
            if (entry.getValue() instanceof String) {
                str.append('\'')
                    .append(entry.getValue())
                    .append('\'')
                    ;
            } else {
                str.append(entry.getValue());
            }
            str.append(",\n");
        }
        if (!asFragment) {
            str.setLength(str.length() - 2);
            str.append("\n}\n");
        }
        return str;
    }
}
