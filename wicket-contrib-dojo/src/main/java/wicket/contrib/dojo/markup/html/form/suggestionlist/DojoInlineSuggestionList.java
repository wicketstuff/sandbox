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

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;
import wicket.contrib.dojo.DojoIdConstants;

/**
 * DojoInline suggestion list is a suggestion list withour server request.
 * It use a select tag and option tag to render the componant.
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoInlineSuggestionList extends WebMarkupContainer
{
	/**
	 * Create a DojoInlineSuggestionList
	 * @param id component id
	 * @param model model associated with the component
	 */
	public DojoInlineSuggestionList(String id, IModel model){
		super(id, model);
		add(new DojoInlineSuggestionListHandler());
	}

	/**
	 * Create a DojoInlineSuggestionList
	 * @param id component id
	 */
	public DojoInlineSuggestionList(String id){
		super(id);
		add(new DojoInlineSuggestionListHandler());
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		checkTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_COMBOBOX);
	}
	
	protected void checkTag(ComponentTag tag){
		if ("select".equals(tag.getName())){
			throw new WicketRuntimeException("DojoInlineSuggestionList " + getMarkupId() + " expected a select tag but found a " + tag.getName() + "tag.");
		}
	}

}
