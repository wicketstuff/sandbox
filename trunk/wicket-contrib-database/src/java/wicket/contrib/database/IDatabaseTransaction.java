/*
 * $Id: DatabaseResourceStream.java 228 2005-08-09 08:23:35 +0000 (Tue, 09 Aug
 * 2005) jonathanlocke $ $Revision: 228 $ $Date: 2005-08-09 08:23:35 +0000 (Tue,
 * 09 Aug 2005) $
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
package wicket.contrib.database;

/**
 * Interface for code run as a database transaction
 * 
 * @author Jonathan Locke
 */
public interface IDatabaseTransaction
{
	/**
	 * Code to run in a transaction
	 * 
	 * @throws Exception
	 */
	void run() throws Exception;
}
