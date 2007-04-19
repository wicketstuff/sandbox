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
package wicket.contrib.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Command for selecting multiple results based on the given queryObject using the given
 * start row and number of rows to load.
 *
 * @author Eelco Hillenius
 */
public interface ISelectListAction extends Serializable
{
	/**
	 * Select and return objects based on the given query object, the start row and the
	 * number of rows to load.
	 * @param queryObject the query object
	 * @param startFromRow load from row
	 * @param numberOfRows the number of rows to load
	 * @return the result
	 */
	List execute(Object queryObject, int startFromRow, int numberOfRows);
}
