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
	private int column;

	public ColumnTimingBehavior(Duration updateInterval, int column)
	{
		super(updateInterval);
		this.column = column;
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

	public int getColumn()
	{
		return column;
	}
}