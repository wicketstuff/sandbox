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
package org.wicketstuff.dojo.markup.html.dialog;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

/**
 * <p>
 * Open a {@link DojoDialog} on click on it
 * <p>
 * <p>
 * 	<pre>
 * package org.wicketstuff.dojo.examples;
 * 
 * import org.apache.wicket.PageParameters;
 * import org.wicketstuff.dojo.markup.html.dialog.DojoDialog;
 * import org.wicketstuff.dojo.markup.html.dialog.DojoDialogCloser;
 * import org.wicketstuff.dojo.markup.html.dialog.DojoDialogOpener;
 * import org.wicketstuff.dojo.toggle.DojoWipeToggle;
 * import org.apache.wicket.markup.html.WebPage;
 * 
 * public class DialogShower extends WebPage {
 * 	
 * 	public DialogShower(PageParameters parameters){
 * 		DojoDialog dialog = new DojoDialog("dialogPanel");
 * 		add(dialog);
 * 		dialog.setToggle(new DojoWipeToggle(500));
 * 		add(new DojoDialogOpener("openner", dialog));
 * 		dialog.add(new DojoDialogCloser("closer", dialog));
 * 	}
 * }
 * 
 *  </pre>
 * </p>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoDialogOpener extends Link{

	public DojoDialogOpener(String id, DojoDialog dialog)
	{
		super(id);
		String dialogId = dialog.getMarkupId();
		String onClick = "";
		onClick = "javascript:dojo.widget.byId('" + dialogId + "').show(); return false;";
		this.add(new AttributeAppender("onClick", new Model(onClick),""));
		this.add(new AttributeModifier("href", new Model("#")));
	}

	public void onClick()
	{
		//DO NOTHING
	}

}
