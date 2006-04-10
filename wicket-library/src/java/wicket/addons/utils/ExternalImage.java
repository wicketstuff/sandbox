/*
 * $Id$ $Revision$
 * $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.addons.utils;

import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.model.IModel;

/**
 * An image component represents a localizable image resource. The image name
 * comes from the src attribute of the image tag that the component is attached
 * to. The image component responds to requests made via IResourceListener's
 * resourceRequested method. The image or subclass responds by returning an
 * IResource from getImageResource(String), where String is the source attribute
 * of the image tag.
 * 
 * @author Jonathan Locke
 */
public class ExternalImage extends WebComponent
{
	/**
	 * @see wicket.Component#Component(String)
	 */
	public ExternalImage(final String name)
	{
		super(name);
	}

	/**
	 * @see wicket.Component#Component(String, IModel)
	 */
	public ExternalImage(final String name, final IModel model)
	{
		super(name, model);
	}

	/**
	 * @see wicket.Component#handleBody(MarkupStream, ComponentTag)
	 */
	protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
	}

	/**
	 * @see wicket.Component#onComponentTag(ComponentTag)
	 */
	protected void onComponentTag(final ComponentTag tag)
	{
		checkComponentTag(tag, "img");
		super.onComponentTag(tag);

		final String resourceToLoad;
		final String imageResource = (String)getModelObject();

		if (imageResource != null)
		{
			resourceToLoad = imageResource;
		}
		else
		{
			resourceToLoad = tag.getString("src").toString();
		}

		tag.put("src", resourceToLoad.replaceAll("&", "&amp;"));
	}
}
