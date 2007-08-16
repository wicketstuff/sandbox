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
 package wicket.contrib.bbcodecomponent.edit;

import org.apache.wicket.model.IModel;

import wicket.contrib.bbcodecomponent.control.bbCodeSetActiveTextFieldAttributeModifier;


/**
 * must add BBCodeControls to same page as adding this else some javascript will
 * fail
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 */
public class TextField extends org.apache.wicket.markup.html.form.TextField {

	public TextField(String id, IModel text) {
		super(id, text);
		setOutputMarkupId(true);

	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		add(new bbCodeSetActiveTextFieldAttributeModifier(this.getMarkupId()));
	}

}
