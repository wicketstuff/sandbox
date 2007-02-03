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
package wicket.contrib.dojo.markup.html.Bubble;

import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.widgets.StyleAttribute;
import wicket.markup.ComponentTag;
import wicket.model.IModel;

/**
 * see {@link AbstractDojoBubble}
 * Create and set up a Bubble in the page
 * 
 * @author Vincent Demay
 *
 */
public class DojoBubble extends AbstractDojoBubble{
	
	/**
	 * Construct
	 * @param id
	 * @param model
	 */
	public DojoBubble(String id, IModel model) {
		super(id, model);
		add(new DojoBubbleHandler());
		
	}
	
	/**
	 * Construct
	 * @param id
	 */
	public DojoBubble(String id) {
		super(id);
		add(new DojoBubbleHandler());
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_BUBBLE);
	}
}
