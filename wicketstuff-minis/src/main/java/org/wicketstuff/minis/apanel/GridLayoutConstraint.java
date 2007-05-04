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
package org.wicketstuff.minis.apanel;

/**
 * Specifies position of a component for {@link org.wicketstuff.minis.apanel.GridLayout}
 * and optionally column/row span.
 */
public class GridLayoutConstraint extends ConstraintBehavior {
	private static final long serialVersionUID = 1L;
	
	private final int col;
	private final int row;
	private int colSpan;
	private int rowSpan;

	public GridLayoutConstraint(final int col, final int row) {
		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getColSpan() {
		return colSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public ConstraintBehavior setColSpan(final int colSpan) {
		if (colSpan < 0) throw new IllegalArgumentException("colspan can't be negative : " + colSpan);

		this.colSpan = colSpan;
		return this;
	}

	public ConstraintBehavior setRowSpan(final int rowSpan) {
		if (rowSpan < 0) throw new IllegalArgumentException("rowspan can't be negative : " + rowSpan);

		this.rowSpan = rowSpan;
		return this;
	}

	boolean contains(final int col, final int row) {
		return ((col >= getCol() && col <= getCol() + getColSpan()) &&
				(row >= getRow() && row <= getRow() + getRowSpan()));
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s, %s]", col, row, colSpan, rowSpan);
	}
}
	