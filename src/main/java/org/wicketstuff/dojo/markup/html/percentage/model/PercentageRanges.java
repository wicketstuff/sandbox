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
package org.wicketstuff.dojo.markup.html.percentage.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Model to use in PercentSelectorWidget
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
@SuppressWarnings("serial")
public class PercentageRanges extends HashMap<String, Integer>
{
	/**
	 * Create a new PencentagesRanges
	 *
	 */
	public PercentageRanges(){
		super();
	}
	
	public void createFromJson(String json){
		String content = json.substring(1, json.length()-1);
		String items[] = content.split(",");
		
		for(int i=0;i<items.length; i++){
			String current = items[i];
			String key = current.substring(1, current.lastIndexOf(':')-1);
			Integer value = new Integer(current.substring(current.lastIndexOf(':')+1, current.length()));
			put(key, value);
		}
	}
	
	public String generateJson(){
		String toReturn = "{";
		
		Iterator ite = this.entrySet().iterator();
		while (ite.hasNext()){
			Entry current = (Entry)ite.next();
			toReturn += "\"" + current.getKey() + "\":" + current.getValue() + ",";
		}
		
		return toReturn.substring(0, toReturn.length() -1) + "}";
	}

}
