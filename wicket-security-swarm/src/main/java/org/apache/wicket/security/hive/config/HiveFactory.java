/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.config;

import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.HiveMind;

/**
 * HiveFactory creates {@link Hive}s to be registered by the {@link HiveMind}.
 * For instance there could be Hives that read the system policy from a file, while others read it from a database.
 * @author marrink
 *
 */
public interface HiveFactory
{
	/**
	 * Creates a new Hive according to the configuration (if any) of this factory.
	 * @return the new Hive.
	 */
	public Hive createHive();
}
