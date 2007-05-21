/*
 * $Id: JaasSecurityCheckKey.java,v 1.1 2006/02/24 08:33:05 Marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/24 08:33:05 $
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.checks;

import org.apache.wicket.MetaDataKey;

/**
 * The default way of attaching an {@link ISecurityCheck} to a component is through the
 * metadata, this is the key used to store it under. This only allows 1 check for each
 * component but you can always nest the checks.
 * @author marrink
 */
public class WaspKey extends MetaDataKey
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new key.
	 */
	public WaspKey()
	{
		super(ISecurityCheck.class);
	}

}
