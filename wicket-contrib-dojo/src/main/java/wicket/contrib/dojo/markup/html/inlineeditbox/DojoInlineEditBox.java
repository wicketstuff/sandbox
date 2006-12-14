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
package wicket.contrib.dojo.markup.html.inlineeditbox;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_INLINE_EDIT_BOX;
import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.model.Model;

/**
 * <p>
 * 	Dojo inlineEditBox widget for wicket
 * <p>
 * <p>
 * 	<b>Sample
 *  </b>
 *  <pre>
 *  public class DojoInlineEditBoxSample extends WebPage {
 *	
 *	public static final String DISPLAY_TEXT = "displayText";
 *
 *	private DojoInlineEditBox dojoInlineEditBox;
 *	private Label displayText;
 *	
 *
 *	public DojoInlineEditBoxSample() {
 *		
 *		new DojoInlineEditBox(this,"inlineEditBox", "inlineEditBox") {
 *			
 *			protected void onSave(AjaxRequestTarget target) {
 *				displayText.setModelObject(getModelObject());
 *				target.addComponent(displayText);
 *			}
 *		};
 *		
 *		displayText = new Label(this, DISPLAY_TEXT, "Label");
 *		displayText.setOutputMarkupId(true);
 *	}
 *}
 *  </pre>
 * </p>
 * @author Gregory Maes
 */
public class DojoInlineEditBox extends WebComponent<String> {

	/**
	 * Construct a Dojo Inline Edit Box
	 * @param parent parent where the inlineEditBox will be added
	 * @param id id of the inlineEditBox
	 * @param label Default Value
	 */
	public DojoInlineEditBox(MarkupContainer parent, final String id, String label) {
		super(parent, id, new Model<String>(label));
		add(new DojoInlineEditBoxHandler());
	}
	
	/**
	 * set the dojoType 
	 * @param tag 
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_INLINE_EDIT_BOX);
	}
	
	/** To initialize the text field with the model value */
	@Override
	protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		replaceComponentTagBody(markupStream, openTag, getModelObjectAsString());
	}
	
	/**
	 * To be overridden for custom action
	 * This function is called after having updated the model
	 * @param target {@link AjaxRequestTarget}
	 */
	protected void onSave(AjaxRequestTarget target) {
		
	}
	
}
