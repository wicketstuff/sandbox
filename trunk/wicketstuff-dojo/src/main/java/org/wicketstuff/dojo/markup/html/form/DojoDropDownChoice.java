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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.IDojoWidget;

@SuppressWarnings("serial")
/**
 * Dojo ComboBox that uses the Wicket model object to know the items in the
 * list. NOTE: instead of overriding the wantOnSelectionChangedNotifications()
 * method, use setHandleSelectionChange() to be notified when the value changes,
 * and override #onSetValue() instead of #onSelectionChanged(). Override
 * onNewValue() to be notified during updateModel() when the user does not
 * select an existing option but types in a new value.
 * 
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class DojoDropDownChoice extends DropDownChoice implements IDojoWidget {



	private boolean handleSelectionChange = false;

	public void onSetValue(AjaxRequestTarget target) {
	}

	protected void onNewValue(String value) {
	}

	@Override
	public void updateModel() {
		String value = getRequest().getParameter(getInputName());
		if (getModelObject() == null && value != null && value.length() > 0)
			onNewValue(value);
		else
			super.updateModel();
	}

	public DojoDropDownChoice(String id, IModel choices, IChoiceRenderer renderer) {
		super(id, choices, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, IModel choices, IChoiceRenderer renderer) {
		super(id, model, choices, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, IModel choices) {
		super(id, model, choices, new DojoChoiceRenderer());
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, List data, IChoiceRenderer renderer) {
		super(id, model, data, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, List choices) {
		super(id, model, choices, new DojoChoiceRenderer());
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel choices) {
		super(id, choices, new DojoChoiceRenderer());
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, List data, IChoiceRenderer renderer) {
		super(id, data, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, List choices) {
		super(id, choices, new DojoChoiceRenderer());
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id) {
		super(id);
		add(new DojoDropDownChoiceHandler());
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_COMBOBOX;
	}

	protected void onComponentTag(ComponentTag tag) {
		checkComponentTag(tag, "select");
	}

	public boolean isHandleSelectionChange() {
		return handleSelectionChange;
	}

	public void setHandleSelectionChange(boolean handleSelectionChange) {
		this.handleSelectionChange = handleSelectionChange;
	}

	protected final boolean wantOnSelectionChangedNotifications() {
		return isHandleSelectionChange();
	}
	
	@Override
	protected void onBeforeRender() {
		// TODO Auto-generated method stub
		super.onBeforeRender();
		add(new AttributeAppender("id", new Model(getMarkupId()),""));
	}
}
