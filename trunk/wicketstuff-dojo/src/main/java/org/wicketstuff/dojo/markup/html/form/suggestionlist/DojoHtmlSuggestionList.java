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

import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.IDojoWidget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * Suggestion list without server request.
 * It uses a select tag and option tag to render the component.
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
@SuppressWarnings("serial")
public class DojoHtmlSuggestionList extends DropDownChoice implements IDojoWidget
{
	/**
	 * Create a DojoInlineSuggestionList
	 * @param id component id
	 * @param model model associated with the component
	 */
	public DojoHtmlSuggestionList(String id, IModel model){
		super(id, model);
		add(new DojoHtmlSuggestionListHandler());
	}

	/**
	 * Create a DojoInlineSuggestionList
	 * @param id component id
	 */
	public DojoHtmlSuggestionList(String id){
		super(id);
		add(new DojoHtmlSuggestionListHandler());
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_COMBOBOX;
	}
}
