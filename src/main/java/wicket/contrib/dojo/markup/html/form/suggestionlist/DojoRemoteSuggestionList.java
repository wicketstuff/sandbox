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

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_COMBOBOX;
import wicket.MarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;

/**
 * Suggestion list that request the server to know which item
 * sould be display with the user input in the widget
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public abstract class DojoRemoteSuggestionList extends TextField
{

	/**
	 * Construct a suggestion list
	 * @param parent parent where the suggestion is rendered
	 * @param id component id
	 * @param model model associated with the widget : TODO pt here a suggestionList???
	 */
	public DojoRemoteSuggestionList(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id, model);
		add(new DojoRemoteSuggestionListHandler());
	}

	/**
	 * Construct a suggestion list
	 * @param parent parent where the suggestion is rendered
	 * @param id component id
	 */
	public DojoRemoteSuggestionList(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoRemoteSuggestionListHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_COMBOBOX);
	}
	
	/**
	 * Return a {@link SuggestionList} filtered by the input in pattern
	 * @param pattern user input
	 * @return list of elemtn to displayed
	 */
	public abstract SuggestionList getMatchingValues(String pattern);
	
}
