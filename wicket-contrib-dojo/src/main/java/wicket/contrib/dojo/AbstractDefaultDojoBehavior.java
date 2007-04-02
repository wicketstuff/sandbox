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


import wicket.Application;
import wicket.ResourceReference;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.IAjaxCallDecorator;
import wicket.ajax.IAjaxIndicatorAware;
import wicket.contrib.dojo.indicator.DojoIndicatorHandlerHelper;
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
 * <p> this class use {@link AjaxRequestTarget} to respond to XMLHttpRequest </p>
 * <p> 
 * this behavior can work with a {@link DojoIndicatorBehavior} to set up an Indicator when a request 
 * has been sent and waiting for the response. This Behavior auto manage Indicator.
 * </p>
 * 
 * @see <a href="http://dojotoolkit.org/">Dojo</a>
 * @author Eelco Hillenius
 */
public abstract class AbstractDefaultDojoBehavior extends AbstractDefaultAjaxBehavior implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;
	public static boolean USE_DOJO_UNCOMPRESSED = false;

	/** reference to the dojo support javascript file. */
	public static final ResourceReference DOJO = new CompressedResourceReference(
			AbstractDefaultDojoBehavior.class, "dojo-0.4/dojo.js");
	public static final ResourceReference DOJO_UNCOMPRESSED = new CompressedResourceReference(
			AbstractDefaultDojoBehavior.class, "dojo-0.4/dojo.js.uncompressed.js");
	public static final ResourceReference DOJO_WICKET =  new CompressedResourceReference(
			AbstractRequireDojoBehavior.class, "dojo-wicket/dojoWicket.js");

	/** A unique ID for the JavaScript Dojo debug script */
	private static final String JAVASCRIPT_DOJO_DEBUG_ID = AbstractDefaultDojoBehavior.class.getName() + "/debug";

	/** A unique ID for the JavaScript Dojo console debug */
	private static final String JAVASCRIPT_DOJO_CONSOLE_DEBUG_ID = AbstractDefaultDojoBehavior.class.getName() + "/consoleDebug";

	/**
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		// enable dojo debug if our configuration type is DEVELOPMENT
		final String configurationType = Application.get().getConfigurationType();
		if (configurationType.equalsIgnoreCase(Application.DEVELOPMENT)) {
			StringBuffer debugScript = new StringBuffer();
			debugScript.append("var djConfig = {};\n");
			debugScript.append("djConfig.isDebug = true;\n");
			response.renderJavascript(debugScript.toString(), JAVASCRIPT_DOJO_DEBUG_ID);
		}
		
		DojoLocaleManager.getInstance().renderLocale(response);
		if (USE_DOJO_UNCOMPRESSED)
			response.renderJavascriptReference(DOJO_UNCOMPRESSED);
		else
			response.renderJavascriptReference(DOJO);
		response.renderJavascriptReference(DOJO_WICKET);
		
		// debug on firebug console if it is installed, otherwise it will just
		// end up at the bottom of the page.
		if (configurationType.equalsIgnoreCase(Application.DEVELOPMENT)) {
			StringBuffer consoleDebugScript = new StringBuffer();
			consoleDebugScript.append("dojo.require(\"dojo.debug.console\");\n");
			consoleDebugScript.append("dojo.require(\"dojo.widget.Tree\");\n");
			response.renderJavascript(consoleDebugScript.toString(), JAVASCRIPT_DOJO_CONSOLE_DEBUG_ID);
		}
	}

	/**
	 * return the indicator Id to show it if it is in the page
	 * @return the indicator Id to show it if it is in the page
	 */
	public String getAjaxIndicatorMarkupId()
	{
		return new DojoIndicatorHandlerHelper(getComponent()).getAjaxIndicatorMarkupId();
	}

	/**
	 * return the ajax call decorator to do more than hide or show an image
	 * @return the ajax call decorator to do more than hide or show an image
	 */
	protected IAjaxCallDecorator getAjaxCallDecorator()
	{
		return new DojoIndicatorHandlerHelper(getComponent()).getAjaxCallDecorator();
	}
	
}
