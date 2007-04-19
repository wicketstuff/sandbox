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
package org.wicketstuff.dojo.widgets;

import org.apache.wicket.model.IModel;

/**
 * Web container hidden by default
 * @author vdemay
 *
 */
@SuppressWarnings("serial")
public class HideWebMarkupContainer extends StylingWebMarkupContainer
{

	/**
	 * @param id
	 * @param model
	 */
	public HideWebMarkupContainer(String id, IModel model)
	{
		super(id, model);
	}

	/**
	 * @param id
	 */
	public HideWebMarkupContainer(String id)
	{
		super(id);
	}


	protected void onStyleAttribute(StyleAttribute styleAttribute)
	{
		super.onStyleAttribute(styleAttribute);
		setDisplay("none");
	}

}
