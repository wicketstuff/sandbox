/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author marrink
 *
 */
public final class EmptyIterator implements Iterator
{

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext()
	{
		return false;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public Object next()
	{
		throw new NoSuchElementException();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
}
