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
package org.wicketstuff.dojo.markup.html.richtexteditor;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.wicketstuff.dojo.AbstractDojoWidgetBehavior;
import org.wicketstuff.dojo.DojoIdConstants;

/**
 * Behavior that allows a TextArea to show as the Dojo Rich Text editor
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
@SuppressWarnings("serial")
public class DojoRichTextEditorBehavior extends AbstractDojoWidgetBehavior {

	public static final String DOJO_EDITOR2_TOOLBAR_URL = "toolbarTemplatePath";
	public static final String DOJO_EDITOR2_HEIGHT = "height";
	public static final String DOJO_EDITOR2_SHARE_TOOLBAR = "shareToolbar";
	
	private ResourceReference toolbarResourceReference;
	private Integer height;
	private Boolean shareToolbar;
	
	/**
	 * Default constructor that sets all editor settings to null, which are the 
	 * defaults.
	 */
	public DojoRichTextEditorBehavior() {
		this.toolbarResourceReference = null;
		this.height = null;
		this.shareToolbar = null;
	}
	
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getShareToolbar() {
		return shareToolbar;
	}

	public void setShareToolbar(Boolean shareToolbar) {
		this.shareToolbar = shareToolbar;
	}

	public ResourceReference getToolbarResourceReference() {
		return toolbarResourceReference;
	}

	public void setToolbarResourceReference(ResourceReference toolbar) {
		this.toolbarResourceReference = toolbar;
	}

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.Editor2");
	}

	protected void respond(AjaxRequestTarget arg0) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see org.wicketstuff.dojo.AbstractDojoWidgetBehavior#getWidgetType()
	 */
	@Override
	protected String getWidgetType() {
		return DojoIdConstants.DOJO_TYPE_RICHTEXT;
	}
	
	protected void onComponentTag(ComponentTag tag) {
		if (! tag.getName().equals("textarea"))
			throw new IllegalArgumentException("Dojo Rich Text Editor works with a <textarea>, found a <" + tag.getName() + ">");
		//tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_RICHTEXT);
		super.onComponentTag(tag);
	}
	
	/* (non-Javadoc)
	 * @see org.wicketstuff.dojo.AbstractDojoWidgetBehavior#getWidgetProperties()
	 */
	@Override
	protected WidgetProperties getWidgetProperties() {
		WidgetProperties props = new WidgetProperties();
		
		if (this.toolbarResourceReference != null) {
			props.addProperty(DOJO_EDITOR2_TOOLBAR_URL, this.toolbarResourceReference);
		}
		
		// + ", height: 100, shareToolbar: false }";
		if (this.height != null) {
			props.addProperty(DOJO_EDITOR2_HEIGHT, this.height);
		}
		
		if (this.shareToolbar != null) {
			props.addProperty(DOJO_EDITOR2_SHARE_TOOLBAR, this.shareToolbar);
		}
		
		return props;
	}
}
