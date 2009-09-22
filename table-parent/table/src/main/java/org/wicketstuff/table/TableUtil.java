package org.wicketstuff.table;

import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

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

}
