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
package org.wicketstuff.table.cell;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Only an component javascript decorator. 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class OpenForEditOnRowSelection extends AbstractBehavior
{
	private Component component;

	@Override
	public void beforeRender(Component component)
	{
		this.component.setOutputMarkupId(true);
	}

	@Override
	public void bind(Component component)
	{
		this.component = component;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderOnDomReadyJavascript(String.format("handleRowSelection('%s')", component
				.getMarkupId()));
	}

}
