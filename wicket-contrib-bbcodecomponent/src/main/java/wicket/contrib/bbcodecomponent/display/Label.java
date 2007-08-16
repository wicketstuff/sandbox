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

package wicket.contrib.bbcodecomponent.display;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.bbcodecomponent.core.Tags;

/**
 * BBCode Label that interprets bbcode and shows html equalent
 * 
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 * 
 */
public class Label extends org.apache.wicket.markup.html.basic.Label {

	public Label(String id, IModel model) {
		super(id, model);
		setEscapeModelStrings(false);
	}

	public Label(String id, String model) {
		this(id, new Model(model));
	}

	@Override
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {
		String display = getModelObjectAsString();
		for (Tags t : Tags.values()) {
			display = display.replace(t.getStartBBTag(), t.getStartHTMLTag());
			display = display.replace(t.getEndBBTag(), t.getEndHTMLTag());
		}
		replaceComponentTagBody(markupStream, openTag, display);
	}
}
