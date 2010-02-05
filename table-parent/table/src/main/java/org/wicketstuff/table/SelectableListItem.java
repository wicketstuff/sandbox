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
package org.wicketstuff.table;

import javax.swing.ListSelectionModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

/**
 * List item that resolve the view complexity for a item that can or not to be
 * selected based on a ListSelectionModel
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public abstract class SelectableListItem<T> extends ColoredListItem<T>
		implements
			IHeaderContributor
{
	private static final long serialVersionUID = 1L;
	private static final HeaderContributor TABLE_JS = JavascriptPackageResource
			.getHeaderContribution(new ResourceReference(SelectableListItem.class, "res/table.js"));
	private static final String SHIFT_P = "shiftKey";
	private static final String CTRL_P = "ctrlKey";
	private ListSelectionModel listSelectionModel;
	private boolean preventSelectTwice;

	/**
	 * @param index
	 * @param model
	 *            list item model, users parameter.
	 * @param listSelectionModel
	 *            Based on its state, the row selection is resolved. Ex: row css
	 *            class will reflect the actual state.
	 */
	public SelectableListItem(int index, IModel<T> model, ListSelectionModel listSelectionModel)
	{
		super(index, model);
		setOutputMarkupId(true);
		add(TABLE_JS);
		this.listSelectionModel = listSelectionModel;
		this.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				onItemSelection(target, Strings.isTrue(getRequest().getParameter(SHIFT_P)), Strings
						.isTrue(getRequest().getParameter(CTRL_P)));
			}

			@Override
			public CharSequence getCallbackUrl(boolean onlyTargetActivePage)
			{
				return super.getCallbackUrl(onlyTargetActivePage)
						+ String.format("&%s='+event.shiftKey+'&%s='+event.ctrlKey+'", SHIFT_P,
								CTRL_P);
			}

			@Override
			protected CharSequence getPreconditionScript()
			{
				if (preventSelectTwice)
				{
					return String.format("return Wicket.$('%s').isSelected == false",
							getComponent().getMarkupId());
				}
				else
				{
					return super.getPreconditionScript();
				}
			}
		});
	}

	/**
	 * Add only the update script to response, reducing the data used to
	 * maintain the row selection state updated.
	 * 
	 * Called from rowClicked on AbstractSelectableListView when this row has an
	 * selection that will to be lost due list mode.
	 * 
	 * @param target
	 */
	public void updateOnAjaxRequest(AjaxRequestTarget target)
	{
		target.appendJavascript(getUpdateScript());
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderOnDomReadyJavascript(getUpdateScript());
	}

	private String getUpdateScript()
	{
		return String.format("updateRow('%s', %b)", getMarkupId(), isSelected());
	}

	public boolean isSelected()
	{
		return listSelectionModel != null && listSelectionModel.isSelectedIndex(getIndex());
	}

	protected abstract void onItemSelection(AjaxRequestTarget target, boolean shiftPressed,
			boolean ctrlPressed);

	public void setPreventSelectTwice(boolean preventSelectTwice)
	{
		this.preventSelectTwice = preventSelectTwice;
	}
}