package org.wicketstuff.teatime.secure;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class TeatimeAuthorizationStrategy extends
		AnnotationsRoleAuthorizationStrategy implements
		IUnauthorizedComponentInstantiationListener {

	public TeatimeAuthorizationStrategy() {
		super(new TeatimeRolesAuthorizer());
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		if(component instanceof BookmarkablePageLink) {
			BookmarkablePageLink link = (BookmarkablePageLink) component;
			return isInstantiationAuthorized(link.getPageClass());
		}
		return super.isActionAuthorized(component, action);
	}
	public void onUnauthorizedInstantiation(Component component) {
		throw new RestartResponseAtInterceptPageException(SignInPage.class);
	}
}
