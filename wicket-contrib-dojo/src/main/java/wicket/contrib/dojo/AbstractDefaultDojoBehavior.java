/*
 * $Id: DojoAjaxHandler.java 594 2006-02-22 05:54:55 -0800 (Wed, 22 Feb 2006)
 * joco01 $ $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.dojo;


import wicket.Page;
import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AbstractAjaxBehavior;
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
 * TODO : see {@link AbstractDefaultAjaxBehavior} to add javascriptCallBack and indicator
 */
public abstract class AbstractDefaultDojoBehavior extends AbstractAjaxBehavior
{
	
	private static final long serialVersionUID = 1L;
	
	/** reference to the dojo support javascript file. */
	private static final ResourceReference DOJO = new CompressedResourceReference(
			AbstractDefaultDojoBehavior.class, "dojo0.3/dojo.js");
	
	/** reference to the default dojo ajax updater support javascript file. */
	private static final ResourceReference DOJO_UPDATER = new CompressedResourceReference(
			AbstractDefaultDojoBehavior.class, "dojo-ajax-updater.js");
	
	
	/**
	 * Subclasses should call super.onBind()
	 * 
	 * @see wicket.behavior.AbstractAjaxBehavior#onBind()
	 */
	@Override
	protected void onBind()
	{
		getComponent().setOutputMarkupId(true);
	}
	
	/**
	 * @see wicket.behavior.AbstractAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(DOJO);
		response.renderJavascriptReference(DOJO_UPDATER);
	}

	/**
	 * @see wicket.behavior.IBehaviorListener#onRequest()
	 */
	public void onRequest()
	{
		boolean isPageVersioned = true;
		Page page = getComponent().getPage();
		try {
			isPageVersioned = page.isVersioned();
			page.setVersioned(false);
			
			AjaxRequestTarget target = new AjaxRequestTarget();
			RequestCycle.get().setRequestTarget(target);
			respond(target);
		}
		finally{
			page.setVersioned(isPageVersioned);
		}
	}


	protected abstract void respond(AjaxRequestTarget target);
}
