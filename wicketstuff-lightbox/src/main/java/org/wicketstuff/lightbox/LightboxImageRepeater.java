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

import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import java.util.Collection;

/**
 * Convenience class providing the ability to quickly
 * add collections of lightbox images to a page.
 * <p/>
 * This file was created on Mar 1, 2008 by s.crissman.
 */
public class LightboxImageRepeater extends RepeatingView {

    private Boolean usePanels;
    private int count;

    /**
     * Constructor.
     * <p/>
     * Repeater will render LightboxImage components, and will not
     * change the visibility of the images in the collection.
     *
     * @param componentId wicket id for this repeater
     * @param images data for the images to be added
     */
    public LightboxImageRepeater( String componentId, Collection<LightboxImageData> images )
    {
        super(componentId);

        this.usePanels = false;
        this.count = 0;

        addImageCollection(images, null);
    }

    /**
     * Constructor.
     * <p/>
     * Repeater will render either LightboxImage components or
     * LightboxImagePanel components dependent upon usePanels,
     * and visibility of images will not be altered.
     *
     * @param componentId wicket id for this repeater
     * @param images data for the images to be added
     * @param usePanels whether repeater should use LightboxImagePanels
     */
    public LightboxImageRepeater( String componentId, Collection<LightboxImageData> images, boolean usePanels )
    {
        super(componentId);

        this.usePanels = usePanels;
        this.count = 0;

        addImageCollection(images, null);
    }

    /**
     * Constructor.
     * <p/>
     * Functions identically to other constructors, but will alter the
     * isHidden attribute of the provided images to match the collection
     * they were provided in.
     *
     * @param componentId wicket id for this repeater
     * @param displayed images which should be added and guaranteed visible
     * @param hidden images which should be added and guaranteed hidden
     * @param usePanels whether the repeater should use LightboxImagePanels
     */
    public LightboxImageRepeater( String componentId, Collection<LightboxImageData> displayed, Collection<LightboxImageData> hidden, boolean usePanels )
    {
        super(componentId);

        this.usePanels = usePanels;
        this.count = 0;

        addImageCollection(displayed, false);
        addImageCollection(hidden, true);
    }

    /**
     * Adds a component for each image defined in the provided collection.
     *
     * @param images all images which should receive a component on the page
     * @param hidden whether the components added should be visible or not
     */
    private void addImageCollection(Collection<LightboxImageData> images, Boolean hidden) {

        if (images == null) return;

        for (LightboxImageData imageData : images) {

            if (hidden != null)
                imageData.setHidden(hidden);

            add(getImageComponent(imageData));
        }
    }

    /**
     * Returns a wicket component corresponding to the configuration
     * of this repeater.  The component will be either a LightboxImage
     * or a LightboxImagePanel, depending upon how this repeater was
     * constructed.
     *
     * @param imageData data for the image the component will represent
     * @return the wicket component
     */
    private Component getImageComponent( LightboxImageData imageData )
    {
        // Ensure every component we return has a unique wicket:id...
        count = count + 1;

        if (usePanels)
            return new LightboxImagePanel(String.valueOf(count), new Model(imageData));

        return new LightboxImage(String.valueOf(count), new Model(imageData));
    }


}

