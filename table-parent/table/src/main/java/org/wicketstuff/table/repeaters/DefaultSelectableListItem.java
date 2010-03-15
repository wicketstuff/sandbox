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
package org.wicketstuff.table.repeaters;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 * @param <T>
 */
public class DefaultSelectableListItem<T> extends SelectableListItem<T>
{
	private AbstractSelectableListView abstractSelectableListView;

	public DefaultSelectableListItem(AbstractSelectableListView abstractSelectableListView,
			int index, IModel<T> model)
	{
		super(index, model, abstractSelectableListView.getListSelectionModel());
		this.abstractSelectableListView = abstractSelectableListView;
		this.setPreventSelectTwice(this.abstractSelectableListView.isPreventSelectTwice());
	}

	@Override
	protected void onItemSelection(AjaxRequestTarget target, boolean shiftPressed,
			boolean ctrlPressed)
	{
		abstractSelectableListView.rowClicked(this, target, shiftPressed, ctrlPressed);
	}

}
