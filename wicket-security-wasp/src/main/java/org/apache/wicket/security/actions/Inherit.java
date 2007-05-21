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
 * Used by containers to specify that all of there children inherit these actions. For
 * example if we specify that a form has these rights {@link Inherit}+{@link Render} it means all the
 * textboxes on the form will be atleast readonly. If we specify however the form has
 * Inherit (==Inherit+{@link Access}) it means none of the textboxes is going to show up (unless
 * we give them individually read rights) because all components should have atleast Render
 * rights before they can be displayed.
 * 
 * @author marrink
 */
public interface Inherit extends WaspAction
{

}
