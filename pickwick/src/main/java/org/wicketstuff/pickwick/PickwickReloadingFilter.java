package org.wicketstuff.pickwick;

import org.apache.wicket.application.ReloadingClassLoader;
import org.apache.wicket.protocol.http.ReloadingWicketFilter;

public class PickwickReloadingFilter extends ReloadingWicketFilter {
	static {
		ReloadingClassLoader.includePattern("org.wicketstuff.pickwick.PickwickApplication*");
		ReloadingClassLoader.includePattern("org.wicketstuff.pickwick.*.pages.*");
		ReloadingClassLoader.includePattern("org.wicketstuff.pickwick.*.panel.*");
	}
}
