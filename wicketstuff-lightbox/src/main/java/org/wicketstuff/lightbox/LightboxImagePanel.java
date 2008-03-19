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

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.behavior.SimpleAttributeModifier;

/**
 * Simple wrapper which allows LightboxImages to be added
 * as a block element, for more styling flexibility.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 *
 */
public class LightboxImagePanel extends Panel {

    /**
     * Standard constructor.
     *
     * @param componentId the component id
     * @param model the component's model
     */
    public LightboxImagePanel(String componentId, IModel model)
    {
        super(componentId, model);

        add( new LightboxImage("image", model));

        LightboxImageData imageData = (LightboxImageData) model.getObject();
        if (imageData.getHidden())
        {
            add(new SimpleAttributeModifier("style", "display:none;"));
        }
    }
}
