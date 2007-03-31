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
package wicket.contrib.dojo.markup.html.richtexteditor;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;

/**
 * Behavior that allows a TextArea to show as the Dojo Rich Text editor
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
@SuppressWarnings("serial")
public class DojoRichTextEditorBehavior extends AbstractRequireDojoBehavior {
	
	@SuppressWarnings("unchecked")
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.Editor2");
	}

	protected void respond(AjaxRequestTarget arg0) {
		// Do nothing
	}

	protected void onComponentTag(ComponentTag tag) {
		if (! tag.getName().equals("textarea"))
			throw new IllegalArgumentException("Dojo Rich Text Editor works with a <textarea>, found a <" + tag.getName() + ">");
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_RICHTEXT);
		super.onComponentTag(tag);
	}
}
