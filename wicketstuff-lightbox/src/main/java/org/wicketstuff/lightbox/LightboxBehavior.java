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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * Behavior which encapsulates all the styles and javascript
 * files that are required in order for the lightbox component
 * to function.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 *
 */
public class LightboxBehavior extends AbstractBehavior implements IBehavior {

    private static final ResourceReference EFFECTS_JS = new JavascriptResourceReference(LightboxBehavior.class, "resources/js/effects.js");
    private static final ResourceReference PROTOTYPE_JS = new JavascriptResourceReference(LightboxBehavior.class, "resources/js/prototype.js");
    private static final ResourceReference SCRIPTACULOUS_JS = new JavascriptResourceReference(LightboxBehavior.class, "resources/js/scriptaculous.js?load=effects");

    private static final ResourceReference LIGHTBOX_JS = new JavascriptResourceReference( "lightbox.js" );
    private static final ResourceReference LIGHTBOX_CSS = new ResourceReference( "lightbox.css" );

    /**
     * Contributes the static and dynamic javascript and css files to the head.
     *
     */
    @Override
	public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);

        // include a link to our static javascripts...
		response.renderJavascriptReference(EFFECTS_JS);
        response.renderJavascriptReference(PROTOTYPE_JS);
        response.renderJavascriptReference(SCRIPTACULOUS_JS);

        // Now just need to render our dynamic resources, the lightbox css and js files.
        response.renderJavascriptReference(LIGHTBOX_JS);
        response.renderCSSReference(LIGHTBOX_CSS);
    }
}
