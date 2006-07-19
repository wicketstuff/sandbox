/*
 * $Id$ $Revision$ $Date$
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
package wicket.examples.cdapp;

import java.sql.Blob;

import wicket.MarkupContainer;
import wicket.Page;
import wicket.PageMap;
import wicket.examples.cdapp.model.CD;
import wicket.markup.html.WebResource;
import wicket.markup.html.image.resource.BlobImageResource;
import wicket.markup.html.link.Link;
import wicket.markup.html.link.PopupSettings;
import wicket.model.IModel;
import wicket.util.resource.IResourceStream;

/**
 * Link that displays the image of a cd in a popup window that resizes to fit.
 * 
 * @author Eelco Hillenius
 */
public final class ImagePopupLink extends Link<CD>
{
	/**
	 * Construct.
	 * 
	 * @param parent
	 *            The parent
	 * @param id
	 *            The component id
	 * @param cdModel
	 *            The model
	 */
	public ImagePopupLink(MarkupContainer parent, String id, IModel<CD> cdModel)
	{
		super(parent, id, cdModel);

		// custom popup settings that uses our automatic resize script
		PopupSettings popupSettings = new PopupSettings(PageMap.forName("imagepopup"));
		popupSettings.setHeight(20);
		popupSettings.setWidth(20);
		setPopupSettings(popupSettings);
	}

	/**
	 * @see wicket.markup.html.link.Link#onClick()
	 */
	public void onClick()
	{
		WebResource imgResource = new WebResource()
		{
			public IResourceStream getResourceStream()
			{
				BlobImageResource img = new BlobImageResource()
				{
					protected Blob getBlob()
					{
						CD cd = getModelObject();
						return cd.getImage();
					}
				};
				return img.getResourceStream();
			}
		};
		getRequestCycle().setResponsePage(new ImagePopup(imgResource));
	}

	/**
	 * @see wicket.markup.html.link.Link#linksTo(wicket.Page)
	 */
	protected boolean linksTo(Page page)
	{
		// this is kind of ugly, but as isEnabled is marked final, this
		// is our best option; otherwise we would have to override render
		final CD cd = getModelObject();
		return cd.getImage() == null;
	}
}