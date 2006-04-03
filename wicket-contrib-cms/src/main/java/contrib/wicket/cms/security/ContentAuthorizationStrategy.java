package contrib.wicket.cms.security;

import java.io.Serializable;

import contrib.wicket.cms.model.Content;

public interface ContentAuthorizationStrategy extends Serializable {
	public boolean hasReadAccess(Content content);

	public boolean hasWriteAccess(Content content);
}
