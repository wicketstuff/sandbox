/*
 * $Id$ $Revision$
 * $Date$
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


import wicket.Application;
import wicket.IInitializer;
import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.PackageResource;
import wicket.util.resource.IResourceStream;

/**
 * Handles event requests using Dojo.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 * 
 * @see <a href="http://dojotoolkit.org/">Dojo</a>
 * @author Eelco Hillenius
 * @deprecated will be remove in 2.0 use {@link AbstractDefaultDojoBehavior}
 */
public abstract class DojoAjaxHandler extends AbstractAjaxBehavior implements IInitializer
{
	/**
	 * Construct.
	 */
	public DojoAjaxHandler()
	{
	}

	/**
	 * Register packaged javascript files.
	 * 
	 * @param application
	 *            The application
	 */
	public void init(Application application)
	{
		PackageResource.bind(application, DojoAjaxHandler.class, "dojo.js");
	}

	/**
	 * @see wicket.behavior.AbstractAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(DojoAjaxHandler.class, "dojo.js"));
	}

	/**
	 * @see wicket.behavior.IBehaviorListener#onRequest()
	 */
	public void onRequest()
	{
		IResourceStream response = getResponse();
		if (response != null)
		{
			boolean isPageVersioned = true;
			try
			{
				isPageVersioned = getComponent().getPage().isVersioned();
				getComponent().getPage().setVersioned(false);

				DojoRequestTarget target = new DojoRequestTarget(response);
				RequestCycle.get().setRequestTarget(target);
			}
			finally
			{
				getComponent().getPage().setVersioned(isPageVersioned);
			}
		}
	}


	protected abstract IResourceStream getResponse();
}
