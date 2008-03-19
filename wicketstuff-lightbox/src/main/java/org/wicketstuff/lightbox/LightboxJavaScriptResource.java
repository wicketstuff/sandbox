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
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.util.collections.MiniMap;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.template.PackagedTextTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Resource that provides the JavaScript file required by the Lightbox Behavior.
 * <p/>
 * The javascript file contains references to images which need to be inserted
 * at runtime.  This resource uses a text template to create the javascript
 * and stream it back upon request.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 *
 */
public class LightboxJavaScriptResource extends WebResource implements IStreamStringProvider {

    private static final String LOADING_GIF_PATH = "/resources/org.wicketstuff.lightbox.LightboxBehavior/resources/images/loading.gif";
    private static final String CLOSING_GIF_PATH = "/resources/org.wicketstuff.lightbox.LightboxBehavior/resources/images/closelabel.gif";
    private static final String LIGHTBOX_JS_TEMPLATE = "resources/js/lightbox.js.template";

    private String m_string;

    @Override
    public IResourceStream getResourceStream() {
        return new LazyStringResourceStream( this, "text/javascript");
    }

    /**
     * Generates the javascript required by the component by using a standard text template.
     *
     * @return the javascript for the lightbox behavior, in string form
     */
    private static String getJavaScript() {

        //  Calculate the urls for the images we need to insert into the template...
        ServletWebRequest servletWebRequest = (ServletWebRequest) RequestCycle.get().getRequest();
        HttpServletRequest request = servletWebRequest.getHttpServletRequest();
        String contextPath = request.getContextPath();
        String loading_url = contextPath + LOADING_GIF_PATH;
        String closing_url = contextPath + CLOSING_GIF_PATH;

        // Create the template's substitution map...
        MiniMap params = new MiniMap(2);
        params.put("loading-gif", loading_url);
        params.put("close-label-gif", closing_url);

        // Run the substitutions through the template, and return the result...
        PackagedTextTemplate template = new PackagedTextTemplate(LightboxBehavior.class, LIGHTBOX_JS_TEMPLATE);
        return template.asString(params);
    }

    /**
     * Returns the string to be streamed back to the client, which in this case
     * is the javascript.
     *
     * @return the string to be streamed
     */
    public String getStreamString() {

        if ( m_string == null )
            m_string = getJavaScript();

        return m_string;
    }
}