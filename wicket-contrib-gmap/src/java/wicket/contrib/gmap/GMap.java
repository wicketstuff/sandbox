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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the main Maps API's GMap object.
 *
 * @author Iulian-Corneliu Costan
 */
public class GMap implements Serializable
{

    private GPoint center;
    private int zoomLevel;
    private List<Overlay> overlays = new ArrayList<Overlay>();
    private boolean typeControl;
    private boolean smallMapControl;

    /**
     * @param center    of the gmap
     * @param zoomLevel only values between 1 and 15 are allowed.
     */
    public GMap(GPoint center, int zoomLevel)
    {
        if (center == null)
        {
            throw new IllegalArgumentException("map's center point cannot be null");
        }
        if (zoomLevel < 0 || zoomLevel > 15)
        {
            throw new IllegalArgumentException("zoomLevel must be 1 < zoomLevel < 15 ");
        }
        this.center = center;
        this.zoomLevel = zoomLevel;
    }

    /**
     * Add a overlay to this map.
     *
     * @param overlay
     */
    public void addOverlay(Overlay overlay)
    {
        overlays.add(overlay);
    }

    /**
     * Get all overlays.
     *
     * @return overlays
     */
    public List<Overlay> getOverlays()
    {
        return overlays;
    }

    /**
     * Show/Hide the type control (right-up corner)
     *
     * @param typeControl
     */
    public void setTypeControl(boolean typeControl)
    {
        this.typeControl = typeControl;
    }

    /**
     * Show/Hide the small map control
     *
     * @param smallMapControl
     */
    public void setSmallMapControl(boolean smallMapControl)
    {
        this.smallMapControl = smallMapControl;
    }

    public boolean isTypeControl()
    {
        return typeControl;
    }

    public boolean isSmallMapControl()
    {
        return smallMapControl;
    }

    public GPoint getCenter()
    {
        return center;
    }

    public int getZoomLevel()
    {
        return zoomLevel;
    }
}
