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
package wicket.contrib.phonebook;

/**
 * 
 * @author igor
 *
 */
public class QueryParam {
	private int first;
	private int count;
	private String sort;
	private boolean sortAsc;
	
	public QueryParam(int first, int count) {
		this(first, count, null, true);
	}
	
	public QueryParam(int first, int count, String sort, boolean sortAsc) {
		this.first=first;
		this.count=count;
		this.sort=sort;
		this.sortAsc=sortAsc;
	}

	public int getCount() {
		return count;
	}

	public int getFirst() {
		return first;
	}

	public String getSort() {
		return sort;
	}

	public boolean isSortAsc() {
		return sortAsc;
	}
	
	public boolean hasSort() {
		return sort!=null;
	}
	
	
}
