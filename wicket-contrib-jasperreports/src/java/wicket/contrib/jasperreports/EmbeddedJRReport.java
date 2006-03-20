/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.jasperreports;

import wicket.Component;
import wicket.IResourceListener;
import wicket.ResourceReference;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebComponent;

/**
 * Component for embedding a jasper report in a page. This component must be attached to either a frame- or an iframe
 * tag. If you don't want to embed the report, but have a link to it instead, use {@link ResourceReference}.
 *
 * @author Eelco Hillenius
 * @deprecated use {@link JRObject} instead.  Frames and IFrames have plugin issues.
 */
public final class EmbeddedJRReport extends WebComponent implements
    IResourceListener {
    /**
     * the report resource.
     */
    private final JRResource resource;

    /**
     * Construcxt.
     *
     * @param componentID component componentID
     * @param jrResource the jrResource
     */
    public EmbeddedJRReport(String componentID, JRResource jrResource) {
        super(componentID);
        this.resource = jrResource;
    }

    /**
     * @see Component#onComponentTag(ComponentTag)
     */
    protected void onComponentTag(ComponentTag tag) {
        if((!"frame".equalsIgnoreCase(tag.getName())) && (!"iframe".equalsIgnoreCase(tag.getName()))) {
            findMarkupStream().throwMarkupException("Component " + getId()
                + " must be applied to a tag of type 'frame' or 'iframe', not " + tag.toUserDebugString());
        }
        String url = urlFor(IResourceListener.INTERFACE);
        tag.put("src", getResponse().encodeURL(url));
        super.onComponentTag(tag);
    }

    /**
     * @see IResourceListener#onResourceRequested()
     */
    public void onResourceRequested() {
        resource.onResourceRequested();
	}
}