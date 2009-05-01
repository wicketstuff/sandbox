/*
 * This work is licensed under the Creative Commons Attribution
 * License 2.5. You may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by/2.5/
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * See the included notice for any additional licensing information.
 */

package org.wicketstuff.lightbox;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.Resource;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.IValueMap;

/**
 * Component which wraps the functionality of Lightbox2.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 * 
 */
public class LightboxImage extends Panel implements IResourceListener {

	/**
	 * Standard constructor.
	 * 
	 * @param componentId
	 *            the component id
	 * @param model
	 *            the component's id
	 */
	public LightboxImage(String componentId, IModel<LightboxImageData> model) {
		super(componentId, model);
		add(new LightboxBehavior());
		addContent();
	}

	protected LightboxImageData getImageData() {
		return getImageDataModel().getObject();
	}

	private IModel<LightboxImageData> getImageDataModel() {
		return (IModel<LightboxImageData>) getDefaultModel();
	}

	public void onResourceRequested() {
		LightboxImageData m = getImageData();
		if (m.getImageResource() != null) {
			m.getImageResource().onResourceRequested();
		}
	}

	/**
	 * Ensures that the component tag used is an anchor.
	 * 
	 * @param componentTag
	 *            the tag, which will be set to an anchor
	 */
	@Override
	protected void onComponentTag(ComponentTag componentTag) {
		componentTag.setName("a");

		IValueMap atts = componentTag.getAttributes();// .put("class", "test");

		// set appropriate tag values
		LightboxImageData data = getImageData();

		// set the URL
		if (data.getImageResource() != null) {
			atts.put("href", urlFor(IResourceListener.INTERFACE));
		} else {
			String href = getRequest().getRelativePathPrefixToContextRoot()
					+ getImageData().getImageUrl();
			atts.put("href", href);
		}

		// rel
		atts.put("rel", getRel(data));

		// title
		atts.put("title", data.getCaption());

		// style
		if (data.getHidden())
			atts.put("style", "display:none;");

		super.onComponentTag(componentTag);
	}

	/**
	 * Adds the nested components to this component.
	 * 
	 * @param imageData
	 *            about the image to be displayed by this lightbox
	 */
	private void addContent() {
		addThumb();
		addText();
	}

	/**
	 * Adds textual content to the component, if specified.
	 * 
	 * @param imageData
	 *            about the image to be displayed by this lightbox
	 */
	private void addText() {
		Label tc = new Label("tc", new PropertyModel<String>(
				getImageDataModel(), "linkText")) {
			@Override
			public boolean isVisible() {
				return this.getDefaultModelObjectAsString().length() > 0;
			}
		};
		add(tc);
	}

	/**
	 * Adds thumbnail image to the component, if specified.
	 * 
	 * @param imageData
	 *            about the image to be displayed by this lightbox
	 */
	private void addThumb() {
		add(new ThumbImage("ic", getImageDataModel()));
	}

	/**
	 * Provides the value of a lightbox specific attribute.
	 * 
	 * @param imageData
	 *            about the image to be displayed in the lightbox
	 * @return the value to which the 'rel' attribute should be set.
	 */
	private String getRel(LightboxImageData imageData) {
		// rel should be:
		// "lightbox" if the image is not associated with the group
		// "lightbox[GROUP]" if the image is associated with a group

		if (imageData.getGroup().length() > 0) {
			return "lightbox[" + imageData.getGroup() + "]";
		} else {
			return "lightbox";
		}
	}

	// ================================================================
	// Helper class for adding a thumbnail image

	private static final class ThumbImage extends WebMarkupContainer {
		private ThumbImage(String id, IModel<?> model) {
			super(id, model);
		}

		@Override
		public boolean isVisible() {
			LightboxImageData imageData = (LightboxImageData) getDefaultModelObject();
			boolean hasThumb = imageData.getThumbUrl().length() > 0
					|| imageData.getThumbResource() != null;
			return hasThumb;
		}

		@Override
		protected void onComponentTag(ComponentTag tag) {
			IValueMap atts = tag.getAttributes();

			LightboxImageData imageData = (LightboxImageData) getDefaultModelObject();
			boolean hasWidth = imageData.getThumbWidth() > 0;
			boolean hasHeight = imageData.getThumbHeight() > 0;
			String thumbUrl = getRequest()
					.getRelativePathPrefixToContextRoot()
					+ imageData.getThumbUrl();
			Resource thumbResource = imageData.getThumbResource();

			if (thumbResource == null) {
				atts.put("src", thumbUrl);
			} else {
				atts.put("src", urlFor(IResourceListener.INTERFACE));
			}

			if (hasHeight) {
				atts.put("height", imageData.getThumbHeight().toString());
			}
			if (hasWidth) {
				atts.put("width", imageData.getThumbWidth().toString());
			}
			
			super.onComponentTag(tag);
		}
	}
}
