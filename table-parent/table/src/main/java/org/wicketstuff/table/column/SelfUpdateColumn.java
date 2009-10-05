package org.wicketstuff.table.column;

import org.apache.wicket.util.time.Duration;
import org.wicketstuff.table.cell.ObjectRender;

/**
 * Provides slots for a renderer and an editor that uses
 * AbstractAjaxTimerBehavior to keep data on column updated on client.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class SelfUpdateColumn extends TableColumn
{
	public SelfUpdateColumn(int modelIndex, Duration duration)
	{
		super(modelIndex);
		ObjectRender render = new TimedRender(duration);
		setCellEditor(render);
		setCellRender(render);
	}

}