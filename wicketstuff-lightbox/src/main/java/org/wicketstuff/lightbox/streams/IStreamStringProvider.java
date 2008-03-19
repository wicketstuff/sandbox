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

/**
 * Provides a simple interface for retrieving a stream string
 * at runtime.  This allows a resource to lazily provide its
 * stream data upon first actual call, rather than upon
 * instantiation. This is necessary for some resources which
 * require access to the RequestCycle to function properly.
 * <p/>
 * This file was created on February 27, 2008 by s.crissman.
 *
 */
public interface IStreamStringProvider {

    /**
     * Returns the string data that should be streamed.
     *
     * @return string to be streamed
     */
    public String getStreamString();
}
