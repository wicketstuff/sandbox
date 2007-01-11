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

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_DIALOG;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.contrib.dojo.widgets.HideWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.model.Model;

/**
 * <p>
 * Dialog showing a Dojo dialog. Associated with {@link DojoDialogCloser} and {@link DojoDialogOpener} to hide and show it
 * <p>
 * <p>
 * 	<pre>
 * 	public class DialogShower extends WebPage {
 *	
 *	public DialogShower(PageParameters parameters){
 *		DojoDialog dialog = new DojoDialog(this,"dialogPanel");
 *		dialog.setToggle(new DojoWipeToggle(500));
 *		new DojoDialogOpener(this, "openner", dialog);
 *		new DojoDialogCloser(dialog, "closer", dialog);
 *	}
 *}
 *
 *  </pre>
 * </p>
 * @author Vincent Demay
 *
 */
public class DojoDialog extends HideWebMarkupContainer
{

	/**
	 * @param parent
	 * @param id
	 */
	public DojoDialog(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.add(new DojoDialogHandler());
		this.setOutputMarkupId(true);
	}

	/**
	 * Set the dialog effect : see {@link DojoToggle}
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_DIALOG);
	}
}
