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
package org.wicketstuff.table.column;

import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.table.SelectableListItem;
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
		ColumnTimingBehavior timing = getColumnTimer(parent, column);
		Component cell = super.getRenderComponent(id, model, parent, row, column)
				.setOutputMarkupId(true);
		timing.addCellToUpdate(cell);
		return cell;
	}

	@Override
	public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		ColumnTimingBehavior timing = getColumnTimer(parent, column);
		Component cell = super.getEditorComponent(id, model, parent, row, column)
				.setOutputMarkupId(true);
		timing.addCellToUpdate(cell);
		return cell;
	}

	private ColumnTimingBehavior getColumnTimer(SelectableListItem parent, int column)
	{
		Component reference = parent.getParent().getParent();
		for (Iterator i = reference.getBehaviors().iterator(); i.hasNext();)
		{
			IBehavior behavior = (IBehavior)i.next();
			if (ColumnTimingBehavior.class.isAssignableFrom(behavior.getClass()))
			{
				ColumnTimingBehavior timing = (ColumnTimingBehavior)behavior;
				if (timing.getColumn() == column)
				{
					return timing;
				}
			}
		}
		ColumnTimingBehavior newTiming = null;
		reference.add(newTiming = new ColumnTimingBehavior(duration, column));
		return newTiming;
	}

}