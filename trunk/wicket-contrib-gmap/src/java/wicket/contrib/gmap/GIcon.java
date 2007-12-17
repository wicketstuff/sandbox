/*
 * $Id: GIcon.java 577 2006-02-12 20:46:53Z syca $
 * $Revision: 577 $
 * $Date: 2006-02-12 12:46:53 -0800 (Sun, 12 Feb 2006) $
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

/**
 * todo to be implemented
 *
 * @author Iulian-Corneliu COSTAN
 */
public class GIcon implements Serializable
{

    private String image;
    private GLatLng anchor;

    /**
     * Construct.
     * @param image
     * @param anchor
     */
    public GIcon(String image, GLatLng anchor)
    {
        this.image = image;
        this.anchor = anchor;
    }

    /**
     * @return
     */
    public String getImage()
    {
        return image;
    }

    /**
     * @return
     */
    public GLatLng getAnchor()
    {
        return anchor;
    }
}
