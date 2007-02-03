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
package wicket.contrib.dojo.markup.html.form.validation.bubble;

import java.util.HashMap;

import wicket.Component;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.form.FormComponent;

/**
 * Ajaxhandler to be bound to FormComponents.<br/> This handler executes wicket
 * validation on set event (e.g. onblur, onchange) with an Ajax server call.<br/>
 * The Form component on error is displayed with a bubble on it containing the error message
 * 
 * 
 * @author Vincent Demay
 */
public class DojoBubbleValidationAjaxBehavior extends AbstractRequireDojoBehavior
{

	/** name event, like onblur. */
	private final String eventName;

	/** component this handler is attached to. */
	private FormComponent formComponent;
	
	/** bubble */
	DojoErrorBubble bubble;
	
	/** who is invalid */
	private static HashMap invalid;


	/**
	 * Default constructor which uses node's current background color when
	 * component is valid.
	 * 
	 * @param eventName
	 * @see #eventName
	 */
	public DojoBubbleValidationAjaxBehavior(String eventName, DojoErrorBubble bubble)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.bubble = bubble;
		this.invalid = new HashMap();
	}

	
	public final void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
	}
	
	/**
	 * Bind this handler to the FormComponent and set the corresponding HTML id
	 * attribute.
	 * @param tag tag
	 */
	protected void onComponentTag(ComponentTag tag){
		super.onComponentTag(tag);
		Component c = getComponent();
		if (!(c instanceof FormComponent))
		{
			throw new WicketRuntimeException("This handler must be bound to FormComponents");
		}
		formComponent = (FormComponent)c;
		tag.put(eventName, "javascript:" + "var wcall=wicketAjaxGet('" + getCallbackUrl() + "&amp;" + formComponent.getInputName() + "=' + this.value, function() { }, function() { });return !wcall;");
		if (!eventName.equals("onclick")){
			tag.put("onclick", "javascript:" + "var wcall=wicketAjaxGet('" + getCallbackUrl() + "&amp;action=click&amp;" + formComponent.getInputName() + "=' + this.value, function() { }, function() { });return !wcall;");
		}
	}


	protected void respond(AjaxRequestTarget target)
	{
		formComponent.validate();	
		if (!formComponent.isValid())
		{
			bubble.setMessage(target,formComponent.getFeedbackMessage().getMessage());
			bubble.place(target, formComponent);
			bubble.show(target);
			invalid.put(formComponent, formComponent.getFeedbackMessage().getMessage());
		}
		else{
			invalid.remove(getComponent());
			if (invalid.isEmpty()){
				bubble.hide(target);
			}else{
				FormComponent current =  (FormComponent) invalid.keySet().iterator().next();
				current.validate();
				bubble.setMessage(target, (String)invalid.get(current));
				bubble.place(target, current);
			}
		}
	}

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojoWicket.widget.ErrorBubble");
	}
}
