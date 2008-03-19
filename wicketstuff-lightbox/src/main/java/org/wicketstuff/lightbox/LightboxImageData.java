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

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

/**
 * Encapsulates all data and options controlling the display of a single image.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 *
 */
public class LightboxImageData implements Serializable {

    String m_linkText;
    String m_imageUrl;
    String m_thumbUrl;
    String m_group;
    String m_caption;

    Boolean m_isHidden;

    Integer m_thumbWidth;
    Integer m_thumbHeight;


    /**
     * Constructor
     *
     * @param builder Pre-populated with all image parameters.
     */
    public LightboxImageData(LightboxImageData.Builder builder)
    {
        m_linkText = builder.m_linkText;
        m_imageUrl = builder.m_imageUrl;
        m_thumbUrl = builder.m_thumbUrl;
        m_group = builder.m_group;
        m_caption = builder.m_caption;
        m_isHidden = builder.m_isHidden;
        m_thumbWidth = builder.m_thumbWidth;
        m_thumbHeight = builder.m_thumbHeight;
    }

    public String getLinkText() {
        return m_linkText;
    }

    public void setLinkText(String linkText) {
        m_linkText = linkText;
    }

    public String getImageUrl() {
        return m_imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        m_imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return m_thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        m_thumbUrl = thumbUrl;
    }

    public String getGroup() {
        return m_group;
    }

    public void setGroup(String group) {
        m_group = group;
    }

    public String getCaption() {
        if (m_caption != null){
            if (m_caption.length() == 0){
                return " ";
            }
        }
        return m_caption;
    }

    public void setCaption(String caption) {
        m_caption = caption;
    }

    public Integer getThumbWidth() {
        return m_thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        m_thumbWidth = thumbWidth;
    }

    public Integer getThumbHeight() {
        return m_thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        m_thumbHeight = thumbHeight;
    }

    public Boolean getHidden() {
        return m_isHidden;
    }

    public void setHidden(Boolean hidden) {
        m_isHidden = hidden;
    }


    /**
     * Factory class that will generate the appropriate LightboxImageData instance on request.
     *
     */
    public static class Builder {

        String m_linkText;
        String m_imageUrl;
        String m_thumbUrl;
        String m_group;
        String m_caption;

        Boolean m_isHidden;

        Integer m_thumbWidth;
        Integer m_thumbHeight;

        /**
         * Standard constructor.
         *
         * @param imageUrl the url of the enlarged image.
         */
        public Builder(String imageUrl)
        {
            // Required parameters...
            m_imageUrl = imageUrl;

            // Optional parameters...
            m_linkText = "";
            m_thumbUrl = "";
            m_group = "";
            m_caption = "";
            m_isHidden = false;
            m_thumbWidth = -1;
            m_thumbHeight = -1;
        }

        /**
         * Sets the thumbnail image url.
         * @param thumbUrl the url of the thumbnail image
         * @return this builder, so that chaining calls are possible.
         */
        public Builder thumbUrl(String thumbUrl)
        {
            m_thumbUrl = thumbUrl;
            return this;
        }

        /**
         * Sets the width of the thumbnail image.  Will only be used if a thumbnail image url is specified.
         * @param width desired width of the thumbnail image
         * @return this builder, so that chaining calls are possible.
         */
        public Builder thumbWidth(Integer width)
        {
            m_thumbWidth = width;
            return this;
        }

        /**
         * Sets the height of the thumbnail image.  Will only be used if a thumbnail image url is specified.
         * @param height desired width of the thumbnail image
         * @return this builder, so that chaining calls are possible.
         */
        public Builder thumbHeight(Integer height)
        {
            m_thumbHeight = height;
            return this;
        }

        /**
         * Sets the text displayed in the link that pops up the image.  Will only be used if a thumbnail image is not.
         * @param text displayed as the link
         * @return this builder, so that chaining calls are possible.
         */
        public Builder linkText(String text)
        {
            m_linkText = text;
            return this;
        }

        /**
         * Sets the group of images the image should belong to.
         * <p/>
         * When viewing next/previous images, only the images in the current image's group will be
         * scrolled through.  To view images in another group, the user would need to click on a link
         * or thumbnail that was in that other group.
         * <p/>
         * This allows you to have multiple 'galleries' of grouped images on a single page.
         * <p/>
         * @param group name of the group that the image should belong to
         * @return this builder, so that chaining calls are possible.
         */
        public Builder group(String group)
        {
            m_group = group;
            return this;
        }

        /**
         * Sets the caption that will be displayed under the enlarged image.
         * <p/>
         * Note: this caption is -not- displayed under the thumbnail image.
         * <p/>
         * @param caption text to be displayed
         * @return this builder, so that chaining calls are possible.
         */
        public Builder caption(String caption)
        {
            m_caption = caption;
            return this;
        }

        /**
         * Sets the visibility of the image to false, preventing browsers from showing it.
         * <p/>
         * This is useful when you want to display several images in succession, but only
         * want a single image to show up on the page initially.  In that scenario, you
         * could add all the images to the same group, but hide all but one of the images.  A
         * single image would display on the web page, but once they enlarged it, they could
         * view next/previous to view the other images in the group.
         * <p/>
         * Images default to visible unless this method is called.
         * <p/>
         * @return this builder, so that chaining calls are possible.
         */
        public Builder hide()
        {
            m_isHidden = true;
            return this;
        }

        /**
         * Convenience method that returns a basic model wrapping the LightboxImageData member built from
         * this builder's settings.  The model could then be immediately passed to a LightboxImage component,
         * for example.
         *
         * @return Model wrapping this builder's LightboxImageData result
         */
        public IModel model()
        {
            return new Model(new LightboxImageData(this));
        }

        /**
         * Factory method to create a LightboxImageData instance from the Builder's current settings.
         *
         * @return LightboxImageData instance corresponding to this builder's current settings
         */
        public LightboxImageData build()
        {
            return new LightboxImageData(this);
        }
    }
}
