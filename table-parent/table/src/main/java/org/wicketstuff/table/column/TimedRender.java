package org.wicketstuff.table.column;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.TableUtil;
import org.wicketstuff.table.cell.ObjectRender;

/**
 * Render and editor that ensure that the cells on column participate on the
 * ColumnTimingBehavior attached to parent component.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class TimedRender extends ObjectRender
{
	private Duration duration;

	public TimedRender(Duration duration)
	{
		this.duration = duration;
	}

	@Override
	public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		ColumnTimingBehavior timing = TableUtil.getBehavior(parent, ColumnTimingBehavior.class);
		if (timing == null)
		{
			parent.add(timing = new ColumnTimingBehavior(duration));
		}
		Component cell = super.getRenderComponent(id, model, parent, row, column)
				.setOutputMarkupId(true);
		timing.addCellToUpdate(cell);
		return cell;
	}
}