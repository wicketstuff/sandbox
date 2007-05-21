/*
 * $Id: ISecurePage.java,v 1.1 2006/02/24 08:33:05 Marrink Exp $ $Revision: 1.1 $ $Date: 2006/02/24 08:33:05 $
 * ==================================================================== Copyright (c) 2005, Topicus B.V. All rights
 * reserved.
 */

package org.apache.wicket.security.components;

import org.apache.wicket.authorization.IAuthorizationStrategy;

/**
 * Tagging interface, could be used by an {@link IAuthorizationStrategy} to check if this
 * class is authorized for instantiation.
 * @author marrink
 */
public interface ISecurePage extends ISecureComponent
{

}
