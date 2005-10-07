/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.gmap;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GMap extends JSContainer {

    private GPoint center;
    private int zoomLevel;
    private Set<Overlay> overlays = new HashSet<Overlay>();
    private boolean hasTypeControl;
    private boolean hasSmallMapControl;

    private Set<String> functionDefinitions = new HashSet<String>();

    public GMap(GPoint center, int zoomLevel) {
        this.center = center;
        this.zoomLevel = zoomLevel;
    }

    public void addOverlay(Overlay overlay) {
        overlay.setContainer(this);
        overlays.add(overlay);
    }

    public void setHasTypeControl(boolean hasTypeControl) {
        this.hasTypeControl = hasTypeControl;
    }

    public void setHasSmallMapControl(boolean hasSmallMapControl) {
        this.hasSmallMapControl = hasSmallMapControl;
    }

    protected String toJavaScript() {
        // todo change hack
        String overlayDefinitions = overlayDefinitions();
        return gmapDefinition() + "\n" + functionDefinitions() + "\n" + overlayDefinitions;
    }

    protected void define(String functionDefinition) {
        functionDefinitions.add(functionDefinition);
    }

    private String overlayDefinitions() {
        StringBuffer buffer = new StringBuffer();
        for (Overlay overlay : overlays) {
            buffer.append("map.addOverlay(").append(overlay.toJavaScript()).append(");\n");
        }
        return buffer.toString();
    }

    private String functionDefinitions() {
        StringBuffer buffer = new StringBuffer();
        for (String function : functionDefinitions) {
            buffer.append(function).append("\n\n");
        }
        return buffer.toString();
    }

    private String gmapDefinition() {
        StringBuffer buffer = new StringBuffer("var map = new GMap(document.getElementById(\"map\"));\n");
        if (hasSmallMapControl) {
            buffer.append("map.addControl(new GSmallMapControl());\n");
        }
        if (hasTypeControl) {
            buffer.append("map.addControl(new GMapTypeControl());\n");
        }
        buffer.append("map.centerAndZoom(").append(center.toJavaScript()).append(", ").append(zoomLevel).append(");\n");
        return buffer.toString();
    }
}
