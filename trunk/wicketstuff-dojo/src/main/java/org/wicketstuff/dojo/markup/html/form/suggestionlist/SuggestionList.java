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
package org.wicketstuff.dojo.markup.html.form.suggestionlist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Model to represent what should be rendered a {@link DojoRequestSuggestionList}
 * its a HashMap : key is the value given for a label which is the value
 * TODO : more documentation
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
@SuppressWarnings("serial")
public class SuggestionList extends HashMap{

	/**
	 * Return jsonString to populate suggestionList
	 * @return jSon to populate suggestionList
	 */
	public String getJson()
	{
		String toReturn = "[";
		Iterator it = this.entrySet().iterator();
		while(it.hasNext()){
			Entry item = (Entry)it.next();
			toReturn +="[\"" + item.getValue() + "\",\"" + item.getKey() + "\"],";
		}
		return toReturn + "]";
	}
	
}
