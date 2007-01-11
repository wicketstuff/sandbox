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

import wicket.Component;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * <p>
 * 	Dojo inlineEditBox widget for wicket
 * <p>
 * <p>
 * 	<b>Sample
 *  </b>
 *  <pre>
 * package wicket.contrib.dojo.examples;
 * 
 * import wicket.ajax.AjaxRequestTarget;
 * import wicket.contrib.dojo.markup.html.inlineeditbox.DojoInlineEditBox;
 * import wicket.markup.html.WebPage;
 * import wicket.markup.html.basic.Label;
 * 
 * public class DojoInlineEditBoxSample extends WebPage {
 * 	
 * 	public static final String DISPLAY_TEXT = "displayText";
 * 
 * 	private DojoInlineEditBox dojoInlineEditBox;
 * 	private Label displayText;	
 * 	
 * 	public DojoInlineEditBoxSample() {
 * 		
 * 		add(new DojoInlineEditBox("inlineEditBox", "inlineEditBox") {
 *  		protected void onSave(AjaxRequestTarget target) {
 * 				displayText.setModelObject(getModelObject());
 * 				target.addComponent(displayText);
 * 			}
 * 		});
 * 		
 * 		displayText = new Label(DISPLAY_TEXT, "Label");
 * 		displayText.setOutputMarkupId(true);
 * 		add(displayText);
 * 	}
 * }
 * 
 *  </pre>
 * </p>
 * @author Gregory Maes
 */
public class DojoInlineEditBox extends WebComponent {

	/**
	 * Construct a Dojo Inline Edit Box
	 * @param id id of the inlineEditBox
	 * @param label Default Value
	 */
	public DojoInlineEditBox(final String id, String label) {
		super(id, new Model(label));
		add(new DojoInlineEditBoxHandler());
	}
	
	/** Only the String object is allowed */
	public Component setModel(IModel model)	{
		if (!(model.getObject(this) instanceof String)) {
			throw new WicketRuntimeException("Model for a DojoInlineEditBox should be a String instance");
		}
		return super.setModel(model);
	}
	

	/** set the dojoType */
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_INLINE_EDIT_BOX);
		tag.put("templatePath", urlFor(new ResourceReference(DojoInlineEditBox.class, "InlineEditBox.htm")));
	}
	
	/** To initialize the text field with the model value */
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
