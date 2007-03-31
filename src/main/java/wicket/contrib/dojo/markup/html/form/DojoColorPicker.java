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
package wicket.contrib.dojo.markup.html.form;

import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * 
 */
@SuppressWarnings("serial")
public class DojoColorPicker extends TextField {

	private boolean allowInput = false;
	
	/**
	 * @param id
	 * @param model
	 */
	public DojoColorPicker(String id, IModel model) {
		super(id, model);
		add(new DojoColorPickerHandler());
		this.setOutputMarkupId(true);
	}

	/**
	 * @param id
	 */
	public DojoColorPicker(String id) {
		this(id, null);
	}

	protected void onComponentTag(ComponentTag tag)	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_COLORPICKER);
		if (!this.allowInput){
			tag.put("inputNotAllowed", "true");
		}
	}
	/**
	 * Set the date picker effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("containerToggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("containerToggleDuration", new Model(toggle.getDuration() + ""),""));
	}

	public boolean isAllowInput() {
		return allowInput;
	}

	/**
	 * Allow or not to input with keyboard in the field. If true, field can only be field by the date picker
	 * @param allowInput If true, field can only be field by the date picker
	 */
	public void setAllowInput(boolean allowInput) {
		this.allowInput = allowInput;
	}
	

}
