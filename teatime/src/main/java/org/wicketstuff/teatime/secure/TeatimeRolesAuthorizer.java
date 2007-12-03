package org.wicketstuff.teatime.secure;

import org.apache.wicket.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.wicketstuff.teatime.TeatimeSession;

public class TeatimeRolesAuthorizer implements IRoleCheckingStrategy
{
	public boolean hasAnyRole(Roles rollen)
	{
		return TeatimeSession.get().isAuthenticated();
	}
}
