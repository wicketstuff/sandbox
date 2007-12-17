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
package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Text editor
 * 
 * @author igor.vaynberg
 * 
 */
public class TextEditor extends FormComponentEditor implements Editor
{
	private final TextField field;
	private int maxLength;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 */
	public TextEditor(String id, IModel model)
	{
		super(id);
		add(field = new TextField("text", model)
		{
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				if (maxLength > 0)
				{
					tag.put("maxlen", maxLength);
				}
			}
		});
	}

	/**
	 * Sets max length
	 * 
	 * @param maxLength
	 * @return
	 */
	public TextEditor setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}


}
