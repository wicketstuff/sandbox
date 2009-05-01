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

import org.wicketstuff.lightbox.streams.IStreamStringProvider;
import org.wicketstuff.lightbox.streams.LazyStringResourceStream;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.template.PackagedTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource that provides the CSS file required by the Lightbox Behavior.
 * <p/>
 * The CSS file contains references to images which need to be inserted
 * at runtime.  This resource uses a text template to create the CSS
 * and stream it back upon request.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 *
 */
public class LightboxCSSResource extends WebResource implements IStreamStringProvider {

    private static final ResourceReference BLANK_GIF = new ResourceReference(LightboxBehavior.class, "resources/images/blank.gif");
    private static final ResourceReference PREVLABEL_GIF = new ResourceReference(LightboxBehavior.class, "resources/images/prevlabel.gif");
    private static final ResourceReference NEXTLABEL_GIF = new ResourceReference(LightboxBehavior.class, "resources/images/nextlabel.gif");

    private String m_string;

    @Override
    public IResourceStream getResourceStream() {
         return new LazyStringResourceStream( this, "text/css");
    }

    /**
     * Creates the CSS content required by the lightbox component
     * from a template.
     *
     * @return the css content used by the lightbox component
     */
    private String getCSS() {

        // create a variable subsitution map...
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("blank", RequestCycle.get().urlFor(BLANK_GIF));
        params.put("prev-label", RequestCycle.get().urlFor(PREVLABEL_GIF));
        params.put("next-label", RequestCycle.get().urlFor(NEXTLABEL_GIF));

        // perform the substitution on our packaged template, and return the result...
        PackagedTextTemplate template = new PackagedTextTemplate(LightboxBehavior.class, "resources/css/lightbox.css.template");
		return template.asString(params);
    }

    /**
     * Returns the string to be streamed back to the client, which in
     * this case is the css content.
     *
     * @return the string to be streamed
     */
    public String getStreamString() {

        if (m_string == null)
            m_string = getCSS();

        return m_string;
    }
}
