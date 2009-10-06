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

import java.io.Serializable;

import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;


/**
 * @see javax.swing.table.TableColumn
 */
public class TableColumn implements Serializable
{
	private CellRender cellRender;
	private CellEditor cellEditor;
	private int modelIndex;

	public TableColumn(int modelIndex)
	{
		this(modelIndex, null, null);
	}

	public TableColumn(int modelIndex, CellRender cellRender, CellEditor cellEditor)
	{
		this.cellRender = cellRender;
		this.cellEditor = cellEditor;
		this.modelIndex = modelIndex;
	}


	public CellRender getCellRender()
	{
		return cellRender;
	}

	public void setCellRender(CellRender cellRender)
	{
		this.cellRender = cellRender;
	}

	public CellEditor getCellEditor()
	{
		return cellEditor;
	}

	public void setCellEditor(CellEditor cellEditor)
	{
		this.cellEditor = cellEditor;
	}

	/**
	 * @see javax.swing.table.TableColumn#getModelIndex()
	 */
	public int getModelIndex()
	{
		return modelIndex;
	}
}
