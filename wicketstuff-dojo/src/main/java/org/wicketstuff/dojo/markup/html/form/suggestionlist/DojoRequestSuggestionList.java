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

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.IDojoWidget;
import org.wicketstuff.dojo.markup.html.form.DojoDropDownChoice;

/**
 * Suggestion list that requests the server to know which items
 * match the user input
 * 
 * @see DojoDropDownChoice for a suggestion list that uses a Wicket model object
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 */
public abstract class DojoRequestSuggestionList extends TextField implements IDojoWidget
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
