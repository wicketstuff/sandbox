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
package wicket.contrib.scriptaculous;

import wicket.Application;
import wicket.Component;
import wicket.IInitializer;
import wicket.Response;
import wicket.markup.html.PackageResource;
import wicket.markup.html.PackageResourceReference;
import wicket.util.resource.IResourceStream;

/**
 * Handles event requests using 'script.aculo.us'.
 * <p>
 * This class is mainly here to automatically add the javascript files you need.
 * As header contributions are done once per class, you can have multiple
 * instances/ subclasses without having duplicate header contributions.
 * </p>
 * 
 * @see <a href="http://script.aculo.us/">script.aculo.us</a>
 */
public abstract class ScriptaculousAjaxHandler extends AjaxHandler {

        public static ScriptaculousAjaxHandler newJavascriptBindingHandler() {
                return new ScriptaculousAjaxHandler() {

                        private static final long serialVersionUID = 1L;

                        /**
                         * @see wicket.contrib.scriptaculous.AjaxHandler#getResponse()
                         */
                        protected IResourceStream getResponse() {
                                return null;
                        }

                        /**
                         * @see wicket.behavior.IBehavior#onException(Component,
                         *      RuntimeException)
                         */
                        public void onException(Component component,
                                        RuntimeException exception) {
                        }

                };
        }

        /**
         * Let this handler print out the needed header contributions.
         * 
         * @param container
         */
        protected void renderHeadInitContribution(Response r) {
                // add our basic javascript needs to the header
                Application application = Application.get();
                addJsReference(r, new PackageResourceReference(application,
                                ScriptaculousAjaxHandler.class, "prototype.js"));
                addJsReference(r, new PackageResourceReference(application,
                                ScriptaculousAjaxHandler.class,
                                "scriptaculous.js"));
                addJsReference(r, new PackageResourceReference(application,
                                ScriptaculousAjaxHandler.class, "behavior.js"));
        }

        /**
         * @see AjaxHandler#getImplementationId()
         */
        protected final String getImplementationId() {
                return "ScriptaculousImpl";
        }
}
