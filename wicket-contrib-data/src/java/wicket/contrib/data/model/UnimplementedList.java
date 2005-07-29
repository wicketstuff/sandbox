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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List implementation that throws an IllegalStateException for each method that is not
 * supported.
 * @author Eelco Hillenius
 */
public abstract class UnimplementedList implements List, Serializable
{
	/** exception message. */
	private static final String MSG_NOT_SUPPORTED = "method not supported";

	/**
	 * Construct.
	 */
	public UnimplementedList()
	{
	}

	/**
	 * @see java.util.Collection#size()
	 */
	public int size()
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	public void clear()
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty()
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray()
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#get(int)
	 */
	public Object get(int index)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	public Object remove(int index)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Object element)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object o)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection c)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	public Iterator iterator()
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	public ListIterator listIterator()
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator listIterator(int index)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Object set(int index, Object element)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

	/**
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a)
	{
		throw new IllegalStateException(MSG_NOT_SUPPORTED);
	}

}
