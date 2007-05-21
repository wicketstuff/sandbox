/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.actions;


/**
 * The right to access the specified data object. In effect it allows you to know it
 * exist. This is mainly used for instantiation checks. The Access action must be implied by
 * every other action.
 * @author marrink
 */
public interface Access extends WaspAction
{

}
