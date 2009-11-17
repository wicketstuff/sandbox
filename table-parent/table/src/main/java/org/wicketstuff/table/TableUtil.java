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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.list.ListItem;
import org.wicketstuff.table.cell.StylizedCell;
import org.wicketstuff.table.column.StylizedColumn;

public class TableUtil
{

	public static ListSelectionModel createSingleSelectionModel()
	{
		ListSelectionModel selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return selectionModel;
	}

	public static Set getRowsToUpdate(int[] oldSelection, int[] newSelection)
	{
		HashSet oldSelectionSet = new HashSet();
		for (int i = 0; i < oldSelection.length; i++)
		{
			oldSelectionSet.add(oldSelection[i]);
		}
		HashSet newSelectionSet = new HashSet();
		for (int i = 0; i < newSelection.length; i++)
		{
			newSelectionSet.add(newSelection[i]);
		}
		final Set newToUpdate = (Set)newSelectionSet.clone();
		newToUpdate.removeAll(oldSelectionSet);
		final Set oldToUpdate = (Set)oldSelectionSet.clone();
		oldToUpdate.removeAll(newSelectionSet);
		newToUpdate.addAll(oldToUpdate);
		return newToUpdate;
	}

	public static int[] getSelectedRows(ListSelectionModel listSelectionModel)
	{
		int iMin = listSelectionModel.getMinSelectionIndex();
		int iMax = listSelectionModel.getMaxSelectionIndex();

		if ((iMin == -1) || (iMax == -1))
		{
			return new int[0];
		}

		int[] rvTmp = new int[1 + (iMax - iMin)];
		int n = 0;
		for (int i = iMin; i <= iMax; i++)
		{
			if (listSelectionModel.isSelectedIndex(i))
			{
				rvTmp[n++] = i;
			}
		}
		int[] rv = new int[n];
		System.arraycopy(rvTmp, 0, rv, 0, n);
		return rv;
	}

	public static boolean doComponentHasBehavior(Component component,
			Class<? extends IBehavior> behavior)
	{
		for (Iterator i = component.getBehaviors().iterator(); i.hasNext();)
		{
			IBehavior b = (IBehavior)i.next();
			if (behavior.isAssignableFrom(b.getClass()))
			{
				return true;
			}
		}
		return false;
	}

	public static <T extends IBehavior> T getBehavior(Component component, Class<T> behavior)
	{
		for (Iterator i = component.getBehaviors().iterator(); i.hasNext();)
		{
			IBehavior b = (IBehavior)i.next();
			if (behavior.isAssignableFrom(b.getClass()))
			{
				return (T)b;
			}
		}
		return null;
	}

	public static void fillCellCss(final ListItem columnItem, TableModel tableModel,
			int modelColumnIndex, int modelRowIndex)
	{
		if (tableModel instanceof StylizedCell)
		{
			String cssClass = ((StylizedCell)tableModel)
					.getCellCss(modelRowIndex, modelColumnIndex);
			if (cssClass != null)
			{
				columnItem.add(new SimpleAttributeModifier("class", cssClass));
			}
		}
	}

	public static void fillColumnCss(final ListItem columnItem, TableModel tableModel,
			int modelColumnIndex)
	{
		if (tableModel instanceof StylizedColumn)
		{
			String cssClass = ((StylizedColumn)tableModel).getColumnCssClass(modelColumnIndex);
			if (cssClass != null)
			{
				columnItem.add(new SimpleAttributeModifier("class", cssClass));
			}
		}
	}

}
