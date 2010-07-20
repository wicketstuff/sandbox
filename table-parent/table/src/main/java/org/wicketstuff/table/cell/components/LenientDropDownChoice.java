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
package org.wicketstuff.table.cell.components;

import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

/**
 * Simple DropDownChoice extension that don't require an select tag on the
 * template markup.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class LenientDropDownChoice<T> extends DropDownChoice<T>
{

	public LenientDropDownChoice(String id, IModel<? extends List<? extends T>> choices,
			IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);

	}

	public LenientDropDownChoice(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);

	}

	public LenientDropDownChoice(String id, IModel<T> model,
			IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);

	}

	public LenientDropDownChoice(String id, IModel<T> model,
			IModel<? extends List<? extends T>> choices)
	{
		super(id, model, choices);

	}

	public LenientDropDownChoice(String id, IModel<T> model, List<? extends T> data,
			IChoiceRenderer<? super T> renderer)
	{
		super(id, model, data, renderer);

	}

	public LenientDropDownChoice(String id, IModel<T> model, List<? extends T> choices)
	{
		super(id, model, choices);

	}

	public LenientDropDownChoice(String id, List<? extends T> data,
			IChoiceRenderer<? super T> renderer)
	{
		super(id, data, renderer);

	}

	public LenientDropDownChoice(String id, List<? extends T> choices)
	{
		super(id, choices);

	}

	public LenientDropDownChoice(String id)
	{
		super(id);

	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		tag.setName("select");
		super.onComponentTag(tag);
	}
}
