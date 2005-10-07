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

/**
 * @author Iulian-Corneliu COSTAN
 */
public abstract class JSComponent implements Serializable {

    private JSContainer container;

    JSContainer getContainer() {
        return container;
    }

    void setContainer(JSContainer container) {
        this.container = container;
    }

    protected abstract String toJavaScript();
}
