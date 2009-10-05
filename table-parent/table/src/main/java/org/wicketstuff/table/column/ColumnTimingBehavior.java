package org.wicketstuff.table.column;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;

/**
 * Timing behavior that updates every component on columnCells list.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ColumnTimingBehavior extends AbstractAjaxTimerBehavior
{
	List<Component> columnCells = new ArrayList();

	public ColumnTimingBehavior(Duration updateInterval)
	{
		super(updateInterval);
	}

	public void addCellToUpdate(Component columnComponent)
	{
		columnCells.add(columnComponent);
	}

	@Override
	protected void onTimer(AjaxRequestTarget target)
	{
		for (Component cell : columnCells)
		{
			target.addComponent(cell);
		}
	}
}