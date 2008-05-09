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

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.Resource;

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
     * @param componentId the component id
     * @param model the component's id
     */
    public LightboxImage(String componentId, IModel model)
    {
        super(componentId, model);

        LightboxImageData m = (LightboxImageData)model.getObject();

        add(new LightboxBehavior());
        addTagAttributes(m);
        addContent(m);
    }

    /**
     * Adds all attributes needed for the component to function to the tag.
     *
     * @param imageData data about the image that will be displayed in the lightbox
     */
    private void addTagAttributes(LightboxImageData imageData)
    {
        String rel = getRel(imageData);
        String title = imageData.getCaption();

        add(new SimpleAttributeModifier("rel", rel));
        add(new SimpleAttributeModifier("title", title));

        if (imageData.getImageResource() == null) {
            String href = getRequest().getRelativePathPrefixToContextRoot() + imageData.getImageUrl();
            add(new SimpleAttributeModifier("href", href));
        }

        if (imageData.getHidden()){
            add(new SimpleAttributeModifier("style", "display:none;"));
        }
    }

    public void onResourceRequested() {
        LightboxImageData m = (LightboxImageData) getModelObject();
        if (m.getImageResource() != null) {
            m.getImageResource().onResourceRequested();
        }
    }

    /**
     * Add the proper URL as href if we use a resource as input. This has to be done
     * that late, because we need the parent page for getting to the URL
     */
    @Override
    protected void onBeforeRender() {
        LightboxImageData data = (LightboxImageData) getModelObject();
        if (data.getImageResource() != null) {
            add(new SimpleAttributeModifier("href", urlFor(IResourceListener.INTERFACE)));
        }
        super.onBeforeRender();
    }

    /**
     * Ensures that the component tag used is an anchor.
     *
     * @param componentTag the tag, which will be set to an anchor
     */
    @Override
    protected void onComponentTag(ComponentTag componentTag)
    {
        componentTag.setName("a");
        super.onComponentTag(componentTag);
    }

    /**
     * Adds the nested components to this component.
     *
     * @param imageData about the image to be displayed by this lightbox
     */
    private void addContent(LightboxImageData imageData)
    {
        addThumb(imageData);
        addText(imageData);
    }

    /**
     * Adds textual content to the component, if specified.
     *
     * @param imageData about the image to be displayed by this lightbox
     */
    private void addText(LightboxImageData imageData)
    {
        Label tc = new Label("tc", imageData.getLinkText());
        tc.setVisible(imageData.getLinkText().length() > 0);
        add(tc);
    }

    /**
     * Adds thumbnail image to the component, if specified.
     *
     * @param imageData about the image to be displayed by this lightbox
     */
    private void addThumb(LightboxImageData imageData)
    {
        String thumbUrl = getRequest().getRelativePathPrefixToContextRoot() + imageData.getThumbUrl();
        boolean hasThumb = imageData.getThumbUrl().length() > 0 || imageData.getThumbResource() != null;
        boolean hasWidth = imageData.getThumbWidth() > 0;
        boolean hasHeight = imageData.getThumbHeight() > 0;

        final Resource thumbResource = imageData.getThumbResource();
        WebMarkupContainer ic;
        if (thumbResource != null) {
            ic = new ResourceWebMarkupContainer("ic", thumbResource);
        } else {
            ic = new WebMarkupContainer("ic");
        }
        ic.setVisible(hasThumb);
        if (thumbResource == null) {
            ic.add( new SimpleAttributeModifier("src", thumbUrl));
        }
        if(hasHeight){
            ic.add( new SimpleAttributeModifier("height", imageData.getThumbHeight().toString()));
        }
        if(hasWidth){
            ic.add( new SimpleAttributeModifier("width", imageData.getThumbWidth().toString()));
        }
        add(ic);
    }

    /**
     * Provides the value of a lightbox specific attribute.
     *
     * @param imageData about the image to be displayed in the lightbox
     * @return the value to which the 'rel' attribute should be set.
     */
    private String getRel( LightboxImageData imageData)
    {
        // rel should be:
        //   "lightbox" if the image is not associated with the group
        //   "lightbox[GROUP]" if the image is associated with a group

        if (imageData.getGroup().length() > 0 ) {
            return "lightbox[" + imageData.getGroup() + "]";
        } else {
            return "lightbox";
        }
    }

    // ================================================================
    // Helper class for adding a dynamic resource

    private class ResourceWebMarkupContainer extends WebMarkupContainer implements IResourceListener {
        private final Resource resource;

        ResourceWebMarkupContainer(String pId, Resource pResource) {
            super(pId);
            resource = pResource;
        }

        @Override
        protected void onBeforeRender() {
            if (resource != null) {
                add(new SimpleAttributeModifier("src", urlFor(IResourceListener.INTERFACE)));
            }
            super.onBeforeRender();
        }

        public void onResourceRequested() {
            if (resource != null) {
                resource.onResourceRequested();
            }
        }
    }
}
