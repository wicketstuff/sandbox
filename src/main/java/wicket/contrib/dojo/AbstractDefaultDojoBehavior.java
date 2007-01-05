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
package wicket.contrib.dojo;


import java.util.Iterator;

import wicket.ResourceReference;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.IAjaxCallDecorator;
import wicket.ajax.IAjaxIndicatorAware;
import wicket.contrib.dojo.indicator.behavior.DojoIndicatorBehavior;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.resources.CompressedResourceReference;

/**
 * Handles event requests using Dojo.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 * <p> this class use {@link AjaxRequestTarget} to respond to XMLHttpRequest
 * 
 * @see <a href="http://dojotoolkit.org/">Dojo</a>
 * @author Eelco Hillenius
 */
public abstract class AbstractDefaultDojoBehavior extends AbstractDefaultAjaxBehavior implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;
	
	/** reference to the dojo support javascript file. */
	public static final ResourceReference DOJO = new CompressedResourceReference(
			AbstractDefaultDojoBehavior.class, "dojo-0.4/dojo.js");
	
	private DojoIndicatorBehavior indicatorBehavior = null;

	/* (non-Javadoc)
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(DOJO);
	}
	
	/**
	 * If a DojoIndicator has already been found does not looking for it again
	 * @return the dojoIndicator id
	 */
	private DojoIndicatorBehavior getIndicator(){
		if (indicatorBehavior == null){
			Iterator behaviors = getComponent().getBehaviors().iterator();
			while (behaviors.hasNext()){
				Object behavior = behaviors.next();
				if (behavior instanceof DojoIndicatorBehavior){
					indicatorBehavior = (DojoIndicatorBehavior)behavior;
				}
			}
		}
		return indicatorBehavior;
	}

	/**
	 * return the indicator Id to show it if it is in the page
	 * @return the indicator Id to show it if it is in the page
	 */
	public String getAjaxIndicatorMarkupId()
	{
		if (getIndicator() != null){
			return indicatorBehavior.getDojoIndicatorMarkupId();
		}
		return null;
	}

	/**
	 * return the ajax call decorator to do more than hide or show an image
	 * @return the ajax call decorator to do more than hide or show an image
	 */
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		if (getIndicator() != null){
			return indicatorBehavior.getDojoCallDecorator();
		}
		return null;
	}
	
}
