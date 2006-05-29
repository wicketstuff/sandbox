/*
 * $Id: DatabaseResourceStream.java 228 2005-08-09 08:23:35 +0000 (Tue, 09 Aug 2005) jonathanlocke $
 * $Revision: 228 $ $Date: 2005-08-09 08:23:35 +0000 (Tue, 09 Aug 2005) $
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
 * Interface for receiving notifications of significant events.
 *
 * @author Jonathan Locke
 */
public interface IDatabaseObjectLifecycle
{
	/**
	 * The object is about to be saved
	 */
	void onSave();
	
	/**
	 * The object is about to be updated
	 */
	void onUpdate();
}
