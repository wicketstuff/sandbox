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

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;

/**
 * the right to enable a component. For {@link Link} this means click on it, for a
 * {@link Form} submit it, for a {@link TextField} write to it, etc. Enable must implie
 * {@link Render}
 * @author marrink
 * @see Action#ENABLE
 * @see Component#ENABLE
 */
public interface Enable extends WaspAction
{

}
