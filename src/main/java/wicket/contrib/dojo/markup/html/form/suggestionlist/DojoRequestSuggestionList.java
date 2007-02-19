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
package wicket.contrib.dojo.markup.html.form.suggestionlist;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;

/**
 * Suggestion list that request the server to know which item
 * sould be display with the user input in the widget
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public abstract class DojoRequestSuggestionList extends TextField
{

	/**
	 * Construct a suggestion list
	 * @param id component id
	 * @param model model associated with the widget : TODO pt here a suggestionList???
	 */
	public DojoRequestSuggestionList(String id, IModel model)
	{
		super(id, model);
		add(new DojoRequestSuggestionListHandler());
	}

	/**
	 * Construct a suggestion list
	 * @param id component id
	 */
	public DojoRequestSuggestionList(String id)
	{
		super(id);
		add(new DojoRequestSuggestionListHandler());
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_COMBOBOX);
	}
	
	/**
	 * Return a {@link SuggestionList} filtered by the input in pattern
	 * @param pattern user input
	 * @return list of elemtn to displayed
	 */
	public abstract SuggestionList getMatchingValues(String pattern);
	
}
