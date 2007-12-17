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
package org.wicketstuff.dojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * A Dojo locale manager to declare some locale on the client side.
 * Using WCD without this manager leads dojo to run with the browser default locale.
 * 
 * @author vdemay
 *
 */
public class DojoLocaleManager {
	private static DojoLocaleManager manager;
	private Set<String> locales;
	
	private DojoLocaleManager(){
		locales = new HashSet<String>();
	}
	
	public static DojoLocaleManager getInstance(){
		if(manager == null){
			manager = new DojoLocaleManager();
		}
		return manager;
	}
	
	public void addLocale(Locale locale){
		locales.add(locale.toString().replace('_', '-').toLowerCase());
	}
	
	private String generateLocaleJs(){
		if(locales.size() != 0){
			String js = "" +
				"if(djConfig == null){\n" +
				"	var djConfig = {};\n" +
				"}" +
				"if(djConfig.extraLocale == null){\n" +
				"	djConfig.extraLocale = new Array();\n" +
					"}\n";
			Iterator ite = locales.iterator();
			while(ite.hasNext()){
				js += "djConfig.extraLocale.push('" + ite.next() + "')\n";
			}
			
			return js;
		}
		return null;
	}
	
	public void renderLocale(IHeaderResponse response){
		String js = generateLocaleJs();
		if (js != null){
			response.renderJavascript(js , "localeManagerJs");
		}
	}
}
