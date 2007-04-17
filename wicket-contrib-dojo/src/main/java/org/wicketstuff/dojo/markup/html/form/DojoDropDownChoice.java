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

import org.wicketstuff.dojo.DojoIdConstants;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

@SuppressWarnings("serial")
public class DojoDropDownChoice extends DropDownChoice {
	
	private boolean handleSelectionChange = false;

	public DojoDropDownChoice(String id, IModel choices, IChoiceRenderer renderer) {
		super(id, choices, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, IModel choices, IChoiceRenderer renderer) {
		super(id, model, choices, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, IModel choices) {
		super(id, model, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, List data, IChoiceRenderer renderer) {
		super(id, model, data, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel model, List choices) {
		super(id, model, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, IModel choices) {
		super(id, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, List data, IChoiceRenderer renderer) {
		super(id, data, renderer);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id, List choices) {
		super(id, choices);
		add(new DojoDropDownChoiceHandler());
	}

	public DojoDropDownChoice(String id) {
		super(id);
		add(new DojoDropDownChoiceHandler());
	}
	
	
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "select");
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_COMBOBOX);
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

	protected void onAttach() {
		super.onAttach();
		this.setOutputMarkupId(true);
	}

}
