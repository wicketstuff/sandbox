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
package wicket.addons;

import wicket.PageParameters;
import wicket.addons.utils.SignIn2Panel;

/**
 * @author Juergen Donnerstag
 */
public final class Login extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    /**
     * Constructor
     * @param parameters
      */
    public Login(final PageParameters parameters)
    {
        super(parameters, "Wicket-Addons: Login page");
        
        SignIn2Panel signInPanel = new SignIn2Panel("loginPanel")
        {
            public boolean signIn(final String username, final String password)
            {
                return getAddonSession().authenticate(username, password);
            }
        };
        
        add(signInPanel);
    }
}
