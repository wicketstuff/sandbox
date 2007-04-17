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
package org.wicketstuff.dojo.markup.html.container.page;

import org.apache.wicket.PageParameters;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebPage;

/**
 * Container able to render a {@link WebPage} in it
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoPageContainer extends DojoSimpleContainer
{
	
	private Class pageClass;
	private String refresh = "false"; 
	
	/**
	 * Construct a DojoPageContainer
	 * @param id container id
	 * @param title container title
	 */
	public DojoPageContainer(final String id, final String title)
	{
		super(id, title);
	}

	/**
	 * Construct a DojoPageContainer
	 * @param id container id
	 */
	public DojoPageContainer(String id)
	{
		super(id);
	}
	
	/**
	 * Construct a DojoPageContainer
	 * @param id container id
	 * @param pageClass class representing the page class to render in the component
	 */
	public DojoPageContainer(String id, Class pageClass)
	{
		super(id);
		this.pageClass = pageClass;
	}
	
	/**
	 * 
	 * @param id container id
	 * @param title container title
	 * @param pageClass class representing the page class to render in the component
	 */
	public DojoPageContainer(String id, String title, Class  pageClass)
	{
		super(id, title);
		this.pageClass = pageClass;
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("href", urlFor(pageClass, new PageParameters()));
		tag.put("refreshOnShow", getRefresh());
	}

	/**
	 * return the page class render in the container
	 * @return the page class render in the container
	 */
	public Class getPageClass()
	{
		return pageClass;
	}

	/**
	 * set the page class to render in the container
	 * @param pageClass page class to render in the container
	 */
	public void setPageClass(Class pageClass)
	{
		this.pageClass = pageClass;
	}
	
	/**
	 * Set true to autorefresh content each time the container is shown
	 * @param refresh true to autorefresh content each time the container is shown false otherwise
	 */
	public void setRefresh(boolean refresh){
		if (refresh){
			this.refresh = "true";
		}else{
			this.refresh = "false";
		}
	}
	
	/**
	 * return true to autorefresh content each time the container is shown false otherwise
	 * @return true to autorefresh content each time the container is shown false otherwise
	 */
	public boolean getRefresh(){
		if ("true".equals(this.refresh)){
			return true;
		}else{
			return false;
		}
	}

}
