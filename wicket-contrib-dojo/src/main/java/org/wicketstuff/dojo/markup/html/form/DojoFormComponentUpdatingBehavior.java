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

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.wicketstuff.dojo.indicator.DojoIndicatorHandlerHelper;
import org.apache.wicket.markup.html.form.persistence.IValuePersister;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

/**
 * A behavior that updates the hosting FormComponent via ajax when an event it
 * is attached to is triggered. This behavior encapsulates the entire
 * form-processing workflow as relevant only to this component so if validation
 * is successfull the component's model will be updated according to the
 * submitted value.
 * <p>
 * NOTE: This behavior does not support persisting form component values into
 * cookie or other {@link IValuePersister}. If this is necessary please add a
 * request for enhancement.
 * <p>
 * NOTE: This behavior does not validate any {@link IFormValidator}s attached
 * to this form even though they may reference the component being updated.
 * 
 *
 */
public abstract class DojoFormComponentUpdatingBehavior extends AjaxFormComponentUpdatingBehavior implements IAjaxIndicatorAware
{

	public DojoFormComponentUpdatingBehavior(String event) {
		super(event);
	}

	/**
	 * return the indicator Id to show it if it is in the page
	 * @return the indicator Id to show it if it is in the page
	 */
	public String getAjaxIndicatorMarkupId()
	{
		return new DojoIndicatorHandlerHelper(getComponent()).getAjaxIndicatorMarkupId();
	}

	/**
	 * return the ajax call decorator to do more than hide or show an image
	 * @return the ajax call decorator to do more than hide or show an image
	 */
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		return new DojoIndicatorHandlerHelper(getComponent()).getAjaxCallDecorator();
	}

}
