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
 package wicket.contrib.bbcodecomponent.control;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket.contrib.bbcodecomponent.core.Tags;

/**
 * Control class, add this to your page in order to be able to insert bbcode
 * works with textfield and textarea also supplied with the contrib
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 *
 */
public class BBCodeControls extends Panel implements IHeaderContributor {
	private static final ResourceReference JAVASCRIPT = new ResourceReference(
			BBCodeControls.class, "bbCodeComponent.js");

	public BBCodeControls(String id) {
		super(id);
		ArrayList list = new ArrayList(Arrays.asList(Tags.values()));
		ListView listView = new ListView("tagControls", new Model(list)) {
			@Override
			protected void populateItem(ListItem item) {
				Tags tag = (Tags) item.getModelObject();
				Button button = new Button("tagControl");
				button.add(new org.apache.wicket.markup.html.basic.Label(
						"label", new Model(tag.getName())));
				button.add(new AttributeModifier("title", new Model(tag
						.getDescription())));
				button.add(new AttributeModifier("onClick", new Model("bbCodeInsertBBTag('"+tag.getStartBBTag()+"','"+tag.getEndBBTag()+"');")));
				item.add(button);

			}
		};
		add(listView);

	}

	public void renderHead(IHeaderResponse response) {

		// Map variables = new HashMap();
		// String widgetId = getComponentMarkupId();
		// variables.put("widgetId", widgetId);
		//
		// String bBCodes = "";
		// boolean firstTag = true;
		// for (Tags t : Tags.values()) {
		// if (firstTag) {
		// firstTag = false;
		// bBCodes += t.getEndBBTag();
		// } else {
		// bBCodes += "," + t.getEndBBTag();
		//
		// }
		// }
		// variables.put("bBCodes", bBCodes);
		response.renderJavascriptReference(JAVASCRIPT);
		// TextTemplateHeaderContributor.forJavaScript(BBCodeControls.class,
		// "bbCodeComponent.js", Model.valueOf(variables)).renderHead(
		// response);

	}

	/**
	 * Gets the DOM id that the BBCode widget will get attached to.
	 * 
	 * @return The DOM id of the BBCode widget - same as the component's markup
	 *         id + 'Dp'}
	 */
	protected final String getComponentMarkupId() {
		return component.getMarkupId();
	}

	/** The target component. */
	private Component component;

	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	public void bind(Component component) {
		this.component = component;
		component.setOutputMarkupId(true);
	}

}
