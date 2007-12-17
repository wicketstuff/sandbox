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
package org.wicketstuff.dojo.markup.html.Bubble;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.IDojoWidget;
import org.wicketstuff.dojo.widgets.StylingWebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * A Bubble is a "comic bubble" which can be shown on any component or html Element
 * 
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public  class AbstractDojoBubble extends StylingWebMarkupContainer{
	
	/**
	 * Constructs
	 * @param id
	 * @param model
	 */
	public AbstractDojoBubble(String id, IModel model) {
		super(id, model);
	}

	/**
	 * Constructs
	 * @param id
	 */
	public AbstractDojoBubble(String id) {
		super(id);
	}


	/**
	 * Show the bubble
	 * @param target {@link AjaxRequestTarget}
	 */
	public void show(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').show()");
	}
	
	/**
	 * hide the bubble
	 * @param target {@link AjaxRequestTarget}
	 */
	public void hide(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').hide()");
	}
	
	/**
	 * Place the bubble on the component c
	 * @param target {@link AjaxRequestTarget}
	 * @param c component where to show the bubble
	 */
	public void place(AjaxRequestTarget target, Component c){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').stickTo('" + c.getMarkupId() + "')");
	}
	
	/**
	 * Place the bubble on a html tag containing a id attribute
	 * @param target {@link AjaxRequestTarget}
	 * @param id id of the Html tag in the source page
	 */
	public void place(AjaxRequestTarget target, String id){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').stickTo('" + id + "')");
	}
	
	/**
	 * Set the bubble message
	 * @param target {@link AjaxRequestTarget}
	 * @param mess message to display
	 */
	public void setMessage (AjaxRequestTarget target, String mess){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').setMessage(\"" + mess + "\")");
	}
}
