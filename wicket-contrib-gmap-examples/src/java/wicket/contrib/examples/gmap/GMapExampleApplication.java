/*
 * $Id: GMapExampleApplication.java 690 2006-04-19 22:49:53Z syca $
 * $Revision: 690 $
 * $Date: 2006-04-19 15:49:53 -0700 (Wed, 19 Apr 2006) $
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
package wicket.contrib.examples.gmap;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.time.Duration;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class GMapExampleApplication extends WebApplication
{

	
    /**
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init()
	{
        getResourceSettings().setResourcePollFrequency(Duration.seconds(10));
	}

    public Class getHomePage()
    {
        return HomePage.class;
    }
}
