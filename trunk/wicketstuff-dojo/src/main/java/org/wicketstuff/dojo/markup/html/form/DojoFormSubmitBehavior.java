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
package org.wicketstuff.dojo.markup.html.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractDefaultDojoBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * Ajax event behavior that submits a form via ajax when the event it is
 * attached to is invoked.
 * <p>
 * The form must have an id attribute in the markup or have MarkupIdSetter
 * added.
 * </p>
 * 
 * @author vdemay
 *
 */
public abstract class DojoFormSubmitBehavior extends AbstractDefaultDojoBehavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Form form;
	private String event;


	/**
	 * Constructor. This constructor can only be used when the component this
	 * behavior is attached to is inside a form.
	 * 
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 */
	public DojoFormSubmitBehavior(String event)
	{
		this(null, event);
	}
	
	/**
	 * Construct.
	 * 
	 * @param form
	 *            form that will be submitted
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 */
	public DojoFormSubmitBehavior(Form form, String event)
	{
		super();
		this.form = form;
		this.event = event;
	}

	protected CharSequence getEventHandler()
	{
		final String formId = form.getMarkupId();
		final CharSequence url = getCallbackUrl();


		AppendingStringBuffer call = new AppendingStringBuffer("wicketSubmitFormById('").append(
				formId).append("', '").append(url).append("', ");

		if (getComponent() instanceof Button)
		{
			call.append("'").append(((FormComponent)getComponent()).getInputName()).append("' ");
		}
		else
		{
			call.append("null");
		}

		return generateCallbackScript(call) + ";";
	}
	
	protected void respond(AjaxRequestTarget target)
	{
		form.onFormSubmitted();
		if (!form.hasError())
		{
			onSubmit(target);
		}
		else
		{
			onError(target);
		}	
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
        // return false to end event processing in case the DojoLink is bound to a <button> contained in a form
        tag.put(event, getEventHandler() + "; return false;");
	}

	/**
	 * Listener method that is invoked after the form has ben submitted and
	 * processed without errors
	 * 
	 * @param target
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);

	/**
	 * Listener method invoked when the form has been processed and errors
	 * occured
	 * 
	 * @param target
	 * 
	 * TODO 1.3: make abstract to be consistent with onsubmit()
	 * 
	 */
	protected void onError(AjaxRequestTarget target)
	{

	}

}
