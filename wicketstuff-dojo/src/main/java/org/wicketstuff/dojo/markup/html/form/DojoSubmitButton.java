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
import org.wicketstuff.dojo.indicator.behavior.DojoIndicatorBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

/**
 * * A button that submits the form via ajax. Since this button takes the form as
 * a constructor argument it does not need to be added to it unlike the
 * {@link Button} component.
 * 
 * This Button can accept a {@link DojoIndicatorBehavior} to display a special indicator 
 * between the ajax request and the respond
 * 
 * @author vdemay
 *
 */
public abstract class DojoSubmitButton extends Button
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param id id in the markup
	 * @param form form associated with this Submit
	 */
	public DojoSubmitButton(String id, final Form form)
	{
		super(id);
		add(new DojoFormSubmitBehavior(form, "onclick" ){
			private static final long serialVersionUID = 1L;

			protected void onSubmit(AjaxRequestTarget target)
			{
				DojoSubmitButton.this.onSubmit(target, form);
			}
			
			protected void onError(AjaxRequestTarget target)
			{
				DojoSubmitButton.this.onError(target, form);
			}
		});
	}

	/**
	 * Listener method invoked on form submit with no errors
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onSubmit(AjaxRequestTarget target, Form form);

	/**
	 * Listener method invoked on form submit with errors
	 * 
	 * @param target
	 * @param form
	 * 
	 * TODO 1.3: Make abstract to be consistent with onsubmit()
	 */
	protected void onError(AjaxRequestTarget target, Form form) {
		
	}

}
