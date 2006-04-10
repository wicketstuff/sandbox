/*
 * $Id$ $Revision:
 * 29 $ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.addons.utils;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juergen Donnerstag
 */
public abstract class AbstractDataList extends AbstractList implements Serializable
{
	private int size = -1;
	private List internalData = new ArrayList();
	protected int firstIndex;
	protected int lastIndex;
	protected int pageSize = 10;

	public Object get(int index)
	{
		if ((size < 0) || (index < firstIndex) || (index >= lastIndex))
		{
			loadData(index);
		}
		index -= firstIndex;
		return internalData.get(index);
	}

	public int size()
	{
		if (size < 0)
		{
			loadData(firstIndex);
		}
		return size;
	}

	private void loadData(final int index)
	{
		if (size < 0)
		{
			this.size = getInternalSize();
		}

		int fromPos = (index / pageSize) * pageSize;
		this.internalData = getInternalData(fromPos, pageSize);
		this.lastIndex = this.firstIndex + internalData.size();
	}

	public void clear()
	{
		size = -1;
		internalData.clear();
		firstIndex = 0;
		lastIndex = 0;
	}

	protected abstract int getInternalSize();

	protected abstract List getInternalData(final int fromPos, final int pageSize);
}
