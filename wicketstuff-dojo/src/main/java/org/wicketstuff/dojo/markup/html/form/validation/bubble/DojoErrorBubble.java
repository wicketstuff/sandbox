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
package org.wicketstuff.dojo.markup.html.form.validation.bubble;

import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.markup.html.Bubble.AbstractDojoBubble;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

/**
 * A red {@link AbstractDojoBubble} show error message
 * 
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoErrorBubble extends AbstractDojoBubble {

	/**
	 * Constructs
	 * @param id
	 * @param model
	 */
	public DojoErrorBubble(String id, IModel model) {
		super(id, model);
		add(new DojoErrorBubbleHandler());
	}

	/**
	 * Constructs
	 * @param id
	 */
	public DojoErrorBubble(String id) {
		super(id);
		add(new DojoErrorBubbleHandler());
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_ERRORBUBBLE);
	}

}
