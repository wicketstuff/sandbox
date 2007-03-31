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
package wicket.contrib.dojo.markup.html.dialog;

import wicket.AttributeModifier;
import wicket.behavior.AttributeAppender;
import wicket.markup.html.link.Link;
import wicket.model.Model;

/**
 * <p>
 * Close a {@link DojoDialog} on click on it
 * <p>
 * <p>
 * 	<pre>
 * package wicket.contrib.dojo.examples;
 * 
 * import wicket.PageParameters;
 * import wicket.contrib.dojo.markup.html.dialog.DojoDialog;
 * import wicket.contrib.dojo.markup.html.dialog.DojoDialogCloser;
 * import wicket.contrib.dojo.markup.html.dialog.DojoDialogOpener;
 * import wicket.contrib.dojo.toggle.DojoWipeToggle;
 * import wicket.markup.html.WebPage;
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
public class DojoDialogCloser extends Link{

	/**
	 * @param id
	 * @param dialog
	 */
	public DojoDialogCloser(String id, DojoDialog dialog)
	{
		super(id);
		String dialogId = dialog.getMarkupId();
		String onClick = "";
		onClick = "javascript:dojo.widget.byId('" + dialogId + "').hide(); return false;";
		this.add(new AttributeAppender("onClick", new Model(onClick),""));
		this.add(new AttributeModifier("href", new Model("#")));
	}

	@Override
	public void onClick()
	{
		//DO NOTHING
	}

}
