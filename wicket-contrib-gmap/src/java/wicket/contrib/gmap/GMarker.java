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

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GMarker extends Overlay {

    private GPoint point;
    private String message;

    public GMarker(GPoint point) {
        this.point = point;
    }

    public void setOnClickMessage(String message) {
        this.message = message;
    }

    protected String toJavaScript() {
        String marker = null;
        if (hasInfoWindow()) {
            marker = createMessageMarker();
        } else {
            marker = createSimpleMarker();
        }
        getContainer().define(marker);
        return getMarkerName() + "()";
    }

    private String createSimpleMarker() {
        return JSUtil.createFunction(getMarkerName(), "return new GMarker(" + point.toJavaScript() + ");");
    }

    private String createMessageMarker() {
        return JSUtil.createFunction(getMarkerName(),
                "var marker = new GMarker(" + point.toJavaScript() + ");" + "\n" +
                        getOnClickHandler() + "\n" +
                        "GEvent.addListener(marker, \"click\", onClick);" + "\n" +
                        "return marker;");
    }

    private String getOnClickHandler() {
        return JSUtil.createFunction("onClick", "marker.openInfoWindowHtml(\"" + JSUtil.escape(message) + "\");");
    }

    private String getMarkerName() {
        return "createMarker" + JSUtil.longitudeAsString(point) + JSUtil.latitudeAsString(point);
    }

    private boolean hasInfoWindow() {
        return message != null && !"".equals(message);
    }
}
