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
 */

package org.wicketstuff.lightbox.streams;

import org.apache.wicket.util.resource.AbstractStringResourceStream;

/**
 * ResourceStream that waits until the first time the data is being
 * accessed to retrieve its payload from a provider specified at
 * instantiation.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 * 
 */
public class LazyStringResourceStream extends AbstractStringResourceStream {

    private IStreamStringProvider m_provider;
    private String m_string;

    /**
     * Standard constructor.
     *
     * @param provider provides the string to be streamed, when requested
     * @param contentType sets the content type that the string is streamed as
     */
    public LazyStringResourceStream( IStreamStringProvider provider, String contentType)
    {
        super(contentType);
        m_provider = provider;
    }

    /**
     * Returns the string to be streamed.
     * <p/>
     * Upon first access, will retrieve string from provider.  String is then cached
     * and provider will not be queried for subsequent calls.
     *
     * @return the string to be streamed
     */
    protected String getString() {
        if (m_string == null)
            m_string = m_provider.getStreamString();

        return m_string;
    }
    
//    
//    
//    @Override
//    public long length() {
//        return getString().length();
//    }
}
