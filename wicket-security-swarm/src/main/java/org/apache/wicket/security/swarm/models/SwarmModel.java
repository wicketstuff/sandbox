/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.swarm.models;

import org.apache.wicket.Component;
import org.apache.wicket.security.hive.authorization.permissions.DataPermission;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.swarm.strategies.SwarmStrategy;

/**
 * A customized {@link ISecureModel} used to check {@link DataPermission}s. Note that
 * although the current implementation is required to work with {@link ISecureModel}s, it
 * is recommended to implement SwarmModel instead as it allows you more control to specify
 * a name for the permission. If you do use a regular ISecureModel we fall back to the
 * toString method.
 * @author marrink
 * @see SwarmStrategy#isModelAuthorized(ISecureModel, Component, org.apache.wicket.security.actions.WaspAction)
 */
public interface SwarmModel extends ISecureModel
{
	/**
	 * Returns a string identifying this model for security purposes. This id is used as
	 * the name of a {@link DataPermission} by
	 * {@link SwarmStrategy#isModelAuthorized(ISecureModel, wicket.Component, org.apache.wicket.security.actions.WaspAction)}
	 * And is therefore required to remain consistent or each invocation provided the same
	 * component is used.
	 * @param component the component requesting the id
	 * @return a non null id
	 */
	public String getSecurityId(Component component);
}
