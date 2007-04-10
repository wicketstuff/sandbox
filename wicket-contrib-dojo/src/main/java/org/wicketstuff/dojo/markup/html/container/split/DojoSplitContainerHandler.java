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
package org.wicketstuff.dojo.markup.html.container.split;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;

/**
 * a Handler for {@link DojoSplitContainer}
 * @author vdemay
 *
 */
@SuppressWarnings("serial")
public class DojoSplitContainerHandler extends AbstractRequireDojoBehavior
{
	
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.SplitContainer");
	}

	
	protected final void respond(AjaxRequestTarget target)
	{
		//No ajax respond on this kind of container
	}

}
