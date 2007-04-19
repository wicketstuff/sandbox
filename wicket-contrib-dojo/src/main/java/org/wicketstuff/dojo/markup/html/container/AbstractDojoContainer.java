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
package org.wicketstuff.dojo.markup.html.container;

import org.wicketstuff.dojo.widgets.StylingWebMarkupContainer;

/**
 * Abstract class to create a DojoContainer
 * @author vincent demay
 *
 */
public abstract class AbstractDojoContainer extends StylingWebMarkupContainer implements IDojoContainer{
	
	private String title;
		
	/**
	 * Construct a dojo container
	 * @param parent parent where this component will be added
	 * @param id component id
	 * @param title component title
	 */
	public AbstractDojoContainer(String id, String title)
	{
		super(id);
		this.title = title;
	}
	
	/**
	 * Construct a dojo container
	 * @param parent parent where this component will be added
	 * @param id component id
	 */
	public AbstractDojoContainer( String id)
	{
		super(id);
	}
	
	/**
	 * Return the Container title
	 * @return container title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Set the container title
	 * @param title container title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	

}
