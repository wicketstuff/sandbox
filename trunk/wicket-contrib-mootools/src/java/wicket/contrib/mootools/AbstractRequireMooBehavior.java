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
package wicket.contrib.mootools;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.wicket.Response;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;


/**
 * Handles all state components
 * <p>
 * This class Inherits from {@link AbstractDefaultAjaxBehavior}
 * </p>
 * @author victori
 *
 */
public abstract class AbstractRequireMooBehavior extends AbstractDefaultAjaxBehavior implements IAjaxIndicatorAware {
	private MooDomReady domReadyFuncations = new MooDomReady();
	
	
	/**
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#onComponentTag(org.apache.wicket.markup.ComponentTag)
	 */
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("id",getComponent().getMarkupId());
	}
	
	/**
	 * Place any JS code here which will be appended to @{link MooDomReady} 
	 * @return
	 */
	public abstract String mooFunction();
	
	/**
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#onComponentRendered()
	 */
	protected void onComponentRendered() {
		super.onComponentRendered();
	}
	
	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(MooBase.getInstance().getMootoolsReference());
		
		if(domReadyFuncations.size() > 0){
			Response resp = response.getResponse();
			resp.write(MFXJavascriptUtils.DOM_READY_OPEN());
			Iterator i = domReadyFuncations.iterator();
			while(i.hasNext())
				resp.write((String)i.next());
			resp.write(MFXJavascriptUtils.DOM_READY_CLOSE());
			
		}
		super.renderHead(response);
	}
	
	/**
	 * Appending a MooTools JS function
	 * @param func
	 */
	public void addMooDomFunction(String func) {
		domReadyFuncations.add(func);
	}
	
	/**
	 * Array that holds MooTools JS functions to will be placed into domReady function in head.
	 * @author victori
	 *
	 */
	public class MooDomReady extends HashSet<String> {
		private static final long serialVersionUID = 1L;
	}
}
